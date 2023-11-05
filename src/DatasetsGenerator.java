import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class DatasetsGenerator {

    private static long seed = 42;
    private static Random rand = new Random(seed);

    public static void generateAndSaveDataset(int size, String fileName, String filePath) {
        int[] dataset = new int[size];

        // Generate angka random
        for (int i = 0; i < size; i++) {
            dataset[i] = rand.nextInt(size);
        }

        // Simpan random dataset
        saveToFile(dataset, filePath, fileName + "_random.txt");

        // Sort dataset
        Arrays.sort(dataset);

        // Simpan sorted dataset
        saveToFile(dataset, filePath, fileName + "_sorted.txt");

        // Reverse dataset
        for (int i = 0; i < dataset.length / 2; i++) {
            int temp = dataset[i];
            dataset[i] = dataset[dataset.length - 1 - i];
            dataset[dataset.length - 1 - i] = temp;
        }

        // Simpan reversed dataset
        saveToFile(dataset, filePath, fileName + "_reversed.txt");
    }

    public static void saveToFile(int[] dataset, String filePath, String fileName) {
        try {
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (int data : dataset) {
                bw.write(Integer.toString(data));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
