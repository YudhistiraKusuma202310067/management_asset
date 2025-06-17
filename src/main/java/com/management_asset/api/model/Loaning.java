package com.management_asset.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "asset_id", referencedColumnName = "id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "loan_status_process_id", referencedColumnName = "id")
    private LoanStatusProcess loanStatusProcess;
}