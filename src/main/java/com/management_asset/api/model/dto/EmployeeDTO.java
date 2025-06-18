package com.management_asset.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String name;
    private String email;
    private Integer manager; //diubah jadi integer bukan lagi model
}
