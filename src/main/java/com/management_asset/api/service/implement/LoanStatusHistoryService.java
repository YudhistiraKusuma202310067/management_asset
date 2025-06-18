package com.management_asset.api.service.implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Employee;
import com.management_asset.api.model.LoanStatusHistory;
import com.management_asset.api.model.dto.LoaningStatusHistoryResponseDTO;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.repository.LoanStatusHistoryRepository;
import com.management_asset.api.repository.LoanStatusProcessRepository;
import com.management_asset.api.repository.LoaningRepository;
import com.management_asset.api.service.ILoanStatusHistoryService;

@Service
public class LoanStatusHistoryService implements ILoanStatusHistoryService {

    private final EmployeeRepository employeeRepository;
    private final LoaningRepository loaningRepository;
    private final LoanStatusProcessRepository loanStatusProcessRepository;
    private final LoanStatusHistoryRepository loanStatusHistoryRepository;

    @Autowired
    public LoanStatusHistoryService(
            EmployeeRepository employeeRepository,
            LoaningRepository loaningRepository,
            LoanStatusProcessRepository loanStatusProcessRepository,
            LoanStatusHistoryRepository loanStatusHistoryRepository) {
        this.employeeRepository = employeeRepository;
        this.loaningRepository = loaningRepository;
        this.loanStatusProcessRepository = loanStatusProcessRepository;
        this.loanStatusHistoryRepository = loanStatusHistoryRepository;
    }

    @Override
    public List<LoanStatusHistory> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public LoanStatusHistory findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public List<LoaningStatusHistoryResponseDTO> findByLoaningId(Integer loaningId) {
        List<LoanStatusHistory> histories = loanStatusHistoryRepository.findByLoaningId(loaningId);

        return histories.stream()
                .map(history -> new LoaningStatusHistoryResponseDTO(
                        history.getLoanStatusProcess() != null ? history.getLoanStatusProcess().getLoaningStatus(): null,
                        history.getCreatedDate()))
                .collect(Collectors.toList());
    }

}
