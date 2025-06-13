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

import com.management_asset.api.model.AssetStatus;
import com.management_asset.api.service.implement.AssetStatusService;

@RestController
@RequestMapping("/api/assetstatus")
public class AssetStatusController {
    private AssetStatusService assetStatusService;

    @Autowired
    public AssetStatusController(AssetStatusService assetStatusService) {
        this.assetStatusService = assetStatusService;
    }

    @GetMapping
    public List<AssetStatus> getAsset() {
        return assetStatusService.findAll();
    }

    @GetMapping("/{id}")
    public AssetStatus getAssetById(@PathVariable Integer id) {
        return assetStatusService.findById(id);
    }

    @PostMapping
    public AssetStatus save(@RequestBody AssetStatus assetStatus) {
        return assetStatusService.save(assetStatus);
    }
}
