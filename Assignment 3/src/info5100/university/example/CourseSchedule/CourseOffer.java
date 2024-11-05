package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.Persona.Faculty.FacultyAssignment;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.StudentAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseOffer {
    private final Course course;
    private final List<Seat> seatList;
    private FacultyAssignment facultyAssignment;
    private final String semester;
    private int availableSeats;
    private List<StudentAccount> enrolledStudents = new ArrayList<>();
    private int totalRevenue = 0;

    public CourseOffer(Course course, String semester) {
        this.course = course;
        this.semester = semester;
        this.seatList = new ArrayList<>();
        this.availableSeats = 0;
        this.enrolledStudents = new ArrayList<>();
        this.totalRevenue = 0;
    }
    public boolean assignEmptySeat(StudentAccount studentAccount) {
    if (getAvailableSeats() > 0) {
        enrolledStudents.add(studentAccount);
        totalRevenue += getCourse().getCoursePrice();
        return true;
    }
    return false;
}
    

    public int getAvailableSeats() {
        return getCourse().getSeatsAvailable() - enrolledStudents.size();
    }

    public int getTotalRevenues() {
        return totalRevenue;
    }

    public void generateSeats(int seats) {
        for (int i = 0; i < seats; i++) {
            Seat seat = new Seat(this, i + 1);
            seatList.add(seat);
        }
        this.availableSeats = seats;
    }
public boolean enrollStudent(StudentAccount student) {
    if (enrolledStudents.contains(student)) {
        System.out.println("Student already enrolled in this course.");
        return false;
    }
    if (getAvailableSeats() > 0) {
        enrolledStudents.add(student);
        totalRevenue += getCourse().getCoursePrice();
        return true;
    }
    return false;
}

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void withdrawStudent(StudentAccount student) {
        if (enrolledStudents.remove(student)) {
            availableSeats++;
            totalRevenue -= course.getCoursePrice();
        }
    }

    public Course getCourse() {
        return course;
    }

    public List<Seat> getSeatList() {
        return Collections.unmodifiableList(seatList);
    }

    public FacultyAssignment getFacultyAssignment() {
        return facultyAssignment;
    }

    public String getSemester() {
        return semester;
    }

    public List<StudentAccount> getRegisteredStudents() {
           return new ArrayList<>(enrolledStudents);
       }

    public String getCourseNumber() {
        return course.getNumber();
    }

    public Seat getEmptySeat() {
        for (Seat seat : seatList) {
            if (!seat.isOccupied()) {
                return seat;
            }
        }
        return null;
    }

    public void assignAsTeacher(FacultyProfile facultyProfile) {
        this.facultyAssignment = new FacultyAssignment(facultyProfile, this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseOffer)) return false;
        CourseOffer that = (CourseOffer) obj;
        return course.equals(that.course) && semester.equals(that.semester);
    }

    @Override
    public int hashCode() {
        return 31 * course.hashCode() + semester.hashCode();
    }

    @Override
    public String toString() {
        return "Course Offer: " + course.getName() + 
               ", Course Number: " + course.getNumber() +
               ", Semester: " + semester + 
               ", Seats Available: " + availableSeats;
    }



}