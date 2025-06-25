package com.management_asset.api.service.implement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Asset;
import com.management_asset.api.model.Employee;
import com.management_asset.api.model.LoanStatusHistory;
import com.management_asset.api.model.Loaning;
import com.management_asset.api.model.User;
import com.management_asset.api.model.dto.ApproveRequestDTO;
import com.management_asset.api.model.dto.LoaningRequestDTO;
import com.management_asset.api.model.dto.LoaningResponseDTO;
import com.management_asset.api.repository.AssetRepository;
import com.management_asset.api.repository.AssetStatusRepository;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.repository.LoanStatusHistoryRepository;
import com.management_asset.api.repository.LoanStatusProcessRepository;
import com.management_asset.api.repository.LoaningRepository;
import com.management_asset.api.repository.UserRepository;
import com.management_asset.api.service.ILoaningService;

@Service
public class LoaningService implements ILoaningService {
    private LoaningRepository loaningRepository;
    private LoanStatusProcessRepository loanStatusProcessRepository;
    private EmployeeRepository employeeRepository;
    private LoanStatusHistoryRepository loanStatusHistoryRepository;
    private AssetRepository assetRepository;
    private AssetStatusRepository assetStatusRepository;
    private UserRepository userRepository;

    @Autowired
    public LoaningService(
            LoaningRepository loaningRepository,
            EmployeeRepository employeeRepository,
            LoanStatusHistoryRepository loanStatusHistoryRepository,
            LoanStatusProcessRepository loanStatusProcessRepository,
            AssetRepository assetRepository,
            AssetStatusRepository assetStatusRepository,
            UserRepository userRepository) {
        this.loaningRepository = loaningRepository;
        this.employeeRepository = employeeRepository;
        this.loanStatusProcessRepository = loanStatusProcessRepository;
        this.loanStatusHistoryRepository = loanStatusHistoryRepository;
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.userRepository = userRepository;
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
        return loaningRepository.findForManager().stream()
                .map(loaning -> new LoaningResponseDTO(
                        loaning.getId(),
                        loaning.getLoanDate(),
                        loaning.getAsset() != null ? loaning.getAsset().getName() : null,
                        loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getLoaningStatus()
                                : null,
                        loaning.getEmployee() != null ? loaning.getEmployee().getName() : null,
                        loaning.getNote() != null ? loaning.getNote() : null))
                .collect(Collectors.toList());
    }

    public List<LoaningResponseDTO> findAllForApprover2() {
        return loaningRepository.findForProcurement().stream()
                .map(loaning -> new LoaningResponseDTO(
                        loaning.getId(),
                        loaning.getLoanDate(),
                        loaning.getAsset() != null ? loaning.getAsset().getName() : null,
                        loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getLoaningStatus()
                                : null,
                        loaning.getEmployee() != null ? loaning.getEmployee().getName() : null,
                        loaning.getNote() != null ? loaning.getNote() : null))
                .collect(Collectors.toList());
    }

    public List<LoaningResponseDTO> findAllForReturn() {
        return loaningRepository.findByLoanStatus(3).stream()
                .map(loaning -> new LoaningResponseDTO(
                        loaning.getId(),
                        loaning.getLoanDate(),
                        loaning.getAsset() != null ? loaning.getAsset().getName() : null,
                        loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getLoaningStatus()
                                : null,
                        loaning.getEmployee() != null ? loaning.getEmployee().getName() : null,
                        loaning.getNote() != null ? loaning.getNote() : null))
                .collect(Collectors.toList());
    }

    public List<LoaningResponseDTO> findAllForBorrower(String employeeRandomCode) {
        User user = userRepository.findByRandomCode(employeeRandomCode);
        return loaningRepository.findByEmployeeId(user.getEmployee().getId()).stream()
                .map(loaning -> new LoaningResponseDTO(
                        loaning.getId(),
                        loaning.getLoanDate(),
                        loaning.getAsset() != null ? loaning.getAsset().getName() : null,
                        loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getLoaningStatus()
                                : null,
                        loaning.getEmployee() != null ? loaning.getEmployee().getName() : null,
                        loaning.getNote() != null ? loaning.getNote() : null))
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
            User user = userRepository.findByRandomCode(loaningRequestDTO.getEmployee());
            loaning.setEmployee(employeeRepository.findById(user.getEmployee().getId()).orElse(null));
        }
        if (loaningRequestDTO.getAsset() != null) {
            loaning.setAsset(assetRepository.findById(loaningRequestDTO.getAsset()).orElse(null));
        }
        if (loaningRequestDTO.getLoanStatusProcess() == null) {
            loaning.setLoanStatusProcess(
                    loanStatusProcessRepository.findById(1).orElse(null)); // Default status is 1 (Pending)
        }

        loaningRepository.save(loaning);

        if (loaningRequestDTO.getAsset() != null) {
            Asset asset = assetRepository.findById(loaningRequestDTO.getAsset()).orElse(null);
            if (asset != null) {
                if (assetStatusRepository.findById(2).isPresent()) {
                    asset.setAssetStatus(assetStatusRepository.findById(2).get());
                }
                assetRepository.save(asset);
            }
        }

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
        Loaning loaning = loaningRepository.findById(approveRequestDTO.getId()).orElse(null);
        if (loaning == null)
            return false;

        Integer previousStatusId = loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getId()
                : null;

        loaning.setLoanStatusProcess(
                loanStatusProcessRepository.findById(approveRequestDTO.getLoanStatusProcess()).orElse(null));
        if (loaning.getLoanStatusProcess() == null)
            return false;

        LoanStatusHistory loanStatusHistory = new LoanStatusHistory();
        loanStatusHistory.setLoaning(loaning);
        loanStatusHistory.setCreatedDate(LocalDateTime.now());
        loanStatusHistory.setLoanStatusProcess(loaning.getLoanStatusProcess());

        Integer statusId = loaning.getLoanStatusProcess().getId();

        if (statusId == 2) {
            loanStatusHistory.setApprover(employeeRepository.findManagerByEmployeeId(loaning.getEmployee().getId()));
        } else if (statusId == 3) {
            Employee employee = userRepository.findByRandomCode(approveRequestDTO.getApprover()).getEmployee();
            loanStatusHistory.setApprover(employee);
        } else if (statusId == 5) {
            if (loaning.getAsset() != null) {
                Asset asset = assetRepository.findById(loaning.getAsset().getId()).orElse(null);
                if (asset != null) {
                    asset.setAssetStatus(assetStatusRepository.findById(1).get());
                    assetRepository.save(asset);
                }
            }
            if (previousStatusId != null && previousStatusId == 1) {
                loanStatusHistory.setApprover(
                        employeeRepository.findManagerByEmployeeId(loaning.getEmployee().getId()));
            } else if (previousStatusId != null && previousStatusId == 2) {
                Employee employee = userRepository.findByRandomCode(approveRequestDTO.getApprover()).getEmployee();
                loanStatusHistory.setApprover(employee);
            } else {
                return false;
            }
        } else {
            return false;
        }

        loanStatusHistoryRepository.save(loanStatusHistory);
        return true;
    }

}