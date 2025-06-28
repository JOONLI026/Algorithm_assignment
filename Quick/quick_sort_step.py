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

def extract_number(element):
    return int(element.split('/')[0])

def record_steps(steps, filename):
    with open(filename, 'w') as file:
        for step in steps:
            if isinstance(step, str):
                file.write(step + '\n')
            else:
                formatted_step = '[' + ', '.join(step) + ']\n'
                file.write(formatted_step)

def quick_sort_with_steps(arr, low, high, steps):
    if low < high:
        pi = partition(arr, low, high)
        steps.append(f"pi={pi} [{', '.join(arr)}]")
        quick_sort_with_steps(arr, low, pi - 1, steps)
        quick_sort_with_steps(arr, pi + 1, high, steps)


def partition(arr, low, high):
    pivot = extract_number(arr[high])
    i = low - 1

    for j in range(low, high):
        if extract_number(arr[j]) <= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]

    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1

dataset_filename = "dataset/generate_dataset/dataset_sample_1000.csv"
start_row = 1
end_row = 7
output_filename = f"dataset/quick_sort_result/quick_sort_step_{start_row}_{end_row}.txt"

data = load_dataset(dataset_filename, start_row, end_row)
steps = []
steps.append(data.copy())

quick_sort_with_steps(data, 0, len(data) - 1, steps)
record_steps(steps, output_filename)
