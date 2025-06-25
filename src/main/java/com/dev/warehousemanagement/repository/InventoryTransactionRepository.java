package com.dev.warehousemanagement.repository;

import com.dev.warehousemanagement.entity.Category;
import com.dev.warehousemanagement.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
}
