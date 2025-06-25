import csv
import random
from faker import Faker
from datetime import datetime, timedelta

fake = Faker()
OUTPUT_FILE = "inventory.csv"
NUM_ROWS = 2500000

# Giả định ID tồn tại
PRODUCT_ID_RANGE = (1, 1000000)
WAREHOUSE_ID_RANGE = (1, 300000)

def generate_inventory_row(i):
    product_id = random.randint(*PRODUCT_ID_RANGE)
    warehouse_id = random.randint(*WAREHOUSE_ID_RANGE)
    location_zone = random.choice(['Z1', 'Z2', 'Z3', 'A', 'B', 'C'])
    location_aisle = str(random.randint(1, 20))
    location_shelf = str(random.randint(1, 10))
    location_bin = str(random.randint(1, 50))
    batch_number = f"BATCH-{random.randint(1000,9999)}"
    serial_number = f"SN-{random.randint(100000,999999)}"
    lot_number = f"LOT-{random.randint(1000,9999)}"
    quantity_on_hand = random.randint(10, 1000)
    quantity_reserved = random.randint(0, quantity_on_hand)
    quantity_incoming = random.randint(0, 200)
    quantity_outgoing = random.randint(0, 200)
    unit_cost = round(random.uniform(10.0, 500.0), 2)

    manufacturing_date = datetime.now().date() - timedelta(days=random.randint(30, 365))
    expiry_date = manufacturing_date + timedelta(days=random.randint(180, 720))
    received_date = manufacturing_date + timedelta(days=random.randint(0, 30))
    last_counted_date = received_date + timedelta(days=random.randint(1, 60))
    last_counted_quantity = quantity_on_hand + random.randint(-20, 20)
    variance_quantity = quantity_on_hand - last_counted_quantity

    abc_classification = random.choice(['A', 'B', 'C'])
    movement_frequency = random.choice(['FAST', 'MEDIUM', 'SLOW'])
    storage_temperature = round(random.uniform(0.0, 25.0), 2)
    storage_humidity = round(random.uniform(30.0, 80.0), 2)
    quality_status = random.choice(['GOOD', 'DAMAGED', 'EXPIRED', 'QUARANTINE', 'REJECTED'])
    hold_reason = fake.sentence(nb_words=5) if quality_status in ['QUARANTINE', 'REJECTED'] else None
    supplier_id = random.randint(1, 10)
    purchase_order_number = f"PO-{random.randint(10000, 99999)}"
    goods_receipt_number = f"GR-{random.randint(10000, 99999)}"
    cycle_count_frequency_days = random.choice([30, 60, 90])
    next_cycle_count_date = last_counted_date + timedelta(days=cycle_count_frequency_days)
    is_hazardous = random.choice([0, 1])
    hazmat_class = f"HZ-{random.randint(1, 9)}" if is_hazardous else None
    special_handling_instructions = fake.sentence(nb_words=6) if is_hazardous else None
    insurance_value = round(unit_cost * quantity_on_hand * random.uniform(1.0, 1.2), 2)
    customs_value = round(unit_cost * quantity_on_hand * random.uniform(0.8, 1.0), 2)
    customs_cleared = random.choice([0, 1])
    quarantine_days_remaining = random.randint(0, 30) if quality_status == "QUARANTINE" else 0
    created_by = random.randint(1, 3)
    updated_by = created_by
    created_at = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    updated_at = created_at

    return [
        product_id, warehouse_id, location_zone, location_aisle,
        location_shelf, location_bin, batch_number, serial_number,
        lot_number, quantity_on_hand, quantity_reserved, quantity_incoming,
        quantity_outgoing, unit_cost, manufacturing_date, expiry_date,
        received_date, last_counted_date, last_counted_quantity, variance_quantity,
        abc_classification, movement_frequency, storage_temperature, storage_humidity,
        quality_status, hold_reason, supplier_id, purchase_order_number,
        goods_receipt_number, cycle_count_frequency_days, next_cycle_count_date,
        is_hazardous, hazmat_class, special_handling_instructions, insurance_value,
        customs_value, customs_cleared, quarantine_days_remaining,
        created_by, updated_by, created_at, updated_at
    ]

header = [
    "product_id", "warehouse_id", "location_zone", "location_aisle",
    "location_shelf", "location_bin", "batch_number", "serial_number",
    "lot_number", "quantity_on_hand", "quantity_reserved", "quantity_incoming",
    "quantity_outgoing", "unit_cost", "manufacturing_date", "expiry_date",
    "received_date", "last_counted_date", "last_counted_quantity", "variance_quantity",
    "abc_classification", "movement_frequency", "storage_temperature", "storage_humidity",
    "quality_status", "hold_reason", "supplier_id", "purchase_order_number",
    "goods_receipt_number", "cycle_count_frequency_days", "next_cycle_count_date",
    "is_hazardous", "hazmat_class", "special_handling_instructions", "insurance_value",
    "customs_value", "customs_cleared", "quarantine_days_remaining",
    "created_by", "updated_by", "created_at", "updated_at"
]

print(f"🔄 Generating {NUM_ROWS} inventory rows...")

with open(OUTPUT_FILE, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    writer.writerow(header)
    for i in range(1, NUM_ROWS + 1):
        writer.writerow(generate_inventory_row(i))

print(f"✅ Done! File '{OUTPUT_FILE}' generated with {NUM_ROWS} rows.")
