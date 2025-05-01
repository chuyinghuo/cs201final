import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
public class DatasetQueryImpl implements StudentMentalHealthQuery {
    private List<Record> records = new ArrayList<>();

    // Add this package-private method for testing
    void setTestRecords(List<Record> records) {
        this.records = records;
    }

    public void clearRecordsForTesting() {
        this.records = new ArrayList<>();
    }
        
    @Override
    public int loadDataset(String filePath) throws IOException {
        this.records = CSVLoader.loadRecords(filePath);
        return records.size();
    }

    @Override
    public List<Record> exactMatchQuery(Map<String, Object> attributeValues) {
        List<Record> result = new ArrayList<>();
    
        for (Record record : records) {
            boolean match = true;
            for (Map.Entry<String, Object> entry : attributeValues.entrySet()) {
                String attr = entry.getKey().toLowerCase().trim();
                Object value = entry.getValue();
                boolean currentMatch = switch (attr) {
                    case "gender" -> record.getGender().equalsIgnoreCase(value.toString());
                    case "age" -> record.getAge() == ((Number) value).doubleValue();
                    case "city" -> record.getCity().equalsIgnoreCase(value.toString());
                    case "profession" -> record.getProfession().equalsIgnoreCase(value.toString());
                    case "degree" -> record.getDegree().equalsIgnoreCase(value.toString());
                    case "academic pressure" -> record.getAcademicPressure() == ((Number) value).doubleValue();
                    case "study satisfaction" -> record.getStudySatisfaction() == ((Number) value).doubleValue();
                    case "depression" -> record.getDepression() == ((Number) value).intValue();
                    case "financial stress" -> record.getFinancialStress() == ((Number) value).doubleValue();
                    case "have you ever had suicidal thoughts?" ->
                        parseBooleanInput(value) == record.getSuicidalThoughts();
                    case "family history of mental illness" ->
                        parseBooleanInput(value) == record.getFamilyHistoryMentalIllness();
                    default -> throw new IllegalArgumentException("Unsupported attribute: " + attr);
                };
    
                if (!currentMatch) {
                    match = false;
                    break;
                }
            }
            if (match) result.add(record);
        }
    
        return result;
    }

    @Override
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
                case "age":
                    sum += record.getAge();
                    count++;
                    break;
                case "academic pressure":
                    sum += record.getAcademicPressure();
                    count++;
                    break;
                case "study satisfaction":
                    sum += record.getStudySatisfaction();
                    count++;
                    break;
                case "financial stress":
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
