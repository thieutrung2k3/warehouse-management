-- ========================================
-- DATABASE SCHEMA: QUẢN LÝ KHO HÀNG
-- Thiết kế cho test hiệu năng với nhiều cột
-- ========================================

-- 1. BẢNG NHÓM SẢN PHẨM (CATEGORIES)
CREATE TABLE categories
(
    category_id        INT PRIMARY KEY AUTO_INCREMENT,
    category_code      VARCHAR(20) UNIQUE NOT NULL,
    category_name      VARCHAR(100)       NOT NULL,
    parent_category_id INT,
    description        TEXT,
    is_active          BOOLEAN   DEFAULT TRUE,
    sort_order         INT       DEFAULT 0,
    image_url          VARCHAR(255),
    created_by         INT,
    updated_by         INT,
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_category_id) REFERENCES categories (category_id),
    INDEX idx_category_code (category_code),
    INDEX idx_parent_category (parent_category_id),
    INDEX idx_active_sort (is_active, sort_order)
);

-- 2. BẢNG SẢN PHẨM (PRODUCTS)
CREATE TABLE products
(
    product_id              INT PRIMARY KEY AUTO_INCREMENT,
    product_code            VARCHAR(50) UNIQUE NOT NULL,
    product_name            VARCHAR(200)       NOT NULL,
    category_id             INT                NOT NULL,
    brand                   VARCHAR(100),
    model                   VARCHAR(100),
    description             TEXT,
    specifications          JSON,
    unit_of_measure         VARCHAR(20)   DEFAULT 'PCS',
    weight                  DECIMAL(10, 3),
    dimensions              VARCHAR(50), -- LxWxH format
    color                   VARCHAR(50),
    material                VARCHAR(100),
    origin_country          VARCHAR(50),
    manufacturer            VARCHAR(100),
    supplier_code           VARCHAR(50),
    barcode                 VARCHAR(50),
    qr_code                 VARCHAR(100),
    cost_price              DECIMAL(15, 2),
    selling_price           DECIMAL(15, 2),
    min_stock_level         INT           DEFAULT 0,
    max_stock_level         INT           DEFAULT 1000,
    reorder_point           INT           DEFAULT 10,
    lead_time_days          INT           DEFAULT 7,
    shelf_life_days         INT,
    storage_temperature_min DECIMAL(5, 2),
    storage_temperature_max DECIMAL(5, 2),
    storage_humidity_max    DECIMAL(5, 2),
    is_hazardous            BOOLEAN       DEFAULT FALSE,
    is_fragile              BOOLEAN       DEFAULT FALSE,
    is_perishable           BOOLEAN       DEFAULT FALSE,
    is_active               BOOLEAN       DEFAULT TRUE,
    tax_rate                DECIMAL(5, 2) DEFAULT 0.00,
    discount_rate           DECIMAL(5, 2) DEFAULT 0.00,
    created_by              INT,
    updated_by              INT,
    created_at              TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    updated_at              TIMESTAMP     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (category_id),
    INDEX idx_product_code (product_code),
    INDEX idx_category (category_id),
    INDEX idx_brand_model (brand, model),
    INDEX idx_price_range (cost_price, selling_price),
    INDEX idx_stock_levels (min_stock_level, max_stock_level),
    INDEX idx_active_products (is_active),
    FULLTEXT idx_product_search (product_name, description, brand, model)
);

