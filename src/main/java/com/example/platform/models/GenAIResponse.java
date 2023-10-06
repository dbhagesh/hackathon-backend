package com.example.platform.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GenAIResponse {

    private String statusCode;
    private String message;
    private GenAIData data;
}

