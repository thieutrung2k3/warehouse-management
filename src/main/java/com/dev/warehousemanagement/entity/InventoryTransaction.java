package com.dev.warehousemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_transactions",
        indexes = {
                @Index(name = "idx_transaction_number", columnList = "transaction_number"),
                @Index(name = "idx_inventory", columnList = "inventory_id"),
                @Index(name = "idx_product", columnList = "product_id"),
                @Index(name = "idx_warehouse", columnList = "warehouse_id"),
                @Index(name = "idx_transaction_type", columnList = "transaction_type"),
                @Index(name = "idx_transaction_date", columnList = "transaction_date"),
                @Index(name = "idx_reference_document", columnList = "reference_document_type, reference_document_number"),
                @Index(name = "idx_batch_serial", columnList = "batch_number, serial_number"),
                @Index(name = "idx_fiscal_period", columnList = "fiscal_year, fiscal_month"),
                @Index(name = "idx_performed_by", columnList = "performed_by"),
                @Index(name = "idx_created_at", columnList = "created_at"),
                @Index(name = "idx_quantity_change", columnList = "quantity_change"),
                @Index(name = "idx_from_to_warehouse", columnList = "from_warehouse_id, to_warehouse_id")
        }
)
public class InventoryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "transaction_number", length = 50, nullable = false, unique = true)
    private String transactionNumber;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(name = "transaction_subtype", length = 50)
    private String transactionSubtype;

    @Column(name = "reference_document_type", length = 50)
    private String referenceDocumentType;

    @Column(name = "reference_document_number", length = 50)
    private String referenceDocumentNumber;

    @Column(name = "reference_line_number")
    private Long referenceLineNumber;

    @Column(name = "quantity_before", nullable = false)
    private Long quantityBefore;

    @Column(name = "quantity_change", nullable = false)
    private Long quantityChange;

    @Column(name = "quantity_after", nullable = false)
    private Long quantityAfter;

    @Column(name = "unit_cost", precision = 15, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "total_cost", precision = 18, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "batch_number", length = 50)
    private String batchNumber;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Column(name = "lot_number", length = 50)
    private String lotNumber;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "from_location", length = 100)
    private String fromLocation;

    @Column(name = "to_location", length = 100)
    private String toLocation;

    @ManyToOne
    @JoinColumn(name = "from_warehouse_id")
    private Warehouse fromWarehouse;

    @ManyToOne
    @JoinColumn(name = "to_warehouse_id")
    private Warehouse toWarehouse;

    @Column(name = "reason_code", length = 20)
    private String reasonCode;

    @Column(name = "reason_description", length = 200)
    private String reasonDescription;

    @Column(name = "performed_by")
    private Long performedBy;

    @Column(name = "approved_by")
    private Long approvedBy;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "carrier_id")
    private Long carrierId;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "transaction_time", nullable = false)
    private Time transactionTime;

    @Column(name = "fiscal_year")
    private Long fiscalYear;

    @Column(name = "fiscal_month")
    private Long fiscalMonth;

    @Column(name = "fiscal_week")
    private Long fiscalWeek;

    @Column(name = "shift", length = 20)
    private String shift;

    @Column(name = "workstation", length = 50)
    private String workstation;

    @Column(name = "device_id", length = 50)
    private String deviceId;

    @Column(name = "gps_latitude", precision = 10, scale = 8)
    private BigDecimal gpsLatitude;

    @Column(name = "gps_longitude", precision = 11, scale = 8)
    private BigDecimal gpsLongitude;

    @Column(name = "temperature_at_transaction", precision = 5, scale = 2)
    private BigDecimal temperatureAtTransaction;

    @Column(name = "humidity_at_transaction", precision = 5, scale = 2)
    private BigDecimal humidityAtTransaction;

    @Column(name = "quality_check_passed")
    private Boolean qualityCheckPassed = true;

    @Column(name = "quality_check_notes", columnDefinition = "TEXT")
    private String qualityCheckNotes;

    @Column(name = "cost_center", length = 20)
    private String costCenter;

    @Column(name = "project_code", length = 50)
    private String projectCode;

    @Column(name = "gl_account", length = 20)
    private String glAccount;

    @Column(name = "is_reversed")
    private Boolean isReversed = false;

    private Long reversedByTransaction;

    @Column(name = "reversal_reason", length = 200)
    private String reversalReason;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    public enum TransactionType {
        IN, OUT, TRANSFER, ADJUSTMENT, COUNT
    }
}
