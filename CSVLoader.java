import java.io.*;
import java.util.*;

public class CSVLoader {
    public static List<Record> loadRecords(String filePath) {
        List<Record> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.replace("'", "").split(",", -1);
                if (tokens.length < 18) continue;

                try {
                    String gender = tokens[1].trim();
                    double age = Double.parseDouble(tokens[2].trim());
                    String city = tokens[3].trim();
                    String profession = tokens[4].trim();
                    double academicPressure = Double.parseDouble(tokens[5].trim());
                    double studySatisfaction = Double.parseDouble(tokens[8].trim());
                    String degree = tokens[12].trim();
                    boolean suicidalThoughts = tokens[13].trim().equalsIgnoreCase("Yes");
                    boolean familyHistory = tokens[16].trim().equalsIgnoreCase("Yes");
                    int depression = Integer.parseInt(tokens[17].trim());
                    double financialStress = Double.parseDouble(tokens[15].trim());

                    records.add(new Record(
                        gender, age, city, profession, academicPressure,
                        studySatisfaction, degree, suicidalThoughts,
                        familyHistory, depression, financialStress
                    ));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error parsing line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file:");
            e.printStackTrace();
        }
        return records;
    }
}