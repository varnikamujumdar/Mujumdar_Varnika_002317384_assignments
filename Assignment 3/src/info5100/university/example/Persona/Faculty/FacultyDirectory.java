package info5100.university.example.Persona.Faculty;

import info5100.university.example.Persona.Person;
import info5100.university.example.Department.Department;
import info5100.university.example.CourseSchedule.CourseOffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a directory of faculty profiles within a specific department,
 * managing both faculty profiles and course offers.
 */
public class FacultyDirectory {

    private Department department; // The department the faculty directory belongs to
    private List<FacultyProfile> teacherList; // List of faculty profiles
    private List<CourseOffer> courseOffers; // List of course offers managed by the faculty

    /**
     * Constructs a FacultyDirectory for the specified department.
     *
     * @param department the department associated with this faculty directory
     */
    public FacultyDirectory(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department cannot be null");
        }
        this.department = department;
        this.teacherList = new ArrayList<>();
        this.courseOffers = new ArrayList<>(); // Initialize the course offers list
    }

    /**
     * Creates a new faculty profile for the given person and adds it to the directory.
     *
     * @param person the person for whom to create the faculty profile
     * @return the created FacultyProfile
     */
    public FacultyProfile newFacultyProfile(Person person) {
        FacultyProfile facultyProfile = new FacultyProfile(person);
        teacherList.add(facultyProfile);
        return facultyProfile;
    }

    /**
     * Adds a course offer to the faculty directory.
     *
     * @param courseOffer the CourseOffer to add
     */
    public void addCourseOffer(CourseOffer courseOffer) {
        courseOffers.add(courseOffer);
    }

    /**
     * Retrieves a CourseOffer by its course number.
     *
     * @param courseNumber the course number of the desired CourseOffer
     * @return an Optional containing the found CourseOffer or empty if not found
     */
    public Optional<CourseOffer> getCourseOffer(String courseNumber) {
        return courseOffers.stream()
                .filter(courseOffer -> courseOffer.getCourseNumber().equals(courseNumber))
                .findFirst();
    }

    /**
     * Retrieves the top professor based on the highest overall rating.
     *
     * @return the FacultyProfile of the top professor, or null if none found
     */
    public FacultyProfile getTopProfessor() {
        double bestRatingSoFar = 0.0;
        FacultyProfile bestProfSoFar = null;

        for (FacultyProfile facultyProfile : teacherList) {
            if (facultyProfile.getProfAverageOverallRating() > bestRatingSoFar) {
                bestRatingSoFar = facultyProfile.getProfAverageOverallRating();
                bestProfSoFar = facultyProfile;
            }
        }
        return bestProfSoFar;
    }

    /**
     * Finds a teaching faculty member by their ID.
     *
     * @param id the ID of the faculty member to find
     * @return an Optional containing the found FacultyProfile or empty if not found
     */
    public Optional<FacultyProfile> findTeachingFaculty(String id) {
        return teacherList.stream()
                .filter(facultyProfile -> facultyProfile.isMatch(id))
                .findFirst();
    }

    /**
     * Gets the list of faculty profiles in this directory.
     *
     * @return a list of faculty profiles
     */
    public List<FacultyProfile> getTeacherList() {
        return new ArrayList<>(teacherList); // Return a copy for encapsulation
    }

    // Getter for the department
    public Department getDepartment() {
        return department;
    }
}
