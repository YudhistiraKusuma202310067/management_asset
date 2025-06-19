package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.Utils;
import com.management_asset.api.model.Loaning;
import com.management_asset.api.model.dto.ApproveRequestDTO;
import com.management_asset.api.model.dto.LoaningRequestDTO;
import com.management_asset.api.model.dto.LoaningResponseDTO;
import com.management_asset.api.service.implement.LoaningService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/loaning")
public class LoaningController {
    private LoaningService loaningService;

    @Autowired
    public LoaningController(LoaningService loaningService) {
        this.loaningService = loaningService;
    }

    @GetMapping("borrower/{employeeId}")
    public ResponseEntity<Object> getAllLoaningForBorrower(@PathVariable Integer employeeId) {
        try {
            List<LoaningResponseDTO> data = loaningService.findAllForBorrower(employeeId);
            if (data.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "No loaning data found for this borrower");
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Success get data", data);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error retrieving loaning data: " + e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getLoaningById(@PathVariable Integer id) {
        try {
            Loaning loaning = loaningService.findById(id);
            if (loaning == null) {
                return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "No loaning data found");
            }
            LoaningResponseDTO data = new LoaningResponseDTO(
                    loaning.getId(),
                    loaning.getLoanDate(),
                    loaning.getAsset() != null ? loaning.getAsset().getName() : null,
                    loaning.getLoanStatusProcess() != null ? loaning.getLoanStatusProcess().getLoaningStatus() : null);
            return Utils.generateResponseEntity(HttpStatus.OK, "Success get data", data);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error retrieving loaning: " + e.getMessage());
        }
    }

    @PostMapping("approver")
    public ResponseEntity<Object> approveLoaning(@RequestBody ApproveRequestDTO approveRequestDTO) {
        try {
            loaningService.approveLoaning(approveRequestDTO);
            return Utils.generateResponseEntity(HttpStatus.OK, "Loaning approved successfully");
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error approving loaning request: " + e.getMessage());
        }
    }

    @GetMapping("approver-1")
    public ResponseEntity<Object> getAllLoaningForApprover1() {
        try {
            List<LoaningResponseDTO> data = loaningService.findAllForApprover1();
            if (data.isEmpty()) {
                Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "No loaning data found for this approver");
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Success get data", data);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error retrieving loaning data: " + e.getMessage());
        }
    }

    @GetMapping("approver-2")
    public ResponseEntity<Object> getAllLoaningForApprover2() {
        try {
            List<LoaningResponseDTO> data = loaningService.findAllForApprover2();
            if (data.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "No loaning data found for this approver");
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Success get data", data);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error retrieving loaning data: " + e.getMessage());
        }
    }

      @GetMapping("return")
    public ResponseEntity<Object> getAllLoaningForReturner() {
        try {
            List<LoaningResponseDTO> data = loaningService.findAllForReturn();
            if (data.isEmpty()) {
                return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "No loaning data found for this approver");
            }
            return Utils.generateResponseEntity(HttpStatus.OK, "Success get data", data);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error retrieving loaning data: " + e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> saveLoaning(@RequestBody LoaningRequestDTO loaningRequestDTO) {
        try {
            loaningService.save(loaningRequestDTO);
            return Utils.generateResponseEntity(HttpStatus.CREATED, "Loaning request created successfully");
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error creating loaning request: " + e.getMessage());
        }
    }

}
