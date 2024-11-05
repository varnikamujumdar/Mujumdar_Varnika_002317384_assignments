package info5100.university.example.CourseSchedule;

import info5100.university.example.CourseCatalog.Course;

/**
 * This class represents the assignment of a seat to a student in a course,
 * including the student's grade and preferences.
 */
public class SeatAssignment {
    private float grade;
    private Seat seat;
    private boolean like;
    private CourseLoad courseLoad;

    public SeatAssignment(CourseLoad courseLoad, Seat seat) {
        if (courseLoad == null || seat == null) {
            throw new IllegalArgumentException("CourseLoad and Seat cannot be null");
        }
        this.courseLoad = courseLoad;
        this.seat = seat;
        this.grade = 0.0f;
        this.like = false;
    }

    public boolean getLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public void assignSeatToStudent(CourseLoad cl) {
        if (cl == null) {
            throw new IllegalArgumentException("CourseLoad cannot be null");
        }
        this.courseLoad = cl;
    }

    public Seat getSeat() {
        return seat;
    }

    public CourseOffer getCourseOffer() {
        return seat.getCourseOffer();
    }

    public Course getAssociatedCourse() {
        CourseOffer courseOffer = getCourseOffer();
        if (courseOffer != null) {
            return courseOffer.getCourse();
        }
        return null;
    }

    public int getCreditHours() {
        Course course = getAssociatedCourse();
        if (course != null) {
            return course.getCredits();
        }
        return 0;
    }

    public float getCourseStudentScore() {
        return getCreditHours() * grade;
    }

    public void setGrade(float grade) {
        if (grade < 0.0f || grade > 4.0f) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 4.0");
        }
        this.grade = grade;
    }

    public float getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "SeatAssignment{" +
                "seat=" + (seat != null ? seat.getNumber() : "null") +
                ", grade=" + grade +
                ", like=" + like +
                ", courseLoad=" + (courseLoad != null ? courseLoad.toString() : "null") +
                '}';
    }
}