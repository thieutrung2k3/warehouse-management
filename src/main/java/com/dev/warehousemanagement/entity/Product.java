package com.dev.warehousemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_code", nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "product_name", nullable = false, length = 200)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String brand;
    private String model;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "JSON")
    private String specifications;

    @Column(name = "unit_of_measure")
    private String unitOfMeasure;

    private BigDecimal weight;
    private String dimensions;
    private String color;
    private String material;

    @Column(name = "origin_country")
    private String originCountry;
    private String manufacturer;

    @Column(name = "supplier_code")
    private String supplierCode;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "cost_price")
    private BigDecimal costPrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "min_stock_level")
    private Long minStockLevel;

    @Column(name = "max_stock_level")
    private Long maxStockLevel;

    @Column(name = "reorder_point")
    private Long reorderPoint;

    @Column(name = "lead_time_days")
    private Long leadTimeDays;

    @Column(name = "shelf_life_days")
    private Long shelfLifeDays;

    @Column(name = "storage_temperature_min")
    private BigDecimal storageTemperatureMin;

    @Column(name = "storage_temperature_max")
    private BigDecimal storageTemperatureMax;

    @Column(name = "storage_humidity_max")
    private BigDecimal storageHumidityMax;

    @Column(name = "is_hazardous")
    private Boolean isHazardous;

    @Column(name = "is_fragile")
    private Boolean isFragile;

    @Column(name = "is_perishable")
    private Boolean isPerishable;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @Column(name = "discount_rate")
    private BigDecimal discountRate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Inventory> inventoryList;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<InventorySnapshot> inventorySnapshots ;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<InventoryTransaction> inventoryTransactions ;
}
