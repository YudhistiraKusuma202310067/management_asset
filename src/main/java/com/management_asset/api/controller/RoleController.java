package com.management_asset.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.model.Role;
import com.management_asset.api.service.implement.RoleService;

@RestController
@RequestMapping("api/role")
public class RoleController {
    private RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @GetMapping
    public List<Role> getAllRoles(){
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Integer id){
        return roleService.findById(id);
    }

    @PostMapping
    public Role saveRole(@RequestBody Role role){
        return roleService.saveRole(role);
    }

}
