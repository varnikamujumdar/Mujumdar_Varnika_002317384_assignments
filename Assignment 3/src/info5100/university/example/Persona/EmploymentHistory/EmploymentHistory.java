package info5100.university.example.Persona.EmploymentHistory;

import java.util.ArrayList;

/**
 * Represents a student's employment history.
 */
public class EmploymentHistory {
    private ArrayList<Employment> employments;

    public EmploymentHistory() {
        employments = new ArrayList<>();
    }

    public Employment newEmployment(String job) {
        Employment ne = new Employment(job);
        employments.add(ne);
        return ne;
    }

    // Optionally, you can add methods to retrieve employments
    public ArrayList<Employment> getEmployments() {
        return employments;
    }
}
