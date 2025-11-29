package util;

import entity.Course;
import entity.Student;
import java.util.*;

public class AIHelper {

    private AIHelper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }


    public static List<String> recommendCourses(Student student, List<Course> availableCourses) {
        if (student == null || availableCourses == null || availableCourses.isEmpty()) {
            return new ArrayList<>();
        }

        String studentMajor = student.getMajor();
        double studentGpa = student.getGpa();
        int studentSemester = student.getSemester();
        List<String> enrolledCourses = student.getEnrolledCourses();


        List<CourseRecommendation> scoredCourses = new ArrayList<>();

        for (Course course : availableCourses) {
            if (enrolledCourses.contains(course.getCourseId())) {
                continue;
            }

            int totalScore = 0;
            Map<String, Integer> scoreBreakdown = new HashMap<>();

            // FACTOR 1: Major Alignment (0-4 points, 40% weight)
            int majorScore = 0;
            if (course.getDepartment() != null &&
                    course.getDepartment().equalsIgnoreCase(studentMajor)) {
                majorScore = 4;  // Perfect match: in-major course
            } else if (course.getDepartment() != null &&
                    isRelatedMajor(studentMajor, course.getDepartment())) {
                majorScore = 2;  // Partial match: related field
            }
            scoreBreakdown.put("Major Match", majorScore);
            totalScore += majorScore;

            // FAcTOR 2: Prerequisite Readiness (0-3 points, 30% weight)
            int prereqScore = calculatePrerequisiteScore(
                    studentSemester, course.getCredits(), course.getCourseId()
            );
            scoreBreakdown.put("Prerequisites", prereqScore);
            totalScore += prereqScore;

            // FACTOR 3: GPA vs Difficulty Matching (0-2 points, 20% weight)
            int difficultyScore = matchGpaToCourseDifficulty(studentGpa, course.getCredits());
            scoreBreakdown.put("Difficulty Match", difficultyScore);
            totalScore += difficultyScore;

            // FACTOR 4: Availability (0-1 point, 10% weight)
            int availScore = course.isFull() ? 0 : 1;
            scoreBreakdown.put("Available", availScore);
            totalScore += availScore;

            // Only recommend if meets minimum threshold (50% = 5/10 points)
            if (totalScore >= 5) {
                scoredCourses.add(new CourseRecommendation(course, totalScore, scoreBreakdown));
            }
        }

        scoredCourses.sort((a, b) -> Integer.compare(b.score, a.score));

        List<String> recommendations = new ArrayList<>();
        int count = Math.min(5, scoredCourses.size());

        for (int i = 0; i < count; i++) {
            CourseRecommendation rec = scoredCourses.get(i);
            Course c = rec.course;

            String breakdown = formatScoreBreakdown(rec.breakdown);
            String recommendation = String.format(
                    "%s - %s (Match Score: %d/10) %s",
                    c.getCourseId(),
                    c.getCourseName(),
                    rec.score,
                    breakdown
            );
            recommendations.add(recommendation);
        }

        return recommendations;
    }


    private static int calculatePrerequisiteScore(int semester, int credits, String courseId) {

        int courseLevel = extractCourseLevel(courseId);

        int score = 0;

        if (semester <= 2) {
            if (courseLevel <= 100) {
                score = 3;
            } else if (courseLevel <= 200) {
                score = 1;
            }

        }
        else if (semester <= 4) {
            if (courseLevel <= 200) {
                score = 3;
            } else if (courseLevel <= 300) {
                score = 2;
            }
        }

        else {
            if (courseLevel >= 300) {
                score = 3;
            } else if (courseLevel >= 200) {
                score = 2;
            } else {
                score = 1;
            }
        }

        return score;
    }


    private static int matchGpaToCourseDifficulty(double gpa, int credits) {

        if (gpa >= 3.5) {
            return credits >= 4 ? 2 : 1;
        }

        else if (gpa >= 2.5) {
            return credits == 3 ? 2 : 1;
        }

        else {
            return credits <= 3 ? 2 : 0;
        }
    }

    private static boolean isRelatedMajor(String major1, String major2) {
        if (major1 == null || major2 == null) return false;

        String m1 = major1.toLowerCase();
        String m2 = major2.toLowerCase();

        Set<String> stemFields = new HashSet<>(Arrays.asList(
                "computer science", "mathematics", "physics", "engineering"
        ));

        if (stemFields.contains(m1) && stemFields.contains(m2)) {
            return true;
        }

        Set<String> businessFields = new HashSet<>(Arrays.asList(
                "business", "economics", "finance", "management"
        ));

        return businessFields.contains(m1) && businessFields.contains(m2);
    }

    private static int extractCourseLevel(String courseId) {
        if (courseId == null || courseId.length() < 3) {
            return 100;
        }

        String digits = courseId.replaceAll("[^0-9]", "");
        try {
            return Integer.parseInt(digits);
        } catch (NumberFormatException e) {
            return 100;
        }
    }


    private static String formatScoreBreakdown(Map<String, Integer> breakdown) {
        StringBuilder sb = new StringBuilder("[");
        List<String> parts = new ArrayList<>();

        parts.add("Major:" + breakdown.getOrDefault("Major Match", 0));
        parts.add("Prereq:" + breakdown.getOrDefault("Prerequisites", 0));
        parts.add("Difficulty:" + breakdown.getOrDefault("Difficulty Match", 0));
        parts.add("Available:" + breakdown.getOrDefault("Available", 0));

        sb.append(String.join(", ", parts));
        sb.append("]");

        return sb.toString();
    }

    private static class CourseRecommendation {
        final Course course;
        final int score;
        final Map<String, Integer> breakdown;

        CourseRecommendation(Course course, int score, Map<String, Integer> breakdown) {
            this.course = course;
            this.score = score;
            this.breakdown = breakdown;
        }
    }
}