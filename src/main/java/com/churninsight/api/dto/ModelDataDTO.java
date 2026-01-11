package com.churninsight.api.dto;

import lombok.Data;

@Data
public class ModelDataDTO {
    private Integer age;
    private String gender;
    private String subscription_type;
    private Double watch_hours;
    private Integer last_login_days;
    private String region;
}
