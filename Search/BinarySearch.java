import java.io.*;
import java.util.*;

public class BinarySearch {

    public static List<long[]> loadDataset(String filename) {
        List<long[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                long number = Long.parseLong(parts[0]);
                data.add(new long[]{number});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static double binarySearch(List<long[]> data, long target) {
        long startTime = System.nanoTime();
        int left = 0;
        int right = data.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            long number = data.get(mid)[0];

            if (number == target) {
                return (System.nanoTime() - startTime) / 1000000.0;
            }
            if (number < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return (System.nanoTime() - startTime) / 1000000.0;
    }

    public static void main(String[] args) {
        String datasetFilename = "dataset/quick_sort_result/quick_sort_1000.csv";
        List<long[]> data = loadDataset(datasetFilename);

        String outputFilename = "dataset/binary_search_result/binary_search_" + data.size() + ".txt";

        Random rand = new Random();

        long target_best = data.get((data.size() - 1) / 2)[0];
        long target_avg = data.get(rand.nextInt(data.size()))[0];
        long target_worst = -1;

        double bestCaseTime = binarySearch(data, target_best);
        double avgCaseTime = binarySearch(data, target_avg);
        double worstCaseTime = binarySearch(data, target_worst);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            writer.write(String.format("Best case time   : %.5f ms\n", bestCaseTime));
            writer.write(String.format("Average case time: %.5f ms\n", avgCaseTime));
            writer.write(String.format("Worst case time  : %.5f ms\n", worstCaseTime));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
