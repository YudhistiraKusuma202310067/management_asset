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
    public List<Parts> getParts() {
        return partsService.findAll();
    }

    @GetMapping("/{id}")
    public Parts getPartsById(@PathVariable Integer id) {
        return partsService.findById(id);
    }
    
    @PostMapping
    public Parts save(@RequestBody Parts parts) {
        return partsService.save(parts);
    }
}
