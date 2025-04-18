import java.io.*;
import java.util.*;

public class main201 {
    public static void main(String[] args) {
        String csvFile = "Student_Grading_Dataset.csv";
        List<String[]> data = new ArrayList<>();
        String[] headers = null;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if (isFirstLine) {
                    headers = row;
                    isFirstLine = false;
                } else {
                    data.add(row);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (headers != null) {
            System.out.println("Headers: " + Arrays.toString(headers));
        }
        for (int i = 0; i < Math.min(data.size(), 5); i++) {
            System.out.println(Arrays.toString(data.get(i)));
        }
    }
}
