import java.io.*;
import java.util.*;

public class QuickSort {

    public static List<String> loadDataset(String filename) {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(parts[0] + "/" + parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void recordResult(List<String> data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String item : data) {
                String[] parts = item.split("/");
                writer.write(parts[0] + "," + parts[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long extractNumber(String element) {
        return Long.parseLong(element.split("/")[0]);
    }

    public static int partition(List<String> arr, int low, int high) {
        long pivot = extractNumber(arr.get(high));
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (extractNumber(arr.get(j)) <= pivot) {
                i++;
                Collections.swap(arr, i, j);
            }
        }

        Collections.swap(arr, i + 1, high);
        return i + 1;
    }

    public static void quickSort(List<String> arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        String datasetFilename = "dataset/generate_dataset/dataset_sample_1000.csv";
        List<String> data = loadDataset(datasetFilename);
        String outputFilename = "dataset/quick_sort_result/quick_sort_" + data.size() + ".csv";

        long startTime = System.nanoTime();
        quickSort(data, 0, data.size() - 1);
        long endTime = System.nanoTime();

        double runningTimeMs = (endTime - startTime) / 1000000;

        recordResult(data, outputFilename);
        System.out.printf("Running time: %.2f ms\n", runningTimeMs);
    }
}
