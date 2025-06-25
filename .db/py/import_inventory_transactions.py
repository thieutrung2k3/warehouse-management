import csv
import random
from faker import Faker
from datetime import datetime, timedelta

fake = Faker()
OUTPUT_FILE = "inventory_transactions.csv"
NUM_ROWS = 1500000

PRODUCT_ID_RANGE = (1, 1000000)
WAREHOUSE_ID_RANGE = (1, 300000)
INVENTORY_ID_RANGE = (1, 1800000)
TRANSACTION_TYPES = ['IN', 'OUT', 'TRANSFER', 'ADJUSTMENT', 'COUNT']

def generate_transaction_row(i):
    transaction_number = f"TXN-{str(i).zfill(6)}"
    inventory_id = random.randint(*INVENTORY_ID_RANGE)
    product_id = random.randint(*PRODUCT_ID_RANGE)
    warehouse_id = random.randint(*WAREHOUSE_ID_RANGE)
    transaction_type = random.choice(TRANSACTION_TYPES)
    transaction_subtype = random.choice(['PURCHASE', 'SALE', 'RETURN', 'DAMAGED', 'ADJUSTMENT'])
    reference_document_type = random.choice(['PO', 'SO', 'TO', 'ADJ'])
    reference_document_number = f"{reference_document_type}-{random.randint(1000, 9999)}"
    reference_line_number = random.randint(1, 10)

    quantity_before = random.randint(50, 500)
    quantity_change = random.randint(-50, 50)
    quantity_after = quantity_before + quantity_change

    unit_cost = round(random.uniform(10.0, 300.0), 2)
    total_cost = round(quantity_change * unit_cost, 2)

    batch_number = f"BATCH-{random.randint(1000,9999)}"
    serial_number = f"SN-{random.randint(100000,999999)}"
    lot_number = f"LOT-{random.randint(1000,9999)}"
    expiry_date = datetime.now().date() + timedelta(days=random.randint(30, 730))

    from_location = fake.street_address() if transaction_type == 'TRANSFER' else None
    to_location = fake.street_address() if transaction_type == 'TRANSFER' else None
    from_warehouse_id = random.randint(*WAREHOUSE_ID_RANGE) if transaction_type == 'TRANSFER' else None
    to_warehouse_id = random.randint(*WAREHOUSE_ID_RANGE) if transaction_type == 'TRANSFER' else None

    reason_code = f"R{random.randint(100, 999)}"
    reason_description = fake.sentence(nb_words=6)
    performed_by = random.randint(1, 10)
    approved_by = random.randint(1, 10)
    customer_id = random.randint(1, 50)
    supplier_id = random.randint(1, 50)
    carrier_id = random.randint(1, 20)
    shipping_method = random.choice(['TRUCK', 'AIR', 'SEA'])
    tracking_number = f"TRK-{random.randint(100000, 999999)}"

    txn_date = datetime.now().date() - timedelta(days=random.randint(1, 365))
    txn_time = datetime.now().time().replace(microsecond=0)

    fiscal_year = txn_date.year
    fiscal_month = txn_date.month
    fiscal_week = txn_date.isocalendar()[1]

    shift = random.choice(['Morning', 'Evening', 'Night'])
    workstation = f"WS-{random.randint(1, 50)}"
    device_id = f"DEV-{random.randint(100, 999)}"
    gps_latitude = round(fake.latitude(), 8)
    gps_longitude = round(fake.longitude(), 8)
    temperature_at_transaction = round(random.uniform(10.0, 40.0), 2)
    humidity_at_transaction = round(random.uniform(30.0, 80.0), 2)
    quality_check_passed = random.choice([True, False])
    quality_check_notes = fake.sentence(nb_words=8) if not quality_check_passed else None
    cost_center = f"CC-{random.randint(10, 99)}"
    project_code = f"PRJ-{random.randint(1000, 9999)}"
    gl_account = f"GL-{random.randint(10000, 99999)}"

    is_reversed = False
    reversal_reason = None
    created_by = random.randint(1, 5)
    created_at = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    return [
        transaction_number, inventory_id, product_id, warehouse_id,
        transaction_type, transaction_subtype, reference_document_type,
        reference_document_number, reference_line_number, quantity_before,
        quantity_change, quantity_after, unit_cost, total_cost, batch_number,
        serial_number, lot_number, expiry_date, from_location, to_location,
        from_warehouse_id, to_warehouse_id, reason_code, reason_description,
        performed_by, approved_by, customer_id, supplier_id, carrier_id,
        shipping_method, tracking_number, txn_date, txn_time,
        fiscal_year, fiscal_month, fiscal_week, shift, workstation, device_id,
        gps_latitude, gps_longitude, temperature_at_transaction,
        humidity_at_transaction, quality_check_passed, quality_check_notes,
        cost_center, project_code, gl_account, is_reversed,
        reversal_reason, created_by, created_at
    ]

header = [
    "transaction_number", "inventory_id", "product_id", "warehouse_id",
    "transaction_type", "transaction_subtype", "reference_document_type",
    "reference_document_number", "reference_line_number", "quantity_before",
    "quantity_change", "quantity_after", "unit_cost", "total_cost", "batch_number",
    "serial_number", "lot_number", "expiry_date", "from_location", "to_location",
    "from_warehouse_id", "to_warehouse_id", "reason_code", "reason_description",
    "performed_by", "approved_by", "customer_id", "supplier_id", "carrier_id",
    "shipping_method", "tracking_number", "transaction_date", "transaction_time",
    "fiscal_year", "fiscal_month", "fiscal_week", "shift", "workstation", "device_id",
    "gps_latitude", "gps_longitude", "temperature_at_transaction",
    "humidity_at_transaction", "quality_check_passed", "quality_check_notes",
    "cost_center", "project_code", "gl_account", "is_reversed",
    "reversal_reason", "created_by", "created_at"
]

print(f"🔄 Generating {NUM_ROWS} inventory transactions...")

with open(OUTPUT_FILE, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    writer.writerow(header)
    for i in range(1, NUM_ROWS + 1):
        row = generate_transaction_row(i)
        writer.writerow(row)

print(f"✅ Done! File '{OUTPUT_FILE}' generated with {NUM_ROWS} transactions.")
