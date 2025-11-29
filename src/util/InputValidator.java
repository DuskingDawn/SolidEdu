package util;
import exception.InvalidDataException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern ID_PATTERN =
            Pattern.compile("^[A-Z0-9]{4,10}$");
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private InputValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidId(String id) {
        return id != null && ID_PATTERN.matcher(id).matches();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isPositiveInteger(int value) {
        return value > 0;
    }


    public static boolean isValidGPA(double gpa) {
        return gpa >= 0.0 && gpa <= 4.0;
    }

    public static boolean isValidGrade(double grade) {
        return grade >= 0.0 && grade <= 100.0;
    }

    public static boolean isValidAttendance(int attendance) {
        return attendance >= 0 && attendance <= 100;
    }

    public static LocalDate parseDate(String dateStr) throws InvalidDataException {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDataException("date", dateStr);
        }
    }

    public static boolean isValidDateFormat(String dateStr) {
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidStudentAge(LocalDate dateOfBirth) {
        int age = LocalDate.now().getYear() - dateOfBirth.getYear();
        return age >= 16 && age <= 100;
    }

    public static boolean isValidLetterGrade(String grade) {
        return grade != null &&
                (grade.equals("A") || grade.equals("B") ||
                        grade.equals("C") || grade.equals("D") || grade.equals("F"));
    }

    public static String sanitize(String input) {
        return input == null ? "" : input.trim();
    }

    public static void validateEmail(String email) throws InvalidDataException {
        if (!isValidEmail(email)) {
            throw new InvalidDataException("email", email);
        }
    }

    public static void validateId(String id) throws InvalidDataException {
        if (!isValidId(id)) {
            throw new InvalidDataException("ID", id);
        }
    }

    public static void validateNotEmpty(String value, String fieldName) throws InvalidDataException {
        if (!isNotEmpty(value)) {
            throw new InvalidDataException(fieldName + " cannot be empty", "");
        }
    }
}