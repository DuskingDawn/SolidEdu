package exception;

public class StudentNotFoundException extends Exception {
    private String studentId;

    public StudentNotFoundException(String studentId) {
        super("Student not found with ID: " + studentId);
        this.studentId = studentId;
    }

    public StudentNotFoundException(String studentId, String message) {
        super(message);
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }
}
