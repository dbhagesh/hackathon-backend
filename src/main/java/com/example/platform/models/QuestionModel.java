package com.example.platform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@Builder
@Table(name = "question")
public class QuestionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String description;
    private String parameters;
    private String expectedOutput;
    private String level;
    private String functionTemplate;
}
