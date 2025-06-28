import csv
import time

def load_dataset(filename):
    data = []
    with open(filename, mode='r') as file:
        reader = csv.reader(file)
        for row in reader:
            data.append(f"{row[0]}/{row[1]}")
    return data

def extract_number(element):
    return int(element.split('/')[0])

def record_result(data, filename):
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        for item in data:
            num, text = item.split('/')
            writer.writerow([num, text])

def quick_sort(arr, low, high):
    if low < high:
        pi = partition(arr, low, high)
        quick_sort(arr, low, pi - 1)
        quick_sort(arr, pi + 1, high)

def partition(arr, low, high):
    pivot = extract_number(arr[high])
    i = low - 1

    for j in range(low, high):
        if extract_number(arr[j]) <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]

    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1


n_list = [1000, 5000, 10000, 50000, 100000, 500000, 1000000, 5000000, 10000000, 30000000]

for n in n_list:
    print(f"Processing dataset with {n} records")
    dataset_filename = f"dataset/generate_dataset/dataset_sample_{n}.csv"
    output_filename = f"dataset/quick_sort_result/quick_sort_{n}.csv"

    data = load_dataset(dataset_filename)

    start_time = time.perf_counter()
    quick_sort(data, 0, len(data) - 1)
    end_time = time.perf_counter()

    running_time_ms = (end_time - start_time) * 1000

    record_result(data, output_filename)
    print(f"Running time: {running_time_ms:.2f} ms\n")