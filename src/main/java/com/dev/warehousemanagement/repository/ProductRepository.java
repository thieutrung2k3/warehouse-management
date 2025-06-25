package com.dev.warehousemanagement.repository;

import com.dev.warehousemanagement.entity.Category;
import com.dev.warehousemanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {


}
