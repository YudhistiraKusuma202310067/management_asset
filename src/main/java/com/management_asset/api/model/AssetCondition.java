package com.management_asset.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_tr_asset_condition")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "part_id", referencedColumnName = "id")
    private Parts parts;

    @ManyToOne
    @JoinColumn(name = "checker_id", referencedColumnName = "id")
    private Employee employee;

    private LocalDateTime checking_date;
    private Integer rate;
    private String proof_of_damage;
    private String notes;

}
