package com.management_asset.api.service;

import org.springframework.stereotype.Service;

import com.management_asset.api.model.LoanStatusHistory;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface LoanStatusHistoryService extends GenericService <LoanStatusHistory,Integer> {
    
}
