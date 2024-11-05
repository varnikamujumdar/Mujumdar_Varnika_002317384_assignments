package info5100.university.example.Degree;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Persona.StudentProfile;
import java.util.ArrayList;
import java.util.List;

public class Degree {
    private String title;
    private List<Course> coreList;
    private List<Course> electives;
    private static final int MINIMUM_CREDITS_TO_GRADUATE = 32;

    public Degree(String name) {
        this.title = name;
        this.coreList = new ArrayList<>();
        this.electives = new ArrayList<>();
    }

    public void addCoreCourse(Course c) {
        if (c != null && !coreList.contains(c)) {
            coreList.add(c);
        }
    }

    public void addElectiveCourse(Course c) {
        if (c != null && !electives.contains(c)) {
            electives.add(c);
        }
    }

    public boolean isStudentReadyToGraduate(StudentProfile sp) {
        if (sp == null) {
            return false;
        }

        List<SeatAssignment> seatAssignments = sp.getCourseList();
        
        if (!validateCoreClasses(seatAssignments)) {
            return false;
        }

        int electivesTaken = getTotalElectiveCoursesTaken(seatAssignments);
        int totalCredits = calculateTotalCredits(seatAssignments);

        return totalCredits >= MINIMUM_CREDITS_TO_GRADUATE;
    }

    private boolean validateCoreClasses(List<SeatAssignment> seatAssignments) {
        for (Course c : coreList) {
            if (!isCoreSatisfied(seatAssignments, c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCoreSatisfied(List<SeatAssignment> seatAssignments, Course c) {
        for (SeatAssignment sa : seatAssignments) {
            Course associatedCourse = sa.getAssociatedCourse();
            if (associatedCourse != null && associatedCourse.equals(c)) {
                return true;
            }
        }
        return false;
    }

    private int getTotalElectiveCoursesTaken(List<SeatAssignment> seatAssignments) {
        int electiveCount = 0;
        for (SeatAssignment sa : seatAssignments) {
            if (isElectiveSatisfied(sa)) {
                electiveCount++;
            }
        }
        return electiveCount;
    }

    private boolean isElectiveSatisfied(SeatAssignment sa) {
        Course associatedCourse = sa.getAssociatedCourse();
        if (associatedCourse == null) {
            return false;
        }
        return electives.contains(associatedCourse);
    }

    private int calculateTotalCredits(List<SeatAssignment> seatAssignments) {
        int totalCredits = 0;
        for (SeatAssignment sa : seatAssignments) {
            Course course = sa.getAssociatedCourse();
            if (course != null) {
                totalCredits += course.getCredits();
            }
        }
        return totalCredits;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Course> getCoreList() {
        return new ArrayList<>(coreList);
    }

    public List<Course> getElectives() {
        return new ArrayList<>(electives);
    }
}