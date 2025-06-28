import java.io.*;
import java.util.*;

public class BinarySearchStep {

    public static List<int[]> loadDataset(String filename) {
        List<int[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int number = Integer.parseInt(parts[0]);
                String text = parts[1];
                data.add(new int[]{number, text.hashCode()});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void recordStep(List<String> steps, String outputFilename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            for (String step : steps) {
                writer.write(step);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void binarySearchStep(List<int[]> data, int target, String outputFilename) {
        int left = 0;
        int right = data.size() - 1;
        List<String> steps = new ArrayList<>();

        while (left <= right) {
            int mid = (left + right) / 2;
            int number = data.get(mid)[0];
            steps.add((mid + 1) + ": " + number);

            if (number == target) {
                recordStep(steps, outputFilename);
                return;
            }

            if (number < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        steps.add("-1");
        recordStep(steps, outputFilename);
    }

    public static void main(String[] args) {
        int targetValue = 4925744;
        String datasetFilename = "dataset/quick_sort_result/quick_sort_1000.csv";
        String outputFilename = "dataset/binary_search_result/binary_search_step_" + targetValue + ".txt";

        List<int[]> data = loadDataset(datasetFilename);
        binarySearchStep(data, targetValue, outputFilename);
    }
}
