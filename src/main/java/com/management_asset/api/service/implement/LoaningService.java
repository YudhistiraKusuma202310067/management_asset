package com.management_asset.api.service.implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.LoanStatusHistory;
import com.management_asset.api.model.Loaning;
import com.management_asset.api.model.dto.ApproveRequestDTO;
import com.management_asset.api.model.dto.LoaningRequestDTO;
import com.management_asset.api.model.dto.LoaningResponseDTO;
import com.management_asset.api.repository.AssetRepository;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.repository.LoanStatusHistoryRepository;
import com.management_asset.api.repository.LoanStatusProcessRepository;
import com.management_asset.api.repository.LoaningRepository;
import com.management_asset.api.service.ILoaningService;

@Service
public class LoaningService implements ILoaningService {
    private LoaningRepository loaningRepository;
    private LoanStatusProcessRepository loanStatusProcessRepository;
    private EmployeeRepository employeeRepository;
    private LoanStatusHistoryRepository loanStatusHistoryRepository;
    private AssetRepository assetRepository;

    @Autowired
    public LoaningService(
            LoaningRepository loaningRepository,
            EmployeeRepository employeeRepository,
            LoanStatusHistoryRepository loanStatusHistoryRepository,
            LoanStatusProcessRepository loanStatusProcessRepository,
            AssetRepository assetRepository) {
        this.loaningRepository = loaningRepository;
        this.employeeRepository = employeeRepository;
        this.loanStatusProcessRepository = loanStatusProcessRepository;
        this.loanStatusHistoryRepository = loanStatusHistoryRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public List<Loaning> findAll() {
        return loaningRepository.findAll();
    }

    @Override
    public Loaning findById(Integer id) {
        return loaningRepository.findById(id).orElse(null);
    }

    public List<LoaningResponseDTO> findAllForApprover1() {
        return loaningRepository.findByLoanStatus(1).stream()
        .map(loaning -> new LoaningResponseDTO(
                loaning.getLoanDate(),
                loaning.getAsset() != null ? loaning.getAsset().getName() : null,
                loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getLoaningStatus() : null
            ))
            .collect(Collectors.toList());
    }

    public List<LoaningResponseDTO> findAllForApprover2() {
        return loaningRepository.findByLoanStatus(2).stream()
        .map(loaning -> new LoaningResponseDTO(
                loaning.getLoanDate(),
                loaning.getAsset() != null ? loaning.getAsset().getName() : null,
                loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getLoaningStatus() : null
            ))
            .collect(Collectors.toList());
    }

   public List<LoaningResponseDTO> findAllForBorrower(Integer employeeId) {
    return loaningRepository.findByEmployeeId(employeeId).stream()
            .map(loaning -> new LoaningResponseDTO(
                loaning.getLoanDate(),
                loaning.getAsset() != null ? loaning.getAsset().getName() : null,
                loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getLoaningStatus() : null
            ))
            .collect(Collectors.toList());
}

    public Boolean save(LoaningRequestDTO loaningRequestDTO) {

        Loaning loaning;
        if (loaningRequestDTO.getId() != null) {
            loaning = loaningRepository.findById(loaningRequestDTO.getId()).orElse(new Loaning());
        } else {
            loaning = new Loaning();
            loaning.setLoanDate(LocalDateTime.now());
        }
        if (loaningRequestDTO.getNote() != null) {
            loaning.setNote(loaningRequestDTO.getNote());
        }
        if (loaningRequestDTO.getEmployee() != null) {
            loaning.setEmployee(employeeRepository.findById(loaningRequestDTO.getEmployee()).orElse(null));
        }
        if (loaningRequestDTO.getAsset() != null) {
            loaning.setAsset(assetRepository.findById(loaningRequestDTO.getAsset()).orElse(null));
        }
        if (loaningRequestDTO.getLoanStatusProcess() != null) {
            loaning.setLoanStatusProcess(
                    loanStatusProcessRepository.findById(loaningRequestDTO.getLoanStatusProcess()).orElse(null));
        }

        loaning = loaningRepository.save(loaning);

        LoanStatusHistory loanStatusHistory = new LoanStatusHistory();
        loanStatusHistory.setLoaning(loaning);
        loanStatusHistory.setCreatedDate(LocalDateTime.now());

        if (loaning.getLoanStatusProcess() != null) {
            loanStatusHistory.setLoanStatusProcess(loaning.getLoanStatusProcess());
            Integer statusId = loaning.getLoanStatusProcess().getId();

            if (statusId == 1) {
                loanStatusHistory.setApprover(null);
            } else {
                return false;
            }
        }

        loanStatusHistoryRepository.save(loanStatusHistory);
        return true;
    }

    public Boolean approveLoaning(ApproveRequestDTO approveRequestDTO) {
            LoanStatusHistory loanStatusHistory = new LoanStatusHistory();
            approveRequestDTO.getId();
            Loaning loaning = loaningRepository.findById(approveRequestDTO.getId()).orElse(null);
            if (loaning == null) {
               return false;
            }
            loaning.setLoanStatusProcess(loanStatusProcessRepository.findById(approveRequestDTO.getLoanStatusProcess()).orElse(null));
            loanStatusHistory.setLoaning(loaning);
            loanStatusHistory.setCreatedDate(LocalDateTime.now());
            if (loaning.getLoanStatusProcess() != null) {
                loanStatusHistory.setLoanStatusProcess(loaning.getLoanStatusProcess());
                Integer statusId = loaning.getLoanStatusProcess().getId();

                if (statusId == 2) {
                    loanStatusHistory
                            .setApprover(employeeRepository.findManagerByEmployeeId(approveRequestDTO.getApprover()));
                } else if (statusId == 3) {
                    loanStatusHistory
                            .setApprover(employeeRepository.findById(approveRequestDTO.getApprover()).orElse(null));
                }
            }
            loanStatusHistoryRepository.save(loanStatusHistory);
            return true;
    }
}