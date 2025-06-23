package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.Utils;
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
    public ResponseEntity<Object> getAsset() {
        try {
            List<AssetStatus> statusList = assetStatusService.findAll();
            if (statusList == null || statusList.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.OK, "No Asset Status Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", statusList);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching asset statuses", null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAssetById(@PathVariable Integer id) {
        try {
            AssetStatus status = assetStatusService.findById(id);
            if (status == null) {
                return Utils.generateResponseEntity(HttpStatus.OK, "Asset Status Not Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", status);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching asset status", null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody AssetStatus assetStatus) {
        try {
            AssetStatus saved = assetStatusService.save(assetStatus);
            return Utils.generateResponseEntity(HttpStatus.OK, "Data has been updated", saved);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving asset status", null);
        }
    }
}
