package Service;

import Domain.Assignment;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.InvalidAssignment;
import Domain.Validators.ValidatorException;
import Repo.Paging.Impl.PageRequest;
import Repo.Paging.Page;
import Repo.Paging.Pageable;
import Repo.Paging.PagingRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service implements Service.ServiceApp {
    private PagingRepository<Long, Student> studentRepo;
    private PagingRepository<Long, Problem> problemRepo;
    private PagingRepository<Long, Assignment> assignmentRepo;

    private int page = 0;
    private int size = 1;

    public Service(PagingRepository<Long, Student> studentRepo, PagingRepository<Long, Problem> problemRepo,
                   PagingRepository<Long, Assignment> assignmentRepo) {
        this.studentRepo = studentRepo;
        this.problemRepo = problemRepo;
        this.assignmentRepo = assignmentRepo;
    }

    @Override
    public void addStudent(Student student) throws ValidatorException {
        studentRepo.save(student);
    }

    @Override
    public Set<Student> getAllStudents() {
        Iterable<Student> students = studentRepo.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public void removeStudent(Long id) {
        studentRepo.delete(id);
    }

    @Override
    public void updateStudent(Student student) {
        studentRepo.update(student);
    }

    @Override
    public void addProblem(Problem problem) throws ValidatorException {
        problemRepo.save(problem);
    }

    @Override
    public Set<Problem> getAllProblems() {
        Iterable<Problem> problems = problemRepo.findAll();
        return StreamSupport.stream(problems.spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public void removeProblem(Long id) {
        problemRepo.delete(id);
    }

    @Override
    public void updateProblem(Problem problem) {
        problemRepo.update(problem);
    }

    @Override
    public void addAssignment(Assignment assignment) {
        if (studentRepo.findOne(assignment.getStudentID()).isPresent() &&
                problemRepo.findOne(assignment.getProblemID()).isPresent()) {
            assignmentRepo.save(assignment);
        } else {
            throw new InvalidAssignment("The student or the problem is not in the database!");
        }
    }

    @Override
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

    @Override
    public Set<Assignment> getAllAssignments() {
        Iterable<Assignment> assignments = assignmentRepo.findAll();
        return StreamSupport.stream(assignments.spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public Set<Student> getAllStudentsByGroup(int group) {
        Iterable<Student> students = studentRepo.findAll();
        return StreamSupport.stream(students.spliterator(), false).filter(student -> student.getGroup() == group).collect(Collectors.toSet());
    }

    @Override
    public Set<Problem> getAllProblemsByDifficulty(String difficulty) {
        Iterable<Problem> problems = problemRepo.findAll();
        return StreamSupport.stream(problems.spliterator(), false).filter(problem -> difficulty.equals(problem.getDifficulty())).collect(Collectors.toSet());
    }

    @Override
    public Set<Assignment> getUngradedAssignments() {
        Iterable<Assignment> assignments = assignmentRepo.findAll();
        return StreamSupport.stream(assignments.spliterator(), false).filter(assignment -> assignment.getGrade() == 0).collect(Collectors.toSet());
    }

    @Override
    public void setPageSize(int size) {
        this.size = size;
        this.page = 0;
    }

    @Override
    public Set<Student> getNextStudents() {
        Pageable pageable = PageRequest.of(size, page);
        try {
            Page<Student> studentPage = studentRepo.findAll(pageable);
            page = studentPage.nextPageable().getPageNumber();
            return studentPage.getContent().collect(Collectors.toSet());
        } catch (IndexOutOfBoundsException ex) {
            page = 0;
            return new HashSet<>();
        }
    }

    @Override
    public Set<Problem> getNextProblems() {
        Pageable pageable = PageRequest.of(size, page);
        try {
            Page<Problem> problemPage = problemRepo.findAll(pageable);
            page = problemPage.nextPageable().getPageNumber();
            return problemPage.getContent().collect(Collectors.toSet());
        } catch (IndexOutOfBoundsException ex) {
            page = 0;
            return new HashSet<>();
        }
    }

    @Override
    public Set<Assignment> getNextAssignments() {
        Pageable pageable = PageRequest.of(size, page);
        try {
            Page<Assignment> assignmentPage = assignmentRepo.findAll(pageable);
            page = assignmentPage.nextPageable().getPageNumber();
            return assignmentPage.getContent().collect(Collectors.toSet());
        } catch (IndexOutOfBoundsException ex) {
            page = 0;
            return new HashSet<>();
        }
    }

}
