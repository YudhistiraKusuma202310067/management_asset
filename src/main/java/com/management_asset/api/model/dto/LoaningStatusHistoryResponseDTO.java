package com.management_asset.api.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaningStatusHistoryResponseDTO {
    private String loanStatusProcess;
    private LocalDateTime createdDate;
}
