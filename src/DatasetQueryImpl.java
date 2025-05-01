import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
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
    // Create temporary maps to simulate indexing
    Map<String, Map<Object, List<Record>>> tempIndex = new HashMap<>();

    for (String attr : attributeValues.keySet()) {
        tempIndex.put(attr.toLowerCase().trim(), new HashMap<>());
    }

    // Build temporary indexes
    for (Record record : records) {
        for (String attr : tempIndex.keySet()) {
            Object key = switch (attr) {
                case "gender" -> record.getGender().toLowerCase();
                case "age" -> record.getAge();
                case "city" -> record.getCity().toLowerCase();
                case "profession" -> record.getProfession().toLowerCase();
                case "degree" -> record.getDegree().toLowerCase();
                case "academic pressure" -> record.getAcademicPressure();
                case "study satisfaction" -> record.getStudySatisfaction();
                case "depression" -> record.getDepression();
                case "financial stress" -> record.getFinancialStress();
                case "have you ever had suicidal thoughts?" -> record.getSuicidalThoughts();
                case "family history of mental illness" -> record.getFamilyHistoryMentalIllness();
                default -> throw new IllegalArgumentException("Unsupported attribute: " + attr);
            };

            tempIndex.get(attr).computeIfAbsent(key, k -> new ArrayList<>()).add(record);
        }
    }

    // Collect candidate lists for each attribute
    List<List<Record>> candidates = new ArrayList<>();
    for (Map.Entry<String, Object> entry : attributeValues.entrySet()) {
        String attr = entry.getKey().toLowerCase().trim();
        Object value = normalizeValue(attr, entry.getValue());

        List<Record> matching = tempIndex.get(attr).get(value);
        if (matching == null) return new ArrayList<>(); // No match
        candidates.add(matching);
    }

    // Intersect the lists
    List<Record> result = new ArrayList<>(candidates.get(0));
    for (int i = 1; i < candidates.size(); i++) {
        result.retainAll(candidates.get(i));
    }

    return result;
}

private Object normalizeValue(String attr, Object value) {
    return switch (attr) {
        case "gender", "city", "profession", "degree" -> value.toString().toLowerCase();
        case "have you ever had suicidal thoughts?", "family history of mental illness" -> parseBooleanInput(value);
        case "age", "academic pressure", "study satisfaction", "financial stress" -> ((Number) value).doubleValue();
        case "depression" -> ((Number) value).intValue();
        default -> throw new IllegalArgumentException("Unsupported attribute: " + attr);
    };
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
