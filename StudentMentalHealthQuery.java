import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* Interface for querying the Student Mental Health dataset.
* This interface provides methods for both exact match and range
* queries on various attributes of student mental health data.
*/
public interface StudentMentalHealthQuery {
    /**
    * Loads the student mental health dataset from the specified file path
    * @param filePath Path to the dataset file (CSV format)
    * @return Number of records loaded
    * @throws IOException If there's an error reading the file
    */
    int loadDataset(String filePath) throws IOException;

    /**
    * Returns all records that exactly match the specified criteria
    * @param attribute The attribute to query on (e.g., "gender", "city", "degree")
    * @param value The exact value to match
    * @return List of matching student records
    * @throws IllegalArgumentException For unsupported attributes or invalid value types
    */
    List<Record> exactMatchQuery(String attribute, Object value);

    /**
    * Returns all records where the specified attribute falls within the given range
    * @param attribute The numeric attribute (e.g., "age", "academic pressure")
    * @param lowerBound The lower bound of the range (inclusive)
    * @param upperBound The upper bound of the range (inclusive)
    * @return List of matching student records
    * @throws IllegalArgumentException For non-numeric attributes or invalid bounds
    */
    List<Record> rangeQuery(String attribute, Comparable lowerBound, Comparable upperBound);

    /**
    * :
    * 
    * 
    * 
    * 
    * @return 
    */
    Map<String, Object> getDatasetStatistics();

    /**
    * 
    * @return 
    */
    int getRecordCount();
}