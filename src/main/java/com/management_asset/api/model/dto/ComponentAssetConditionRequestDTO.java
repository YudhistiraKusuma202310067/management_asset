package com.management_asset.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComponentAssetConditionRequestDTO {
    private Integer parts;
    private Integer rate;
    private String proof_of_damage;
    private String notes;
}
