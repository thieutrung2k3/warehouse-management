import csv
import random
from faker import Faker
from datetime import datetime, timedelta

fake = Faker()
OUTPUT_FILE = "warehouses.csv"
NUM_ROWS = 300000

def random_enum(enum_list):
    return random.choice(enum_list)

def random_date(start_days_ago=365, end_days_future=365):
    base = datetime.now()
    return (base - timedelta(days=random.randint(0, start_days_ago))).date(), (base + timedelta(days=random.randint(0, end_days_future))).date()

def generate_warehouse_row(i):
    warehouse_code = f"WH{str(i).zfill(4)}"
    warehouse_name = f"{random_enum(['Central', 'Regional', 'Transit', 'Backup'])} Warehouse {i}"
    warehouse_type = random_enum(['MAIN', 'BRANCH', 'TRANSIT', 'VIRTUAL'])
    address_line1 = fake.street_address()
    address_line2 = fake.secondary_address()
    city = fake.city()
    state_province = fake.state()
    postal_code = fake.postcode()
    country = fake.country()
    phone = fake.phone_number()
    email = fake.company_email()
    manager_name = fake.name()
    manager_phone = fake.phone_number()
    manager_email = fake.email()
    total_area = round(random.uniform(1000, 10000), 2)
    storage_area = round(total_area * random.uniform(0.6, 0.9), 2)
    office_area = round(total_area - storage_area, 2)
    loading_docks = random.randint(1, 10)
    storage_zones = random.randint(1, 5)
    max_capacity_weight = round(random.uniform(50000, 500000), 2)
    max_capacity_volume = round(random.uniform(2000, 10000), 2)
    current_utilization_percent = round(random.uniform(10.0, 95.0), 2)
    temperature_controlled = random.choice([0, 1])
    humidity_controlled = random.choice([0, 1])
    security_level = random_enum(['LOW', 'MEDIUM', 'HIGH'])
    operating_hours = "08:00 - 17:00"
    timezone = random.choice(['Asia/Ho_Chi_Minh', 'UTC', 'Asia/Bangkok'])
    is_active = 1 if random.random() > 0.05 else 0
    certification_iso = random.choice(['ISO 9001', 'ISO 22000', 'ISO 14001', None])
    certification_gmp = random.choice([0, 1])
    last_inspection_date, next_inspection_date = random_date()
    insurance_policy_number = f"INS{random.randint(100000, 999999)}"
    insurance_expiry_date = datetime.now().date() + timedelta(days=random.randint(30, 365))
    created_by = random.randint(1, 3)
    updated_by = created_by
    created_at = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    updated_at = created_at

    return [
        warehouse_code, warehouse_name, warehouse_type, address_line1,
        address_line2, city, state_province, postal_code, country,
        phone, email, manager_name, manager_phone, manager_email,
        total_area, storage_area, office_area, loading_docks, storage_zones,
        max_capacity_weight, max_capacity_volume, current_utilization_percent,
        temperature_controlled, humidity_controlled, security_level,
        operating_hours, timezone, is_active, certification_iso,
        certification_gmp, last_inspection_date, next_inspection_date,
        insurance_policy_number, insurance_expiry_date, created_by,
        updated_by, created_at, updated_at
    ]

header = [
    "warehouse_code", "warehouse_name", "warehouse_type", "address_line1",
    "address_line2", "city", "state_province", "postal_code", "country",
    "phone", "email", "manager_name", "manager_phone", "manager_email",
    "total_area", "storage_area", "office_area", "loading_docks", "storage_zones",
    "max_capacity_weight", "max_capacity_volume", "current_utilization_percent",
    "temperature_controlled", "humidity_controlled", "security_level",
    "operating_hours", "timezone", "is_active", "certification_iso",
    "certification_gmp", "last_inspection_date", "next_inspection_date",
    "insurance_policy_number", "insurance_expiry_date", "created_by",
    "updated_by", "created_at", "updated_at"
]

print(f"🔄 Generating {NUM_ROWS} warehouse rows...")

with open(OUTPUT_FILE, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    writer.writerow(header)
    for i in range(1, NUM_ROWS + 1):
        writer.writerow(generate_warehouse_row(i))

print(f"✅ Done! File '{OUTPUT_FILE}' generated with {NUM_ROWS} rows.")
