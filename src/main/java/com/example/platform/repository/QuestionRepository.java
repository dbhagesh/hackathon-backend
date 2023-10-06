package com.example.platform.repository;

import com.example.platform.models.MessageModel;
import com.example.platform.models.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionRepository
        extends JpaRepository<QuestionModel, UUID> {
}