package com.example.eduCAS.service;

import com.example.eduCAS.dto.gemini.ClassificationData;
import com.example.eduCAS.dto.gemini.GeminiResponse;
import com.example.eduCAS.model.Document;
import com.example.eduCAS.model.ClassificationResult;
import com.example.eduCAS.model.User;
import com.example.eduCAS.repository.ClassificationResultRepository;
import com.example.eduCAS.repository.DocumentRepository;
import com.example.eduCAS.dto.gemini.GeminiRequest;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import tools.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

@Service
public class AnalysisService {

    private final DocumentRepository documentRepository;
    private final ClassificationResultRepository classificationResultRepository;
    private final RestClient restClient;

    public AnalysisService(DocumentRepository documentRepository, ClassificationResultRepository classificationResultRepository, RestClient restClient) {
        this.documentRepository = documentRepository;
        this.classificationResultRepository = classificationResultRepository;
        this.restClient = restClient;
    }

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file.getBytes())){
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractTextFromDocx(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             XWPFDocument document = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            return extractor.getText();
        }
    }

    public String extractText(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();

        if(filename == null) {
            throw new RuntimeException("File has no name");
        }
        if(filename.toLowerCase().endsWith(".pdf")) {
            return extractTextFromPdf(file);
        } else if (filename.toLowerCase().endsWith(".docx")) {
            return extractTextFromDocx(file);
        } else {
            throw new RuntimeException("Unsupported file type, Only PDF and DOCX supported");
        }
    }

    private Map<String, Object> buildResponseSchema() {
        return Map.of(
                "type", "OBJECT",
                "properties", Map.of(
                        "primaryDomain", Map.of("type", "STRING"),
                        "secondarySubject", Map.of("type", "STRING"),
                        "keyTopics", Map.of("type", "STRING"),
                        "summary", Map.of("type", "STRING"),
                        "recommendations", Map.of("type", "STRING"),
                        "confidence", Map.of("type", "INTEGER")
                )
        );
    }

    private GeminiRequest buildGeminiRequest(String documentText) {
        String prompt = "You are analyzing an educational document. Based on the content below, "+
                "classify it and provide: the primary domain/field it belongs to, a more specific secondary subject, "+
                "a comma-separated list of key topics covered, a concise summary, learning recommendations for the reader, "+
                "and a confidence score from 0 to 100 for your classification.\\n\\n" +
                "Document content:\n" + documentText;

        GeminiRequest.Part part = new GeminiRequest.Part();
        part.setText(prompt);

        GeminiRequest.Content content = new GeminiRequest.Content();
        content.setParts(List.of(part));

        GeminiRequest.GenerationConfig config = new GeminiRequest.GenerationConfig();
        config.setResponseSchema(buildResponseSchema());

        GeminiRequest request = new GeminiRequest();
        request.setContents(List.of(content));
        request.setGenerationConfig(config);

        return request;
    }

    private String callGemini(String documentText) {
        GeminiRequest request = buildGeminiRequest(documentText);

        GeminiResponse response = restClient.post()
                .uri(geminiApiUrl)
                .header("x-goog-api-key", geminiApiKey)
                .body(request)
                .retrieve()
                .body(GeminiResponse.class);

        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ClassificationData parseGeminiResponse(String jsonText) {
        try {
            return objectMapper.readValue(jsonText, ClassificationData.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response" + e.getMessage());
        }
    }

    public Document analyzeDocument(MultipartFile file, User user) throws IOException {
        String extractedText = extractText(file);

        String geminiJsonText = callGemini(extractedText);
        ClassificationData data = parseGeminiResponse(geminiJsonText);

        String fileType = file.getOriginalFilename().toLowerCase().endsWith(".pdf") ? "pdf" : "docx";

        Document document = new Document(user, file.getOriginalFilename(), fileType, extractedText, LocalDateTime.now());
        documentRepository.save(document);

        ClassificationResult result = new ClassificationResult(
                document,
                data.getConfidence(),
                data.getPrimaryDomain(),
                data.getSecondarySubject(),
                data.getKeyTopics(),
                data.getSummary(),
                data.getRecommendations()
        );
        classificationResultRepository.save(result);
        return document;
    }
}
