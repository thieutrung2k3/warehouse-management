package com.dev.warehousemanagement.service;

import com.dev.warehousemanagement.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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


}
