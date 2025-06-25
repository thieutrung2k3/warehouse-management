package com.dev.warehousemanagement.controller;

import com.dev.warehousemanagement.dto.response.ProductInventoryJPQLResponse;
import com.dev.warehousemanagement.dto.response.ProductInventoryResponse;
import com.dev.warehousemanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("")
    public void getAllTest() {
        productService.getAllTest();
    }

    @GetMapping("/getAllProductInventories")
    public ResponseEntity<Page<ProductInventoryJPQLResponse>> getAllProductInventories() {
        return ResponseEntity.ok(productService.getAllProductInventory());
    }

    @GetMapping("/getAllProductsInventoryWithNative")
    public ResponseEntity<Page<ProductInventoryResponse>> getAllProductsInventoryWithNative() {
        return ResponseEntity.ok(productService.getAllProductsInventoryWithNative());
    }
}
