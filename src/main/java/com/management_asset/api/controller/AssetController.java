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

import com.management_asset.api.model.Asset;
import com.management_asset.api.model.dto.AssetDTO;
import com.management_asset.api.service.implement.AssetService;

@RestController
@RequestMapping("api/asset")
public class AssetController {
    private AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService){
        this.assetService = assetService;
    }

    @GetMapping
    public List<Asset> getAllAsset() {
        return assetService.findAll();
    }

    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable Integer id) {
        return assetService.findById(id);
    }   

    @PostMapping
    public Asset saveAsset(@RequestBody AssetDTO assetDTO) {
        return assetService.save(assetDTO);
    }

    @GetMapping("/assetByStatus")
    public List<Asset> getAssetByStatus(@RequestParam("assetStatusId") Integer assetStatusId) {
        return assetService.findAssetByStatus(assetStatusId);
    }

    @GetMapping("/borrowed")
    public List<Asset> getBorrowedAssetByEmployeeId(
        @RequestParam("employeeId") Integer employeeId) {
        return assetService.findBorrowedAssetByEmployeeId(employeeId);
    }
}
