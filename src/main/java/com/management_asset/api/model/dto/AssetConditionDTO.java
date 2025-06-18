package com.management_asset.api.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetConditionDTO {
    private Integer id;
    private Integer asset;
    private Integer parts;
    private Integer employee;
    private Date checking_date;
    private Integer rate;
    private String proof_of_damage;
    private String notes;
}
