package info5100.university.example;

import java.util.*;
import info5100.university.example.CourseCatalog.Course;
import info5100.university.example.CourseCatalog.CourseCatalog;
import info5100.university.example.CourseSchedule.CourseLoad;
import info5100.university.example.CourseSchedule.CourseOffer;
import info5100.university.example.CourseSchedule.CourseSchedule;
import info5100.university.example.CourseSchedule.SeatAssignment;
import info5100.university.example.Department.Department;
import info5100.university.example.Persona.Faculty.FacultyDirectory;
import info5100.university.example.Persona.Faculty.FacultyProfile;
import info5100.university.example.Persona.Person;
import info5100.university.example.Persona.PersonDirectory;
import info5100.university.example.Persona.StudentAccount;
import info5100.university.example.Persona.StudentDirectory;
import info5100.university.example.Persona.StudentProfile;

public class Info5001UniversityExample {

    public static void main(String[] args) {
        Department department = new Department("Information Systems");
        PersonDirectory personDirectory = department.getPersonDirectory();
        CourseCatalog courseCatalog = department.getCourseCatalog();
        
        createCourses(courseCatalog);
        
        CourseSchedule courseSchedule = department.newCourseSchedule("Spring 2024");
        createCourseOffers(courseSchedule);

        FacultyDirectory facultyDirectory = department.getFacultyDirectory();
        createFacultyProfiles(personDirectory, facultyDirectory, courseSchedule);

        StudentDirectory studentDirectory = department.getStudentDirectory();
        createStudentProfiles(personDirectory, studentDirectory, courseSchedule);

        assignGrades(studentDirectory);
        displayGPAs(studentDirectory);
        displayCourseRevenue(courseSchedule);
        printSemesterReport(studentDirectory);
    }

    private static void createCourses(CourseCatalog courseCatalog) {
        String[][] courseInfo = {
            {"INFO 6206", "Data Structures", "4", "false"},
            {"INFO 6205", "Algorithms", "4", "false"},
            {"INFO 5100", "Application Engineering", "4", "true"},
            {"INFO 5101", "Web Development", "3", "false"},
            {"INFO 6203", "Machine Learning", "4", "false"},
            {"INFO 5104", "Software Engineering", "4", "false"},
            {"INFO 5105", "Cloud Computing", "3", "false"},
            {"INFO 5200", "Big Data Analytics", "4", "false"},
            {"INFO 5300", "Artificial Intelligence", "4", "false"},
            {"INFO 6105", "Data Science Tools and Methods", "4", "false"},
            {"INFO 7100", "Advanced Data Science", "4", "false"}};

        System.out.println("Courses added to catalog: ");
        for (String[] info : courseInfo) {
            Course course = courseCatalog.addCourse(
                info[0],  // course number
                info[1],  // course name
                Integer.parseInt(info[2]),  // credits
                Boolean.parseBoolean(info[3])  // required
            );
            
            // Calculate price based on credits (assuming $1500 per credit)
            int price = course.getCredits() * 1500;
            
            System.out.printf("Course Name: %s, Course Number: %s, Credits: %d, Total Price: $%d, Seats Available: 30, Total Revenue: $0%n",
                course.getName(),
                course.getNumber(),
                course.getCredits(),
                price);
        }
    }

private static void createCourseOffers(CourseSchedule courseSchedule) {
    System.out.println("\nCreation of Course offer:");

    Set<String> courseNumbers = new HashSet<>(Arrays.asList(
        "INFO 6206", "INFO 6205", "INFO 5100", "INFO 5101", 
        "INFO 6203", "INFO 5104", "INFO 5105","INFO 5200","INFO 5300","INFO 6105","INFO 7100"
    ));
    
    for (String courseNumber : courseNumbers) {
        if (courseSchedule.getCourseOffer(courseNumber).isEmpty()) {
            CourseOffer courseOffer = courseSchedule.createCourseOffer(courseNumber);
            if (courseOffer != null) {
                courseOffer.generateSeats(30);
                System.out.printf("Successfully added %s (%s) to %s schedule%n",
                    courseOffer.getCourse().getName(),
                    courseNumber,
                    courseSchedule.getSemester());
            }
        } else {
            System.out.printf("\nCourse offer for %s already exists", courseNumber);
        }
    }
}


    private static void createFacultyProfiles(PersonDirectory personDirectory, 
                                            FacultyDirectory facultyDirectory, 
                                            CourseSchedule courseSchedule) {
        System.out.println("\nAssign Professors to students:");
        Map<String, String> courseFacultyMap = new LinkedHashMap<>();
        courseFacultyMap.put("INFO 6206", "Varnika");
        courseFacultyMap.put("INFO 6205", "Manasvi");
        courseFacultyMap.put("INFO 5100", "Akshay");
        courseFacultyMap.put("INFO 5101", "Shweta");
        courseFacultyMap.put("INFO 6203", "Himani");
        courseFacultyMap.put("INFO 5104", "Shalin");
        courseFacultyMap.put("INFO 5105", "Sankriti");
        courseFacultyMap.put("INFO 5200", "Pooja");
        courseFacultyMap.put("INFO 5300", "Tommy");
        courseFacultyMap.put("INFO 6105", "Ann");
        courseFacultyMap.put("INFO 7100", "Ally");



        courseFacultyMap.forEach((courseNumber, facultyId) -> {
            Person teacherPerson = personDirectory.newPerson(facultyId);
            FacultyProfile teacher = facultyDirectory.newFacultyProfile(teacherPerson);
            
            Optional<CourseOffer> courseOffer = courseSchedule.getCourseOffer(courseNumber);
            courseOffer.ifPresent(offer -> {
                offer.assignAsTeacher(teacher);
                System.out.printf("Professor %s assigned to %s%n",
                    teacher.getPerson().getId(),
                    offer.getCourse().getName());
            });
        });
    }

