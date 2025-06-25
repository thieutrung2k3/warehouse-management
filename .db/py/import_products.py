import csv
import random
from faker import Faker
from datetime import datetime

fake = Faker()
OUTPUT_FILE = "products.csv"
NUM_ROWS = 1000000  # 👉 Sửa tại đây để sinh số lượng tuỳ ý
CATEGORY_ID_RANGE = (1, 100000)  # 👉 Giả sử đã có 100 categories

def generate_dimensions():
    l = random.randint(10, 100)
    w = random.randint(10, 100)
    h = random.randint(10, 100)
    return f"{l}x{w}x{h}"

def generate_product_row(i):
    product_code = f"PRD{str(i).zfill(8)}"
    product_name = f"{fake.color_name()} {fake.word().capitalize()} {fake.word().capitalize()}"
    category_id = random.randint(*CATEGORY_ID_RANGE)
    brand = fake.company()
    model = f"Model-{random.randint(100, 999)}"
    description = fake.text(max_nb_chars=100)
    specifications = '{"warranty": "12 months", "certified": true}'
    unit_of_measure = "PCS"
    weight = round(random.uniform(0.1, 10.0), 3)
    dimensions = generate_dimensions()
    color = fake.color_name()
    material = random.choice(["Plastic", "Metal", "Wood", "Glass", "Rubber"])
    origin_country = fake.country()
    manufacturer = fake.company()
    supplier_code = f"SUP{random.randint(1000, 9999)}"
    barcode = str(fake.random_number(digits=12))
    qr_code = f"QR-{i}"
    cost_price = round(random.uniform(10, 500), 2)
    selling_price = round(cost_price + random.uniform(5, 200), 2)
    min_stock_level = random.randint(1, 10)
    max_stock_level = random.randint(100, 500)
    reorder_point = random.randint(5, 50)
    lead_time_days = random.randint(1, 15)
    shelf_life_days = random.randint(30, 365)
    storage_temperature_min = round(random.uniform(0, 10), 2)
    storage_temperature_max = round(random.uniform(11, 25), 2)
    storage_humidity_max = round(random.uniform(40, 90), 2)
    is_hazardous = random.choice([0, 1])
    is_fragile = random.choice([0, 1])
    is_perishable = random.choice([0, 1])
    is_active = 1 if random.random() > 0.05 else 0
    tax_rate = round(random.uniform(0, 10), 2)
    discount_rate = round(random.uniform(0, 30), 2)
    created_by = random.randint(1, 3)
    updated_by = created_by
    created_at = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
    updated_at = created_at

    return [
        product_code, product_name, category_id, brand, model,
        description, specifications, unit_of_measure, weight,
        dimensions, color, material, origin_country, manufacturer,
        supplier_code, barcode, qr_code, cost_price, selling_price,
        min_stock_level, max_stock_level, reorder_point, lead_time_days,
        shelf_life_days, storage_temperature_min, storage_temperature_max,
        storage_humidity_max, is_hazardous, is_fragile, is_perishable,
        is_active, tax_rate, discount_rate, created_by, updated_by,
        created_at, updated_at
    ]

header = [
    "product_code", "product_name", "category_id", "brand", "model",
    "description", "specifications", "unit_of_measure", "weight",
    "dimensions", "color", "material", "origin_country", "manufacturer",
    "supplier_code", "barcode", "qr_code", "cost_price", "selling_price",
    "min_stock_level", "max_stock_level", "reorder_point", "lead_time_days",
    "shelf_life_days", "storage_temperature_min", "storage_temperature_max",
    "storage_humidity_max", "is_hazardous", "is_fragile", "is_perishable",
    "is_active", "tax_rate", "discount_rate", "created_by", "updated_by",
    "created_at", "updated_at"
]

print(f"🔄 Generating {NUM_ROWS} product rows...")

with open(OUTPUT_FILE, mode='w', newline='', encoding='utf-8') as file:
    writer = csv.writer(file)
    writer.writerow(header)
    for i in range(1, NUM_ROWS + 1):
        writer.writerow(generate_product_row(i))

print(f"✅ File '{OUTPUT_FILE}' generated with {NUM_ROWS} rows.")
