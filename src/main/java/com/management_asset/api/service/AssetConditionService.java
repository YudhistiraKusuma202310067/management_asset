package com.management_asset.api.service;

import org.springframework.stereotype.Service;

import com.management_asset.api.model.AssetCondition;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface AssetConditionService extends GenericService<AssetCondition, Integer> {
    
}
