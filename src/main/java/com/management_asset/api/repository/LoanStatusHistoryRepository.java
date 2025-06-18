package com.management_asset.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.management_asset.api.model.LoanStatusHistory;

import java.util.List;

@Repository
public interface LoanStatusHistoryRepository extends JpaRepository<LoanStatusHistory, Integer> {
    @Query("SELECT l FROM LoanStatusHistory l WHERE l.loaning.id = :loaningId ORDER BY l.createdDate DESC")
    List<LoanStatusHistory> findByLoaningId(Integer loaningId);

}
