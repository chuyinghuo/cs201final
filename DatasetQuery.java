import java.sql.Timestamp;
import java.util.List;

public interface DatasetQuery<T> {
    List<T> exactMatchQuery(String attribute, Object value);
    List<T> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound);
    List<T> averageQuery(String attribute, Timestamp startTime, Timestamp endTime);
}
