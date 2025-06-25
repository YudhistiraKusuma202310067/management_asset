package com.management_asset.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management_asset.api.model.AssetCondition;

@Repository
public interface AssetConditionRepository extends JpaRepository<AssetCondition, Integer> {
    @Query("SELECT a FROM AssetCondition a WHERE a.asset.id = :assetId ORDER BY a.checking_date DESC")
    List<AssetCondition> findAssetConditionByAssetId(@Param("assetId") Integer assetId);
}
