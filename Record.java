import java.time.LocalDateTime;

public class Record {
    private String id;
    private String gender;
    private int age;
    private String city;
    private String profession;
    private int academicPressure;
    private int workPressure;
    private double cgpa;
    private int studySatisfaction;
    private int jobSatisfaction;
    private double sleepDuration;
    private int dietaryHabits;
    private String degree;
    private boolean suicidalThoughts;
    private double workStudyHours;
    private int financialStress;
    private boolean familyMentalHistory;
    private int depression;

    public Record(String id, String gender, int age, String city, String profession,
                  int academicPressure, int workPressure, double cgpa, int studySatisfaction,
                  int jobSatisfaction, double sleepDuration, int dietaryHabits, String degree,
                  boolean suicidalThoughts, double workStudyHours, int financialStress,
                  boolean familyMentalHistory, int depression) {

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
        this.familyMentalHistory = familyMentalHistory;
        this.depression = depression;
    }

    public Object getAttribute(String attribute) {
        return switch (attribute.toLowerCase()) {
            case "id" -> id;
            case "gender" -> gender;
            case "age" -> age;
            case "city" -> city;
            case "profession" -> profession;
            case "academic pressure" -> academicPressure;
            case "work pressure" -> workPressure;
            case "cgpa" -> cgpa;
            case "study satisfaction" -> studySatisfaction;
            case "job satisfaction" -> jobSatisfaction;
            case "sleep duration" -> sleepDuration;
            case "dietary habits" -> dietaryHabits;
            case "degree" -> degree;
            case "have you ever had suicidal thoughts ?" -> suicidalThoughts;
            case "work/study hours" -> workStudyHours;
            case "financial stress" -> financialStress;
            case "family history of mental illness" -> familyMentalHistory;
            case "depression" -> depression;
            default -> null;
        };
    }

    public double getNumericAttribute(String attribute) {
        Object attr = getAttribute(attribute);
        if (attr instanceof Number) {
            return ((Number) attr).doubleValue();
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format("Record{id=%s, age=%d, cgpa=%.2f, depression=%d}", id, age, cgpa, depression);
    }
}
