package com.churninsight.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class PredictionResponseDTO {

    private int prediction;
    private double probability;
    private Map<String, Object> client;
}

