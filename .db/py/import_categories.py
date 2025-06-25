import csv
import random
from faker import Faker
from datetime import datetime

fake = Faker()
OUTPUT_FILE = "categories.csv"
NUM_ROWS = 100000

def generate_category_row(i, parent_ids):
 category_code = f"CAT{str(i).zfill(5)}"
 category_name = f"{fake.word().capitalize()} {fake.word().capitalize()}"
 parent_category_id = random.choice(parent_ids) if parent_ids and random.random() < 0.2 else None
 description = fake.sentence(nb_words=10)
 is_active = 1 if random.random() > 0.1 else 0
 sort_order = i
 image_url = fake.image_url()
 created_by = random.randint(1, 5)
 updated_by = created_by
 created_at = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
 updated_at = created_at

 return [
     category_code, category_name, parent_category_id, description,
     is_active, sort_order, image_url, created_by, updated_by,
     created_at, updated_at
 ]

# CSV Header (bỏ AUTO_INCREMENT column)
header = [
 "category_code", "category_name", "parent_category_id", "description",
 "is_active", "sort_order", "image_url", "created_by", "updated_by",
 "created_at", "updated_at"
]

print(f"🔄 Generating {NUM_ROWS} category rows...")

# Build initial rows
rows = []
for i in range(1, NUM_ROWS + 1):
 parent_ids = list(range(1, i))  # Chỉ chọn parent từ các dòng trước
 rows.append(generate_category_row(i, parent_ids))

# Write to CSV
with open(OUTPUT_FILE, mode='w', newline='', encoding='utf-8') as file:
 writer = csv.writer(file)
 writer.writerow(header)
 writer.writerows(rows)

print(f"✅ Done! File '{OUTPUT_FILE}' generated with {NUM_ROWS} rows.")
