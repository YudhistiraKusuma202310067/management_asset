package com.management_asset.api.service.implement;

import java.util.ArrayList;
import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.User;
import com.management_asset.api.model.dto.ManagerDTO;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.repository.RoleRepository;
import com.management_asset.api.repository.UserRepository;
import com.management_asset.api.service.IUserService;

@Service
public class UserService implements IUserService {

    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    // @Autowired
    public UserService(UserRepository userRepository, EmployeeRepository employeeRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<ManagerDTO> findAllManager() {
        List<User> users = userRepository.getAllManager();

        List<ManagerDTO> dto = new ArrayList<>();
        for (User user : users) {
            dto.add(new ManagerDTO(user.getId(), user.getUsername(), user.getRole().getName()));
        }
        return dto;
    }

}