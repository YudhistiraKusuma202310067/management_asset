package com.management_asset.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_m_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private Integer id;
    @Column(unique = true)
    private String username;
    private String password;
    private String randomCode;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @OneToOne
    @MapsId // saat nge post atau pengisian ga perlu isi id PK nya cukup Id FK ini aja karna
            // dah pake @mapsid
    @JoinColumn(name = "id")
    private Employee employee;
}
