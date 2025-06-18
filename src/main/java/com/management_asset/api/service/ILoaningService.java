package com.management_asset.api.service;

import org.springframework.stereotype.Service;

import com.management_asset.api.model.Loaning;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface ILoaningService extends GenericService<Loaning, Integer> {
    
}
