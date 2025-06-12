package com.management_asset.api.service;

import org.springframework.stereotype.Service;

import com.management_asset.api.model.Employee;
import com.management_asset.api.service.generic.GenericService;

@Service
public interface EmployeeService extends GenericService<Employee, Integer> {

}
