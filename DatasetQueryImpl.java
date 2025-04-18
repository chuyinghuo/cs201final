import java.sql.Timestamp;
import java.util.*;

public class DatasetQueryImpl implements DatasetQuery<Record> {

    private List<Record> records;

    public DatasetQueryImpl(String filePath) {
        this.records = CSVLoader.loadRecords(filePath);
    }

    @Override
    public List<Record> averageQuery(String attribute, Timestamp startTime, Timestamp endTime) {
        double sum = 0;
        int count = 0;

        for (Record record : records) {
            Timestamp recordTimestamp = record.getTimestamp();

            if (recordTimestamp != null &&
                (recordTimestamp.after(startTime) && recordTimestamp.before(endTime))) {
                switch (attribute.toLowerCase()) {
                    case "cgpa":
                        sum += record.cgpa;
                        count++;
                        break;
                    case "study satisfaction":
                        sum += record.studySatisfaction;
                        count++;
                        break;
                    default:
                        System.err.println("Attribute not recognized for average query.");
                }
            }
        }

        double average = (count > 0) ? sum / count : 0.0;
        System.out.println("Average " + attribute + ": " + average);
        return Collections.emptyList();
    }

    @Override
    public List<Record> exactMatchQuery(String attribute, Object value) {
        // TODO: Implement logic
        return Collections.emptyList();
    }

    @Override
    public List<Record> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound) {
        // TODO: Implement logic
        return Collections.emptyList();
    }

    @Override
    public List<Record> rangeQuery(String attribute, Comparable upperBound) {
        // TODO: Implement logic
        return Collections.emptyList();
    }
}
