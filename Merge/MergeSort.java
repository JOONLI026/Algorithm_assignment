package Merge;

import java.io.*;
import java.util.*;

public class MergeSort {

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

    public static void mergeSort(List<String> arr) {
        if (arr.size() > 1) {
            int mid = arr.size() / 2;
            List<String> L = new ArrayList<>(arr.subList(0, mid));
            List<String> R = new ArrayList<>(arr.subList(mid, arr.size()));

            mergeSort(L);
            mergeSort(R);

            int i = 0, j = 0, k = 0;

            while (i < L.size() && j < R.size()) {
                if (extractNumber(L.get(i)) < extractNumber(R.get(j))) {
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
    }

    public static void main(String[] args) {
        int[] nList = {1000, 5000, 10000, 50000, 100000, 500000, 1000000, 5000000, 10000000, 30000000};

        for (int n : nList) {
            System.out.println("Processing dataset with " + n + " records");

            String datasetFilename = "dataset/generate_dataset/dataset_sample_" + n + ".csv";
            String outputFilename = "dataset/merge_sort_result/merge_sort_" + n + ".csv";

            List<String> data = loadDataset(datasetFilename);

            long startTime = System.nanoTime();
            mergeSort(data);
            long endTime = System.nanoTime();

            double runningTimeMs = (endTime - startTime) / 1000000;

            recordResult(data, outputFilename);
            System.out.printf("Running time: %.2f ms\n\n", runningTimeMs);
        }
    }
}
