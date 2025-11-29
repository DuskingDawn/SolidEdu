package entity;

import java.time.LocalDate;


public class GraduateStudent extends Student {

    private String thesisTitle;
    private String advisor;
    private String researchArea;
    private boolean thesisSubmitted;

    public GraduateStudent(String id, String name, String email, LocalDate dateOfBirth,
                           String major, int semester, String thesisTitle, String advisor) {
        super(id, name, email, dateOfBirth, major, semester);

        if (thesisTitle == null || thesisTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Thesis title cannot be null or empty");
        }
        if (advisor == null || advisor.trim().isEmpty()) {
            throw new IllegalArgumentException("Advisor cannot be null or empty");
        }

        this.thesisTitle = thesisTitle;
        this.advisor = advisor;
        this.researchArea = major;
        this.thesisSubmitted = false;  // Default: thesis not yet submitted
    }


    public String getThesisTitle() {
        return thesisTitle;
    }

    public void setThesisTitle(String thesisTitle) {
        if (thesisTitle == null || thesisTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Thesis title cannot be null or empty");
        }
        this.thesisTitle = thesisTitle;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        if (advisor == null || advisor.trim().isEmpty()) {
            throw new IllegalArgumentException("Advisor cannot be null or empty");
        }
        this.advisor = advisor;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public boolean isThesisSubmitted() {
        return thesisSubmitted;
    }

    public void setThesisSubmitted(boolean thesisSubmitted) {
        this.thesisSubmitted = thesisSubmitted;
    }


    public void submitThesis() {
        if (thesisSubmitted) {
            throw new IllegalStateException("Thesis already submitted");
        }
        this.thesisSubmitted = true;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();

        System.out.println("--- Graduate Information ---");
        System.out.println("Thesis Title: " + (thesisTitle != null ? thesisTitle : "Not assigned"));
        System.out.println("Advisor: " + (advisor != null ? advisor : "Not assigned"));
        System.out.println("Research Area: " + researchArea);
        System.out.println("Thesis Status: " + (thesisSubmitted ? "Submitted ✓" : "In Progress"));
    }


    @Override
    public String calculateGrade() {
        double gpa = getGpa();
        if (gpa >= 3.85) return "A";
        if (gpa >= 3.5) return "B";
        if (gpa >= 3.0) return "C";
        if (gpa >= 2.5) return "D";
        return "F";
    }


    @Override
    public boolean isPassing() {
        return getNumericGrade() >= 3.0;
    }


    @Override
    public boolean matchesSearch(String query) {
        if (super.matchesSearch(query)) {
            return true;
        }

        String lowerQuery = query.toLowerCase();
        return (thesisTitle != null && thesisTitle.toLowerCase().contains(lowerQuery)) ||
                (advisor != null && advisor.toLowerCase().contains(lowerQuery)) ||
                (researchArea != null && researchArea.toLowerCase().contains(lowerQuery));
    }

    @Override
    public String getSearchableInfo() {
        return String.format("%s | Thesis: %s | Advisor: %s",
                super.getSearchableInfo(), thesisTitle, advisor);
    }

    @Override
    public String toString() {
        return String.format("GraduateStudent{id='%s', name='%s', thesis='%s', advisor='%s'}",
                getId(), getName(), thesisTitle, advisor);
    }
}

