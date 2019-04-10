package client.Service;

import common.Domain.Assignment;
import common.Domain.Problem;
import common.Domain.Student;
import common.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.Future;

@Component
public class ClientServiceClient implements ServiceInterface {
    @Autowired
    private ServiceInterface studentService;

    public ClientServiceClient() {
    }

    @Override
    public void addStudent(Student student) {
        studentService.addStudent(student);
    }

    @Override
    public Set<Student> getAllStudents() {
        Set<Student> students = studentService.getAllStudents();
        return students;
    }

    @Override
    public void removeStudent(Long id) {
        studentService.removeStudent(id);
    }

    @Override
    public void updateStudent(Student student) {
        studentService.updateStudent(student);
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
        return studentService.getAllStudentsByGroup(group);
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
        studentService.setPageSize(size);
    }

    @Override
    public Set<Student> getNextStudents() {
        return studentService.getNextStudents();
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
