package com.management_asset.api.service.implement;

import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Employee;
import com.management_asset.api.model.dto.EmployeeDTO;
import com.management_asset.api.repository.EmployeeRepository;
import com.management_asset.api.service.IEmployeeService;

@Service
public class EmployeeService implements IEmployeeService {
    private EmployeeRepository employeeRepository;

    // @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    // @Override
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());

        Employee manager = employeeRepository.findById(employeeDTO.getManager()).orElse(null);
        employee.setManager(manager); // saat nge post atau pengisian ga perlu isi id PK nya cukup Id FK ini aja karna
                                      // // dah pake @mapsid
        return employeeRepository.save(employee);

        // save sekalian register user

    }

    public String findEmailById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        return employee.getEmail();
    }

}