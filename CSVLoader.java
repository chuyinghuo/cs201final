import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class CSVLoader {
    public static List<Record> loadRecords(String filePath) {
        List<Record> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                // Remove extra quotes and split by commas
                String[] tokens = line.replace("'", "").split(",", -1);
                if (tokens.length < 19) continue; // <-- 19 now if timestamp is included

                Record r = new Record(
                    Integer.parseInt(tokens[0].trim()),
                    tokens[1].trim(),
                    Double.parseDouble(tokens[2].trim()),
                    tokens[3].trim(),
                    tokens[4].trim(),
                    Double.parseDouble(tokens[5].trim()),
                    Double.parseDouble(tokens[6].trim()),
                    Double.parseDouble(tokens[7].trim()),
                    Double.parseDouble(tokens[8].trim()),
                    Double.parseDouble(tokens[9].trim()),
                    tokens[10].trim(),
                    tokens[11].trim(),
                    tokens[12].trim(),
                    tokens[13].trim().equalsIgnoreCase("yes"),
                    Double.parseDouble(tokens[14].trim()),
                    Double.parseDouble(tokens[15].trim()),
                    tokens[16].trim().equalsIgnoreCase("yes"),
                    Integer.parseInt(tokens[17].trim()),
                    Timestamp.valueOf(tokens[18].trim()) // <-- parse timestamp
                );

                records.add(r);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error reading or parsing line:");
            e.printStackTrace();
        }

        return records;
    }
}
