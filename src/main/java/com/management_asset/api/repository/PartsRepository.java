package com.management_asset.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.management_asset.api.model.Parts;

@Repository
public interface PartsRepository extends JpaRepository<Parts, Integer> {
    
}
