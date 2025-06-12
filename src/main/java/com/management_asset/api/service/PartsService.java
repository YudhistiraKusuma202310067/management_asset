package com.management_asset.api.service;

import org.springframework.stereotype.Service;

import com.management_asset.api.model.Parts;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface PartsService extends GenericService<Parts, Integer> {
    
}
