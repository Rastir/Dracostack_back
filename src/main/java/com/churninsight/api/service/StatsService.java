package com.churninsight.api.service;

import com.churninsight.api.dto.StatsItemDTO;
import com.churninsight.api.dto.StatsResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL =
            "http://168.197.48.239:8000";

    public StatsResponseDTO getStats(String type) {

        String url = BASE_URL + "/probability/" + type;

        try {
            Map<String, Object> response =
                    restTemplate.getForObject(url, Map.class);

            if (response == null) {
                return new StatsResponseDTO();
            }

            StatsResponseDTO dto = new StatsResponseDTO();

            // Obtener total_users
            if (response.get("total_users") != null) {
                dto.setTotalUsers(((Number) response.get("total_users")).intValue());
            }

            // Determinar la clave correcta según el tipo
            String groupKey = getGroupKey(type);

            List<Map<String, Object>> rawList =
                    (List<Map<String, Object>>) response.get(groupKey);

            if (rawList == null) {
                return dto;
            }

            List<StatsItemDTO> items = new ArrayList<>();

            // Determinar el campo de label según el tipo
            String labelField = getLabelField(type);

            for (Map<String, Object> item : rawList) {

                StatsItemDTO stat = new StatsItemDTO();

                // Obtener el label dinámicamente
                Object labelValue = item.get(labelField);
                stat.setLabel(labelValue != null ? labelValue.toString() : "Unknown");

                // Usar snake_case como viene del microservicio ML
                stat.setChurnProbability(
                        ((Number) item.get("churn_probability")).doubleValue()
                );
                stat.setNotChurnProbability(
                        ((Number) item.get("not_churn_probability")).doubleValue()
                );
                stat.setUsersCount(
                        ((Number) item.get("users_count")).intValue()
                );

                items.add(stat);
            }

            dto.setData(items);
            return dto;

        } catch (Exception e) {
            System.err.println("Error en StatsService: " + e.getMessage());
            e.printStackTrace();
            return new StatsResponseDTO();
        }
    }

    // Obtener la clave del grupo según el tipo
    private String getGroupKey(String type) {
        switch (type) {
            case "gender":
                return "grouped_by_gender";
            case "region":
                return "grouped_by_region";
            case "subscription":
                return "grouped_by_subscription_type";
            case "age":
                return "grouped_by_age";
            default:
                return "data";
        }
    }

    // Obtener el campo de label según el tipo
    private String getLabelField(String type) {
        switch (type) {
            case "gender":
                return "gender";
            case "region":
                return "region";
            case "subscription":
                return "subscription_type";
            case "age":
                return "age";
            default:
                return "label";
        }
    }
}