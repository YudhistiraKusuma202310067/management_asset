package com.management_asset.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_tr_loaning")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Loaning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;
    private String note;
    // @OneToMany
    // @JoinColumn(name = "employee_id", referencedColumnName = "id")
    // private Employee employee;

    // @OneToMany
    // @JoinColumn(name = "asset_id", referencedColumnName = "id")
    // private Asset asset;
    @OneToMany
    @JoinColumn(name = "loan_status_process_id", referencedColumnName = "id")
    private LoanStatusProcess loanStatusProcess;
}
