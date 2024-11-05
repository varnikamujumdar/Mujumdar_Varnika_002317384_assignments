package info5100.university.example.CourseSchedule;

import info5100.university.example.Persona.StudentAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CourseLoad {
    private final String semester;
    private final StudentAccount studentAccount;
    private List<SeatAssignment> seatAssignments;
    private final HashMap<String, CourseOffer> courseOffers;

    public CourseLoad(String semester, StudentAccount studentAccount) {
        this.semester = semester;
        this.studentAccount = studentAccount;
        this.seatAssignments = new ArrayList<>();
        this.courseOffers = new HashMap<>();
    }

    public void addCourseOffer(CourseOffer courseOffer) {
        courseOffers.put(courseOffer.getCourseNumber(), courseOffer);
    }

    public CourseOffer getCourseOffer(String courseNumber) {
        return courseOffers.get(courseNumber);
    }

    public String getSemester() {
        return semester;
    }

    public StudentAccount getStudentAccount() {
        return studentAccount;
    }

    public List<SeatAssignment> getSeatAssignments() {
        return Collections.unmodifiableList(seatAssignments);
    }

    public SeatAssignment newSeatAssignment(CourseOffer courseOffer) {
        if (courseOffer == null) {
            throw new IllegalArgumentException("CourseOffer cannot be null");
        }
        Seat seat = courseOffer.getEmptySeat();
        if (seat == null) {
            return null; // No empty seats available
        }
        SeatAssignment seatAssignment = new SeatAssignment(this, seat);
        seatAssignments.add(seatAssignment);
        courseOffer.enrollStudent(studentAccount); // Ensure this method updates the revenue in CourseOffer
        return seatAssignment;
    }

public SeatAssignment registerStudentInClass(CourseOffer courseOffer) {
    if (courseOffer == null) {
        System.out.println("CourseOffer cannot be null.");
        return null;
    }

    // Check if the student is already registered for this course
    for (SeatAssignment sa : seatAssignments) {
        if (sa.getCourseOffer().equals(courseOffer)) {
            System.out.println("Student already registered for " + courseOffer.getCourse().getName());
            return sa;
        }
    }

    Seat seat = courseOffer.getEmptySeat();
    if (seat == null) {
        System.out.println("No available seats in " + courseOffer.getCourseNumber());
        return null;
    }

    SeatAssignment seatAssignment = seat.newSeatAssignment(this);
    seatAssignments.add(seatAssignment);
    courseOffer.enrollStudent(studentAccount);
    return seatAssignment;
}

    public void registerStudent(SeatAssignment seatAssignment) {
        if (seatAssignment == null) {
            System.out.println("SeatAssignment cannot be null.");
            return;
        }

        seatAssignment.assignSeatToStudent(this);
        seatAssignments.add(seatAssignment);
        CourseOffer courseOffer = seatAssignment.getCourseOffer();
        courseOffer.enrollStudent(studentAccount); // Ensure this method updates the revenue in CourseOffer
    }

    public float getSemesterScore() {
        float sum = 0;
        for (SeatAssignment sa : seatAssignments) {
            sum += sa.getCourseStudentScore();
        }
        return sum;
    }

    public List<CourseOffer> getCourseOffers() {
        return new ArrayList<>(courseOffers.values());
    }

    public int getTotalRevenue() {
        int totalRevenue = 0;
        for (CourseOffer offer : courseOffers.values()) {
            totalRevenue += offer.getTotalRevenue();
        }
        return totalRevenue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseLoad)) return false;
        CourseLoad that = (CourseLoad) obj;
        return semester.equals(that.semester) && studentAccount.equals(that.studentAccount);
    }

    @Override
    public int hashCode() {
        return 31 * semester.hashCode() + studentAccount.hashCode();
    }

    @Override
    public String toString() {
        return "CourseLoad{" +
                "semester='" + semester + '\'' +
                ", studentAccount=" + studentAccount +
                ", seatAssignments=" + seatAssignments.size() +
                ", courseOffers=" + courseOffers.size() +
                '}';
    }
}