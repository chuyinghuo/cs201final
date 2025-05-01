public class Record {
    private String gender;
    private double age;
    private String city;
    private String profession;
    private double academicPressure;
    private double studySatisfaction;
    private String degree;
    private boolean suicidalThoughts;
    private boolean familyHistoryMentalIllness;
    private int depression;
    private double financialStress;

    public Record(String gender, double age, String city, String profession, 
                 double academicPressure, double studySatisfaction, String degree,
                 boolean suicidalThoughts, boolean familyHistoryMentalIllness,
                 int depression, double financialStress) {
        this.gender = gender;
        this.age = age;
        this.city = city;
        this.profession = profession;
        this.academicPressure = academicPressure;
        this.studySatisfaction = studySatisfaction;
        this.degree = degree;
        this.suicidalThoughts = suicidalThoughts;
        this.familyHistoryMentalIllness = familyHistoryMentalIllness;
        this.depression = depression;
        this.financialStress = financialStress;
    }

    // Getters
    public String getGender() { return gender; }
    public double getAge() { return age; }
    public String getCity() { return city; }
    public String getProfession() { return profession; }
    public double getAcademicPressure() { return academicPressure; }
    public double getStudySatisfaction() { return studySatisfaction; }
    public String getDegree() { return degree; }
    public boolean getSuicidalThoughts() { return suicidalThoughts; }
    public boolean getFamilyHistoryMentalIllness() { return familyHistoryMentalIllness; }
    public int getDepression() { return depression; }
    public double getFinancialStress() { return financialStress; }
}