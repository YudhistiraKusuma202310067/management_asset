package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.model.AssetCondition;
import com.management_asset.api.model.dto.AssetConditionDTO;
import com.management_asset.api.service.implement.AssetConditionService;

@RestController
@RequestMapping("api/assetcondition")
public class AssetConditionController {
    private AssetConditionService assetConditionService;

    @Autowired
    public AssetConditionController(AssetConditionService assetConditionService) {
        this.assetConditionService = assetConditionService;
    }

    @GetMapping
    public List<AssetCondition> getAssetCondition() {
        return assetConditionService.findAll();
    }

    @GetMapping("/{id}")
    public AssetCondition getAssetConditionById(@PathVariable Integer id) {
        return assetConditionService.findById(id);
    }

    @PostMapping
    public AssetCondition save(@RequestBody AssetConditionDTO assetConditionDTO) {
        return assetConditionService.save(assetConditionDTO);
    }
}
