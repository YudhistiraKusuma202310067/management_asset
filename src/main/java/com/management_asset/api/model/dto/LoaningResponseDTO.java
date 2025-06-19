package com.management_asset.api.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaningResponseDTO {
    private Integer id;
    private LocalDateTime loanDate;
    private String assetName;
    private String loaningStatusProcess;
    
}
