package springjpa.Domain;

import java.util.Objects;

public class Problem extends BaseEntity<Long> {
    private String subject;
    private String difficulty;
    private String text;

    public Problem() {
    }

    public Problem(String subject, String difficulty, String text) {
        this.subject = subject;
        this.difficulty = difficulty;
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return subject == problem.subject &&
                Objects.equals(difficulty, problem.difficulty) &&
                Objects.equals(text, problem.text);
    }

    @Override
    public int hashCode() {
        int result = subject.hashCode();
        result = 31 * result + difficulty.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Problem{\n Subject: " + subject +
                "\n Difficulty: " + difficulty + "\n Text: " + text +
                "\n       }" + super.toString();
    }
}
