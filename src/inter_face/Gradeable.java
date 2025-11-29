package inter_face;

public interface Gradeable {

    String calculateGrade();

    double getNumericGrade();

    default boolean isPassing() {
        return getNumericGrade() >= 2.0;  // C or better
    }

    default String getGradeWithStatus() {
        return calculateGrade() + " (" + (isPassing() ? "PASS" : "FAIL") + ")";
    }

    static String getGradeDescription(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return "Excellent";
            case "B": return "Good";
            case "C": return "Satisfactory";
            case "D": return "Poor";
            case "F": return "Fail";
            default: return "Unknown";
        }
    }
}
