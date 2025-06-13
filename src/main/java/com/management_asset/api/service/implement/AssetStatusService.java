package com.management_asset.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.AssetStatus;
import com.management_asset.api.model.dto.AssetDTO;
import com.management_asset.api.repository.AssetStatusRepository;
import com.management_asset.api.service.IAssetStatusService;

@Service
public class AssetStatusService implements IAssetStatusService {
    public AssetStatusRepository assetStatusRepository;

    @Autowired
    public AssetStatusService(AssetStatusRepository assetStatusRepository) {
        this.assetStatusRepository = assetStatusRepository;
    }

    @Override
    public List<AssetStatus> findAll() {
        return assetStatusRepository.findAll();
    }

    @Override
    public AssetStatus findById(Integer id) {
        return assetStatusRepository.findById(id).orElse(null);
    }

    public AssetStatus save(AssetStatus assetStatus) {
        return assetStatusRepository.save(assetStatus);
    }
}
