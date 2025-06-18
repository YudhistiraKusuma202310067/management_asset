package com.management_asset.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    // private String recipient;
    private String subject;
    private String body;
    // private String attachment;

    private String employeeEmail;
    private String employeeName;
    private String assetName;
    private boolean isApproved;
}
