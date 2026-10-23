package com.example.eduCAS.repository;

import com.example.eduCAS.model.Document;
import com.example.eduCAS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUser(User user);

}
