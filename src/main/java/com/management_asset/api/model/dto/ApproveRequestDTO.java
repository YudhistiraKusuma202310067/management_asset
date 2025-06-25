package com.management_asset.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveRequestDTO {
    private Integer id;
    private String approver;
    private Integer loanStatusProcess;
    
}
