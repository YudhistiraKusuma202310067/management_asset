package com.management_asset.api.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.LoanStatusProcess;
import com.management_asset.api.repository.LoanStatusProcessRepository;
import com.management_asset.api.service.ILoanStatusProcessService;

@Service
public class LoanStatusProcessService implements ILoanStatusProcessService {

    private final LoanStatusProcessRepository loanStatusProcessRepository;

    @Autowired
    public LoanStatusProcessService(LoanStatusProcessRepository loanStatusProcessRepository) {
        this.loanStatusProcessRepository = loanStatusProcessRepository;
    }

    @Override
    public List<LoanStatusProcess> findAll() {
        return loanStatusProcessRepository.findAll();
    }

    @Override
    public LoanStatusProcess findById(Integer id) {
        return loanStatusProcessRepository.findById(id).orElse(null);
    }

    public LoanStatusProcess save(LoanStatusProcess model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
    
}
