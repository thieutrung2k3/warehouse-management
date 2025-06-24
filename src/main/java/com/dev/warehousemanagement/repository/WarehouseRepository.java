package com.dev.warehousemanagement.repository;
import com.dev.warehousemanagement.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

}
