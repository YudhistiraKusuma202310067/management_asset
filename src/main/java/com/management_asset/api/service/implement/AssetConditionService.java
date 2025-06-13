package com.management_asset.api.service.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.AssetCondition;
import com.management_asset.api.model.dto.AssetConditionDTO;
import com.management_asset.api.repository.AssetConditionRepository;
import com.management_asset.api.repository.AssetRepository;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.repository.PartsRepository;
import com.management_asset.api.service.IAssetConditionService;


@Service
public class AssetConditionService implements IAssetConditionService {

    private final AssetRepository assetRepository;

    private final EmployeeRepository employeeRepository;

    private final PartsRepository partsRepository;
    private AssetConditionRepository assetConditionRepository;

    @Autowired
    public AssetConditionService(AssetConditionRepository assetConditionRepository, PartsRepository partsRepository, EmployeeRepository employeeRepository, AssetRepository assetRepository) {
        this.assetConditionRepository = assetConditionRepository;
        this.partsRepository = partsRepository;
        this.employeeRepository = employeeRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public List<AssetCondition> findAll() {
        return assetConditionRepository.findAll();
    }

    @Override
    public AssetCondition findById(Integer id) {
        return assetConditionRepository.findById(id).orElse(null);
    }

    public AssetCondition save(AssetConditionDTO assetConditionDTO) {
        AssetCondition assetCondition = new AssetCondition();
        assetCondition.setId(assetConditionDTO.getId());
        assetCondition.setAsset(assetRepository.findById(assetConditionDTO.getAsset()).orElse(null));
        assetCondition.setParts(partsRepository.findById(assetConditionDTO.getParts()).orElse(null));
        assetCondition.setEmployee(employeeRepository.findById(assetConditionDTO.getEmployee()).orElse(null));
        assetCondition.setChecking_date(LocalDateTime.now());
        assetCondition.setProof_of_damage(assetConditionDTO.getProof_of_damage());
        assetCondition.setNotes(assetConditionDTO.getNotes());
        return assetConditionRepository.save(assetCondition);
    }
}
