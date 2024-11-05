package info5100.university.example.Department;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Degree.Degree;
import info5100.university.example.Employer.EmployerDirectory;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentAccount;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class Department {
    private final String name;
    private final CourseCatalog courseCatalog;
    private final PersonDirectory personDirectory;
    private final StudentDirectory studentDirectory;
    private final FacultyDirectory facultyDirectory;
    private final EmployerDirectory employerDirectory;
    private final Degree degree;
    private final HashMap<String, CourseSchedule> masterCourseCatalog;

    public Department(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be null or empty.");
        }

        this.name = name;
        this.masterCourseCatalog = new HashMap<>();
        this.courseCatalog = new CourseCatalog(this);
        this.studentDirectory = new StudentDirectory(this);
        this.personDirectory = new PersonDirectory();
        this.facultyDirectory = new FacultyDirectory(this);
        this.degree = new Degree("MSIS");
        this.employerDirectory = new EmployerDirectory(this);
    }

    public String getName() {
        return name;
    }

    public void addCoreCourse(Course course) {
        degree.addCoreCourse(course);
    }

    public void addElectiveCourse(Course course) {
        degree.addElectiveCourse(course);
    }

    public FacultyDirectory getFacultyDirectory() {
        return facultyDirectory;
    }

    public PersonDirectory getPersonDirectory() {
        return personDirectory;
    }

    public StudentDirectory getStudentDirectory() {
        return studentDirectory;
    }

    public CourseSchedule newCourseSchedule(String semester) {
        CourseSchedule courseSchedule = new CourseSchedule(semester, courseCatalog);
        masterCourseCatalog.put(semester, courseSchedule);
        return courseSchedule;
    }

    public CourseSchedule getCourseSchedule(String semester) {
        return masterCourseCatalog.get(semester);
    }

    public CourseCatalog getCourseCatalog() {
        return courseCatalog;
    }

    public Course newCourse(String number, String name, int credits, boolean isCore) {
        return courseCatalog.addCourse(number, name, credits, isCore);
    }

    public int calculateRevenuesBySemester(String semester) {
        CourseSchedule courseSchedule = masterCourseCatalog.get(semester);
        return (courseSchedule != null) ? courseSchedule.calculateTotalRevenues() : 0;
    }

public boolean registerForClass(String studentId, String courseNumber, String semester) {
    StudentProfile studentProfile = studentDirectory.findStudent(studentId);
    if (studentProfile == null) {
        System.out.println("Student not found.");
        return false;
    }

    CourseSchedule courseSchedule = masterCourseCatalog.get(semester);
    if (courseSchedule == null) {
        System.out.println("Course schedule not found for the semester.");
        return false;
    }

    Optional<CourseOffer> optionalCourseOffer = courseSchedule.getCourseOffer(courseNumber);
    if (optionalCourseOffer.isEmpty()) {
        System.out.println("Course offer not found for the course number.");
        return false;
    }

    CourseOffer courseOffer = optionalCourseOffer.get();
    StudentAccount studentAccount = studentProfile.getStudentAccount();

    if (studentAccount == null) {
        System.out.println("Student account is null.");
        return false;
    }

    if (courseOffer.assignEmptySeat(studentAccount)) {
        CourseLoad courseLoad = studentProfile.getCourseLoadBySemester(semester);
        if (courseLoad == null) {
            courseLoad = studentProfile.newCourseLoad(semester);
        }
        SeatAssignment seatAssignment = courseLoad.newSeatAssignment(courseOffer);
        if (seatAssignment != null) {
            System.out.println("Student " + studentId + " registered for " + courseOffer.getCourse().getName());
            return true;
        } else {
            System.out.println("Failed to create seat assignment for student " + studentId);
            courseOffer.withdrawStudent(studentAccount); // Rollback the enrollment
            return false;
        }
    } else {
        System.out.println("Unable to register student, course offer is full.");
        return false;
    }
}
    public void printCoreCourses() {
        ArrayList<Course> coreCourses = (ArrayList<Course>) courseCatalog.getCoreCourses();
        if (coreCourses.isEmpty()) {
            System.out.println("No core courses found in the catalog.");
            return;
        }
        System.out.println("Core Courses in the Catalog:");
        for (Course course : coreCourses) {
            System.out.println(formatCourseDetails(course));
        }
    }

    private String formatCourseDetails(Course course) {
        return String.format("Course Name: %s, Course Number: %s, Credits: %d",
                course.getName(), course.getNumber(), course.getCredits());
    }
}