package service;

import entity.Course;
import exception.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private List<Course> courses;
    private static final String COURSE_FILE = "data/courses.csv";

    public CourseService() {
        this.courses = new ArrayList<>();
        loadCoursesFromFile();
    }

    public void addCourse(Course course) throws InvalidDataException {
        if (course.getCourseId() == null || course.getCourseId().isEmpty()) {
            throw new InvalidDataException("Course ID cannot be empty");
        }

        if (findCourseById(course.getCourseId()) != null) {
            throw new InvalidDataException("Course with ID " + course.getCourseId() + " already exists");
        }

        courses.add(course);
        saveCoursesToFile();
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public Course findCourseById(String id) {
        return courses.stream()
                .filter(c -> c.getCourseId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Course> searchCourses(String query) {
        return courses.stream()
                .filter(c -> c.matchesSearch(query))
                .collect(Collectors.toList());
    }

    public void updateCourse(String id, Course updatedCourse) throws CourseNotFoundException {
        Course existing = findCourseById(id);
        if (existing == null) {
            throw new CourseNotFoundException(id);
        }

        existing.setCourseName(updatedCourse.getCourseName());
        existing.setInstructor(updatedCourse.getInstructor());
        existing.setCredits(updatedCourse.getCredits());
        existing.setMaxCapacity(updatedCourse.getMaxCapacity());
        existing.setDepartment(updatedCourse.getDepartment());

        saveCoursesToFile();
    }

    public void deleteCourse(String id) throws CourseNotFoundException {
        Course course = findCourseById(id);
        if (course == null) {
            throw new CourseNotFoundException(id);
        }

        courses.remove(course);
        saveCoursesToFile();
    }

    public List<Course> getCoursesByDepartment(String department) {
        return courses.stream()
                .filter(c -> c.getDepartment() != null &&
                        c.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    private void saveCoursesToFile() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COURSE_FILE))) {
            // Write header
            writer.write("CourseID,CourseName,Instructor,Credits,MaxCapacity,Department,EnrolledCount\n");

            // Write course data
            for (Course course : courses) {
                writer.write(courseToCSV(course));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
        }
    }

    private void loadCoursesFromFile() {
        courses.clear();
        File file = new File(COURSE_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                Course course = csvToCourse(line);
                if (course != null) {
                    courses.add(course);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }
    }


    private String courseToCSV(Course course) {
        return String.format("%s,%s,%s,%d,%d,%s,%d",
                course.getCourseId(),
                course.getCourseName(),
                course.getInstructor(),
                course.getCredits(),
                course.getMaxCapacity(),
                course.getDepartment() != null ? course.getDepartment() : "",
                course.getEnrolledStudentIds().size()
        );
    }

    private Course csvToCourse(String csv) {
        try {
            String[] parts = csv.split(",");
            if (parts.length < 6) return null;

            Course course = new Course(
                    parts[0], // courseId
                    parts[1], // courseName
                    parts[2], // instructor
                    Integer.parseInt(parts[3]) // credits
            );
            course.setMaxCapacity(Integer.parseInt(parts[4]));
            course.setDepartment(parts[5]);

            return course;
        } catch (Exception e) {
            System.err.println("Error parsing course CSV: " + e.getMessage());
            return null;
        }
    }
}