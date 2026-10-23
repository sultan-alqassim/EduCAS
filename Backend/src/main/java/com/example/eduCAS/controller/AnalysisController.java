package com.example.eduCAS.controller;

import com.example.eduCAS.model.ClassificationResult;
import com.example.eduCAS.model.Document;
import com.example.eduCAS.model.User;
import com.example.eduCAS.repository.DocumentRepository;
import com.example.eduCAS.repository.UserRepository;
import com.example.eduCAS.repository.ClassificationResultRepository;
import com.example.eduCAS.service.AnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    private final UserRepository userRepository;
    private final AnalysisService analysisService;
    private final DocumentRepository documentRepository;
    private final ClassificationResultRepository classificationResultRepository;

    public AnalysisController(UserRepository userRepository, AnalysisService analysisService, DocumentRepository documentRepository, ClassificationResultRepository classificationResultRepository) {
        this.userRepository = userRepository;
        this.analysisService = analysisService;
        this.documentRepository = documentRepository;
        this.classificationResultRepository = classificationResultRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/upload")
    public ResponseEntity<Document> upload(@RequestParam("file")MultipartFile file) throws IOException{
        User currentUser = getCurrentUser();
        Document document = analysisService.analyzeDocument(file, currentUser);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Document>> history() {
        User currentUser = getCurrentUser();
        List<Document> documents = documentRepository.findByUser(currentUser);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/result/{documentId}")
    public ResponseEntity<ClassificationResult> result(@PathVariable Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(()-> new RuntimeException("Document not found"));

        User currentUser = getCurrentUser();
        if(!document.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You do not have access to this document");
        }

        ClassificationResult result = classificationResultRepository.findByDocument(document)
                .orElseThrow(()-> new RuntimeException("Result not found"));

        return ResponseEntity.ok(result);
    }

}
