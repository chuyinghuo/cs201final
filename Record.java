import java.sql.Timestamp;

public class Record {
    // Existing fields...
    int id;
    String gender;
    double age;
    String city;
    String profession;
    double academicPressure;
    double workPressure;
    double cgpa;
    double studySatisfaction;
    double jobSatisfaction;
    String sleepDuration; // text like "5-6 hours"
    String dietaryHabits;
    String degree;
    boolean suicidalThoughts;
    double workStudyHours;
    double financialStress;
    boolean familyHistoryMentalIllness;
    int depression;

    // Add a timestamp field
    private Timestamp timestamp;  // Assuming this field exists in your CSV

    // Constructor (add timestamp as needed)
    public Record(int id, String gender, double age, String city, String profession,
                  double academicPressure, double workPressure, double cgpa,
                  double studySatisfaction, double jobSatisfaction, String sleepDuration,
                  String dietaryHabits, String degree, boolean suicidalThoughts,
                  double workStudyHours, double financialStress, boolean familyHistoryMentalIllness,
                  int depression, Timestamp timestamp) {
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.profession = profession;
        this.academicPressure = academicPressure;
        this.workPressure = workPressure;
        this.cgpa = cgpa;
        this.studySatisfaction = studySatisfaction;
        this.jobSatisfaction = jobSatisfaction;
        this.sleepDuration = sleepDuration;
        this.dietaryHabits = dietaryHabits;
        this.degree = degree;
        this.suicidalThoughts = suicidalThoughts;
        this.workStudyHours = workStudyHours;
        this.financialStress = financialStress;
        this.familyHistoryMentalIllness = familyHistoryMentalIllness;
        this.depression = depression;
        this.timestamp = timestamp;  // Initialize timestamp
    }

    // Getter for timestamp
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
