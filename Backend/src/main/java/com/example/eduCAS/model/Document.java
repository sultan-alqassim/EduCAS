package com.example.eduCAS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime analyzedAt;

    public Document() {

    }

    public Document(User user, String fileName, String fileType, String content, LocalDateTime analyzedAt) {
        this.user = user;
        this.fileName = fileName;
        this.fileType = fileType;
        this.content = content;
        this.analyzedAt = analyzedAt;
    }
}
