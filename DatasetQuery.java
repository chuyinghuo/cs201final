import java.time.LocalDateTime;
import java.util.List;

public interface DatasetQuery<T> {
    List<T> exactMatchQuery(String attribute, Object value);
    List<T> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound);
    List<T> rangeQuery(String attribute, Comparable upperBound);
    List<T> averageQuery(String attribute, LocalDateTime startTime, LocalDateTime endTime);
}
