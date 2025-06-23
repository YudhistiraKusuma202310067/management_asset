package com.management_asset.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management_asset.api.model.Loaning;

@Repository
public interface LoaningRepository extends JpaRepository<Loaning, Integer> {
    
    @Query("SELECT l FROM Loaning l WHERE l.loanStatusProcess.id = :statusId")
    List<Loaning> findByLoanStatus(@Param("statusId") Integer statusId);
    
    @Query("SELECT l FROM Loaning l WHERE l.loanStatusProcess.id IN (2, 3)")
    List<Loaning> findForProcurement();
    
    @Query("SELECT l FROM Loaning l WHERE l.employee.id = :employeeId")
    List<Loaning> findByEmployeeId(@Param("employeeId") Integer employeeId);
}