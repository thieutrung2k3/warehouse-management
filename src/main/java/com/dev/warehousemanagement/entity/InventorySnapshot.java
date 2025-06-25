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
@Table(name = "inventory_snapshots",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_snapshot_product_warehouse", columnNames = {
                        "snapshot_date", "snapshot_type", "product_id", "warehouse_id"
                })
        },
        indexes = {
                @Index(name = "idx_snapshot_date", columnList = "snapshot_date"),
                @Index(name = "idx_snapshot_type", columnList = "snapshot_type"),
                @Index(name = "idx_product", columnList = "product_id"),
                @Index(name = "idx_warehouse", columnList = "warehouse_id"),
                @Index(name = "idx_category", columnList = "category_id"),
                @Index(name = "idx_fiscal_period", columnList = "fiscal_year, fiscal_quarter, fiscal_month"),
                @Index(name = "idx_stock_status", columnList = "stock_status"),
                @Index(name = "idx_abc_classification", columnList = "abc_classification"),
                @Index(name = "idx_total_value", columnList = "total_value"),
                @Index(name = "idx_aging_analysis", columnList = "aging_over_180_days_qty, aging_over_180_days_value"),
                @Index(name = "idx_expiry_analysis", columnList = "expiry_within_30_days_qty, expired_qty")
        }
)
public class InventorySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snapshot_id")
    private Long id;

    @Column(name = "snapshot_date", nullable = false)
    private Date snapshotDate;

    @Column(name = "snapshot_time", nullable = false)
    private Time snapshotTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "snapshot_type", nullable = false)
    private SnapshotType snapshotType;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "product_code", length = 50, nullable = false)
    private String productCode;

    @Column(name = "product_name", length = 200, nullable = false)
    private String productName;

    @Column(name = "warehouse_code", length = 20, nullable = false)
    private String warehouseCode;

    @Column(name = "warehouse_name", length = 100, nullable = false)
    private String warehouseName;

    @Column(name = "category_name", length = 100, nullable = false)
    private String categoryName;

    @Column(name = "quantity_on_hand", nullable = false)
    private Long quantityOnHand;

    @Column(name = "quantity_reserved", nullable = false)
    private Long quantityReserved;

    @Column(name = "quantity_available", nullable = false)
    private Long quantityAvailable;

    @Column(name = "quantity_incoming")
    private Long quantityIncoming;

    @Column(name = "quantity_outgoing")
    private Long quantityOutgoing;

    @Column(name = "unit_cost", precision = 15, scale = 2)
    private BigDecimal unitCost;

    @Column(name = "total_value", precision = 18, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "avg_cost_last_30_days", precision = 15, scale = 2)
    private BigDecimal avgCostLast30Days;

    @Column(name = "avg_cost_last_90_days", precision = 15, scale = 2)
    private BigDecimal avgCostLast90Days;

    @Column(name = "min_stock_level")
    private Long minStockLevel;

    @Column(name = "max_stock_level")
    private Long maxStockLevel;

    @Column(name = "reorder_point")
    private Long reorderPoint;

    @Column(name = "stock_coverage_days")
    private Long stockCoverageDays;

    @Column(name = "turnover_ratio", precision = 8, scale = 4)
    private BigDecimal turnoverRatio;

    @Column(name = "days_of_supply")
    private Long daysOfSupply;

    @Column(name = "last_receipt_date")
    private Date lastReceiptDate;

    @Column(name = "last_issue_date")
    private Date lastIssueDate;

    @Column(name = "last_sale_date")
    private Date lastSaleDate;

    @Column(name = "days_since_last_movement")
    private Long daysSinceLastMovement;

    @Enumerated(EnumType.STRING)
    @Column(name = "abc_classification")
    private AbcClassification abcClassification;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_frequency")
    private MovementFrequency movementFrequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_status")
    private StockStatus stockStatus;

    @Column(name = "aging_0_30_days_qty")
    private Long aging0To30Qty;

    @Column(name = "aging_31_60_days_qty")
    private Long aging31To60Qty;

    @Column(name = "aging_61_90_days_qty")
    private Long aging61To90Qty;

    @Column(name = "aging_91_180_days_qty")
    private Long aging91To180Qty;

    @Column(name = "aging_over_180_days_qty")
    private Long agingOver180Qty;

    @Column(name = "aging_0_30_days_value", precision = 15, scale = 2)
    private BigDecimal aging0To30Value = BigDecimal.ZERO;

    @Column(name = "aging_31_60_days_value", precision = 15, scale = 2)
    private BigDecimal aging31To60Value = BigDecimal.ZERO;

    @Column(name = "aging_61_90_days_value", precision = 15, scale = 2)
    private BigDecimal aging61To90Value = BigDecimal.ZERO;

    @Column(name = "aging_91_180_days_value", precision = 15, scale = 2)
    private BigDecimal aging91To180Value = BigDecimal.ZERO;

    @Column(name = "aging_over_180_days_value", precision = 15, scale = 2)
    private BigDecimal agingOver180Value = BigDecimal.ZERO;

    @Column(name = "expiry_within_30_days_qty")
    private Long expiryWithin30DaysQty ;

    @Column(name = "expiry_within_60_days_qty")
    private Long expiryWithin60DaysQty ;

    @Column(name = "expiry_within_90_days_qty")
    private Long expiryWithin90DaysQty;

    @Column(name = "expired_qty")
    private Long expiredQty ;

    @Column(name = "damaged_qty")
    private Long damagedQty ;

    @Column(name = "quarantine_qty")
    private Long quarantineQty ;

    @Column(name = "fiscal_year")
    private Long fiscalYear;

    @Column(name = "fiscal_quarter")
    private Long fiscalQuarter;

    @Column(name = "fiscal_month")
    private Long fiscalMonth;

    @Column(name = "fiscal_week")
    private Long fiscalWeek;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "VND";

    @Column(name = "exchange_rate", precision = 12, scale = 6)
    private BigDecimal exchangeRate = BigDecimal.valueOf(1.000000);

    @Column(name = "cost_center", length = 20)
    private String costCenter;

    @Column(name = "profit_center", length = 20)
    private String profitCenter;

    @Column(name = "business_unit", length = 50)
    private String businessUnit;

    @Column(name = "region", length = 50)
    private String region;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    public enum SnapshotType {
        DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY, AD_HOC
    }

    public enum AbcClassification {
        A, B, C
    }

    public enum MovementFrequency {
        FAST, MEDIUM, SLOW
    }

    public enum StockStatus {
        NORMAL, LOW, OUT_OF_STOCK, OVERSTOCK, DEAD_STOCK
    }
}
