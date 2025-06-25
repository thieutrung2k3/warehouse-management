package com.dev.warehousemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouses",
        indexes = {
                @Index(name = "idx_warehouse_code", columnList = "warehouse_code"),
                @Index(name = "idx_warehouse_type", columnList = "warehouse_type"),
                @Index(name = "idx_location", columnList = "city, state_province, country"),
                @Index(name = "idx_active_warehouses", columnList = "is_active"),
                @Index(name = "idx_capacity", columnList = "max_capacity_weight, max_capacity_volume")
        }
)
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long id;

    @Column(name = "warehouse_code", unique = true, nullable = false, length = 20)
    private String code;

    @Column(name = "warehouse_name", nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "warehouse_type", columnDefinition = "ENUM('MAIN', 'BRANCH', 'TRANSIT', 'VIRTUAL') DEFAULT 'MAIN'")
    private WarehouseType type = WarehouseType.MAIN;

    @Column(name = "address_line1", length = 200)
    private String addressLine1;

    @Column(name = "address_line2", length = 200)
    private String addressLine2;

    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
    private String phone;
    private String email;

    @Column(name = "manager_name", length = 100)
    private String managerName;

    @Column(name = "manager_phone", length = 20)
    private String managerPhone;

    @Column(name = "manager_email", length = 100)
    private String managerEmail;

    @Column(name = "total_area", precision = 10, scale = 2)
    private BigDecimal totalArea;

    @Column(name = "storage_area", precision = 10, scale = 2)
    private BigDecimal storageArea;

    @Column(name = "office_area", precision = 10, scale = 2)
    private BigDecimal officeArea;

    @Column(name = "loading_docks")
    private Long loadingDocks;

    @Column(name = "storage_zones")
    private Integer storageZones = 1;

    @Column(name = "max_capacity_weight", precision = 15, scale = 2)
    private BigDecimal maxCapacityWeight;

    @Column(name = "max_capacity_volume", precision = 15, scale = 2)
    private BigDecimal maxCapacityVolume;

    @Column(name = "current_utilization_percent", precision = 5, scale = 2)
    private BigDecimal currentUtilizationPercent = BigDecimal.ZERO;

    @Column(name = "temperature_controlled")
    private Boolean temperatureControlled = false;

    @Column(name = "humidity_controlled")
    private Boolean humidityControlled = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "security_level", columnDefinition = "ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM'")
    private SecurityLevel securityLevel = SecurityLevel.MEDIUM;

    @Column(name = "operating_hours")
    private String operatingHours;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "certification_iso", length = 50)
    private String certificationIso;

    @Column(name = "certification_gmp")
    private Boolean certificationGmp = false;

    @Column(name = "last_inspection_date")
    private Date lastInspectionDate;

    @Column(name = "next_inspection_date")
    private Date nextInspectionDate;

    @Column(name = "insurance_policy_number", length = 50)
    private String insurancePolicyNumber;

    @Column(name = "insurance_expiry_date")
    private Date insuranceExpiryDate;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    public enum WarehouseType {
        MAIN, BRANCH, TRANSIT, VIRTUAL
    }

    public enum SecurityLevel {
        LOW, MEDIUM, HIGH
    }

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventory> inventoryList;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<InventorySnapshot> inventorySnapshots ;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<InventoryTransaction> inventoryTransactions ;
    @OneToMany(mappedBy = "fromWarehouse", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<InventoryTransaction> fromWarehouses ;
    @OneToMany(mappedBy = "toWarehouse", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<InventoryTransaction> toWarehouse ;
}
