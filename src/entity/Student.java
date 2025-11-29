package entity;

import inter_face.Gradeable;
import inter_face.Searchable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student extends Person implements Gradeable, Searchable {

    private String major;
    private int semester;
    private double gpa;
    private final List<String> enrolledCourses;  // final reference = can't reassign list
    private int attendancePercentage;


    public Student(String id, String name, String email, LocalDate dateOfBirth,
                   String major, int semester) {
        super(id, name, email, dateOfBirth);

        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("Major cannot be null or empty");
        }
        if (semester < 1) {
            throw new IllegalArgumentException("Semester must be positive");
        }

        this.major = major;
        this.semester = semester;
        this.gpa = 0.0;
        this.enrolledCourses = new ArrayList<>();
        this.attendancePercentage = 100;
    }

    // GETTERS
    public String getMajor() {
        return major;
    }

    public int getSemester() {
        return semester;
    }

    public double getGpa() {
        return gpa;
    }

    public List<String> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }

    public int getAttendancePercentage() {
        return attendancePercentage;
    }

    // SETTERS
    public void setMajor(String major) {
        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("Major cannot be null or empty");
        }
        this.major = major;
    }

    public void setSemester(int semester) {
        if (semester < 1) {
            throw new IllegalArgumentException("Semester must be positive");
        }
        this.semester = semester;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = gpa;
    }

    public void setAttendancePercentage(int attendance) {
        if (attendance < 0 || attendance > 100) {
            throw new IllegalArgumentException("Attendance must be between 0 and 100");
        }
        this.attendancePercentage = attendance;
    }

    public void enrollInCourse(String courseId) {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
        }
    }

    public void unenrollFromCourse(String courseId) {
        enrolledCourses.remove(courseId);
    }

    @Override
    public void displayInfo() {
        System.out.println("=== STUDENT INFORMATION ===");
        System.out.println("ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + getEmail());
        System.out.println("Age: " + getAge());
        System.out.println("Major: " + major);
        System.out.println("Semester: " + semester);
        System.out.println("GPA: " + String.format("%.2f", gpa));
        System.out.println("Grade: " + calculateGrade());
        System.out.println("Attendance: " + attendancePercentage + "%");
        System.out.println("Enrolled Courses: " + enrolledCourses.size());
    }

    @Override
    public String calculateGrade() {
        if (gpa >= 3.7) return "A";
        if (gpa >= 3.0) return "B";
        if (gpa >= 2.0) return "C";
        if (gpa >= 1.0) return "D";
        return "F";
    }

    @Override
    public double getNumericGrade() {
        return gpa;
    }

    @Override
    public boolean matchesSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return false;
        }
        String lowerQuery = query.toLowerCase();
        return getId().toLowerCase().contains(lowerQuery) ||
                getName().toLowerCase().contains(lowerQuery) ||
                getEmail().toLowerCase().contains(lowerQuery) ||
                major.toLowerCase().contains(lowerQuery);
    }

    @Override
    public String getSearchableInfo() {
        return String.format("ID: %s | Name: %s | Major: %s | GPA: %.2f | Grade: %s",
                getId(), getName(), major, gpa, calculateGrade());
    }

    @Override
    public String toString() {
        return String.format("Student{id='%s', name='%s', major='%s', gpa=%.2f, semester=%d}",
                getId(), getName(), major, gpa, semester);
    }
}
