package com.management_asset.api.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetConditionResponseDTO {
    private String assetName;
    private String checkerName;
    private List<ComponentAssetConditionResponseDTO> parts;
    private LocalDateTime checking_date;
}
