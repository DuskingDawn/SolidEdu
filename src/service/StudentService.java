package service;
import entity.GraduateStudent;
import entity.Student;
import exception.InvalidDataException;
import exception.StudentNotFoundException;
import util.InputValidator;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentService {

    private final List<Student> students;

    private static final String DATA_DIR = "data";
    private static final String STUDENT_FILE = DATA_DIR + "/students.csv";
    private static final String GRADUATE_FILE = DATA_DIR + "/graduate_students.csv";


    public StudentService() {
        this.students = new ArrayList<>();
        loadStudentsFromFile();
    }


    public void addStudent(Student student) throws InvalidDataException {
        InputValidator.validateId(student.getId());
        InputValidator.validateEmail(student.getEmail());

        if (findStudentById(student.getId()) != null) {
            throw new InvalidDataException("Student with ID " + student.getId() + " already exists");
        }

        students.add(student);

        // Persist immediately (could batch, but immediate is simpler and safer)
        saveStudentsToFile();
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public Student findStudentById(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Student> searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return students.stream()
                .filter(s -> s.matchesSearch(query))
                .collect(Collectors.toList());
    }

    public void updateStudent(String id, Student updatedData)
            throws StudentNotFoundException, InvalidDataException {

        Student existing = findStudentById(id);
        if (existing == null) {
            throw new StudentNotFoundException(id);
        }

        InputValidator.validateEmail(updatedData.getEmail());

        existing.setName(updatedData.getName());
        existing.setEmail(updatedData.getEmail());
        existing.setMajor(updatedData.getMajor());
        existing.setSemester(updatedData.getSemester());
        existing.setGpa(updatedData.getGpa());
        existing.setAttendancePercentage(updatedData.getAttendancePercentage());

        saveStudentsToFile();
    }

    public void deleteStudent(String id) throws StudentNotFoundException {
        Student student = findStudentById(id);
        if (student == null) {
            throw new StudentNotFoundException(id);
        }

        students.remove(student);
        saveStudentsToFile();
    }


    public List<Student> getStudentsByMajor(String major) {
        return students.stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsByGrade(String letterGrade) {
        return students.stream()
                .filter(s -> s.calculateGrade().equals(letterGrade))
                .collect(Collectors.toList());
    }

    public List<GraduateStudent> getGraduateStudents() {
        List<GraduateStudent> gradStudents = new ArrayList<>();
        for (Student student : students) {
            if (student instanceof GraduateStudent) {
                gradStudents.add((GraduateStudent) student);
            }
        }
        return gradStudents;
    }


    public double calculateAverageGPA() {
        return students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", students.size());
        stats.put("averageGPA", calculateAverageGPA());
        stats.put("graduateStudents", getGraduateStudents().size());

        // Grade distribution using grouping
        Map<String, Long> gradeDistribution = students.stream()
                .collect(Collectors.groupingBy(Student::calculateGrade, Collectors.counting()));
        stats.put("gradeDistribution", gradeDistribution);

        return stats;
    }

    private void saveStudentsToFile() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE))) {
            writer.write("ID,Name,Email,DateOfBirth,Major,Semester,GPA,Attendance\n");

            for (Student student : students) {
                if (!(student instanceof GraduateStudent)) {
                    writer.write(studentToCSV(student));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GRADUATE_FILE))) {
            writer.write("ID,Name,Email,DateOfBirth,Major,Semester,GPA,Attendance,ThesisTitle,Advisor,ResearchArea,ThesisSubmitted\n");

            for (Student student : students) {
                if (student instanceof GraduateStudent) {
                    writer.write(graduateStudentToCSV((GraduateStudent) student));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving graduate students: " + e.getMessage());
        }
    }

    private void loadStudentsFromFile() {
        students.clear();

        File studentFile = new File(STUDENT_FILE);
        if (studentFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(studentFile))) {
                reader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    Student student = csvToStudent(line);
                    if (student != null) {
                        students.add(student);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading students: " + e.getMessage());
            }
        }


        File gradFile = new File(GRADUATE_FILE);
        if (gradFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(gradFile))) {
                reader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    GraduateStudent student = csvToGraduateStudent(line);
                    if (student != null) {
                        students.add(student);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading graduate students: " + e.getMessage());
            }
        }
    }

    private String studentToCSV(Student student) {
        return String.format("%s,%s,%s,%s,%s,%d,%.2f,%d",
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDateOfBirth(),
                student.getMajor(),
                student.getSemester(),
                student.getGpa(),
                student.getAttendancePercentage()
        );
    }

    private String graduateStudentToCSV(GraduateStudent student) {
        return String.format("%s,%s,%s,%s,%s,%d,%.2f,%d,%s,%s,%s,%b",
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDateOfBirth(),
                student.getMajor(),
                student.getSemester(),
                student.getGpa(),
                student.getAttendancePercentage(),
                student.getThesisTitle(),
                student.getAdvisor(),
                student.getResearchArea(),
                student.isThesisSubmitted()
        );
    }

    private Student csvToStudent(String csv) {
        try {
            String[] parts = csv.split(",");
            if (parts.length < 8) return null;

            Student student = new Student(
                    parts[0], parts[1], parts[2],
                    LocalDate.parse(parts[3]),
                    parts[4], Integer.parseInt(parts[5])
            );
            student.setGpa(Double.parseDouble(parts[6]));
            student.setAttendancePercentage(Integer.parseInt(parts[7]));

            return student;
        } catch (Exception e) {
            System.err.println("Error parsing student CSV: " + e.getMessage());
            return null;
        }
    }

    private GraduateStudent csvToGraduateStudent(String csv) {
        try {
            String[] parts = csv.split(",");
            if (parts.length < 12) return null;

            GraduateStudent student = new GraduateStudent(
                    parts[0], parts[1], parts[2],
                    LocalDate.parse(parts[3]),
                    parts[4], Integer.parseInt(parts[5]),
                    parts[8], parts[9]
            );
            student.setGpa(Double.parseDouble(parts[6]));
            student.setAttendancePercentage(Integer.parseInt(parts[7]));
            student.setResearchArea(parts[10]);
            student.setThesisSubmitted(Boolean.parseBoolean(parts[11]));

            return student;
        } catch (Exception e) {
            System.err.println("Error parsing graduate student CSV: " + e.getMessage());
            return null;
        }
    }
}