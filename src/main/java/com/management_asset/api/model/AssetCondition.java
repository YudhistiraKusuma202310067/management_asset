package com.management_asset.api.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_tr_asset_condition")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssetCondition {

    @OneToMany
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    private Asset asset;

    @OneToMany
    @JoinColumn(name = "part_id", referencedColumnName = "id")
    private Parts parts;

    @ManyToOne
    @JoinColumn(name = "checker_id", referencedColumnName = "id")
    private Employee employee;

    private Date checking_date;
    private String proof_of_damage;
    private String notes;

}
