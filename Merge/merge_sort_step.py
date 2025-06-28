import csv

def load_dataset(filename, start_row, end_row):
    data = []
    with open(filename, mode='r') as file:
        reader = csv.reader(file)
        for idx, row in enumerate(reader, start=1):
            if start_row <= idx <= end_row:
                data.append(f"{row[0]}/{row[1]}")
            if idx > end_row:
                break
    return data

def record_step(steps, filename):
    with open(filename, 'w') as file:
        for step in steps:
            file.write('[' + ', '.join(step) + ']\n')

def extract_number(element):
    return int(element.split('/')[0])

def merge_sort_with_steps(arr, steps, left, right):
    if left < right:
        
        mid = (left + right) // 2
        merge_sort_with_steps(arr, steps, left, mid)
        merge_sort_with_steps(arr, steps, mid + 1, right)
        merge(arr, left, mid, right)

        steps.append(arr.copy())

def merge(arr, left, mid, right):
    L = arr[left:mid + 1]
    R = arr[mid + 1:right + 1]

    i = 0
    j = 0
    k = left

    while i < len(L) and j < len(R):
        if extract_number(L[i]) <= extract_number(R[j]):
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
start_row = 1
end_row = 7
output_filename = f"dataset/merge_sort_result/merge_sort_step_{start_row}_{end_row}.txt"

data = load_dataset(dataset_filename, start_row, end_row)

steps = []
steps.append(data.copy())

merge_sort_with_steps(data, steps, 0, len(data) - 1)
record_step(steps, output_filename)
