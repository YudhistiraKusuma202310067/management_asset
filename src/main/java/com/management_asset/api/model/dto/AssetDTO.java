package com.management_asset.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetDTO {
    private Integer id;
    private String name;
    private String description;
    private String path_photo_asset;
    private Integer category;
    private Integer assetStatus;
}
