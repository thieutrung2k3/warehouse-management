package com.dev.warehousemanagement.repository;

import com.dev.warehousemanagement.entity.Category;
import com.dev.warehousemanagement.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {


}
