import csv
import random
from faker import Faker
from datetime import datetime, timedelta

fake = Faker()
OUTPUT_FILE = "inventory_snapshots.csv"
NUM_ROWS = 2000000  # 👈 Bạn có thể thay đổi số lượng dòng

# Constants
PRODUCT_ID_RANGE = (1, 1000000)
WAREHOUSE_ID_RANGE = (1, 300000)
CATEGORY_ID_RANGE = (1, 100000)
SNAPSHOT_TYPES = ['DAILY', 'WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY', 'AD_HOC']
ABC_CLASSES = ['A', 'B', 'C']
MOVEMENT_FREQ = ['FAST', 'MEDIUM', 'SLOW']
STOCK_STATUSES = ['NORMAL', 'LOW', 'OUT_OF_STOCK', 'OVERSTOCK', 'DEAD_STOCK']
CURRENCIES = ['VND', 'USD', 'EUR']

def generate_snapshot_row(i):
    snapshot_date = fake.date_between(start_date='-1y', end_date='today')
    snapshot_time = fake.time()
    snapshot_type = random.choice(SNAPSHOT_TYPES)
    product_id = random.randint(*PRODUCT_ID_RANGE)
    warehouse_id = random.randint(*WAREHOUSE_ID_RANGE)
    category_id = random.randint(*CATEGORY_ID_RANGE)

    product_code = f"P-{product_id:05d}"
    product_name = fake.word().capitalize() + " " + fake.color_name()
    warehouse_code = f"W-{warehouse_id:03d}"
    warehouse_name = fake.company()
    category_name = fake.word().capitalize()

    qoh = random.randint(0, 1000)
    reserved = random.randint(0, qoh)
    available = qoh - reserved
    incoming = random.randint(0, 200)
    outgoing = random.randint(0, 200)

    unit_cost = round(random.uniform(10.0, 1000.0), 2)
    total_value = round(qoh * unit_cost, 2)
    avg30 = round(unit_cost * random.uniform(0.8, 1.2), 2)
    avg90 = round(unit_cost * random.uniform(0.7, 1.3), 2)

    min_stock = random.randint(50, 200)
    max_stock = random.randint(300, 1000)
    reorder_point = random.randint(min_stock, max_stock)
    stock_days = random.randint(1, 120)
    turnover = round(random.uniform(0.5, 5.0), 4)
    days_supply = random.randint(5, 90)

    last_receipt = snapshot_date - timedelta(days=random.randint(1, 180))
    last_issue = snapshot_date - timedelta(days=random.randint(1, 180))
    last_sale = snapshot_date - timedelta(days=random.randint(1, 180))
    days_since_movement = (snapshot_date - last_issue).days

    abc = random.choice(ABC_CLASSES)
    move_freq = random.choice(MOVEMENT_FREQ)
    stock_status = random.choice(STOCK_STATUSES)

    aging_qty = [random.randint(0, 300) for _ in range(5)]
    aging_val = [round(q * unit_cost, 2) for q in aging_qty]

    expiry_30 = random.randint(0, 50)
    expiry_60 = random.randint(0, 50)
    expiry_90 = random.randint(0, 50)
    expired = random.randint(0, 50)
    damaged = random.randint(0, 30)
    quarantine = random.randint(0, 20)

    fiscal_year = snapshot_date.year
    fiscal_month = snapshot_date.month
    fiscal_quarter = (fiscal_month - 1) // 3 + 1
    fiscal_week = snapshot_date.isocalendar()[1]

    currency_code = random.choice(CURRENCIES)
    exchange_rate = round(random.uniform(0.9, 1.5), 6)

    cost_center = f"CC-{random.randint(10, 99)}"
    profit_center = f"PC-{random.randint(10, 99)}"
    business_unit = random.choice(['Retail', 'Wholesale', 'Online'])
    region = fake.state()
    created_by = random.randint(1, 20)
    created_at = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

    return [
        i, snapshot_date, snapshot_time, snapshot_type, product_id, warehouse_id,
        category_id, product_code, product_name, warehouse_code, warehouse_name,
        category_name, qoh, reserved, available, incoming, outgoing, unit_cost,
        total_value, avg30, avg90, min_stock, max_stock, reorder_point, stock_days,
        turnover, days_supply, last_receipt, last_issue, last_sale, days_since_movement,
        abc, move_freq, stock_status,
        *aging_qty, *aging_val,
        expiry_30, expiry_60, expiry_90, expired, damaged, quarantine,
        fiscal_year, fiscal_quarter, fiscal_month, fiscal_week, currency_code,
        exchange_rate, cost_center, profit_center, business_unit, region,
        created_by, created_at
    ]

header = [
    "snapshot_id", "snapshot_date", "snapshot_time", "snapshot_type", "product_id", "warehouse_id",
    "category_id", "product_code", "product_name", "warehouse_code", "warehouse_name",
    "category_name", "quantity_on_hand", "quantity_reserved", "quantity_available",
    "quantity_incoming", "quantity_outgoing", "unit_cost", "total_value",
    "avg_cost_last_30_days", "avg_cost_last_90_days", "min_stock_level", "max_stock_level",
    "reorder_point", "stock_coverage_days", "turnover_ratio", "days_of_supply",
    "last_receipt_date", "last_issue_date", "last_sale_date", "days_since_last_movement",
    "abc_classification", "movement_frequency", "stock_status",
    "aging_0_30_days_qty", "aging_31_60_days_qty", "aging_61_90_days_qty", "aging_91_180_days_qty", "aging_over_180_days_qty",
    "aging_0_30_days_value", "aging_31_60_days_value", "aging_61_90_days_value", "aging_91_180_days_value", "aging_over_180_days_value",
    "expiry_within_30_days_qty", "expiry_within_60_days_qty", "expiry_within_90_days_qty", "expired_qty",
    "damaged_qty", "quarantine_qty", "fiscal_year", "fiscal_quarter", "fiscal_month", "fiscal_week",
    "currency_code", "exchange_rate", "cost_center", "profit_center", "business_unit",
    "region", "created_by", "created_at"
]

print(f"🔄 Generating {NUM_ROWS} inventory snapshots...")

with open(OUTPUT_FILE, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    writer.writerow(header)
    for i in range(1, NUM_ROWS + 1):
        writer.writerow(generate_snapshot_row(i))

print(f"✅ Done! File '{OUTPUT_FILE}' generated with {NUM_ROWS} rows.")
