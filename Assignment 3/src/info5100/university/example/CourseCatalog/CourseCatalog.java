package info5100.university.example.CourseCatalog;

import info5100.university.example.Department.Department;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseCatalog {
    private Department department;
    private String lastUpdated;
    private List<Course> courseList;

    public CourseCatalog(Department department) {
        this.department = department;
        this.courseList = new ArrayList<>();
        updateLastUpdated();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    private void updateLastUpdated() {
        this.lastUpdated = java.time.LocalDateTime.now().toString();
    }

    public List<Course> getCourseList() {
        return new ArrayList<>(courseList); // Encapsulation
    }

    public Course addCourse(String number, String name, int credits, boolean isCore) {
        Course course = new Course(number, name, credits, isCore);
        courseList.add(course);
        updateLastUpdated();
        return course;
    }

    public Optional<Course> findCourseByNumber(String number) {
        return courseList.stream()
                         .filter(c -> c.getNumber().equals(number))
                         .findFirst();
    }

    public void registerStudentForCourse(String studentId, String courseId) {
        Optional<Course> optionalCourse = findCourseByNumber(courseId);
        Optional<StudentProfile> optionalStudent = getStudentById(studentId);

        if (optionalCourse.isPresent() && optionalStudent.isPresent()) {
            Course course = optionalCourse.get();
            StudentProfile student = optionalStudent.get();

            if (course.getSeatsAvailable() > 0) {
                course.enrollStudent(student); // Ensure this reduces seats and updates revenue
                System.out.println("Student " + studentId + " registered for " + course.getName());
            } else {
                System.out.println("No seats available for " + course.getName());
            }
        } else {
            System.out.println("Invalid course or student ID");
        }
    }

    private Optional<StudentProfile> getStudentById(String studentId) {
        if (department != null) {
            StudentDirectory studentDirectory = department.getStudentDirectory();
            return Optional.ofNullable(studentDirectory.findStudent(studentId));
        }
        return Optional.empty();
    }

    public List<Course> getCoreCourses() {
        return courseList.stream()
                         .filter(Course::isCore)
                         .toList();
    }

    public List<Course> getElectiveCourses() {
        return courseList.stream()
                         .filter(course -> !course.isCore())
                         .toList();
    }
}
