package com.churninsight.api.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ModelPredictionDTO {

    private int prediction;
    private Map<String, Double> probabilities;
}