-- 3. BẢNG KHO HÀNG (WAREHOUSES)
CREATE TABLE warehouses
(
    warehouse_id                INT PRIMARY KEY AUTO_INCREMENT,
    warehouse_code              VARCHAR(20) UNIQUE NOT NULL,
    warehouse_name              VARCHAR(100)       NOT NULL,
    warehouse_type              ENUM ('MAIN', 'BRANCH', 'TRANSIT', 'VIRTUAL') DEFAULT 'MAIN',
    address_line1               VARCHAR(200),
    address_line2               VARCHAR(200),
    city                        VARCHAR(100),
    state_province              VARCHAR(100),
    postal_code                 VARCHAR(20),
    country                     VARCHAR(50),
    phone                       VARCHAR(20),
    email                       VARCHAR(100),
    manager_name                VARCHAR(100),
    manager_phone               VARCHAR(20),
    manager_email               VARCHAR(100),
    total_area                  DECIMAL(10, 2), -- m2
    storage_area                DECIMAL(10, 2), -- m2
    office_area                 DECIMAL(10, 2), -- m2
    loading_docks               INT                                           DEFAULT 0,
    storage_zones               INT                                           DEFAULT 1,
    max_capacity_weight         DECIMAL(15, 2), -- kg
    max_capacity_volume         DECIMAL(15, 2), -- m3
    current_utilization_percent DECIMAL(5, 2)                                 DEFAULT 0.00,
    temperature_controlled      BOOLEAN                                       DEFAULT FALSE,
    humidity_controlled         BOOLEAN                                       DEFAULT FALSE,
    security_level              ENUM ('LOW', 'MEDIUM', 'HIGH')                DEFAULT 'MEDIUM',
    operating_hours             VARCHAR(50),
    timezone                    VARCHAR(50),
    is_active                   BOOLEAN                                       DEFAULT TRUE,
    certification_iso           VARCHAR(50),
    certification_gmp           BOOLEAN                                       DEFAULT FALSE,
    last_inspection_date        DATE,
    next_inspection_date        DATE,
    insurance_policy_number     VARCHAR(50),
    insurance_expiry_date       DATE,
    created_by                  INT,
    updated_by                  INT,
    created_at                  TIMESTAMP                                     DEFAULT CURRENT_TIMESTAMP,
    updated_at                  TIMESTAMP                                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_warehouse_code (warehouse_code),
    INDEX idx_warehouse_type (warehouse_type),
    INDEX idx_location (city, state_province, country),
    INDEX idx_active_warehouses (is_active),
    INDEX idx_capacity (max_capacity_weight, max_capacity_volume)
);

