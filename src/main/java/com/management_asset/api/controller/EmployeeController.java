package com.management_asset.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management_asset.api.model.Employee;
import com.management_asset.api.service.IEmployeeService;



@RestController
@RequestMapping("api/employee")
public class EmployeeController {
        private IEmployeeService iEmployeeService;

    @Autowired
    public EmployeeController(IEmployeeService iEmployeeService) {
        this.iEmployeeService = iEmployeeService;
    }
    
    @GetMapping
    public List<Employee> getAllAsset() {
        return iEmployeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getAssetById(@PathVariable Integer id){
        return iEmployeeService.findById(id);
    }
}
