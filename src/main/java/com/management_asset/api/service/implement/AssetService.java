package com.management_asset.api.service.implement;
import com.management_asset.api.repository.AssetStatusRepository;
import com.management_asset.api.repository.CategoryRepository;
import com.management_asset.api.service.IAssetService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Asset;
import com.management_asset.api.model.Category;
import com.management_asset.api.model.dto.AssetDTO;
import com.management_asset.api.repository.AssetRepository;

@Service
public class AssetService implements IAssetService {

    private final AssetStatusRepository assetStatusRepository;

    private final CategoryRepository categoryRepository;
    private AssetRepository assetRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository, CategoryRepository categoryRepository, AssetStatusRepository assetStatusRepository) {
        this.assetRepository = assetRepository;
        this.categoryRepository = categoryRepository;
        this.assetStatusRepository = assetStatusRepository;
    }

    @Override
    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    @Override
    public Asset findById(Integer id) {
        return assetRepository.findById(id).orElse(null);
    }

    public Asset save(AssetDTO assetDTO) {
        Asset asset = new Asset();
        asset.setId(assetDTO.getId());
        asset.setDescription(assetDTO.getDescription());
        asset.setName(assetDTO.getName());
        asset.setPath_photo_asset(assetDTO.getPath_photo_asset());
        // asset.setCategory(new Category(assetDTO.getCategory()));
        asset.setCategory(categoryRepository.findById(assetDTO.getCategory()).orElse(null));
        asset.setAssetStatus(assetStatusRepository.findById(assetDTO.getAssetStatus()).orElse(null));
        return assetRepository.save(asset);
    }
}
