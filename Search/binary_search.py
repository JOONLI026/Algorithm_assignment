import csv
import random
import time

def load_dataset(filename):
    data = []
    with open(filename, mode='r') as file:
        reader = csv.reader(file)
        for row in reader:
            data.append(int(row[0]))
    return data

def binary_search(data, target):
    start_time = time.perf_counter()
    left = 0
    right = len(data) - 1

    while left <= right:
        mid = (left + right) // 2
        number = data[mid]

        if number == target:
            return (time.perf_counter() - start_time) * 1000
        if number < target:
            left = mid + 1
        else:
            right = mid - 1

    return (time.perf_counter() - start_time) * 1000

n_list = [1000, 5000, 10000, 50000, 100000, 500000, 1000000, 5000000, 10000000, 30000000]

for n in n_list:
    print(f"Processing dataset with {n} records")
    dataset_filename = f"dataset/quick_sort_result/quick_sort_{n}.csv"
    output_filename = f"dataset/binary_search_result/binary_search_{n}.txt"

    data = load_dataset(dataset_filename)

    target_best = data[(len(data) - 1) // 2]
    target_avg = random.choice(data)
    target_worst = -1

    best_case_time = binary_search(data, target_best)
    avg_case_time = binary_search(data, target_avg)
    worst_case_time = binary_search(data, target_worst)

    with open(output_filename, 'w') as file:
        file.write(f"Best case time   : {best_case_time:.5f} ms\n")
        file.write(f"Average case time: {avg_case_time:.5f} ms\n")
        file.write(f"Worst case time  : {worst_case_time:.5f} ms\n")