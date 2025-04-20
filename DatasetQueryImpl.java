import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Record> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound) {
        throw new UnsupportedOperationException("rangeQuery not implemented yet");
    }

    @Override
    public Map<String, Object> getDatasetStatistics(String attribute) {
        throw new UnsupportedOperationException("getDatasetStatistics not implemented yet");
    }

    private boolean parseBooleanInput(Object value) {
        if (value instanceof Boolean b) return b;
        if (value instanceof String s) return s.equalsIgnoreCase("yes");
        throw new IllegalArgumentException("Expected boolean or string 'yes'/'no'");
    }
}
