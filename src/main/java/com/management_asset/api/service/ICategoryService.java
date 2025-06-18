package com.management_asset.api.service;

import org.springframework.stereotype.Service;

import com.management_asset.api.model.Category;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface ICategoryService extends GenericService<Category, Integer> {
    
}
