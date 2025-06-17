package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.Utils;
import com.management_asset.api.model.dto.LoaningStatusHistoryResponseDTO;
import com.management_asset.api.service.implement.LoanStatusHistoryService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("api/loan-status-history")
public class LoanStatusHistoryController {
    private LoanStatusHistoryService loanStatusHistoryService;

    @Autowired
    public LoanStatusHistoryController(LoanStatusHistoryService loanStatusHistoryService) {
        this.loanStatusHistoryService = loanStatusHistoryService;
    }

    @GetMapping("{loaning_id}")
    public org.springframework.http.ResponseEntity<Object> gethistory(@PathVariable Integer loaning_id) {
        try {
            List<LoaningStatusHistoryResponseDTO> data = loanStatusHistoryService.findByLoaningId(loaning_id);
            if (data.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "No loan status history found for this loaning ID");
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Success get data", data);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Data not found");
        }
    }
    
    
    
}
