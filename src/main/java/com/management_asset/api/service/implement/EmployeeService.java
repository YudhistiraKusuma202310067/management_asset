package com.management_asset.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Employee;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.service.IEmployeeService;

@Service
public class EmployeeService implements IEmployeeService{
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
    @Override
    public Employee findById(Integer id){
        return employeeRepository.findById(id).get();
    }

    @Override
    public Employee save(Employee model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
}
