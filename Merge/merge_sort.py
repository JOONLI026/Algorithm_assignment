import csv
import time

def load_dataset(filename):
    data = []
    with open(filename, mode='r') as file:
        reader = csv.reader(file)
        for row in reader:
            data.append(f"{row[0]}/{row[1]}")
    return data

def record_step(data, filename):
    with open(filename, 'w', newline='') as file:
        writer = csv.writer(file)
        for item in data:
            num, text = item.split('/')
            writer.writerow([num, text])

def extract_number(element):
    return int(element.split('/')[0])

def merge_sort(arr):
    if len(arr) > 1:
        mid = len(arr) // 2
        L = arr[:mid]
        R = arr[mid:]

        merge_sort(L)
        merge_sort(R)

        i = j = k = 0

        while i < len(L) and j < len(R):
            if extract_number(L[i]) < extract_number(R[j]):
                arr[k] = L[i]
                i += 1
            else:
                arr[k] = R[j]
                j += 1
            k += 1

        while i < len(L):
            arr[k] = L[i]
            i += 1
            k += 1

        while j < len(R):
            arr[k] = R[j]
            j += 1
            k += 1

dataset_filename = "dataset/generate_dataset/dataset_sample_1000.csv"
data = load_dataset(dataset_filename)
output_filename = f"dataset/merge_sort_result/merge_sort_{len(data)}.csv"

start_time = time.perf_counter()
merge_sort(data)
end_time = time.perf_counter()

running_time_ms = (end_time - start_time) * 1000

record_step(data, output_filename)
print(f"Running time: {running_time_ms:.2f} ms")
