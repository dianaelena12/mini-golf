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
    private ServiceInterface clientService;

    public ClientServiceClient() {
    }

    @Override
    public void addStudent(Student student) {
        clientService.addStudent(student);
    }

    @Override
    public Set<Student> getAllStudents() {
        return clientService.getAllStudents();
    }

    @Override
    public void removeStudent(Long id) {
        clientService.removeStudent(id);
    }

    @Override
    public void updateStudent(Student student) {
        clientService.updateStudent(student);
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
        return clientService.getAllStudentsByGroup(group);
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
        clientService.setPageSize(size);
    }

    @Override
    public Set<Student> getNextStudents() {
        return clientService.getNextStudents();
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
