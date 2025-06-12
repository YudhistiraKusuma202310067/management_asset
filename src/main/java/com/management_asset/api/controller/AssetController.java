package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.model.Asset;
import com.management_asset.api.service.IAssetService;

@RestController
@RequestMapping("api/asset")
public class AssetController {

    private IAssetService iAssetService;

    @Autowired
    public AssetController(IAssetService iAssetService) {
        this.iAssetService = iAssetService;
    }
    
    @GetMapping
    public List<Asset> getAllAsset() {
        return iAssetService.findAll();
    }

    @GetMapping("/{id}")
    public Asset getAssetById(@PathVariable Integer id){
        return iAssetService.findById(id);
    }

    }