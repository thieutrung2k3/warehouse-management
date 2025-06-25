package com.dev.warehousemanagement.repository;

import com.dev.warehousemanagement.dto.response.ProductInventoryResponse;
import com.dev.warehousemanagement.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
            SELECT
                c.name as categoryName,
                p.name as productName,
                SUM(i.quantityOnHand) as quantityOnHand,
                SUM(i.totalValue) as totalValue
            FROM Inventory i
            JOIN i.product p
            JOIN p.category c
            GROUP BY c.name, p.name
            ORDER BY SUM(i.totalValue) DESC
            """)
    Page<ProductInventoryResponse> getAllProductsInventory(Pageable pageable);

    @Query(value = """
            SELECT
                c.category_name AS categoryName,
                p.product_name AS productName,
                SUM(i.quantity_on_hand) AS quantityOnHand,
                SUM(i.total_value) AS totalValue
                FROM inventory i
                JOIN products p ON i.product_id = p.product_id
                JOIN categories c ON p.category_id = c.category_id
                GROUP BY c.category_name, p.product_name
                ORDER BY totalValue DESC
            """, nativeQuery = true)
    Page<ProductInventoryResponse> getAllProductsInventoryWithNative(Pageable pageable);
}
