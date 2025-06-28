import csv

def load_dataset(filename):
    data = []
    with open(filename, mode='r') as file:
        reader = csv.reader(file)
        for row in reader:
            data.append((int(row[0]), row[1]))
    return data

def record_step(step_list, filename):
    with open(filename, 'w') as file:
        for step in step_list:
            file.write(step + '\n')

def binary_search_step(data, target, output_filename):
    left = 0
    right = len(data) - 1
    steps = []

    while left <= right:
        mid = (left + right) // 2
        number, text = data[mid]
        steps.append(f"{mid + 1}: {number}/{text}")

        if number == target:
            record_step(steps, output_filename)
            return

        if number < target:
            left = mid + 1
        else:
            right = mid - 1

    steps.append("-1")
    record_step(steps, output_filename)
    return

target_value = 1018802776
dataset_filename = "dataset/quick_sort_result/quick_sort_1000.csv"
output_filename = f"dataset/binary_search_result/binary_search_step_{target_value}.txt"

data = load_dataset(dataset_filename)
binary_search_step(data, target_value, output_filename)