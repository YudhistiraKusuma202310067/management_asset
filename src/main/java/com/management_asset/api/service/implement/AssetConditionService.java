package com.management_asset.api.service.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Asset;
import com.management_asset.api.model.AssetCondition;
import com.management_asset.api.model.AssetStatus;
import com.management_asset.api.model.Employee;
import com.management_asset.api.model.LoanStatusHistory;
import com.management_asset.api.model.Loaning;
import com.management_asset.api.model.Parts;
import com.management_asset.api.model.dto.AssetConditionRequestDTO;
import com.management_asset.api.model.dto.AssetConditionResponseDTO;
import com.management_asset.api.model.dto.ComponentAssetConditionRequestDTO;
import com.management_asset.api.model.dto.ComponentAssetConditionResponseDTO;
import com.management_asset.api.repository.*;
import com.management_asset.api.service.IAssetConditionService;


@Service
public class AssetConditionService implements IAssetConditionService {

    private final LoanStatusHistoryRepository loanStatusHistoryRepository;

    private final LoanStatusProcessRepository loanStatusProcessRepository;

    private final AssetStatusRepository assetStatusRepository;

    private final AssetRepository assetRepository;

    private final EmployeeRepository employeeRepository;

    private final PartsRepository partsRepository;

    private final LoaningRepository loaningRepository;
    private AssetConditionRepository assetConditionRepository;

    @Autowired
    public AssetConditionService(AssetConditionRepository assetConditionRepository, PartsRepository partsRepository, EmployeeRepository employeeRepository, AssetRepository assetRepository, AssetStatusRepository assetStatusRepository, LoaningRepository loaningRepository, LoanStatusProcessRepository loanStatusProcessRepository, LoanStatusHistoryRepository loanStatusHistoryRepository) {
        this.assetConditionRepository = assetConditionRepository;
        this.partsRepository = partsRepository;
        this.employeeRepository = employeeRepository;
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.loaningRepository = loaningRepository;
        this.loanStatusProcessRepository = loanStatusProcessRepository;
        this.loanStatusHistoryRepository = loanStatusHistoryRepository;
    }

    @Override
    public List<AssetCondition> findAll() {
        return assetConditionRepository.findAll();
    }

    @Override
    public AssetCondition findById(Integer id) {
        return assetConditionRepository.findById(id).orElse(null);
    }

    public AssetConditionResponseDTO save(AssetConditionRequestDTO saveAssetConditionDTO, Integer loaningId) {
        List<ComponentAssetConditionResponseDTO> partsList = new ArrayList<>();
        int totalRate = 0;

        // Asset asset = assetRepository.findById(saveAssetConditionDTO.getAsset()).orElse(null);
        Employee employee = employeeRepository.findById(saveAssetConditionDTO.getEmployee()).orElse(null);

        Loaning loaning = loaningRepository.findById(loaningId).orElse(null);
        if (loaning == null) return null;
        Asset asset = loaning.getAsset();

        if (asset == null || employee == null) return null;

        for (ComponentAssetConditionRequestDTO component : saveAssetConditionDTO.getComponents()) {
            Parts parts = partsRepository.findById(component.getParts()).orElse(null);
            if (parts == null) continue;

            AssetCondition assetCondition = new AssetCondition();
            assetCondition.setId(component.getId());
            assetCondition.setAsset(asset);
            assetCondition.setEmployee(employee);
            assetCondition.setParts(parts);
            assetCondition.setChecking_date(LocalDateTime.now());
            assetCondition.setRate(component.getRate());
            assetCondition.setProof_of_damage(component.getProof_of_damage());
            assetCondition.setNotes(component.getNotes());

            totalRate += component.getRate();
            assetConditionRepository.save(assetCondition);

            partsList.add(new ComponentAssetConditionResponseDTO(parts.getId(), parts.getName()));
        }

        // Rata-rata dan update status asset
        double averageRate = totalRate / (double) saveAssetConditionDTO.getComponents().size();
        Integer newStatusId = (averageRate > 80) ? 1 : 3; // 1 = Available, 3 = Damaged
        AssetStatus newStatus = assetStatusRepository.findById(newStatusId).orElse(null);
        asset.setAssetStatus(newStatus);
        assetRepository.save(asset);

        // Update Loaning Status
        loaning.setReturnDate(LocalDateTime.now());
        loaning.setLoanStatusProcess(loanStatusProcessRepository.findById(4).orElse(null));

        loaningRepository.save(loaning);

        // Save History Asset Returned
        LoanStatusHistory loanStatusHistory = new LoanStatusHistory();
        loanStatusHistory.setCreatedDate(LocalDateTime.now());
        loanStatusHistory.setApprover(employee);
        loanStatusHistory.setLoanStatusProcess(loaning.getLoanStatusProcess());
        loanStatusHistory.setLoaning(loaning);
        
        loanStatusHistoryRepository.save(loanStatusHistory);

        return new AssetConditionResponseDTO(
            asset.getName(),
            employee.getName(),
            partsList,
            LocalDateTime.now()
        );
    }
}
