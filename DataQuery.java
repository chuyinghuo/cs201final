import java.sql.Timestamp;
import java.util.List;

public interface DataQuery<T> { 

    /**
     * Returns all records that exactly match the specified criteria.
     * 
     * @param attribute The attribute/field to query on.
     * @param value The exact value to match.
     * @return A collection of records matching the criteria.
     */
    List<T> exactMatchQuery(String attribute, Object value);

    /**
     * Returns all records where the specified attribute falls within the given range.
     * 
     * @param attribute The attribute/field to query on.
     * @param lowerBound The lower bound of the range (inclusive).
     * @param upperBound The upper bound of the range (inclusive).
     * @return A collection of records matching the criteria.
     */
    List<T> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound);

    /**
     * Returns all records where the specified attribute is less than or equal to a value.
     * 
     * @param attribute The attribute/field to query on.
     * @param upperBound The upper bound of the range.
     * @return A collection of records matching the criteria.
     */
    List<T> rangeQuery(String attribute, Comparable upperBound);

    /**git 
     * Returns the average value of the specified attribute during a given time frame.
     * 
     * @param attribute The attribute/field to calculate the average for.
     * @param startTime The start time of the period (inclusive).
     * @param endTime The end time of the period (inclusive).
     * @return The average value of the attribute during the specified time frame.
     */
    List<T> averageQuery(String attribute, Timestamp startTime, Timestamp endTime);
}
