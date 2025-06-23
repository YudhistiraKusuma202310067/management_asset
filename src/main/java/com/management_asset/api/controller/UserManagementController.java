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
import com.management_asset.api.model.dto.LoginRequestDTO;
import com.management_asset.api.model.dto.LoginResponDTO;
import com.management_asset.api.model.dto.UserDTO;
import com.management_asset.api.service.implement.UserManagementService;

@RestController
@RequestMapping("api/user-management")
public class UserManagementController {
    private UserManagementService userManagementService;
    // private final AppSecurityConfig passwordEncoder;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;

    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = userManagementService.register(employeeDTO);
            if (employee != null) {
                return Utils.generateResponseEntity(HttpStatus.OK, "Succes to Register", employeeDTO);
            } else {
                return Utils.generateResponseEntity(HttpStatus.OK, "Name or Email of Employee has been register");
            }

        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.OK, "Error while Register", null);
        }
    }

    @PostMapping("authentication")
    public ResponseEntity<Object> login(@RequestBody LoginRequestDTO login) throws Exception {
        User userData = userManagementService.login(login.getUsername(), login.getPassword());
        if (userData != null) {
            List<String> roles = new ArrayList<>();

            roles.add(userData.getRole().getName());
            LoginResponDTO loginResponDTO = new LoginResponDTO();

            // loginResponDTO.setId(userData.getId());
            loginResponDTO.setRoles(roles);
            loginResponDTO.setName(userData.getUsername());
            loginResponDTO.setEmail(userData.getEmployee().getEmail());
            loginResponDTO.setRandomCode(userData.getRandomCode());

            try {
                org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                        userData.getId().toString(), // id employe nya diambil //dapat dari
                        "", // harus terapin encode dulu
                        getAuthorities(roles) // masukan role name
                );
                PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
                        user, "", user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                return Utils.generateResponseEntity(HttpStatus.OK, "Login Success", loginResponDTO);

            } catch (Exception e) {
                return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Login Failed Server");
            }
        } else {
            return Utils.generateResponseEntity(HttpStatus.OK, "User Not Found");
        }

    }

    @PostMapping("changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody UserDTO dto) {
        Boolean result = userManagementService.changePassword(dto.getUsername(), dto.getPassword(),
                dto.getNewPassword());
        if (result != false) {
            try {
                return Utils.generateResponseEntity(HttpStatus.OK, "Password Change", result);
            } catch (Exception e) {
                return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error while change Password",
                        null);
            }
        } else {
            return Utils.generateResponseEntity(HttpStatus.OK, "User Not Found", result);
        }
    }

    @PostMapping("updateUserRole")
    public ResponseEntity<Object> updateUserRole(@RequestBody UserDTO dto) {
        Boolean result = userManagementService.updateUserRole(dto.getUsername(), dto.getRole());
        if (result != false) {
            try {
                return Utils.generateResponseEntity(HttpStatus.OK, "Update Role Succes", result);
            } catch (Exception e) {
                return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error Update Role", null);
            }
        } else {
            return Utils.generateResponseEntity(HttpStatus.OK, "User Not Found", result);
        }
    }

    @PostMapping("updateUserManager")
    public ResponseEntity<Object> updateUserManager(@RequestBody EmployeeDTO dto) {
        try {
            Boolean result = userManagementService.updateUserManager(dto.getName(), dto.getManager());
            return Utils.generateResponseEntity(HttpStatus.OK, "Update Manager Succes", result);
        } catch (Exception e) {
            return Utils.generateResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Error Update Manager", null);
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