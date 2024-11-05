package info5100.university.example.Persona;

import info5100.university.example.Department.Department;
import java.util.ArrayList;

/**
 * Manages a directory of student profiles within a department.
 */
public class StudentDirectory {

    private Department department;
    private ArrayList<StudentProfile> studentList; // Use camelCase for variable names

    // Constructor
    public StudentDirectory(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department cannot be null"); // Add null check for department
        }
        this.department = department;
        this.studentList = new ArrayList<>(); // Initialize the list correctly
    }

    // Method to create a new student profile with an associated StudentAccount
    public StudentProfile newStudentProfile(Person person, StudentAccount account) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null"); // Add null check
        }
        if (account == null) {
            throw new IllegalArgumentException("StudentAccount cannot be null"); // Add null check
        }

        // Create a new StudentProfile instance
        StudentProfile studentProfile = new StudentProfile(person, account);
        studentList.add(studentProfile); // Add the new profile to the list
        return studentProfile;
    }

    // Method to find a student by ID
    public StudentProfile findStudent(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        for (StudentProfile sp : studentList) {
            if (sp.isMatch(id)) {
                return sp; // Return matching student profile
            }
        }
        return null; // Not found after going through the whole list
    }

    // Method to get all student profiles
    public ArrayList<StudentProfile> getAllStudentProfiles() {
        return new ArrayList<>(studentList); // Return a copy of the list to avoid modification
    }

    // Method to get the associated department
    public Department getDepartment() {
        return department; // Return the department associated with this directory
    }
}
