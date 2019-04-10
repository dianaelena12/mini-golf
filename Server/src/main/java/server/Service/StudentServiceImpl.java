package server.Service;

import common.Domain.Assignment;
import common.Domain.Problem;
import common.Domain.Student;
import common.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.Paging.Impl.PageRequest;
import server.Paging.Page;
import server.Paging.Pageable;
import server.Repository.StudentRepositoryImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class StudentServiceImpl implements ServiceInterface {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private StudentRepositoryImpl studentRepository;
    private int size = 1;
    private int page = 0;

    public StudentServiceImpl() {
    }


    @Override
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Set<Student> getAllStudents() {
        Iterable<Student> students = studentRepository.findAll();
        return StreamSupport.stream(students.spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public void removeStudent(Long id) {
        studentRepository.delete(id);
    }

    @Override
    public void updateStudent(Student student) {
        studentRepository.update(student);
    }

    @Override
    public void addProblem(Problem problem) {

    }

    @Override
    public Set<Problem> getAllProblems() {
        return null;
    }

    @Override
    public void removeProblem(Long id) {

    }

    @Override
    public void updateProblem(Problem problem) {

    }

    @Override
    public void addAssignment(Assignment assignment) {

    }

    @Override
    public void assignGrade(Long studentID, Long problemID, int grade) {

    }

    @Override
    public Set<Assignment> getAllAssignments() {
        return null;
    }

    @Override
    public Set<Student> getAllStudentsByGroup(int group) {
        Iterable<Student> students = studentRepository.findAll();
        Set<Student> filtered = new HashSet<>();
        students.forEach(filtered::add);
        filtered.removeIf(s -> s.getGroup() != group);
        return filtered;
    }

    @Override
    public Set<Problem> getAllProblemsByDifficulty(String difficulty) {
        return null;
    }

    @Override
    public Set<Assignment> getUngradedAssignments() {
        return null;
    }

    @Override
    public void setPageSize(int size) {
        this.page = 0;
        this.size = size;
    }

    @Override
    public Set<Student> getNextStudents() {
        Pageable pageable = PageRequest.of(size, page);
        try {
            Page<Student> studentPage = studentRepository.findAll(pageable);
            page = ((Page) studentPage).nextPageable().getPageNumber();
            return studentPage.getContent().collect(Collectors.toSet());
        } catch (IndexOutOfBoundsException e) {
            page = 0;
            return new HashSet<>();
        }
    }

    @Override
    public Set<Problem> getNextProblems() {
        return null;
    }

    @Override
    public Set<Assignment> getNextAssignments() {
        return null;
    }
}
