import entity.*;
import service.*;
import util.*;
import exception.*;
import java.time.LocalDate;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║  STUDENT MANAGEMENT SYSTEM - DESIGN SHOWCASE   ║");
        System.out.println("║  Demonstrating SOLID Principles & OOP Concepts ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        initializeSampleData();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    demonstrateInheritance();
                    break;
                case 2:
                    demonstratePolymorphism();
                    break;
                case 3:
                    demonstrateInterfaceSegregation();
                    break;
                case 4:
                    demonstrateCourseRecommendation();
                    break;
                case 5:
                    demonstrateCRUD();
                    break;
                case 6:
                    demonstrateValidation();
                    break;
                case 7:
                    displayStatistics();
                    break;
                case 0:
                    running = false;
                    System.out.println("\n✓ Exiting system. Thank you!");
                    break;
                default:
                    System.out.println("\n✗ Invalid choice. Try again.\n");
            }

            if (running) {
                pauseForUser();
            }
        }

        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU - Design Principle Demonstrations");
        System.out.println("=".repeat(50));
        System.out.println("1. Inheritance Demo (Person → Student → GraduateStudent)");
        System.out.println("2. Polymorphism Demo (Method Overriding)");
        System.out.println("3. Interface Segregation (Gradeable, Searchable)");
        System.out.println("4. Smart Course Recommendation System");
        System.out.println("5. CRUD Operations (Create, Read, Update, Delete)");
        System.out.println("6. Validation & Exception Handling");
        System.out.println("7. View System Statistics");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
    }

    private static void demonstrateInheritance() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  DEMO 1: INHERITANCE HIERARCHY             ║");
        System.out.println("╚════════════════════════════════════════════╝");

        System.out.println("\n📌 WHY THIS DESIGN?");
        System.out.println("   Person is ABSTRACT because:");
        System.out.println("   • We never create generic 'Person' objects");
        System.out.println("   • Forces subclasses to implement displayInfo()");
        System.out.println("   • Provides common fields (id, name, email, DOB)");

        System.out.println("\n📌 Student is CONCRETE because:");
        System.out.println("   • We DO create Student objects directly");
        System.out.println("   • Has complete implementation");
        System.out.println("   • Represents real undergraduate students");

        System.out.println("\n📌 GraduateStudent EXTENDS Student because:");
        System.out.println("   • GraduateStudent IS-A Student (true inheritance)");
        System.out.println("   • Adds grad-specific fields (thesis, advisor)");
        System.out.println("   • Overrides behavior (stricter grading standards)");

        System.out.println("\n" + "-".repeat(50));
        System.out.println("EXAMPLE: Creating Students");
        System.out.println("-".repeat(50));

        Student student = new Student(
                "STU001", "Divya Jain", "divya@university.edu",
                LocalDate.of(2003, 5, 15), "Computer Science", 3
        );
        student.setGpa(3.6);
        student.enrollInCourse("CS101");
        student.enrollInCourse("MATH201");

        System.out.println("\n1️⃣  UNDERGRADUATE STUDENT:");
        student.displayInfo();

        GraduateStudent gradStudent = new GraduateStudent(
                "GRAD001", "Raj Singh", "raj@university.edu",
                LocalDate.of(1998, 8, 22), "Computer Science", 7,
                "Machine Learning in Healthcare", "Dr. Sarah Williams"
        );
        gradStudent.setGpa(3.8);
        gradStudent.setResearchArea("Artificial Intelligence");

        System.out.println("\n2️⃣  GRADUATE STUDENT:");
        gradStudent.displayInfo();

        System.out.println("\n📊 KEY OBSERVATIONS:");
        System.out.println("   ✓ Both share Person attributes (inherited)");
        System.out.println("   ✓ GraduateStudent has additional fields");
        System.out.println("   ✓ GraduateStudent overrides displayInfo() to show more data");
        System.out.println("   ✓ Code reuse through inheritance (DRY principle)");
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  DEMO 2: POLYMORPHISM & METHOD OVERRIDING  ║");
        System.out.println("╚════════════════════════════════════════════╝");

        System.out.println("\n📌 WHAT IS POLYMORPHISM?");
        System.out.println("   'Many forms' - same method, different behavior");
        System.out.println("   Key: calculateGrade() works differently for:");
        System.out.println("   • Student: Regular grading (GPA ≥ 3.7 = A)");
        System.out.println("   • GraduateStudent: Stricter (GPA ≥ 3.85 = A)");

        System.out.println("\n" + "-".repeat(50));
        System.out.println("EXAMPLE: Same Method, Different Behavior");
        System.out.println("-".repeat(50));

        double testGpa = 3.75;

        Student undergrad = new Student(
                "STU002", "Chandra Pandey", "chandra@university.edu",
                LocalDate.of(2004, 3, 10), "Physics", 4
        );
        undergrad.setGpa(testGpa);

        GraduateStudent grad = new GraduateStudent(
                "GRAD002", "Diya Prince", "diya@university.edu",
                LocalDate.of(1997, 12, 5), "Physics", 8,
                "Quantum Computing Applications", "Dr. John Anderson"
        );
        grad.setGpa(testGpa);

        System.out.println("\n🧪 TEST CASE: Both have GPA = " + testGpa);
        System.out.println("\n   Undergraduate Grade: " + undergrad.calculateGrade() +
                " (Threshold: 3.7 for A)");
        System.out.println("   Graduate Grade: " + grad.calculateGrade() +
                " (Threshold: 3.85 for A)");

        System.out.println("\n📊 POLYMORPHISM IN ACTION:");
        System.out.println("   List<Student> students = [undergrad, grad];");
        System.out.println("   for (Student s : students) {");
        System.out.println("       s.calculateGrade(); // Calls correct version!");
        System.out.println("   }");

        List<Student> students = Arrays.asList(undergrad, grad);
        System.out.println("\n   Results:");
        for (Student s : students) {
            System.out.println("   • " + s.getName() + ": " + s.calculateGrade() +
                    " (isPassing: " + s.isPassing() + ")");
        }

        System.out.println("\n✓ Runtime decides which method to call (dynamic dispatch)");
    }

    private static void demonstrateInterfaceSegregation() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  DEMO 3: INTERFACE SEGREGATION PRINCIPLE   ║");
        System.out.println("╚════════════════════════════════════════════╝");

        System.out.println("\n📌 WHY INTERFACES?");
        System.out.println("   • Java doesn't allow multiple inheritance (Diamond Problem)");
        System.out.println("   • Interfaces provide contracts without implementation");
        System.out.println("   • Classes can implement multiple interfaces");

        System.out.println("\n📌 STUDENT IMPLEMENTS:");
        System.out.println("   1. Gradeable - because students can be graded");
        System.out.println("   2. Searchable - because students can be searched");

        System.out.println("\n📌 COURSE IMPLEMENTS:");
        System.out.println("   • Searchable - courses can be searched");
        System.out.println("   • NOT Gradeable - courses don't have grades");

        System.out.println("\n" + "-".repeat(50));
        System.out.println("EXAMPLE: Using Interfaces for Polymorphism");
        System.out.println("-".repeat(50));

        Student student = studentService.getAllStudents().stream()
                .findFirst()
                .orElse(new Student("DEMO", "Demo Student", "demo@edu.edu",
                        LocalDate.of(2003, 1, 1), "CS", 1));

        Course course = courseService.getAllCourses().stream()
                .findFirst()
                .orElse(new Course("CS101", "Intro to CS", "Dr. Smith", 3));


        System.out.println("\n1️⃣  GRADEABLE INTERFACE:");
        System.out.println("   Student grade: " + student.calculateGrade());
        System.out.println("   Numeric GPA: " + student.getNumericGrade());
        System.out.println("   Is passing? " + student.isPassing());
        System.out.println("   Grade description: " +
                inter_face.Gradeable.getGradeDescription(student.calculateGrade()));

        System.out.println("\n2️⃣  SEARCHABLE INTERFACE:");
        System.out.println("   Can search students and courses with same interface!\n");

        String searchQuery = "CS";
        System.out.println("   Search query: '" + searchQuery + "'");
        System.out.println("   Student matches? " + student.matchesSearch(searchQuery));
        System.out.println("   Course matches? " + course.matchesSearch(searchQuery));

        // Polymorphic collection
        System.out.println("\n3️⃣  POLYMORPHIC USAGE:");
        System.out.println("   List<Searchable> searchables = [students, courses];");

        List<inter_face.Searchable> searchables = new ArrayList<>();
        searchables.add(student);
        searchables.add(course);

        System.out.println("\n   Results:");
        for (inter_face.Searchable searchable : searchables) {
            System.out.println("   • " + searchable.getSearchableInfo());
        }

        System.out.println("\n✓ Same interface, different implementations!");
    }

    private static void demonstrateCourseRecommendation() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  DEMO 4: SMART COURSE RECOMMENDATION       ║");
        System.out.println("╚════════════════════════════════════════════╝");

        System.out.println("\n📌 WHY THIS FEATURE?");
        System.out.println("   Real universities need course recommendation systems");
        System.out.println("   Based on academic research about student success factors");

        System.out.println("\n📌 ALGORITHM DESIGN:");
        System.out.println("   Weighted scoring system considers:");
        System.out.println("   • Major alignment (40%) - Most important");
        System.out.println("   • Prerequisite completion (30%) - Must be ready");
        System.out.println("   • GPA vs difficulty (20%) - Match capability");
        System.out.println("   • Availability (10%) - Can't take full courses");

        System.out.println("\n" + "-".repeat(50));
        System.out.println("SELECT A STUDENT");
        System.out.println("-".repeat(50));

        List<Student> students = studentService.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students available");
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println((i + 1) + ". " + s.getName() +
                    " (" + s.getMajor() + ", GPA: " + s.getGpa() + ")");
        }

        int choice = getIntInput("\nSelect student (1-" + students.size() + "): ");
        if (choice < 1 || choice > students.size()) {
            System.out.println("Invalid choice");
            return;
        }

        Student student = students.get(choice - 1);
        List<Course> allCourses = courseService.getAllCourses();

        System.out.println("\n" + "-".repeat(50));
        System.out.println("ANALYZING: " + student.getName());
        System.out.println("-".repeat(50));
        System.out.println("Major: " + student.getMajor());
        System.out.println("Semester: " + student.getSemester());
        System.out.println("GPA: " + student.getGpa());
        System.out.println("Current Courses: " + student.getEnrolledCourses().size());

        List<String> recommendations = AIHelper.recommendCourses(student, allCourses);

        System.out.println("\n" + "-".repeat(50));
        System.out.println("TOP RECOMMENDATIONS");
        System.out.println("-".repeat(50));

        if (recommendations.isEmpty()) {
            System.out.println("No suitable courses available");
            System.out.println("(Add more courses via CRUD menu to see recommendations)");
        } else {
            for (int i = 0; i < recommendations.size(); i++) {
                System.out.println((i + 1) + ". " + recommendations.get(i));
            }
        }

        System.out.println("\n📊 WHY THIS DESIGN?");
        System.out.println("   ✓ Stateless utility method (no object state needed)");
        System.out.println("   ✓ Clear scoring criteria (transparent algorithm)");
        System.out.println("   ✓ Easily testable and maintainable");
        System.out.println("   ✓ Based on real academic advising principles");
    }


    private static void demonstrateCRUD() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  DEMO 5: CRUD OPERATIONS (Service Layer)   ║");
        System.out.println("╚════════════════════════════════════════════╝");

        System.out.println("\n📌 WHY SERVICE LAYER?");
        System.out.println("   • Separates business logic from entities");
        System.out.println("   • Handles transactions (CRUD operations)");
        System.out.println("   • Manages data persistence (CSV files)");
        System.out.println("   • Enforces business rules (no duplicate IDs)");

        System.out.println("\n" + "-".repeat(50));
        System.out.println("CRUD MENU");
        System.out.println("-".repeat(50));
        System.out.println("1. Create Student");
        System.out.println("2. Read (View) Student");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Students");
        System.out.println("0. Back to Main Menu");

        int choice = getIntInput("\nChoice: ");

        try {
            switch (choice) {
                case 1:
                    createStudentDemo();
                    break;
                case 2:
                    readStudentDemo();
                    break;
                case 3:
                    updateStudentDemo();
                    break;
                case 4:
                    deleteStudentDemo();
                    break;
                case 5:
                    searchStudentDemo();
                    break;
            }
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage());
        }
    }

    private static void demonstrateValidation() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  DEMO 6: VALIDATION & EXCEPTION HANDLING   ║");
        System.out.println("╚════════════════════════════════════════════╝");

        System.out.println("\n📌 WHY VALIDATION?");
        System.out.println("   • Prevents invalid data from entering system");
        System.out.println("   • Fail-fast: catch errors immediately");
        System.out.println("   • Centralized in InputValidator (DRY)");

        System.out.println("\n📌 CUSTOM EXCEPTIONS:");
        System.out.println("   • StudentNotFoundException");
        System.out.println("   • InvalidDataException");
        System.out.println("   • CourseNotFoundException");
        System.out.println("   → More meaningful than generic RuntimeException");

        System.out.println("\n" + "-".repeat(50));
        System.out.println("VALIDATION TESTS");
        System.out.println("-".repeat(50));

        String[] testEmails = {
                "valid@university.edu",
                "invalid-email",
                "no-at-sign.com"
        };

        System.out.println("\n1️⃣  EMAIL VALIDATION:");
        for (String email : testEmails) {
            boolean valid = InputValidator.isValidEmail(email);
            System.out.println("   " + email + " → " +
                    (valid ? "✓ VALID" : "✗ INVALID"));
        }

        String[] testIds = {
                "STU001",
                "GRAD123",
                "invalid",
                "123"
        };

        System.out.println("\n2️⃣  ID VALIDATION (4-10 uppercase alphanumeric):");
        for (String id : testIds) {
            boolean valid = InputValidator.isValidId(id);
            System.out.println("   " + id + " → " +
                    (valid ? "✓ VALID" : "✗ INVALID"));
        }

        double[] testGPAs = {3.5, 4.0, 4.5, -1.0};

        System.out.println("\n3️⃣  GPA VALIDATION (0.0 - 4.0):");
        for (double gpa : testGPAs) {
            boolean valid = InputValidator.isValidGPA(gpa);
            System.out.println("   " + gpa + " → " +
                    (valid ? "✓ VALID" : "✗ INVALID"));
        }

        System.out.println("\n4️⃣  EXCEPTION HANDLING DEMO:");
        try {
            System.out.println("   Attempting to add student with invalid email...");
            Student badStudent = new Student(
                    "TEST001", "Test Student", "bad-email",
                    LocalDate.of(2003, 1, 1), "CS", 1
            );
            studentService.addStudent(badStudent);
        } catch (InvalidDataException e) {
            System.out.println("   ✓ Caught: " + e.getMessage());
        }

        try {
            System.out.println("\n   Attempting to find non-existent student...");
            studentService.updateStudent("NONEXIST", null);
        } catch (StudentNotFoundException e) {
            System.out.println("   ✓ Caught: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("   ✓ Caught: " + e.getClass().getSimpleName());
        }

        System.out.println("\n✓ Validation prevents bad data, exceptions provide clear errors!");
    }

    private static void displayStatistics() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║  SYSTEM STATISTICS                         ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        Map<String, Object> stats = studentService.getStatistics();

        System.out.println("Total Students: " + stats.get("totalStudents"));
        System.out.println("Graduate Students: " + stats.get("graduateStudents"));
        System.out.println("Average GPA: " + String.format("%.2f", stats.get("averageGPA")));

        @SuppressWarnings("unchecked")
        Map<String, Long> gradeDistribution =
                (Map<String, Long>) stats.get("gradeDistribution");

        System.out.println("\nGrade Distribution:");
        gradeDistribution.forEach((grade, count) ->
                System.out.println("  " + grade + ": " + count + " students"));

        System.out.println("\nTotal Courses: " + courseService.getAllCourses().size());
        System.out.println("Total Persons Created: " + Person.getPersonCount());
    }

    private static void createStudentDemo() throws InvalidDataException {
        System.out.println("\n--- CREATE STUDENT ---");
        System.out.print("ID (e.g., STU001): ");
        String id = scanner.nextLine();
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Birth Year (e.g., 2003): ");
        int year = getIntInput("");
        System.out.print("Major: ");
        String major = scanner.nextLine();
        System.out.print("Semester: ");
        int semester = getIntInput("");

        Student student = new Student(
                id, name, email,
                LocalDate.of(year, 1, 1),
                major, semester
        );

        studentService.addStudent(student);
        System.out.println("\n✓ Student created successfully!");
        student.displayInfo();
    }

    private static void readStudentDemo() {
        System.out.println("\n--- VIEW STUDENT ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();

        Student student = studentService.findStudentById(id);
        if (student != null) {
            student.displayInfo();
        } else {
            System.out.println("✗ Student not found");
        }
    }

    private static void updateStudentDemo() throws Exception {
        System.out.println("\n--- UPDATE STUDENT ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();

        Student existing = studentService.findStudentById(id);
        if (existing == null) {
            System.out.println("✗ Student not found");
            return;
        }

        System.out.println("\nCurrent data:");
        existing.displayInfo();

        System.out.print("\nNew GPA (current: " + existing.getGpa() + "): ");
        double gpa = Double.parseDouble(scanner.nextLine());

        existing.setGpa(gpa);
        studentService.updateStudent(id, existing);
        System.out.println("\n✓ Student updated!");
    }

    private static void deleteStudentDemo() throws StudentNotFoundException {
        System.out.println("\n--- DELETE STUDENT ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();

        studentService.deleteStudent(id);
        System.out.println("✓ Student deleted");
    }

    private static void searchStudentDemo() {
        System.out.println("\n--- SEARCH STUDENTS ---");
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();

        List<Student> results = studentService.searchStudents(query);
        System.out.println("\nFound " + results.size() + " results:");
        results.forEach(s -> System.out.println("  • " + s.getSearchableInfo()));
    }

    private static void initializeSampleData() {
        try {
            if (studentService.getAllStudents().isEmpty()) {
                Student s1 = new Student(
                        "STU001", "Divya Jain", "divya@university.edu",
                        LocalDate.of(2003, 5, 15), "Computer Science", 3
                );
                s1.setGpa(3.6);
                s1.setAttendancePercentage(92);
                s1.enrollInCourse("CS101");
                s1.enrollInCourse("MATH201");
                studentService.addStudent(s1);

                Student s2 = new Student(
                        "STU002", "Raj Singh", "raj@university.edu",
                        LocalDate.of(2004, 8, 22), "Physics", 2
                );
                s2.setGpa(3.2);
                s2.setAttendancePercentage(85);
                studentService.addStudent(s2);

                // Add graduate student
                GraduateStudent g1 = new GraduateStudent(
                        "GRAD001", "Carol Williams", "carol@university.edu",
                        LocalDate.of(1998, 3, 10), "Computer Science", 7,
                        "Machine Learning in Healthcare", "Dr. Sarah Anderson"
                );
                g1.setGpa(3.85);
                g1.setAttendancePercentage(95);
                studentService.addStudent(g1);
            }

            if (courseService.getAllCourses().isEmpty()) {
                Course c1 = new Course("CS101", "Intro to Programming", "Dr. Smith", 3, "Computer Science");
                c1.setMaxCapacity(30);
                courseService.addCourse(c1);

                Course c2 = new Course("CS201", "Data Structures", "Dr. Johnson", 4, "Computer Science");
                c2.setMaxCapacity(25);
                courseService.addCourse(c2);

                Course c3 = new Course("CS301", "Algorithms", "Dr. Brown", 4, "Computer Science");
                c3.setMaxCapacity(20);
                courseService.addCourse(c3);

                Course c4 = new Course("MATH201", "Calculus II", "Dr. Taylor", 4, "Mathematics");
                courseService.addCourse(c4);

                Course c5 = new Course("PHYS101", "Physics I", "Dr. Wilson", 4, "Physics");
                courseService.addCourse(c5);
            }
        } catch (Exception e) {
            System.out.println("exception in initializeSampleData");
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("✗ Please enter a valid number");
            }
        }
    }

    private static void pauseForUser() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}