package springjpa.Domain;

import java.util.Objects;

public class Assignment extends BaseEntity<Long> {
    private Long studentID, problemID;
    private int grade;

    public Assignment(){}

    public Assignment(Long studentID, Long problemID) {
        this.studentID = studentID;
        this.problemID = problemID;
        this.grade = 0;
    }

    public Assignment(Long studentID, Long problemID, int grade) {
        this.studentID = studentID;
        this.problemID = problemID;
        this.grade = grade;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public Long getProblemID() {
        return problemID;
    }

    public void setProblemID(Long problemID) {
        this.problemID = problemID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment assignment = (Assignment) o;
        return grade == assignment.grade &&
                Objects.equals(studentID, assignment.studentID) &&
                Objects.equals(problemID, assignment.problemID);
    }

    @Override
    public int hashCode() {
        int result = studentID.hashCode();
        result = 31 * result + problemID.hashCode();
        result = 31 * result + grade;
        return result;
    }

    @Override
    public String toString() {
        return "Assignment{\n Student ID: " + studentID +
                "\n Problem ID: " + problemID + "\n Grade: " + grade +
                "\n       }" + super.toString();
    }
}