-- 4. BẢNG TỒN KHO (INVENTORY)
CREATE TABLE inventory
(
    inventory_id                  INT PRIMARY KEY AUTO_INCREMENT,
    product_id                    INT NOT NULL,
    warehouse_id                  INT NOT NULL,
    location_zone                 VARCHAR(20),
    location_aisle                VARCHAR(10),
    location_shelf                VARCHAR(10),
    location_bin                  VARCHAR(10),
    batch_number                  VARCHAR(50),
    serial_number                 VARCHAR(100),
    lot_number                    VARCHAR(50),
    quantity_on_hand              INT NOT NULL                                                  DEFAULT 0,
    quantity_reserved             INT                                                           DEFAULT 0,
    quantity_available            INT GENERATED ALWAYS AS (quantity_on_hand - quantity_reserved) STORED,
    quantity_incoming             INT                                                           DEFAULT 0,
    quantity_outgoing             INT                                                           DEFAULT 0,
    unit_cost                     DECIMAL(15, 2),
    total_value                   DECIMAL(18, 2) GENERATED ALWAYS AS (quantity_on_hand * unit_cost) STORED,
    manufacturing_date            DATE,
    expiry_date                   DATE,
    received_date                 DATE,
    last_counted_date             DATE,
    last_counted_quantity         INT,
    variance_quantity             INT                                                           DEFAULT 0,
    abc_classification            ENUM ('A', 'B', 'C')                                          DEFAULT 'C',
    movement_frequency            ENUM ('FAST', 'MEDIUM', 'SLOW')                               DEFAULT 'MEDIUM',
    storage_temperature           DECIMAL(5, 2),
    storage_humidity              DECIMAL(5, 2),
    quality_status                ENUM ('GOOD', 'DAMAGED', 'EXPIRED', 'QUARANTINE', 'REJECTED') DEFAULT 'GOOD',
    hold_reason                   VARCHAR(100),
    supplier_id                   INT,
    purchase_order_number         VARCHAR(50),
    goods_receipt_number          VARCHAR(50),
    cycle_count_frequency_days    INT                                                           DEFAULT 90,
    next_cycle_count_date         DATE,
    is_hazardous                  BOOLEAN                                                       DEFAULT FALSE,
    hazmat_class                  VARCHAR(10),
    special_handling_instructions TEXT,
    insurance_value               DECIMAL(15, 2),
    customs_value                 DECIMAL(15, 2),
    customs_cleared               BOOLEAN                                                       DEFAULT TRUE,
    quarantine_days_remaining     INT                                                           DEFAULT 0,
    created_by                    INT,
    updated_by                    INT,
    created_at                    TIMESTAMP                                                     DEFAULT CURRENT_TIMESTAMP,
    updated_at                    TIMESTAMP                                                     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (product_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses (warehouse_id),
    UNIQUE KEY uk_product_warehouse_location (product_id, warehouse_id, location_zone, location_aisle, location_shelf,
                                              location_bin, batch_number),
    INDEX idx_product (product_id),
    INDEX idx_warehouse (warehouse_id),
    INDEX idx_location (warehouse_id, location_zone, location_aisle),
    INDEX idx_batch_serial (batch_number, serial_number),
    INDEX idx_expiry_date (expiry_date),
    INDEX idx_quantity_available (quantity_available),
    INDEX idx_abc_classification (abc_classification),
    INDEX idx_quality_status (quality_status),
    INDEX idx_last_counted (last_counted_date),
    INDEX idx_cycle_count (next_cycle_count_date)
);

-- 5. BẢNG GIAO DỊCH TỒN KHO (INVENTORY_TRANSACTIONS)
CREATE TABLE inventory_transactions
(
    transaction_id             INT PRIMARY KEY AUTO_INCREMENT,
    transaction_number         VARCHAR(50) UNIQUE                                    NOT NULL,
    inventory_id               INT                                                   NOT NULL,
    product_id                 INT                                                   NOT NULL,
    warehouse_id               INT                                                   NOT NULL,
    transaction_type           ENUM ('IN', 'OUT', 'TRANSFER', 'ADJUSTMENT', 'COUNT') NOT NULL,
    transaction_subtype        VARCHAR(50), -- PURCHASE, SALE, RETURN, DAMAGED, etc.
    reference_document_type    VARCHAR(50), -- PO, SO, TO, ADJ, etc.
    reference_document_number  VARCHAR(50),
    reference_line_number      INT,
    quantity_before            INT                                                   NOT NULL,
    quantity_change            INT                                                   NOT NULL,
    quantity_after             INT                                                   NOT NULL,
    unit_cost                  DECIMAL(15, 2),
    total_cost                 DECIMAL(18, 2),
    batch_number               VARCHAR(50),
    serial_number              VARCHAR(100),
    lot_number                 VARCHAR(50),
    expiry_date                DATE,
    from_location              VARCHAR(100),
    to_location                VARCHAR(100),
    from_warehouse_id          INT,
    to_warehouse_id            INT,
    reason_code                VARCHAR(20),
    reason_description         VARCHAR(200),
    performed_by               INT,
    approved_by                INT,
    customer_id                INT,
    supplier_id                INT,
    carrier_id                 INT,
    shipping_method            VARCHAR(50),
    tracking_number            VARCHAR(100),
    transaction_date           DATE                                                  NOT NULL,
    transaction_time           TIME                                                  NOT NULL,
    fiscal_year                INT,
    fiscal_month               INT,
    fiscal_week                INT,
    shift                      VARCHAR(20),
    workstation                VARCHAR(50),
    device_id                  VARCHAR(50),
    gps_latitude               DECIMAL(10, 8),
    gps_longitude              DECIMAL(11, 8),
    temperature_at_transaction DECIMAL(5, 2),
    humidity_at_transaction    DECIMAL(5, 2),
    quality_check_passed       BOOLEAN   DEFAULT TRUE,
    quality_check_notes        TEXT,
    cost_center                VARCHAR(20),
    project_code               VARCHAR(50),
    gl_account                 VARCHAR(20),
    is_reversed                BOOLEAN   DEFAULT FALSE,
    reversed_by_transaction_id INT,
    reversal_reason            VARCHAR(200),
    created_by                 INT,
    created_at                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses (warehouse_id),
    FOREIGN KEY (from_warehouse_id) REFERENCES warehouses (warehouse_id),
    FOREIGN KEY (to_warehouse_id) REFERENCES warehouses (warehouse_id),
    FOREIGN KEY (reversed_by_transaction_id) REFERENCES inventory_transactions (transaction_id),
    INDEX idx_transaction_number (transaction_number),
    INDEX idx_inventory (inventory_id),
    INDEX idx_product (product_id),
    INDEX idx_warehouse (warehouse_id),
    INDEX idx_transaction_type (transaction_type),
    INDEX idx_transaction_date (transaction_date),
    INDEX idx_reference_document (reference_document_type, reference_document_number),
    INDEX idx_batch_serial (batch_number, serial_number),
    INDEX idx_fiscal_period (fiscal_year, fiscal_month),
    INDEX idx_performed_by (performed_by),
    INDEX idx_created_at (created_at),
    INDEX idx_quantity_change (quantity_change),
    INDEX idx_from_to_warehouse (from_warehouse_id, to_warehouse_id)
);

-- 6. BẢNG BÁO CÁO TỒN KHO ĐỊNH KỲ (INVENTORY_SNAPSHOTS)
CREATE TABLE inventory_snapshots
(
    snapshot_id               INT PRIMARY KEY AUTO_INCREMENT,
    snapshot_date             DATE                                                                 NOT NULL,
    snapshot_time             TIME                                                                 NOT NULL,
    snapshot_type             ENUM ('DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY', 'AD_HOC') NOT NULL,
    product_id                INT                                                                  NOT NULL,
    warehouse_id              INT                                                                  NOT NULL,
    category_id               INT                                                                  NOT NULL,
    product_code              VARCHAR(50)                                                          NOT NULL,
    product_name              VARCHAR(200)                                                         NOT NULL,
    warehouse_code            VARCHAR(20)                                                          NOT NULL,
    warehouse_name            VARCHAR(100)                                                         NOT NULL,
    category_name             VARCHAR(100)                                                         NOT NULL,
    quantity_on_hand          INT                                                                  NOT NULL,
    quantity_reserved         INT                                                                  NOT NULL,
    quantity_available        INT                                                                  NOT NULL,
    quantity_incoming         INT            DEFAULT 0,
    quantity_outgoing         INT            DEFAULT 0,
    unit_cost                 DECIMAL(15, 2),
    total_value               DECIMAL(18, 2),
    avg_cost_last_30_days     DECIMAL(15, 2),
    avg_cost_last_90_days     DECIMAL(15, 2),
    min_stock_level           INT,
    max_stock_level           INT,
    reorder_point             INT,
    stock_coverage_days       INT,           -- Based on average daily usage
    turnover_ratio            DECIMAL(8, 4), -- Annual turnover
    days_of_supply            INT,
    last_receipt_date         DATE,
    last_issue_date           DATE,
    last_sale_date            DATE,
    days_since_last_movement  INT,
    abc_classification        ENUM ('A', 'B', 'C'),
    movement_frequency        ENUM ('FAST', 'MEDIUM', 'SLOW'),
    stock_status              ENUM ('NORMAL', 'LOW', 'OUT_OF_STOCK', 'OVERSTOCK', 'DEAD_STOCK'),
    aging_0_30_days_qty       INT            DEFAULT 0,
    aging_31_60_days_qty      INT            DEFAULT 0,
    aging_61_90_days_qty      INT            DEFAULT 0,
    aging_91_180_days_qty     INT            DEFAULT 0,
    aging_over_180_days_qty   INT            DEFAULT 0,
    aging_0_30_days_value     DECIMAL(15, 2) DEFAULT 0,
    aging_31_60_days_value    DECIMAL(15, 2) DEFAULT 0,
    aging_61_90_days_value    DECIMAL(15, 2) DEFAULT 0,
    aging_91_180_days_value   DECIMAL(15, 2) DEFAULT 0,
    aging_over_180_days_value DECIMAL(15, 2) DEFAULT 0,
    expiry_within_30_days_qty INT            DEFAULT 0,
    expiry_within_60_days_qty INT            DEFAULT 0,
    expiry_within_90_days_qty INT            DEFAULT 0,
    expired_qty               INT            DEFAULT 0,
    damaged_qty               INT            DEFAULT 0,
    quarantine_qty            INT            DEFAULT 0,
    fiscal_year               INT,
    fiscal_quarter            INT,
    fiscal_month              INT,
    fiscal_week               INT,
    currency_code             VARCHAR(3)     DEFAULT 'VND',
    exchange_rate             DECIMAL(12, 6) DEFAULT 1.000000,
    cost_center               VARCHAR(20),
    profit_center             VARCHAR(20),
    business_unit             VARCHAR(50),
    region                    VARCHAR(50),
    created_by                INT,
    created_at                TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products (product_id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouses (warehouse_id),
    FOREIGN KEY (category_id) REFERENCES categories (category_id),
    UNIQUE KEY uk_snapshot_product_warehouse (snapshot_date, snapshot_type, product_id, warehouse_id),
    INDEX idx_snapshot_date (snapshot_date),
    INDEX idx_snapshot_type (snapshot_type),
    INDEX idx_product (product_id),
    INDEX idx_warehouse (warehouse_id),
    INDEX idx_category (category_id),
    INDEX idx_fiscal_period (fiscal_year, fiscal_quarter, fiscal_month),
    INDEX idx_stock_status (stock_status),
    INDEX idx_abc_classification (abc_classification),
    INDEX idx_total_value (total_value),
    INDEX idx_aging_analysis (aging_over_180_days_qty, aging_over_180_days_value),
    INDEX idx_expiry_analysis (expiry_within_30_days_qty, expired_qty)
);

-- ========================================
-- SAMPLE DATA GENERATION PROCEDURES
-- ========================================

DELIMITER //

-- Procedure tạo dữ liệu mẫu cho Categories
CREATE PROCEDURE generate_sample_categories(IN num_categories INT)
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= num_categories
        DO
            INSERT INTO categories (category_code, category_name, description,
                                    is_active, sort_order, created_by)
            VALUES (CONCAT('CAT', LPAD(i, 5, '0')),
                    CONCAT('Category ', i),
                    CONCAT('Description for category ', i),
                    IF(RAND() > 0.1, TRUE, FALSE),
                    i,
                    1);
            SET i = i + 1;
        END WHILE;
END;

CALL generate_sample_categories(1000);
-- Procedure tạo dữ liệu mẫu cho Products
CREATE PROCEDURE generate_sample_products(IN num_products INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE max_category_id INT;

    SELECT MAX(category_id) INTO max_category_id FROM categories;

    WHILE i <= num_products
        DO
            INSERT INTO products (product_code, product_name, category_id, brand, model,
                                  description, cost_price, selling_price, min_stock_level,
                                  max_stock_level, reorder_point, is_active, created_by)
            VALUES (CONCAT('PRD', LPAD(i, 8, '0')),
                    CONCAT('Product ', i),
                    FLOOR(1 + RAND() * max_category_id),
                    CONCAT('Brand', FLOOR(1 + RAND() * 50)),
                    CONCAT('Model', FLOOR(1 + RAND() * 100)),
                    CONCAT('Description for product ', i),
                    ROUND(10 + RAND() * 1000, 2),
                    ROUND(15 + RAND() * 1500, 2),
                    FLOOR(5 + RAND() * 20),
                    FLOOR(100 + RAND() * 500),
                    FLOOR(10 + RAND() * 50),
                    IF(RAND() > 0.05, TRUE, FALSE),
                    1);
            SET i = i + 1;
        END WHILE;
END

-- Procedure tạo dữ liệu mẫu cho Warehouses
CREATE PROCEDURE generate_sample_warehouses(IN num_warehouses INT)
BEGIN DECLARE i INT DEFAULT 1000;

WHILE i <= num_warehouses
DO
    INSERT INTO warehouses (
            warehouse_code, warehouse_name, warehouse_type,
            city, country, total_area, max_capacity_weight,
            is_active, created_by
        ) VALUES (
            CONCAT('WH', LPAD(i, 3, '0')),
            CONCAT('Warehouse ', i),
            ELT(FLOOR(1 + RAND() * 4), 'MAIN', 'BRANCH', 'TRANSIT', 'VIRTUAL'),
            CONCAT('City ', i),
            'Vietnam',
            ROUND(1000 + RAND() * 9000, 2),
            ROUND(10000 + RAND() * 90000, 2),
            IF(RAND() > 0.1, TRUE, FALSE),
            1
        );
SET i = i + 1;
END WHILE;
END

DELIMITER ;

-- ========================================
-- PERFORMANCE TEST QUERIES
-- ========================================

-- Query 1: Báo cáo tồn kho theo sản phẩm và kho
-- SELECT p.product_code, p.product_name, w.warehouse_name,
--        i.quantity_on_hand, i.quantity_available, i.total_value
-- FROM inventory i
-- JOIN products p ON i.product_id = p.product_id
-- JOIN warehouses w ON i.warehouse_id = w.warehouse_id
-- WHERE i.quantity_available > 0;

-- Query 2: Báo cáo giao dịch theo thời gian
-- SELECT DATE(created_at) as transaction_date,
--        transaction_type,
--        COUNT(*) as transaction_count,
--        SUM(ABS(quantity_change)) as total_quantity,
--        SUM(total_cost) as total_value
-- FROM inventory_transactions
-- WHERE created_at >= DATE_SUB(NOW(), INTERVAL 30 DAY)
-- GROUP BY DATE(created_at), transaction_type;

-- Query 3: Phân tích ABC và aging
-- SELECT s.abc_classification,
--        COUNT(*) as product_count,
--        SUM(total_value) as total_value,
--        AVG(aging_over_180_days_qty) as avg_old_stock
-- FROM inventory_snapshots s
-- WHERE snapshot_date = CURDATE()
-- GROUP BY s.abc_classification;

-- ========================================
-- END OF SCHEMA
-- ========================================
CALL generate_sample_products(1000000);
CALL generate_sample_warehouses(10);

SELECT COUNT(*)
FROM products p
