package com.management_asset.api.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.Utils;
import com.management_asset.api.model.Employee;
import com.management_asset.api.model.User;
import com.management_asset.api.model.dto.EmployeeDTO;
import com.management_asset.api.model.dto.LoginDTO;
import com.management_asset.api.model.dto.LoginResponDTO;
import com.management_asset.api.model.dto.UserDTO;
import com.management_asset.api.service.implement.UserManagementService;

import com.management_asset.api.config.AppSecurityConfig;

@RestController
@RequestMapping("api/user-management")
public class UserManagementController {
    private UserManagementService userManagementService;
    // private final AppSecurityConfig passwordEncoder;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;

    }

    @PostMapping("register")
    public Employee register(@RequestBody EmployeeDTO employeeDTO) {
        return userManagementService.register(employeeDTO);
    }

    @PostMapping("authentication")
    public ResponseEntity<Object> login(@RequestBody LoginDTO login) throws Exception {
        User userData = userManagementService.login(login.getUsername(), login.getPassword());
        List<String> roles = new ArrayList<>();

        roles.add(userData.getRole().getName());
        LoginResponDTO loginResponDTO = new LoginResponDTO();

        // loginResponDTO.setId(userData.getId());
        loginResponDTO.setRoles(roles);
        loginResponDTO.setName(userData.getUsername());
        loginResponDTO.setEmail(userData.getEmployee().getEmail());
        loginResponDTO.setRandomCode(userData.getRandomCode());

        login.setData(loginResponDTO);

        try {
            org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                    userData.getId().toString(), // id employe nya diambil //dapat dari
                    "", // harus terapin encode dulu
                    getAuthorities(roles) // masukan role name
            );
            PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
                    user, "", user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            return Utils.generateResponseEntity(HttpStatus.OK, "Login Success", login.getData());

        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.OK, "Login Failed");
        }
    }

    @PostMapping("changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody UserDTO dto) {
        try {
            Boolean result = userManagementService.changePassword(dto.getUsername(), dto.getPassword(),
                    dto.getNewPassword());
            return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "Password Change", result);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while change Password", null);
        }
    }

    @PostMapping("updateUserRole")
    public ResponseEntity<Object> updateUserRole(@RequestBody UserDTO dto) {
        try {
            Boolean result = userManagementService.updateUserRole(dto.getUsername(), dto.getRole());
            return Utils.generateResponseEntity(HttpStatus.NOT_FOUND, "No Assets Found", result);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error Update Role", null);
        }
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

}