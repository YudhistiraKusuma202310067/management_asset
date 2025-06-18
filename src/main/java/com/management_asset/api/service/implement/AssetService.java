package com.management_asset.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Asset;
import com.management_asset.api.repository.AssetRepository;
import com.management_asset.api.service.IAssetService;

@Service
public class AssetService implements IAssetService {

    private AssetRepository assetRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    @Override
    public Asset findById(Integer id){
        return assetRepository.findById(id).get();
    }

    @Override
    public Asset save(Asset model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}
