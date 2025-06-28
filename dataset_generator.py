import random
import string
import csv
import time

def generate_integer(min, max, n):
    return random.sample(range(min, max + 1), n)

def generate_strings(min, max, n):
    strings_list = set()
    while len(strings_list) < n:
        length = random.randint(min, max)
        string_result = ''.join(random.choices(string.ascii_lowercase, k=length))
        strings_list.add(string_result)
    return list(strings_list)


n_list = [1000, 5000, 10000, 50000, 100000, 500000, 1000000, 5000000, 10000000, 30000000]
for n in n_list:
    start_time = time.perf_counter()

    integers = generate_integer(0, 10000000000, n)
    strings = generate_strings(4, 6, n)

    end_time = time.perf_counter()
    running_time_ms = (end_time - start_time) * 1000

    filename = f"dataset/generate_dataset/dataset_sample_{n}.csv"

    with open(filename, mode='w', newline='') as file:
        writer = csv.writer(file)
        for num, text in zip(integers, strings):
            writer.writerow([num, text])

    print(f"Dataset generated with {n} records in {running_time_ms:.2f} ms")