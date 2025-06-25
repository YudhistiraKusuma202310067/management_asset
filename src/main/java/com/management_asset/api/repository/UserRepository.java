package com.management_asset.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management_asset.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { // Integer buat ambil Primary Key nya

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role = 1")
    List<User> getAllManager();

    @Query("SELECT u FROM User u WHERE u.randomCode = :randomCode")
    User findByRandomCode(@Param("randomCode") String randomCode);

}
