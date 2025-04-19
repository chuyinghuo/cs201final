import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DatasetQueryImpl implements DatasetQuery<Record> {

    private List<Record> records;

    public DatasetQueryImpl(String filePath) {
        this.records = CSVLoader.loadRecords(filePath);
    }

    @Override
    public List<Record> exactMatchQuery(String attribute, Object value) {
        List<Record> result = new ArrayList<>();
        for (Record record : records) {
            boolean match = false;
            String attrLower = attribute.toLowerCase().trim();
            switch (attrLower) {
                case "id":
                    if (value instanceof Integer) {
                        match = (record.id == (Integer) value);
                    }
                    break;
                case "gender":
                    if (value instanceof String) {
                        match = record.gender.equalsIgnoreCase((String) value);
                    }
                    break;
                case "age":
                    if (value instanceof Double) {
                        match = (record.age == (Double) value);
                    } else if (value instanceof Integer) {
                        match = (record.age == ((Integer) value).doubleValue());
                    }
                    break;
                case "city":
                    if (value instanceof String) {
                        match = record.city.equalsIgnoreCase((String) value);
                    }
                    break;
                case "profession":
                    if (value instanceof String) {
                        match = record.profession.equalsIgnoreCase((String) value);
                    }
                    break;
                case "academic pressure":
                    if (value instanceof Double) {
                        match = (record.academicPressure == (Double) value);
                    } else if (value instanceof Integer) {
                        match = (record.academicPressure == ((Integer) value).doubleValue());
                    }
                    break;
                case "work pressure":
                    if (value instanceof Double) {
                        match = (record.workPressure == (Double) value);
                    } else if (value instanceof Integer) {
                        match = (record.workPressure == ((Integer) value).doubleValue());
                    }
                    break;
                case "cgpa":
                    if (value instanceof Double) {
                        match = (record.cgpa == (Double) value);
                    } else if (value instanceof Integer) {
                        match = (record.cgpa == ((Integer) value).doubleValue());
                    }
                    break;
                case "study satisfaction":
                    if (value instanceof Double) {
                        match = (record.studySatisfaction == (Double) value);
                    } else if (value instanceof Integer) {
                        match = (record.studySatisfaction == ((Integer) value).doubleValue());
                    }
                    break;
                case "job satisfaction":
                    if (value instanceof Double) {
                        match = (record.jobSatisfaction == (Double) value);
                    } else if (value instanceof Integer) {
                        match = (record.jobSatisfaction == ((Integer) value).doubleValue());
                    }
                    break;
                case "sleep duration":
                    if (value instanceof String) {
                        match = record.sleepDuration.equalsIgnoreCase((String) value);
                    }
                    break;
                case "dietary habits":
                    if (value instanceof String) {
                        match = record.dietaryHabits.equalsIgnoreCase((String) value);
                    }
                    break;
                case "degree":
                    if (value instanceof String) {
                        match = record.degree.equalsIgnoreCase((String) value);
                    }
                    break;
                case "have you ever had suicidal thoughts?": // Assuming the CSV header has a trailing '?'
                    if (value instanceof Boolean) {
                        match = (record.suicidalThoughts == (Boolean) value);
                    } else if (value instanceof String) {
                        String strValue = ((String) value).trim();
                        boolean boolValue = strValue.equalsIgnoreCase("yes");
                        match = (record.suicidalThoughts == boolValue);
                    }
                    break;
                case "work/study hours":
                    if (value instanceof Double) {
                        match = (record.workStudyHours == (Double) value);
                    } else if (value instanceof Integer) {
                        match = (record.workStudyHours == ((Integer) value).doubleValue());
                    }
                    break;
                case "financial stress":
                    if (value instanceof Double) {
                        match = (record.financialStress == (Double) value);
                    } else if (value instanceof Integer) {
                        match = (record.financialStress == ((Integer) value).doubleValue());
                    }
                    break;
                case "family history of mental illness":
                    if (value instanceof Boolean) {
                        match = (record.familyHistoryMentalIllness == (Boolean) value);
                    } else if (value instanceof String) {
                        String strValue = ((String) value).trim();
                        boolean boolValue = strValue.equalsIgnoreCase("yes");
                        match = (record.familyHistoryMentalIllness == boolValue);
                    }
                    break;
                case "depression":
                    if (value instanceof Integer) {
                        match = (record.depression == (Integer) value);
                    } else if (value instanceof Double) {
                        match = (record.depression == ((Double) value).intValue());
                    }
                    break;
                case "timestamp":
                    if (value instanceof Timestamp) {
                        match = record.getTimestamp().equals((Timestamp) value);
                    }
                    break;
                default:
                    System.err.println("Attribute not recognized: " + attribute);
                    break;
            }
            if (match) {
                result.add(record);
            }
        }
        return result;
    }

    @Override
    public List<Record> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rangeQuery'");
    }

    @Override
    public List<Record> averageQuery(String attribute, Timestamp startTime, Timestamp endTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'averageQuery'");
    }
    

}