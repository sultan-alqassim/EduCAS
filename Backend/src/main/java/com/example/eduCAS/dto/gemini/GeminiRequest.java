package com.example.eduCAS.dto.gemini;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GeminiRequest {

    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Getter
    @Setter
    public static class Content{
        private List<Part> parts;
    }

    @Getter
    @Setter
    public static class Part {
        private String text;
    }

    @Getter
    @Setter
    public static class GenerationConfig{
        private String responseMimeType = "application/json";
        private Map<String, Object> responseSchema;
    }
}
