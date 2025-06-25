package com.dev.warehousemanagement.service;

import com.dev.warehousemanagement.dto.response.ProductInventoryResponse;
import com.dev.warehousemanagement.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;
    private final InventorySnapshotRepository inventorySnapshotRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public void getAllTest(){
        categoryRepository.findAll();
        inventoryRepository.findAll();
        inventorySnapshotRepository.findAll();
        inventoryTransactionRepository.findAll();
        productRepository.findAll();
        warehouseRepository.findAll();
    }

    public Page<ProductInventoryResponse> getAllProductInventory(){
        return productRepository.getAllProductsInventory(PageRequest.of(0, 100));
    }

    public Page<ProductInventoryResponse> getAllProductsInventoryWithNative(){
        return productRepository.getAllProductsInventoryWithNative(PageRequest.of(0, 100));
    }

}
