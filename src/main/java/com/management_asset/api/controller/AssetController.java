package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.Utils;
import com.management_asset.api.model.Asset;
import com.management_asset.api.model.dto.AssetDTO;
import com.management_asset.api.service.implement.AssetService;

@RestController
@RequestMapping("api/asset")
public class AssetController {

    private AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllAsset() {
        try {
            List<Asset> assets = assetService.findAll();
            if (assets == null || assets.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.OK, "No Assets Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", assets);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching assets", null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAssetById(@PathVariable Integer id) {
        try {
            Asset asset = assetService.findById(id);
            if (asset == null) {
                return Utils.generateResponseEntity(HttpStatus.OK, "Asset Not Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", asset);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching asset", null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveAsset(@RequestBody AssetDTO assetDTO) {
        try {
            Asset savedAsset = assetService.save(assetDTO);
            return Utils.generateResponseEntity(HttpStatus.OK, "Asset has been saved", savedAsset);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving asset", null);
        }
    }

    @GetMapping("/status/{assetStatusId}")
    public ResponseEntity<Object> getAssetByStatus(@PathVariable Integer assetStatusId) {
        try {
            List<Asset> assets = assetService.findAssetByStatus(assetStatusId);
            if (assets == null || assets.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.OK, "No Assets Found for the given status", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", assets);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while fetching assets by status", null);
        }
    }

    @GetMapping("/borrowed/{employeeId}")
    public ResponseEntity<Object> getBorrowedAssetByEmployeeId(@PathVariable Integer employeeId) {
        try {
            List<Asset> assets = assetService.findBorrowedAssetByEmployeeId(employeeId);
            if (assets == null || assets.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.OK, "No Borrowed Assets Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", assets);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while fetching borrowed assets", null);
        }
    }
}
