package entity;

import inter_face.Searchable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Course implements Searchable {

    private final String courseId;  // final = course ID never changes
    private String courseName;
    private String instructor;
    private int credits;

    private int maxCapacity;
    private final List<String> enrolledStudentIds;  // final reference = can't reassign list

    private String department;


    public Course(String courseId, String courseName, String instructor, int credits) {
        // Validation
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        if (credits < 1) {
            throw new IllegalArgumentException("Credits must be positive");
        }

        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.credits = credits;
        this.maxCapacity = 30;  // Default capacity
        this.enrolledStudentIds = new ArrayList<>();
        this.department = null;  // Optional, set via setter
    }


    public Course(String courseId, String courseName, String instructor,
                  int credits, String department) {
        this(courseId, courseName, instructor, credits);  // Constructor chaining
        this.department = department;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getCredits() {
        return credits;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }


    public List<String> getEnrolledStudentIds() {
        return Collections.unmodifiableList(enrolledStudentIds);
    }

    public String getDepartment() {
        return department;
    }

    // Setters

    public void setCourseName(String courseName) {
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        this.courseName = courseName;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;  // Can be null
    }

    public void setCredits(int credits) {
        if (credits < 1) {
            throw new IllegalArgumentException("Credits must be positive");
        }
        this.credits = credits;
    }

    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity < enrolledStudentIds.size()) {
            throw new IllegalArgumentException(
                    "Max capacity cannot be less than current enrollment"
            );
        }
        this.maxCapacity = maxCapacity;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isFull() {
        return enrolledStudentIds.size() >= maxCapacity;
    }

    public int getAvailableSeats() {
        return maxCapacity - enrolledStudentIds.size();
    }


    public boolean addStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            return false;
        }
        if (isFull()) {
            return false;
        }
        if (enrolledStudentIds.contains(studentId)) {
            return false;  // Already enrolled
        }
        return enrolledStudentIds.add(studentId);
    }

    public boolean removeStudent(String studentId) {
        return enrolledStudentIds.remove(studentId);
    }

    public int getEnrollmentCount() {
        return enrolledStudentIds.size();
    }


    @Override
    public boolean matchesSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return false;
        }
        String lowerQuery = query.toLowerCase();
        return courseId.toLowerCase().contains(lowerQuery) ||
                courseName.toLowerCase().contains(lowerQuery) ||
                (instructor != null && instructor.toLowerCase().contains(lowerQuery)) ||
                (department != null && department.toLowerCase().contains(lowerQuery));
    }

    @Override
    public String getSearchableInfo() {
        return String.format("ID: %s | Name: %s | Instructor: %s | Credits: %d | Seats: %d/%d",
                courseId, courseName,
                instructor != null ? instructor : "TBD",
                credits, enrolledStudentIds.size(), maxCapacity);
    }


    public void displayInfo() {
        System.out.println("=== COURSE INFORMATION ===");
        System.out.println("Course ID: " + courseId);
        System.out.println("Course Name: " + courseName);
        System.out.println("Instructor: " + (instructor != null ? instructor : "TBD"));
        System.out.println("Credits: " + credits);
        System.out.println("Department: " + (department != null ? department : "N/A"));
        System.out.println("Enrollment: " + enrolledStudentIds.size() + "/" + maxCapacity);
        System.out.println("Available Seats: " + getAvailableSeats());
    }

    @Override
    public String toString() {
        return String.format("Course{id='%s', name='%s', enrolled=%d/%d}",
                courseId, courseName, enrolledStudentIds.size(), maxCapacity);
    }
}
