package com.management_asset.api.service;

import org.springframework.stereotype.Service;

import com.management_asset.api.model.AssetStatus;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface AssetStatusService extends GenericService<AssetStatus, Integer> {
    
}
