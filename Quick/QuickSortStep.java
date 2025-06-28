import java.io.*;
import java.util.*;

public class QuickSortStep {

    public static List<String> loadDataset(String filename, int startRow, int endRow) {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int idx = 1;
            while ((line = br.readLine()) != null) {
                if (idx >= startRow && idx <= endRow) {
                    String[] parts = line.split(",");
                    data.add(parts[0] + "/" + parts[1]);
                }
                if (idx > endRow) break;
                idx++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void recordSteps(List<String> steps, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String step : steps) {
                writer.write(step);
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
        long pivot = extractNumber(arr.get(high)); // change to long
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

    public static void quickSortWithSteps(List<String> arr, int low, int high, List<String> steps) {
        if (low < high) {
            int pi = partition(arr, low, high);
            steps.add("pi=" + (pi + 1) + " [" + String.join(", ", arr) + "]");
            quickSortWithSteps(arr, low, pi - 1, steps);
            quickSortWithSteps(arr, pi + 1, high, steps);
        }
    }

    public static void main(String[] args) {
        String datasetFilename = "dataset/generate_dataset/dataset_sample_1000.csv";
        int startRow = 1;
        int endRow = 7;
        String outputFilename = "dataset/quick_sort_result/quick_sort_step_" + startRow + "_" + endRow + ".txt";

        List<String> data = loadDataset(datasetFilename, startRow, endRow);
        List<String> steps = new ArrayList<>();
        steps.add("[" + String.join(", ", data) + "]");

        quickSortWithSteps(data, 0, data.size() - 1, steps);
        recordSteps(steps, outputFilename);
    }
}
