package com.management_asset.api.controller;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.management_asset.api.model.Employee;
import com.management_asset.api.service.implement.EmployeeService;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    // @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllAsset() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getAssetById(@PathVariable Integer id) {
        return employeeService.findById(id);

    }

    @GetMapping("/email/{id}")
    public String findEmailById(@PathVariable Integer id) {
        return employeeService.findEmailById(id);
    }

}
