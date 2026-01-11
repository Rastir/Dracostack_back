package com.churninsight.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResponseDTO {

    private int prediction;
    private double probability;
    private Map<String, Object> client;
}

