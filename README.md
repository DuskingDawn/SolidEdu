# Student Management System

A Java-based student management system demonstrating core OOP principles, SOLID design patterns, and clean code architecture.

## Features

- **Student & Course Management**: Complete CRUD operations for students and courses
- **Graduate Student Support**: Extended functionality through inheritance
- **Smart Course Recommendations**: Weighted algorithm considering major, GPA, and prerequisites
- **Data Persistence**: CSV-based file storage
- **Input Validation**: Comprehensive validation and custom exception handling
- **Interactive Console Interface**: Menu-driven system for easy interaction

## Architecture

### Package Structure
```
src/
├── entity/           # Domain models (Person, Student, GraduateStudent, Course)
├── inter_face/       # Interfaces (Gradeable, Searchable)
├── service/          # Business logic (StudentService, CourseService)
├── util/             # Utilities (InputValidator, AIHelper)
├── exception/        # Custom exceptions
└── main/             # Application entry point
```

### Design Highlights

**Inheritance Hierarchy**
```
Person (Abstract)
    ↓
Student → implements Gradeable, Searchable
    ↓
GraduateStudent → overrides grading logic
```

**Key OOP Concepts Demonstrated**
- **Abstraction**: Abstract `Person` class with template methods
- **Encapsulation**: Private fields with controlled access
- **Inheritance**: `Student` extends `Person`, `GraduateStudent` extends `Student`
- **Polymorphism**: Method overriding for different grading criteria
- **Interface Segregation**: Separate `Gradeable` and `Searchable` contracts

**SOLID Principles**
- **Single Responsibility**: Separate service layer for business logic
- **Open/Closed**: Extensible through inheritance without modification
- **Liskov Substitution**: `GraduateStudent` can replace `Student` seamlessly
- **Interface Segregation**: Small, focused interfaces
- **Dependency Inversion**: Depends on abstractions, not concrete classes

## Course Recommendation System

Uses a weighted scoring algorithm (0-10 points):

| Factor | Weight | Description |
|--------|--------|-------------|
| Major Alignment | 40% | Matches student's major |
| Prerequisites | 30% | Appropriate for student's semester level |
| GPA vs Difficulty | 20% | Course difficulty matches student capability |
| Availability | 10% | Course has open seats |

**Minimum recommendation threshold**: 5/10 points

## Getting Started

### Prerequisites
- Java 8 or higher
- No external dependencies required

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/student-management-system.git
cd student-management-system
```

2. Compile the project
```bash
javac -d bin src/**/*.java
```

3. Run the application
```bash
java -cp bin main.Main
```

### Usage

The system provides an interactive menu with the following options:

1. **Inheritance Demo** - Explore Person → Student → GraduateStudent hierarchy
2. **Polymorphism Demo** - See method overriding in action
3. **Interface Segregation** - Compare Gradeable vs Searchable implementations
4. **Course Recommendations** - Get personalized course suggestions
5. **CRUD Operations** - Manage students and courses
6. **Validation & Exception Handling** - Test input validation
7. **System Statistics** - View aggregate data and analytics

## Data Persistence

Data is stored in CSV format under the `data/` directory:
```
data/
├── students.csv
├── graduate_students.csv
└── courses.csv
```

Files are automatically created on first run and updated after each modification.

## Project Structure

```
student-management-system/
├── src/
│   ├── entity/        # Domain entities (Person, Student, GraduateStudent, Course)
│   ├── inter_face/    # Contracts (Gradeable, Searchable)
│   ├── service/       # Business logic layer (StudentService, CourseService)
│   ├── util/          # Utilities (InputValidator, AIHelper)
│   ├── exception/     # Custom exceptions
│   └── main.java      # Entry point
├── data/
│   ├── students.csv
│   ├── graduate_students.csv
│   └── courses.csv
└── README.md
```

## License

This project is created for educational purposes as part of a Java OOPs Fundamentals course.

## Author

[Sneha Kurmi]
