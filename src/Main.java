import RadixSort.RadixSort;
import PeekSort.PeekSort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "./datasets/";
        DatasetsGenerator.generateAndSaveDataset(1000, "dataset_1000", filePath);
        DatasetsGenerator.generateAndSaveDataset(10000, "dataset_10000", filePath);
        DatasetsGenerator.generateAndSaveDataset(100000, "dataset_100000", filePath);

        int[] datasetSizes = {1000, 10000, 100000};
        String[] datasetTypes = {"random", "sorted", "reversed"};

        for (int size : datasetSizes) {
            for (String type : datasetTypes) {
                try {
                    int[] data = readDataFromFile(filePath + "dataset_" + size + "_" + type + ".txt");
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Processing dataset_" + size + "_" + type + ".txt\n");
                    compareSorts(size, type, data.clone(), "PeekSort");
                    compareSorts(size, type, data.clone(), "RadixSort");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static int[] readDataFromFile(String fileName) throws IOException {
        List<Integer> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(Integer.parseInt(line));
            }
        }
        return data.stream().mapToInt(i -> i).toArray();
    }

    private static void compareSorts(int datasetSize, String datasetType, int[] data, String sortType) {
        long startMemory = 0L, endMemory = 0L, startTime = 0L, endTime = 0L;

        if (sortType.equals("PeekSort")) {
            startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            startTime = System.nanoTime();
            PeekSort.peeksort(data, 0, data.length - 1);
            endTime = System.nanoTime();
            endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        } else if (sortType.equals("RadixSort")) {
            startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            startTime = System.nanoTime();
            RadixSort.radixSort(data);
            endTime = System.nanoTime();
            endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        }
        
        long duration = (endTime - startTime) / 1000000;  // convert to milliseconds
        long memoryUsed = (endMemory - startMemory);
        long memoryUsedInKB = (endMemory - startMemory) / 1024;  // convert to kilobytes
        
        System.out.println(sortType + " - " + datasetSize + " - " + datasetType + " :");
        System.out.println("Running Time: " + duration + " ms");
        System.out.println("Memory Used: " + memoryUsed + " bytes / " + memoryUsedInKB + " kilobytes\n");
    }
}
