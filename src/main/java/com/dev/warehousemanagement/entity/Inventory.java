package com.dev.warehousemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory",
        indexes = {
                @Index(name = "idx_product", columnList = "product_id"),
                @Index(name = "idx_warehouse", columnList = "warehouse_id"),
                @Index(name = "idx_location", columnList = "warehouse_id, location_zone, location_aisle"),
                @Index(name = "idx_batch_serial", columnList = "batch_number, serial_number"),
                @Index(name = "idx_expiry_date", columnList = "expiry_date"),
                @Index(name = "idx_quantity_available", columnList = "quantity_available"),
                @Index(name = "idx_abc_classification", columnList = "abc_classification"),
                @Index(name = "idx_quality_status", columnList = "quality_status"),
                @Index(name = "idx_last_counted", columnList = "last_counted_date"),
                @Index(name = "idx_cycle_count", columnList = "next_cycle_count_date")
        }
)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "location_zone", length = 20)
    private String locationZone;

    @Column(name = "location_aisle", length = 10)
    private String locationAisle;

    @Column(name = "location_shelf", length = 10)
    private String locationShelf;

    @Column(name = "location_bin", length = 10)
    private String locationBin;

    @Column(name = "batch_number", length = 50)
    private String batchNumber;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Column(name = "lot_number", length = 50)
    private String lotNumber;

    @Column(name = "quantity_on_hand", nullable = false)
    private Long quantityOnHand ;

    @Column(name = "quantity_reserved")
    private Long quantityReserved;

    @Column(name = "quantity_available", insertable = false, updatable = false)
    private Long quantityAvailable;

    @Column(name = "quantity_incoming")
    private Long quantityIncoming;

    @Column(name = "quantity_outgoing")
    private Long quantityOutgoing;

    @Column(name = "unit_cost", precision = 15, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "total_value", precision = 18, scale = 2, insertable = false, updatable = false)
    private BigDecimal totalValue;

    @Column(name = "manufacturing_date")
    private Date manufacturingDate;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "received_date")
    private Date receivedDate;

    @Column(name = "last_counted_date")
    private Date lastCountedDate;

    @Column(name = "last_counted_quantity")
    private Long lastCountedQuantity;

    @Column(name = "variance_quantity")
    private Long varianceQuantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "abc_classification", columnDefinition = "ENUM('A', 'B', 'C') DEFAULT 'C'")
    private AbcClassification abcClassification = AbcClassification.C;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_frequency", columnDefinition = "ENUM('FAST', 'MEDIUM', 'SLOW') DEFAULT 'MEDIUM'")
    private MovementFrequency movementFrequency = MovementFrequency.MEDIUM;

    @Column(name = "storage_temperature", precision = 5, scale = 2)
    private BigDecimal storageTemperature;

    @Column(name = "storage_humidity", precision = 5, scale = 2)
    private BigDecimal storageHumidity;

    @Enumerated(EnumType.STRING)
    @Column(name = "quality_status", columnDefinition = "ENUM('GOOD', 'DAMAGED', 'EXPIRED', 'QUARANTINE', 'REJECTED') DEFAULT 'GOOD'")
    private QualityStatus qualityStatus = QualityStatus.GOOD;

    @Column(name = "hold_reason", length = 100)
    private String holdReason;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "purchase_order_number", length = 50)
    private String purchaseOrderNumber;

    @Column(name = "goods_receipt_number", length = 50)
    private String goodsReceiptNumber;

    @Column(name = "cycle_count_frequency_days")
    private Long cycleCountFrequencyDays;

    @Column(name = "next_cycle_count_date")
    private Date nextCycleCountDate;

    @Column(name = "is_hazardous")
    private Boolean isHazardous = false;

    @Column(name = "hazmat_class", length = 10)
    private String hazmatClass;

    @Column(name = "special_handling_instructions", columnDefinition = "TEXT")
    private String specialHandlingInstructions;

    @Column(name = "insurance_value", precision = 15, scale = 2)
    private BigDecimal insuranceValue;

    @Column(name = "customs_value", precision = 15, scale = 2)
    private BigDecimal customsValue;

    @Column(name = "customs_cleared")
    private Boolean customsCleared = true;

    @Column(name = "quarantine_days_remaining")
    private Long quarantineDaysRemaining;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    public enum AbcClassification {
        A, B, C
    }

    public enum MovementFrequency {
        FAST, MEDIUM, SLOW
    }

    public enum QualityStatus {
        GOOD, DAMAGED, EXPIRED, QUARANTINE, REJECTED
    }

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<InventoryTransaction> inventoryTransactions ;


}
