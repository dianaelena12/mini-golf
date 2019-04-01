package server.Service;

import common.Domain.Assignment;
import common.Domain.Problem;
import common.Domain.Student;
import common.Domain.Validators.DuplicateException;
import common.Domain.Validators.NoEntityStored;
import common.ServiceInterface;
import server.Paging.Impl.PageRequest;
import server.Paging.Page;
import server.Paging.Pageable;
import server.Paging.PagingRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceImpl implements ServiceInterface {
    private ExecutorService executorService;
    private PagingRepository<Long, Student> studRepo;
    private PagingRepository<Long, Problem> problemRepo;
    private PagingRepository<Long, Assignment> assigRepo;

    private int size = 1;
    private int page = 0;

    public ServiceImpl(ExecutorService executorService,
                       PagingRepository<Long, Student> studRepo,
                       PagingRepository<Long, Problem> problemRepo,
                       PagingRepository<Long, Assignment> assigRepo) {
        this.executorService = executorService;
        this.studRepo = studRepo;
        this.problemRepo = problemRepo;
        this.assigRepo = assigRepo;
    }

    @Override
    public void addStudent(Student student) {
        executorService.submit(() -> {
            Optional<Student> optStud = studRepo.save(student);
            optStud.ifPresent(s -> {
                throw new DuplicateException("Student already exists in database!");
            });
        });
    }

    @Override
    public Future<Set<Student>> getAllStudents() {
        return executorService.submit(() -> {
            Iterable<Student> students = studRepo.findAll();
            return StreamSupport.stream(students.spliterator(), false)
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public void removeStudent(Long id) {
        executorService.submit(() -> {
            Optional<Student> optStud = studRepo.delete(id);
            optStud.orElseThrow(() -> new NoEntityStored("Student does not exist in database!"));
        });
    }

    @Override
    public void updateStudent(Student student) {
        executorService.submit(() -> {
            Optional<Student> optStud = studRepo.update(student);
            optStud.orElseThrow(() -> new NoEntityStored("Student does not exist in database!"));
        });
    }

    @Override
    public void addProblem(Problem problem) {
        executorService.submit(() -> {
            Optional<Problem> optProb = problemRepo.save(problem);
            optProb.ifPresent(s -> {
                throw new DuplicateException("Problem already exists in database!");
            });
        });
    }

    @Override
    public Future<Set<Problem>> getAllProblems() {
        return executorService.submit(() -> {
            Iterable<Problem> problems = problemRepo.findAll();
            return StreamSupport.stream(problems.spliterator(), false)
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public void removeProblem(Long id) {
        executorService.submit(() -> {
            Optional<Problem> optProb = problemRepo.delete(id);
            optProb.orElseThrow(() -> new NoEntityStored("Problem does not exist in database!"));
        });
    }

    @Override
    public void updateProblem(Problem problem) {
        executorService.submit(() -> {
            Optional<Problem> optProb = problemRepo.update(problem);
            optProb.orElseThrow(() -> new NoEntityStored("Problem does not exist in database!"));
        });
    }

    @Override
    public void addAssignment(Assignment assignment) {
        executorService.submit(() -> {
            Optional<Assignment> optAssign = assigRepo.save(assignment);
            optAssign.ifPresent(s -> {
                throw new DuplicateException("Assignment already exists in database!");
            });
        });
    }

    @Override
    public void assignGrade(Long studentID, Long problemID, int grade) {
    }

    @Override
    public Future<Set<Assignment>> getAllAssignments() {
        return executorService.submit(() -> {
            Iterable<Assignment> assignments = assigRepo.findAll();
            return StreamSupport.stream(assignments.spliterator(), false)
                    .collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Student>> getAllStudentsByGroup(int group) {
        return executorService.submit(() -> {
            Iterable<Student> students = studRepo.findAll();
            Set<Student> filterStudents = new HashSet<>();
            students.forEach(filterStudents::add);
            filterStudents.removeIf(s -> s.getGroup() != group);
            return filterStudents;
        });
    }

    @Override
    public Future<Set<Problem>> getAllProblemsByDifficulty(String difficulty) {
        return executorService.submit(() -> {
            Iterable<Problem> problems = problemRepo.findAll();
            Set<Problem> filterProblems = new HashSet<>();
            problems.forEach(filterProblems::add);
            filterProblems.removeIf(p -> !difficulty.equals(p.getDifficulty()));
            return filterProblems;
        });
    }

    @Override
    public Future<Set<Assignment>> getUngradedAssignments() {
        return executorService.submit(() -> {
            Iterable<Assignment> assignments = assigRepo.findAll();
            Set<Assignment> filterAssignments = new HashSet<>();
            assignments.forEach(filterAssignments::add);
            filterAssignments.removeIf(a -> a.getGrade() != 0);
            return filterAssignments;
        });
    }

    @Override
    public void setPageSize(int size) {
        executorService.submit(() -> {
            this.page = 0;
            this.size = size;
        });
    }

    @Override
    public Future<Set<Student>> getNextStudents() {
        return executorService.submit(() -> {
            Pageable pageable = PageRequest.of(size, page);
            try {
                Page<Student> studentPage = studRepo.findAll(pageable);
                page = studentPage.nextPageable().getPageNumber();
                return studentPage.getContent().collect(Collectors.toSet());
            } catch (IndexOutOfBoundsException ex) {
                page = 0;
                return new HashSet<>();
            }
        });
    }

    @Override
    public Future<Set<Problem>> getNextProblems() {
        return executorService.submit(() -> {
            Pageable pageable = PageRequest.of(size,page);
            try {
                Page<Problem> problemPage = problemRepo.findAll(pageable);
                page = problemPage.nextPageable().getPageNumber();
                return problemPage.getContent().collect(Collectors.toSet());
            } catch (IndexOutOfBoundsException ex) {
                page = 0;
                return new HashSet<>();
            }
        });
    }

    @Override
    public Future<Set<Assignment>> getNextAssignments() {
        return executorService.submit(() -> {
            Pageable pageable = PageRequest.of(size,page);
            try {
                Page<Assignment> assignmentPage = assigRepo.findAll(pageable);
                page = assignmentPage.nextPageable().getPageNumber();
                return assignmentPage.getContent().collect(Collectors.toSet());
            } catch (IndexOutOfBoundsException ex) {
                page = 0;
                return new HashSet<>();
            }
        });
    }
}
