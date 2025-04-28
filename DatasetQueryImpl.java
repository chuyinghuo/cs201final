import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DatasetQueryImpl implements StudentMentalHealthQuery {
    private List<Record> records = new ArrayList<>();

    @Override
    public int loadDataset(String filePath) throws IOException {
        this.records = CSVLoader.loadRecords(filePath);
        return records.size();
    }

    @Override
    public List<Record> exactMatchQuery(String attribute, Object value) {
        List<Record> result = new ArrayList<>();
        String attrLower = attribute.toLowerCase().trim();

        for (Record record : records) {
            boolean match = false;
            switch (attrLower) {
                case "gender":
                    if (value instanceof String s)
                        match = record.getGender().equalsIgnoreCase(s.trim());
                    break;
                case "age":
                    if (value instanceof Number n)
                        match = (record.getAge() == n.doubleValue());
                    break;
                case "city":
                    if (value instanceof String s)
                        match = record.getCity().equalsIgnoreCase(s.trim());
                    break;
                case "profession":
                    if (value instanceof String s)
                        match = record.getProfession().equalsIgnoreCase(s.trim());
                    break;
                case "academic pressure":
                    if (value instanceof Number n)
                        match = (record.getAcademicPressure() == n.doubleValue());
                    break;
                case "study satisfaction":
                    if (value instanceof Number n)
                        match = (record.getStudySatisfaction() == n.doubleValue());
                    break;
                case "degree":
                    if (value instanceof String s)
                        match = record.getDegree().equalsIgnoreCase(s.trim());
                    break;
                case "have you ever had suicidal thoughts?":
                    match = parseBooleanInput(value) == record.getSuicidalThoughts();
                    break;
                case "family history of mental illness":
                    match = parseBooleanInput(value) == record.getFamilyHistoryMentalIllness();
                    break;
                case "depression":
                    if (value instanceof Number n)
                        match = (record.getDepression() == n.intValue());
                    break;
                case "financial stress":
                    if (value instanceof Number n)
                        match = (record.getFinancialStress() == n.doubleValue());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported attribute: " + attribute);
            }

            if (match) result.add(record);
        }

        return result;
    }

    public List<Record> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound) {
    if (lowerBound == null || upperBound == null) {
        throw new IllegalArgumentException("Bounds cannot be null.");
    }

    TreeMap<Comparable, List<Record>> bst = new TreeMap<>();

    // Insert records into the BST
    for (Record record : records) {
        Comparable key = null;

        switch (attribute.toLowerCase().trim()) {
            case "age": key = record.getAge(); break;
            case "academic pressure": key = record.getAcademicPressure(); break;
            case "study satisfaction": key = record.getStudySatisfaction(); break;
            case "depression": key = record.getDepression(); break;
            case "financial stress": key = record.getFinancialStress(); break;
            default:
                throw new IllegalArgumentException("Attribute '" + attribute + "' is not supported for range queries.");
        }

        if (key != null) {
            bst.computeIfAbsent(key, k -> new ArrayList<>()).add(record);
        }
    }

    // Use subMap to efficiently get range
    List<Record> result = new ArrayList<>();
    for (List<Record> group : bst.subMap(lowerBound, true, upperBound, true).values()) {
        result.addAll(group);
    }

    return result;
}

@Override
public double getDatasetStatistics(String attribute) {
    if (records.isEmpty()) {
        return 0.0; 
    }

    String attrLower = attribute.toLowerCase().trim();
    double sum = 0.0;
    int count = 0;

    for (Record record : records) {
        switch (attrLower) {
            case "Age":
                sum += record.getAge();
                count++;
                break;
            case "Academic Pressure":
                sum += record.getAcademicPressure();
                count++;
                break;
            case "Study Satisfaction":
                sum += record.getStudySatisfaction();
                count++;
                break;
            case "Financial Stress":
                sum += record.getFinancialStress();
                count++;
                break;
            default:
                throw new IllegalArgumentException("Unsupported attribute for statistics: " + attribute);
        }
    }

    return sum / count;
}

    private boolean parseBooleanInput(Object value) {
        if (value instanceof Boolean b) return b;
        if (value instanceof String s) return s.equalsIgnoreCase("yes");
        throw new IllegalArgumentException("Expected boolean or string 'yes'/'no'");
    }
}
