package info5100.university.example.CourseCatalog;

import info5100.university.example.Persona.StudentProfile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course {
    private String number;
    private String name;
    private int credits;
    private boolean isCore;
    private static final int PRICE_PER_CREDIT = 1500;
    private static final int DEFAULT_SEATS = 30;
    private int seatsAvailable;
    private int totalRevenue;
    private List<StudentProfile> enrolledStudents;

    public Course(String number, String name, int credits, boolean isCore) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Course number cannot be null or empty.");
        }
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be a positive number.");
        }
        
        this.number = number;
        this.name = name;
        this.credits = credits;
        this.isCore = isCore;
        this.seatsAvailable = DEFAULT_SEATS;
        this.totalRevenue = 0;
        this.enrolledStudents = new ArrayList<>();
    }

    public boolean isCore() {
        return isCore;
    }

    public boolean enrollStudent(StudentProfile student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (seatsAvailable > 0) {
            enrolledStudents.add(student);
            seatsAvailable--;
            totalRevenue += getCoursePrice();
            return true;
        }
        return false;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seats) {
        if (seats < 0) {
            throw new IllegalArgumentException("Seats cannot be negative.");
        }
        this.seatsAvailable = seats;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Course number cannot be null or empty.");
        }
        this.number = number;
    }

    public int getEnrolledCount() {
        return enrolledStudents.size();
    }

    public double getEnrollmentPercentage() {
        return ((DEFAULT_SEATS - seatsAvailable) / (double) DEFAULT_SEATS) * 100;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty.");
        }
        this.name = name;
    }

    public int getCoursePrice() {
        return PRICE_PER_CREDIT * credits;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        if (credits <= 0) {
            throw new IllegalArgumentException("Credits must be a positive number.");
        }
        this.credits = credits;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    @Override
    public String toString() {
        return String.format("Course Name: %s, Course Number: %s, Credits: %d, Total Price: $%d, Seats Available: %d, Total Revenue: $%d",
                name, number, credits, getCoursePrice(), seatsAvailable, totalRevenue);
    }

    public List<StudentProfile> getEnrolledStudents() {
        return Collections.unmodifiableList(enrolledStudents);
    }
}