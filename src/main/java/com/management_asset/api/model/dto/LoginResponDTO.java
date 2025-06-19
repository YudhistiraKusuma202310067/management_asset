package com.management_asset.api.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponDTO {
    private Integer id;
    private List<String> roles;
    private String name;
    private String email;
    private String randomCode;
}
