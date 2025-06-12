package com.management_asset.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_tr_loan_status_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanStatusHistory {

    private LocalDateTime createdDate;

    // @OneToMany
    // @JoinColumn(name = "approver_id", referencedColumnName = "id")
    // private Employee approver;
    @ManyToOne
    @JoinColumn(name = "loaning_id", referencedColumnName = "id")
    private Loaning loaning;

    @ManyToOne
    @JoinColumn(name = "loan_status_process_id", referencedColumnName = "id")
    private LoanStatusProcess loanStatusProcess;

}
