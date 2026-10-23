package com.example.eduCAS.repository;


import com.example.eduCAS.model.ClassificationResult;
import com.example.eduCAS.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassificationResultRepository extends JpaRepository<ClassificationResult, Long> {

    Optional<ClassificationResult> findByDocument(Document document);
}
