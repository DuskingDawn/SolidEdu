package exception;

public class CourseNotFoundException extends Exception {
  private String courseId;

  public CourseNotFoundException(String courseId) {
    super("Course not found with ID: " + courseId);
    this.courseId = courseId;
  }

  public CourseNotFoundException(String courseId, String message) {
    super(message);
    this.courseId = courseId;
  }

  public String getCourseId() {
    return courseId;
  }
}