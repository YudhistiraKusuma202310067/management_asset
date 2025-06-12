package com.management_asset.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management_asset.api.model.AssetCondition;

@Repository
public interface AssetConditionRepository extends JpaRepository<AssetCondition, Integer> {

    
}
