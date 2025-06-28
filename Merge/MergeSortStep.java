package Merge;

import java.io.*;
import java.util.*;

public class MergeSortStep {

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

    public static void recordStep(List<List<String>> steps, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (List<String> step : steps) {
                writer.write("[" + String.join(", ", step) + "]");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long extractNumber(String element) {
        return Long.parseLong(element.split("/")[0]);
    }

    public static void mergeSortWithSteps(List<String> arr, List<List<String>> steps, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortWithSteps(arr, steps, left, mid);
            mergeSortWithSteps(arr, steps, mid + 1, right);

            merge(arr, left, mid, right);

            steps.add(new ArrayList<>(arr));
        }
    }

    public static void merge(List<String> arr, int left, int mid, int right) {
        List<String> L = new ArrayList<>(arr.subList(left, mid + 1));
        List<String> R = new ArrayList<>(arr.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < L.size() && j < R.size()) {
            if (extractNumber(L.get(i)) <= extractNumber(R.get(j))) {
                arr.set(k, L.get(i));
                i++;
            } else {
                arr.set(k, R.get(j));
                j++;
            }
            k++;
        }

        while (i < L.size()) {
            arr.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < R.size()) {
            arr.set(k, R.get(j));
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        String datasetFilename = "dataset/generate_dataset/dataset_sample_1000.csv";
        int startRow = 1;
        int endRow = 7;
        String outputFilename = "dataset/merge_sort_result/merge_sort_step_" + startRow + "_" + endRow + ".txt";

        List<String> data = loadDataset(datasetFilename, startRow, endRow);

        List<List<String>> steps = new ArrayList<>();
        steps.add(new ArrayList<>(data));

        mergeSortWithSteps(data, steps, 0, data.size() - 1);

        recordStep(steps, outputFilename);
    }
}
