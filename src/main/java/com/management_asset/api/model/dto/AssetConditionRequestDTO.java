package com.management_asset.api.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetConditionRequestDTO {
    
    // private Integer asset;
    private String employee;
    private List<ComponentAssetConditionRequestDTO> components;
}
