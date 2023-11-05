import RadixSort.RadixSort;
import PeekSort.PeekSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filePath = "./datasets/";
        String outputFilePath = "./comparison_results.txt";

        try {
            File file = new File(outputFilePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter eraser = new BufferedWriter(new FileWriter(outputFilePath, false));
            eraser.write(""); // overwrites the file with an empty string; to keep only the latest result
            eraser.close();

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, true));

            DatasetsGenerator.generateAndSaveDataset(1000, "dataset_1000", filePath);
            DatasetsGenerator.generateAndSaveDataset(10000, "dataset_10000", filePath);
            DatasetsGenerator.generateAndSaveDataset(100000, "dataset_100000", filePath);

            int[] datasetSizes = {1000, 10000, 100000};
            String[] datasetTypes = {"random", "sorted", "reversed"};

            for (int size : datasetSizes) {
                for (String type : datasetTypes) {
                    int[] data = readDataFromFile(filePath + "dataset_" + size + "_" + type + ".txt");
                    writer.write("--------------------------------------------------------\n");
                    writer.write("Processing dataset_" + size + "_" + type + ".txt\n\n");
                    
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Processing dataset_" + size + "_" + type + ".txt\n");
                    for (int i = 1; i <= 5; i++) {
                        compareSorts(size, type, data.clone(), "PeekSort", writer, i);
                        compareSorts(size, type, data.clone(), "RadixSort", writer, i);
                    }
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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

    private static void compareSorts(int datasetSize, String datasetType, int[] data, String sortType, BufferedWriter writer, int iteration) {
        try {
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
    
            double duration = ((double) (endTime - startTime)) / 1_000_000;  // convert to milliseconds
            double memoryUsed = (double) endMemory - startMemory;
            double memoryUsedInKB = memoryUsed / 1024;  // convert to kilobytes
            
            writer.write(sortType + " - " + datasetSize + " - " + datasetType + " - Iteration: " + iteration + ":\n");
            writer.write("Running Time: " + duration + " ms\n");
            writer.write("Memory Used: " + memoryUsed + " bytes / " + memoryUsedInKB + " kilobytes\n\n");
            
            System.out.println(sortType + " - " + datasetSize + " - " + datasetType + " - Iteration: " + iteration + ":\n");
            System.out.println("Running Time: " + duration + " ms");
            System.out.println("Memory Used: " + memoryUsed + " bytes / " + memoryUsedInKB + " kilobytes\n");
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
