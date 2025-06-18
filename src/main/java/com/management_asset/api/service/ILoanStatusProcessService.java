package com.management_asset.api.service;

import org.springframework.stereotype.Service;

import com.management_asset.api.model.LoanStatusProcess;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface ILoanStatusProcessService extends  GenericService <LoanStatusProcess, Integer> {
    
}
