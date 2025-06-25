package com.management_asset.api.service.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Asset;
import com.management_asset.api.model.AssetCondition;
import com.management_asset.api.model.AssetStatus;
import com.management_asset.api.model.LoanStatusHistory;
import com.management_asset.api.model.Loaning;
import com.management_asset.api.model.Parts;
import com.management_asset.api.model.User;
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
    private final PartsRepository partsRepository;
    private final LoaningRepository loaningRepository;
    private final UserRepository userRepository;
    private AssetConditionRepository assetConditionRepository;

    @Autowired
    public AssetConditionService(AssetConditionRepository assetConditionRepository, PartsRepository partsRepository, AssetRepository assetRepository, AssetStatusRepository assetStatusRepository, LoaningRepository loaningRepository, LoanStatusProcessRepository loanStatusProcessRepository, LoanStatusHistoryRepository loanStatusHistoryRepository, UserRepository userRepository) {
        this.assetConditionRepository = assetConditionRepository;
        this.partsRepository = partsRepository;
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.loaningRepository = loaningRepository;
        this.loanStatusProcessRepository = loanStatusProcessRepository;
        this.loanStatusHistoryRepository = loanStatusHistoryRepository;
        this.userRepository = userRepository;
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

        User user = userRepository.findByRandomCode(saveAssetConditionDTO.getEmployee());

        Loaning loaning = loaningRepository.findById(loaningId).orElse(null);
        if (loaning == null) return null;
        Asset asset = loaning.getAsset();

        if (asset == null || user.getId() == null) return null;

        for (ComponentAssetConditionRequestDTO component : saveAssetConditionDTO.getComponents()) {
            Parts parts = partsRepository.findById(component.getParts()).orElse(null);
            if (parts == null) continue;

            AssetCondition assetCondition = new AssetCondition();
            assetCondition.setId(component.getId());
            assetCondition.setAsset(asset);
            assetCondition.setEmployee(user.getEmployee());
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
        loanStatusHistory.setApprover(user.getEmployee());
        loanStatusHistory.setLoanStatusProcess(loaning.getLoanStatusProcess());
        loanStatusHistory.setLoaning(loaning);
        
        loanStatusHistoryRepository.save(loanStatusHistory);

        return new AssetConditionResponseDTO(
            asset.getName(),
            user.getEmployee().getName(),
            partsList,
            LocalDateTime.now()
        );
    }

    public List<AssetCondition> findAssetConditionByAssetId(Integer assetId) {
        return assetConditionRepository.findAssetConditionByAssetId(assetId);
    }
}
