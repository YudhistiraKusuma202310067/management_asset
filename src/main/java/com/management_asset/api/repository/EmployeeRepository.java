package com.management_asset.api.repository;

import com.management_asset.api.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> { // Integer buat ambil Primary Key nya
    @Query("SELECT e.manager FROM Employee e WHERE e.id = :employeeId")
    Employee findManagerByEmployeeId(@Param("employeeId") Integer employeeId);
}
