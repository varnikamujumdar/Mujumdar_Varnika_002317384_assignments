package info5100.university.example.Persona;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Persona.EmploymentHistory.EmploymentHistory;
import info5100.university.example.CourseSchedule.CourseOffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentProfile {
    private Person person;
    private StudentAccount studentAccount;
    private Transcript transcript;
    private EmploymentHistory employmentHistory;
    private Map<CourseOffer, Double> grades;
    private Map<String, CourseLoad> courseLoads;

    public StudentProfile(Person p, StudentAccount studentAccount) {
        if (p == null || studentAccount == null) {
            throw new IllegalArgumentException("Person and StudentAccount cannot be null");
        }
        this.person = p;
        this.studentAccount = studentAccount;
        this.transcript = new Transcript(this, studentAccount);
        this.employmentHistory = new EmploymentHistory();
        this.grades = new HashMap<>();
        this.courseLoads = new HashMap<>();
    }

    public CourseLoad getCourseLoad(String term) {
        return courseLoads.get(term);
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        this.person = person;
    }

    public StudentAccount getStudentAccount() {
        return studentAccount;
    }

    public EmploymentHistory getEmploymentHistory() {
        return employmentHistory;
    }

    public void setEmploymentHistory(EmploymentHistory employmentHistory) {
        this.employmentHistory = employmentHistory;
    }

    public boolean isMatch(String id) {
        return person.getPersonId().equals(id);
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public CourseLoad getCourseLoadBySemester(String semester) {
        return courseLoads.get(semester);
    }

    public CourseLoad getCurrentCourseLoad() {
        return transcript.getCurrentCourseLoad();
    }

    public CourseLoad newCourseLoad(String semester) {
        CourseLoad courseLoad = transcript.newCourseLoad(semester);
        courseLoads.put(semester, courseLoad);
        return courseLoad;
    }

    public List<SeatAssignment> getCourseList() {
        return transcript.getCourseList();
    }

    public void setGrade(CourseOffer courseOffer, double grade) {
        if (courseOffer == null) {
            throw new IllegalArgumentException("CourseOffer cannot be null");
        }
        if (grade < 0 || grade > 4.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 4.0");
        }
        grades.put(courseOffer, grade);
    }

    public Double getGrade(CourseOffer courseOffer) {
        return grades.get(courseOffer);
    }

    public double calculateGPA() {
        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Map.Entry<String, CourseLoad> entry : courseLoads.entrySet()) {
            CourseLoad courseLoad = entry.getValue();
            for (SeatAssignment seat : courseLoad.getSeatAssignments()) {
                Course course = seat.getCourseOffer().getCourse();
                totalGradePoints += seat.getGrade() * course.getCredits();
                totalCredits += course.getCredits();
            }
        }

        return totalCredits > 0 ? totalGradePoints / totalCredits : 0;
    }

    public void addSeatAssignment(SeatAssignment seatAssignment) {
        if (seatAssignment == null) {
            throw new IllegalArgumentException("SeatAssignment cannot be null");
        }
        String semester = seatAssignment.getCourseOffer().getSemester();
        CourseLoad courseLoad = courseLoads.computeIfAbsent(semester, this::newCourseLoad);
        courseLoad.registerStudent(seatAssignment);
    }

    public List<SeatAssignment> getSeatAssignments() {
        List<SeatAssignment> allAssignments = new ArrayList<>();
        for (CourseLoad courseLoad : courseLoads.values()) {
            allAssignments.addAll(courseLoad.getSeatAssignments());
        }
        return allAssignments;
    }

    public void addCourseLoad(CourseLoad courseLoad) {
        if (courseLoad == null) {
            throw new IllegalArgumentException("CourseLoad cannot be null");
        }
        this.courseLoads.put(courseLoad.getSemester(), courseLoad);
    }
}