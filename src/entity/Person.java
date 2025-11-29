package entity;

import java.time.LocalDate;
import java.time.Period;

public abstract class Person {

    private final String id;
    private String name;
    private String email;
    private final LocalDate dateOfBirth;

    private static int personCount = 0;

    protected Person(String id, String name, String email, LocalDate dateOfBirth) {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid date of birth");
        }

        this.id = id;
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        personCount++;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public abstract void displayInfo();

    public static int getPersonCount() {
        return personCount;
    }


    @Override
    public String toString() {
        return String.format("Person{id='%s', name='%s'}", id, name);
    }

}