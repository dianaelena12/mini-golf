package Service;

import Domain.Assignment;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.InvalidAssignment;
import Domain.Validators.ValidatorException;
import Repo.RepositoryInterface;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service {
    private RepositoryInterface<Long, Student> studentRepo;
    private RepositoryInterface<Long, Problem> problemRepo;
    private RepositoryInterface<Long, Assignment> assignmentRepo;

    public Service(RepositoryInterface<Long, Student> studentRepo, RepositoryInterface<Long, Problem> problemRepo,
                   RepositoryInterface<Long, Assignment> assignmentRepo) {
        this.studentRepo = studentRepo;
        this.problemRepo = problemRepo;
        this.assignmentRepo = assignmentRepo;
    }

    public void addStudent(Student student) throws ValidatorException {
        studentRepo.save(student);
    }

    public Set<Student> getAllStudents() {
        Iterable<Student> students = studentRepo.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    public void removeStudent(Long id) {
        studentRepo.delete(id);
    }

    public void updateStudent(Student student) {
        studentRepo.update(student);
    }

    public void addProblem(Problem problem) throws ValidatorException {
        problemRepo.save(problem);
    }

    public Set<Problem> getAllProblems() {
        Iterable<Problem> problems = problemRepo.findAll();
        return StreamSupport.stream(problems.spliterator(), false).collect(Collectors.toSet());
    }

    public void removeProblem(Long id) {
        problemRepo.delete(id);
    }

    public void updateProblem(Problem problem) {
        problemRepo.update(problem);
    }

    public void addAssignment(Assignment assignment) {
        if (studentRepo.findOne(assignment.getStudentID()).isPresent() &&
                problemRepo.findOne(assignment.getProblemID()).isPresent()) {
            assignmentRepo.save(assignment);
        } else {
            throw new InvalidAssignment("The student or the problem is not in the database!");
        }
    }

    public void assignGrade(Long studentID, Long problemID, int grade) {
        if (studentRepo.findOne(studentID).isPresent() && problemRepo.findOne(problemID).isPresent()) {
            getAllAssignments().forEach(assignment -> {
                if (assignment.getStudentID() == studentID && assignment.getProblemID() == problemID) {
                    assignment.getGrade();
                    Assignment assignmentUpdate = new Assignment(studentID, problemID, grade);
                    assignmentUpdate.setId(assignment.getId());
                    assignmentRepo.update(assignmentUpdate);
                }
            });
        } else {
            throw new InvalidAssignment("The student or the problem is not in the database!");
        }
    }

    public Set<Assignment> getAllAssignments() {
        Iterable<Assignment> assignments = assignmentRepo.findAll();
        return StreamSupport.stream(assignments.spliterator(), false).collect(Collectors.toSet());
    }

    public Set<Student> getAllStudentsByGroup(int group) {
        Iterable<Student> students = studentRepo.findAll();
        return StreamSupport.stream(students.spliterator(), false).filter(student -> student.getGroup() == group).collect(Collectors.toSet());
    }

    public Set<Problem> getAllProblemsByDifficulty(String difficulty) {
        Iterable<Problem> problems = problemRepo.findAll();
        return StreamSupport.stream(problems.spliterator(), false).filter(problem -> difficulty.equals(problem.getDifficulty())).collect(Collectors.toSet());
    }

    public Set<Assignment> getUngradedAssignments() {
        Iterable<Assignment> assignments = assignmentRepo.findAll();
        return StreamSupport.stream(assignments.spliterator(), false).filter(assignment -> assignment.getGrade() == 0).collect(Collectors.toSet());
    }
}
