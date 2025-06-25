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
import com.management_asset.api.model.AssetCondition;
import com.management_asset.api.model.dto.AssetConditionRequestDTO;
import com.management_asset.api.model.dto.AssetConditionResponseDTO;
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
    public ResponseEntity<Object> getAssetCondition() {
        try {
            List<AssetCondition> conditions = assetConditionService.findAll();
            if (conditions == null || conditions.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.OK, "No Asset Conditions Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", conditions);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching asset conditions", null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAssetConditionById(@PathVariable Integer id) {
        try {
            AssetCondition condition = assetConditionService.findById(id);
            if (condition == null) {
                return Utils.generateResponseEntity(HttpStatus.OK, "Asset Condition Not Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", condition);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching asset condition", null);
        }
    }

    @PostMapping("/{loaningId}")
    public ResponseEntity<Object> save(@RequestBody AssetConditionRequestDTO assetConditionDTO, @PathVariable Integer loaningId) {
        try {
            AssetConditionResponseDTO saved = assetConditionService.save(assetConditionDTO, loaningId);
            return Utils.generateResponseEntity(HttpStatus.OK, "Data has been updated", saved);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving asset conditions", null);
        }
    }

    @GetMapping("/asset/{id}")
    public ResponseEntity<Object> getAssetConditionByAssetId(@PathVariable Integer id) {
        try {
            List<AssetCondition> condition = assetConditionService.findAssetConditionByAssetId(id);
            if (condition == null) {
                return Utils.generateResponseEntity(HttpStatus.OK, "Asset Condition Not Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", condition);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching asset condition", null);
        }
    }
}
