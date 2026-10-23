package com.example.eduCAS.dto.gemini;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassificationData {

    private String primaryDomain;
    private String secondarySubject;
    private String keyTopics;
    private String summary;
    private String recommendations;
    private Integer confidence;
}
