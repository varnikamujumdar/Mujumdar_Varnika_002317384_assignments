package info5100.university.example.CourseSchedule;

/**
 * Represents a seat in a course offering.
 */
public class Seat {

    private boolean occupied; // Track if the seat is occupied
    private int number; // Seat number
    private SeatAssignment seatAssignment; // Links back to student profile
    private CourseOffer courseOffer; // Reference to the course offer

    // Constructor to initialize the seat
    public Seat(CourseOffer courseOffer, int number) {
        this.courseOffer = courseOffer;
        this.number = number;
        this.occupied = false; // Initially unoccupied
    }

    // Check if the seat is occupied
    public boolean isOccupied() {
        return this.occupied;
    }

    // Create a new seat assignment for this seat
    public SeatAssignment newSeatAssignment(CourseLoad courseLoad) {
        if (occupied) {
            throw new IllegalStateException("Seat is already occupied.");
        }
        this.seatAssignment = new SeatAssignment(courseLoad, this);
        this.occupied = true; // Mark seat as occupied
        return this.seatAssignment; // Return the new seat assignment
    }

    // Get the course offer associated with this seat
    public CourseOffer getCourseOffer() {
        return this.courseOffer;
    }

    // Get the seat number
    public int getNumber() {
        return this.number;
    }

    // Get the seat assignment
    public SeatAssignment getSeatAssignment() {
        return seatAssignment;
    }

    // Set the seat assignment
    public void setSeatAssignment(SeatAssignment seatAssignment) {
        this.seatAssignment = seatAssignment;
    }

    // Setter for occupied status
    public void setOccupied(boolean occupied) {
        this.occupied = occupied; // Update occupied status
    }

    // Override toString for better representation
    @Override
    public String toString() {
        return "Seat Number: " + number + (occupied ? " (Occupied)" : " (Available)");
    }
}
