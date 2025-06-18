package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.model.LoanStatusProcess;
import com.management_asset.api.service.ILoanStatusProcessService;

@RestController
@RequestMapping("api/loan-status-process")
public class LoanStatusProcessController {

    private final ILoanStatusProcessService loanStatusProcessService;

    @Autowired
    public LoanStatusProcessController(ILoanStatusProcessService loanStatusProcessService) {
        this.loanStatusProcessService = loanStatusProcessService;
    }

    @GetMapping
    public List<LoanStatusProcess> getAllLoanStatusProcesses() {
        return loanStatusProcessService.findAll();
    }

    @GetMapping("{id}")
    public LoanStatusProcess getLoanStatusProcessById(@PathVariable Integer id) {
        return loanStatusProcessService.findById(id);
}

}
