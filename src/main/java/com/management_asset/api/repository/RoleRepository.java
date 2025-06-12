package com.management_asset.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.management_asset.api.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> { //Integer buat ambil Primary Key nya

}
