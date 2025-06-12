package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.model.User;
import com.management_asset.api.service.IUserService;

@RestController
@RequestMapping("api/user")
public class UserController {

    private IUserService iUserService;

    @Autowired
    public UserController(IUserService iUserService){
        this.iUserService = iUserService;
    }

    @GetMapping
    public List<User> getAllAsset(){
        return iUserService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id){
        return iUserService.findById(id);
    }
}
