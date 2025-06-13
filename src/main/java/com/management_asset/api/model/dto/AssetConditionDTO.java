package com.management_asset.api.model.dto;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.management_asset.api.model.Asset;
import com.management_asset.api.model.Employee;
import com.management_asset.api.model.Parts;

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
    private String proof_of_damage;
    private String notes;
}
