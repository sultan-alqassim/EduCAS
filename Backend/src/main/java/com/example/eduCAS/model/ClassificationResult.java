package com.example.eduCAS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "classification_results")
public class ClassificationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "document_id", nullable = false, unique = true)
    private Document document;

    @Column(nullable = false)
    private Integer confidence;

    @Column(nullable = false)
    private String primaryDomain;

    @Column(nullable = false)
    private String secondarySubject;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String keyTopics;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String recommendations;

    public ClassificationResult(){

    }

    public ClassificationResult(Document document, Integer confidence, String primaryDomain, String secondarySubject, String keyTopics, String summary, String recommendations) {
        this.document = document;
        this.confidence = confidence;
        this.primaryDomain = primaryDomain;
        this.secondarySubject = secondarySubject;
        this.keyTopics = keyTopics;
        this.summary = summary;
        this.recommendations = recommendations;
    }
}
