package info5100.university.example.Persona;

import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.SeatAssignment;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the transcript for a student, including course loads and seat assignments.
 */
public class Transcript {

    private StudentProfile student;
    private StudentAccount studentAccount; // Reference to the associated student account
    private HashMap<String, CourseLoad> courseLoadList; // Changed the variable name to use camelCase
    private CourseLoad currentCourseLoad;

    public Transcript(StudentProfile studentProfile, StudentAccount studentAccount) {
        this.student = studentProfile;
        this.studentAccount = studentAccount; // Initialize the student account
        this.courseLoadList = new HashMap<>();
    }

    /**
     * Creates a new course load for a semester and adds it to the course load list.
     * @param semester The semester for which to create a new course load.
     * @return The newly created CourseLoad.
     */
    public CourseLoad newCourseLoad(String semester) {
        currentCourseLoad = new CourseLoad(semester, studentAccount); // Pass the student account
        courseLoadList.put(semester, currentCourseLoad);
        return currentCourseLoad;
    }

    /**
     * Retrieves the current course load.
     * @return The current CourseLoad.
     */
    public CourseLoad getCurrentCourseLoad() {
        return currentCourseLoad;
    }

    /**
     * Retrieves a course load by its semester.
     * @param semester The semester for which to retrieve the course load.
     * @return The CourseLoad for the specified semester.
     */
    public CourseLoad getCourseLoadBySemester(String semester) {
        return courseLoadList.get(semester);
    }

    /**
     * Calculates the total score of the student across all semesters.
     * @return The total score as a float.
     */
    public float getStudentTotalScore() {
        float sum = 0;
        for (CourseLoad cl : courseLoadList.values()) {
            sum += cl.getSemesterScore();
        }
        return sum;
    }

    /**
     * Calculates the satisfaction index based on liked courses.
     * @return The number of liked courses.
     */
    public int getStudentSatisfactionIndex() {
        ArrayList<SeatAssignment> courseRegistrations = getCourseList();
        int sum = 0;
        for (SeatAssignment sa : courseRegistrations) {
            if (sa.getLike()) { // Ensure getLike() is implemented in SeatAssignment
                sum++;
            }
        }
        return sum;
    }

    /**
     * Generates a list of all seat assignments from multiple semesters.
     * @return An ArrayList of SeatAssignments.
     */
    public ArrayList<SeatAssignment> getCourseList() {
        ArrayList<SeatAssignment> tempList = new ArrayList<>();
        for (CourseLoad cl : courseLoadList.values()) {
            tempList.addAll(cl.getSeatAssignments()); // Assuming getSeatAssignments() returns a list of SeatAssignments
        }
        return tempList;
    }
}
