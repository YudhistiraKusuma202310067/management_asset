package com.management_asset.api.service;

import java.util.Locale.Category;

import org.springframework.stereotype.Service;

import com.management_asset.api.service.generic.GenericService;

@Service
public interface CategoryService extends GenericService<Category, Integer> {
    
}
