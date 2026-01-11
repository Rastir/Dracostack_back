package com.churninsight.api.controller;

import com.churninsight.api.dto.ModelDataDTO;
import com.churninsight.api.dto.ModelPredictionDTO;
import com.churninsight.api.dto.PredictionResponseDTO;
import com.churninsight.api.service.PredictionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/predict")
@CrossOrigin(origins = "*")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    //predicción manual
    @PostMapping
    public ModelPredictionDTO predict(@RequestBody ModelDataDTO request) {
        return predictionService.predict(request);
    }

    //predicción por id cliente
    @GetMapping("/client/{publicId}")
    public PredictionResponseDTO predictByPublicId(
            @PathVariable String publicId) {

        return predictionService.predictByPublicId(publicId);
    }
}
