package com.dev.warehousemanagement.repository;

import com.dev.warehousemanagement.entity.Category;
import com.dev.warehousemanagement.entity.InventorySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventorySnapshotRepository extends JpaRepository<InventorySnapshot, Long> {


}
