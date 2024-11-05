package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CourseSchedule {
    private CourseCatalog courseCatalog;
    private Map<String, CourseOffer> courseOffers;
    private String semester;

    public CourseSchedule(String semester, CourseCatalog courseCatalog) {
        this.semester = semester;
        this.courseCatalog = courseCatalog;
        this.courseOffers = new HashMap<>();
    }
public CourseOffer createCourseOffer(String courseNumber) {
    if (courseOffers.containsKey(courseNumber)) {
        System.out.println("Course " + courseNumber + " already offered in this semester.");
        return courseOffers.get(courseNumber);
    }

    Optional<Course> optionalCourse = courseCatalog.findCourseByNumber(courseNumber);
    if (optionalCourse.isPresent()) {
        Course course = optionalCourse.get();
        CourseOffer courseOffer = new CourseOffer(course, semester);
        courseOffer.generateSeats(course.getSeatsAvailable());
        courseOffers.put(courseNumber, courseOffer);
//        System.out.println("Successfully added " + course.getName() + " (" + courseNumber + ") to " + semester + " schedule");
        return courseOffer;
    }
    
    System.out.println("Course " + courseNumber + " not found in the catalog.");
    return null;
}

    public Optional<CourseOffer> getCourseOffer(String courseNumber) {
        return Optional.ofNullable(courseOffers.get(courseNumber));
    }

    public int calculateTotalRevenues() {
        return courseOffers.values().stream()
                           .mapToInt(CourseOffer::getTotalRevenue)
                           .sum();
    }

    public String getSemester() {
        return semester;
    }
    
    public List<CourseOffer> getSchedule() {
        return new ArrayList<>(courseOffers.values());
    }

    public Iterable<CourseOffer> getCourseOffers() {
        return courseOffers.values();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CourseSchedule for ").append(semester).append(":\n");
        courseOffers.values().forEach(offer -> 
            sb.append(offer.getCourseNumber())
              .append(" - ")
              .append(offer.getCourse().getName())
              .append(" (Seats Available: ")
              .append(offer.getAvailableSeats())
              .append(")\n")
        );
        return sb.toString();
    }
}