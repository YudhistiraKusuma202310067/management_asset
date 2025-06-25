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
import com.management_asset.api.model.Parts;
import com.management_asset.api.service.implement.PartsService;

@RestController
@RequestMapping("api/parts")
public class PartsController {
    private PartsService partsService;

    @Autowired
    public PartsController(PartsService partsService) {
        this.partsService = partsService;
    }

    @GetMapping
    public ResponseEntity<Object> getParts() {
        try {
            List<Parts> partsList = partsService.findAll();
            if (partsList == null || partsList.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.OK, "No Parts Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", partsList);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching parts", null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPartsById(@PathVariable Integer id) {
        try {
            Parts part = partsService.findById(id);
            if (part == null) {
                return Utils.generateResponseEntity(HttpStatus.OK, "Part Not Found", null);
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Data Found", part);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching part", null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Parts parts) {
        try {
            Parts saved = partsService.save(parts);
            return Utils.generateResponseEntity(HttpStatus.OK, "Data has been updated", saved);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while saving part", null);
        }
    }
}
