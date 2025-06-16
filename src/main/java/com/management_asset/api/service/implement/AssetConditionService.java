package com.management_asset.api.service.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Asset;
import com.management_asset.api.model.AssetCondition;
import com.management_asset.api.model.AssetStatus;
import com.management_asset.api.model.dto.AssetConditionDTO;
import com.management_asset.api.repository.AssetConditionRepository;
import com.management_asset.api.repository.AssetRepository;
import com.management_asset.api.repository.AssetStatusRepository;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.repository.PartsRepository;
import com.management_asset.api.service.IAssetConditionService;


@Service
public class AssetConditionService implements IAssetConditionService {

    private final AssetStatusRepository assetStatusRepository;

    private final AssetRepository assetRepository;

    private final EmployeeRepository employeeRepository;

    private final PartsRepository partsRepository;
    private AssetConditionRepository assetConditionRepository;

    @Autowired
    public AssetConditionService(AssetConditionRepository assetConditionRepository, PartsRepository partsRepository, EmployeeRepository employeeRepository, AssetRepository assetRepository, AssetStatusRepository assetStatusRepository) {
        this.assetConditionRepository = assetConditionRepository;
        this.partsRepository = partsRepository;
        this.employeeRepository = employeeRepository;
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
    }

    @Override
    public List<AssetCondition> findAll() {
        return assetConditionRepository.findAll();
    }

    @Override
    public AssetCondition findById(Integer id) {
        return assetConditionRepository.findById(id).orElse(null);
    }

    public List<AssetCondition> save(List<AssetConditionDTO> assetConditionDTO) {
        List<AssetCondition> savedConditions = new ArrayList<>();

        Integer totalRate = 0;
        Integer assetId = null;

        for (AssetConditionDTO dto : assetConditionDTO) {
            AssetCondition assetCondition = new AssetCondition();
            assetCondition.setId(dto.getId());
            assetCondition.setAsset(assetRepository.findById(dto.getAsset()).orElse(null));
            assetCondition.setParts(partsRepository.findById(dto.getParts()).orElse(null));
            assetCondition.setEmployee(employeeRepository.findById(dto.getEmployee()).orElse(null));
            assetCondition.setChecking_date(LocalDateTime.now());
            assetCondition.setRate(dto.getRate());
            assetCondition.setProof_of_damage(dto.getProof_of_damage());
            assetCondition.setNotes(dto.getNotes());

            totalRate += dto.getRate();
            assetId = dto.getAsset();
            
            savedConditions.add(assetConditionRepository.save(assetCondition));
        }

        // Calculate Rate Average
        double averageRate = totalRate / assetConditionDTO.size();

        // Update asset status
        Asset asset = assetRepository.findById(assetId).orElse(null);
        if (asset != null) {
            Integer newStatusId = (averageRate > 80) ? 1 : 3; // 1 = Available, 3 = Damaged
            AssetStatus newStatus = assetStatusRepository.findById(newStatusId).orElse(null);
            asset.setAssetStatus(newStatus);
            assetRepository.save(asset);
        }

        // Tinggal tambahin update status ke tabel loaning

        return savedConditions;
    }
}
