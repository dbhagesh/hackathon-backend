package com.example.platform.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GenAIData {

    private String response;
    private String cost;
    private String tokens;
}
