package com.management_asset.api.controller;

import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.model.User;
import com.management_asset.api.model.dto.ManagerDTO;
import com.management_asset.api.service.implement.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

    private UserService userService;

    // @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUser() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/getAllManager")
    public List<ManagerDTO> getAllManager() {
        return userService.findAllManager();
    }

}
