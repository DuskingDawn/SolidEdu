package exception;

public class InvalidDataException extends Exception {
    private String fieldName;
    private String invalidValue;

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String fieldName, String invalidValue) {
        super("Invalid " + fieldName + ": " + invalidValue);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}