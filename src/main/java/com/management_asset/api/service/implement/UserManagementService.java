package com.management_asset.api.service.implement;

import java.util.UUID;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.management_asset.api.model.Employee;
import com.management_asset.api.model.User;
import com.management_asset.api.model.dto.EmployeeDTO;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.repository.RoleRepository;
import com.management_asset.api.repository.UserRepository;

@Service
public class UserManagementService {
    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    // @Autowired
    public UserManagementService(UserRepository userRepository,
            EmployeeRepository employeeRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee register(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        Employee manager = employeeRepository.findById(employeeDTO.getManager()).orElse(null);
        employee.setManager(manager);
        Employee savedEmployee = employeeRepository.save(employee);

        // 2. Simpan User (otomatis username password = nama, role default = 3)
        User user = new User();
        user.setUsername(savedEmployee.getName());
        user.setRandomCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(savedEmployee.getName()));
        user.setEmployee(employeeRepository.findById(savedEmployee.getId()).orElse(null));
        Integer role = roleRepository.findRoleIdByLevel(3); // role default staff
        user.setRole(roleRepository.findById(role).orElse(null));
        userRepository.save(user);
        return savedEmployee;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    public Boolean changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean updateUserRole(String username, Integer newLevel) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Integer roleId = roleRepository.findRoleIdByLevel(newLevel);
            user.setRole(roleRepository.findById(roleId).orElse(null));
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