    private static void createStudentProfiles(PersonDirectory personDirectory,
                                            StudentDirectory studentDirectory,
                                            CourseSchedule courseSchedule) {
    System.out.println("\nRegistered Courses for students:");

    List<CourseOffer> courseOffers = new ArrayList<>();
    for (CourseOffer offer : courseSchedule.getCourseOffers()) {
        courseOffers.add(offer);
    }
    if (courseOffers.isEmpty()) {
        System.out.println("No course offers available.%n");
        return;
    }

        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            String studentId = String.format("S%03d", i);
            StudentAccount studentAccount = new StudentAccount(studentId, "Student" + i, "Last" + i);
            Person studentPerson = personDirectory.newPerson("0112304" + i);
            StudentProfile studentProfile = studentDirectory.newStudentProfile(studentPerson, studentAccount);
            CourseLoad courseLoad = studentProfile.newCourseLoad("Spring 2024");

            // Register for 3 unique random courses
            List<CourseOffer> availableCourses = new ArrayList<>(courseOffers);
            for (int j = 0; j < 3 && !availableCourses.isEmpty(); j++) {
                int randomIndex = random.nextInt(availableCourses.size());
                CourseOffer selectedCourse = availableCourses.remove(randomIndex);
                
                SeatAssignment seatAssignment = courseLoad.registerStudentInClass(selectedCourse);
                if (seatAssignment != null) {
                    System.out.printf("Student %s registered for %s%n",
                        studentAccount.getStudentID(),
                        selectedCourse.getCourse().getName());
                }
            }
        }
    }

    private static void assignGrades(StudentDirectory studentDirectory) {
        System.out.println("\nAssigning grades to students:");
        Random random = new Random();
        
        for (StudentProfile student : studentDirectory.getAllStudentProfiles()) {
            CourseLoad courseLoad = student.getCourseLoadBySemester("Spring 2024");
            if (courseLoad != null) {
                for (SeatAssignment seat : courseLoad.getSeatAssignments()) {
                    double grade = 2.0 + (random.nextDouble() * 2.0); // Generate grade between 2.0 and 4.0
                    seat.setGrade((float) grade);
                    System.out.printf("Student %s received %.2f in %s%n",
                        student.getPerson().getId(),
                        grade,
                        seat.getCourseOffer().getCourse().getName());
                }
            }
        }
    }

    private static void displayGPAs(StudentDirectory studentDirectory) {
        System.out.println("\nStudent GPAs:");
        for (StudentProfile student : studentDirectory.getAllStudentProfiles()) {
            System.out.printf("Student ID: %s | GPA: %.2f%n",
                student.getPerson().getId(),
                student.calculateGPA());
        }
    }

    private static void displayCourseRevenue(CourseSchedule courseSchedule) {
        System.out.println("\nDisplay Course Revenue:");
        double totalRevenue = 0.0;
        
        for (CourseOffer offer : courseSchedule.getCourseOffers()) {
            int enrolledCount = offer.getRegisteredStudents().size();
            double courseRevenue = enrolledCount * (offer.getCourse().getCredits() * 1500); // $1500 per credit
            totalRevenue += courseRevenue;
            
            System.out.printf("Course: %s, Enrolled: %d, Revenue: $%.0f%n",
                offer.getCourse().getName(),
                enrolledCount,
                courseRevenue);
        }
        
        System.out.printf("%nTotal Revenue for Spring 2024: $%.2f%n", totalRevenue);
    }

    private static void printSemesterReport(StudentDirectory studentDirectory) {
        System.out.println("\nSemester Report for Spring 2024:");
        
        for (StudentProfile student : studentDirectory.getAllStudentProfiles()) {
            System.out.println("\nStudent: " + student.getPerson().getId());
            CourseLoad courseLoad = student.getCourseLoadBySemester("Spring 2024");
            
            if (courseLoad != null) {
                double totalTuition = 0.0;
                
                for (SeatAssignment seat : courseLoad.getSeatAssignments()) {
                    CourseOffer courseOffer = seat.getCourseOffer();
                    FacultyProfile professor = courseOffer.getFacultyAssignment().getFacultyProfile();
                    
                    System.out.printf("Course: %s, Professor: %s, Grade: %.2f%n",
                        courseOffer.getCourse().getName(),
                        professor.getPerson().getId(),
                        seat.getGrade());
                    
                    totalTuition += courseOffer.getCourse().getCredits() * 1500; // $1500 per credit
                }
                
                System.out.printf("GPA: %.2f%n", student.calculateGPA());
                System.out.printf("Tuition Paid: $%.2f%n", totalTuition);
            }
        }
    }
}