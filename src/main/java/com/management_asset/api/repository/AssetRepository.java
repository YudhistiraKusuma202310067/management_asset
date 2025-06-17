package com.management_asset.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management_asset.api.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {

    @Query("SELECT a FROM Asset a WHERE a.assetStatus.id = :assetStatusId")
    List<Asset> findAssetByStatus(@Param("assetStatusId") Integer assetStatusId);

    @Query("SELECT a FROM Asset a JOIN Loaning l ON l.asset.id = a.id WHERE a.assetStatus.id = 2 AND l.employee.id = :employeeId")
    List<Asset> findBorrowedAssetByEmployeeId(@Param("employeeId") Integer employeeId);

}
