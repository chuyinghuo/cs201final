import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class UnitTesting {
    private DatasetQueryImpl queryEngine;
    private List<Record> testDataset;

    @Before
    public void setUp() {
        // Create a test dataset
        testDataset = new ArrayList<>();
        
        // Add sample student records to the test dataset
        testDataset.add(new Record(
            "Male", 33.0, "Visakhapatnam", "Student", 
            5.0, 2.0, "B.Pharm", 
            true, false, 1, 1.0
        ));
        
        testDataset.add(new Record(
            "Female", 24.0, "Bangalore", "Student", 
            2.0, 5.0, "BSc", 
            false, true, 0, 2.0
        ));
        
        testDataset.add(new Record(
            "Male", 31.0, "Srinagar", "Student", 
            3.0, 5.0, "BA", 
            false, true, 0, 1.0
        ));
        
        testDataset.add(new Record(
            "Female", 28.0, "Varanasi", "Student", 
            3.0, 2.0, "BCA", 
            true, true, 1, 5.0
        ));
        
        testDataset.add(new Record(
            "Female", 25.0, "Jaipur", "Student", 
            4.0, 3.0, "M.Tech", 
            true, false, 0, 1.0
        ));
        
        // Initialize the query engine with the test dataset
        queryEngine = new DatasetQueryImpl();
        queryEngine.setTestRecords(testDataset); // Directly set the records for testing
    }

    // Test exact match queries
    @Test
    public void testExactMatchQuery_Gender() {
        List<Record> results = queryEngine.exactMatchQuery("gender", "Female");
        assertEquals(3, results.size());
        for (Record record : results) {
            assertEquals("Female", record.getGender());
        }
    }

    @Test
    public void testExactMatchQuery_City() {
        List<Record> results = queryEngine.exactMatchQuery("city", "Bangalore");
        assertEquals(1, results.size());
        assertEquals("Bangalore", results.get(0).getCity());
    }

    @Test
    public void testExactMatchQuery_Age() {
        List<Record> results = queryEngine.exactMatchQuery("age", 28.0);
        assertEquals(1, results.size());
        assertEquals(28.0, results.get(0).getAge(), 0.001);
    }

    @Test
    public void testExactMatchQuery_SuicidalThoughts_Yes() {
        List<Record> results = queryEngine.exactMatchQuery("Have you ever had suicidal thoughts?", "Yes");
        assertEquals(3, results.size());
        for (Record record : results) {
            assertTrue(record.getSuicidalThoughts());
        }
    }

    @Test
    public void testExactMatchQuery_SuicidalThoughts_No() {
        List<Record> results = queryEngine.exactMatchQuery("Have you ever had suicidal thoughts?", "No");
        assertEquals(2, results.size());
        for (Record record : results) {
            assertFalse(record.getSuicidalThoughts());
        }
    }

    @Test
    public void testExactMatchQuery_WithNoResults() {
        List<Record> results = queryEngine.exactMatchQuery("city", "New York");
        assertTrue(results.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExactMatchQuery_WithUnsupportedAttribute() {
        queryEngine.exactMatchQuery("unsupported_attribute", "value");
    }

    // Test range queries
    @Test
    public void testRangeQuery_Age() {
        List<Record> results = queryEngine.rangeQuery("age", 25.0, 30.0);
        assertEquals(3, results.size());
        for (Record record : results) {
            assertTrue(record.getAge() >= 25.0 && record.getAge() <= 30.0);
        }
    }

    @Test
    public void testRangeQuery_AcademicPressure() {
        List<Record> results = queryEngine.rangeQuery("academic pressure", 3.0, 4.0);
        assertEquals(3, results.size());
        for (Record record : results) {
            assertTrue(record.getAcademicPressure() >= 3.0 && record.getAcademicPressure() <= 4.0);
        }
    }

    @Test
    public void testRangeQuery_FinancialStress() {
        List<Record> results = queryEngine.rangeQuery("financial stress", 1.0, 2.0);
        assertEquals(4, results.size());
        for (Record record : results) {
            assertTrue(record.getFinancialStress() >= 1.0 && record.getFinancialStress() <= 2.0);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeQuery_WithUnsupportedAttribute() {
        queryEngine.rangeQuery("gender", "A", "Z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRangeQuery_WithNullBounds() {
        queryEngine.rangeQuery("age", null, null);
    }

    // Test dataset statistics
    @Test
    public void testGetDatasetStatistics_Age() {
        double averageAge = queryEngine.getDatasetStatistics("Age");
        assertEquals(28.2, averageAge, 0.001); // (33+24+31+28+25)/5 = 28.2
    }

    @Test
    public void testGetDatasetStatistics_AcademicPressure() {
        double average = queryEngine.getDatasetStatistics("Academic Pressure");
        assertEquals(3.4, average, 0.001); // (5+2+3+3+4)/5 = 3.4
    }

    @Test
    public void testGetDatasetStatistics_StudySatisfaction() {
        double average = queryEngine.getDatasetStatistics("Study Satisfaction");
        assertEquals(3.4, average, 0.001); // (2+5+5+2+3)/5 = 3.4
    }

    @Test
    public void testGetDatasetStatistics_FinancialStress() {
        double average = queryEngine.getDatasetStatistics("Financial Stress");
        assertEquals(2.0, average, 0.001); // (1+2+1+5+1)/5 = 2.0
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDatasetStatistics_WithUnsupportedAttribute() {
        queryEngine.getDatasetStatistics("Gender");
    }

    @Test
    public void testGetDatasetStatistics_WithEmptyDataset() {
        queryEngine.clearRecordsForTesting();  // Empty dataset
        double result = queryEngine.getDatasetStatistics("Age");
        assertEquals(0.0, result, 0.001);
    }
}