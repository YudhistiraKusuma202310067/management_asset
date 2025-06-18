package com.management_asset.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.management_asset.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { // Integer buat ambil Primary Key nya

    User findByUsername(String username);

}
