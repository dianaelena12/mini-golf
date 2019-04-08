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

    @Autowired
    public void addStudent(Student student) {
        clientService.addStudent(student);
    }

    @Autowired
    public Set<Student> getAllStudents() {
        return clientService.getAllStudents();
    }

    @Autowired
    public void removeStudent(Long id) {
        clientService.removeStudent(id);
    }

    @Autowired
    public void updateStudent(Student student) {
        clientService.updateStudent(student);
    }

    @Autowired
    public void addProblem(Problem problem) {

    }

    @Autowired
    public Set<Problem> getAllProblems() {
        return null;
    }

    @Autowired
    public void removeProblem(Long id) {

    }

    @Autowired
    public void updateProblem(Problem problem) {

    }

    @Autowired
    public void addAssignment(Assignment assignment) {

    }

    @Autowired
    public void assignGrade(Long studentID, Long problemID, int grade) {

    }

    @Autowired
    public Set<Assignment> getAllAssignments() {
        return null;
    }

    @Autowired
    public Set<Student> getAllStudentsByGroup(int group) {
        return clientService.getAllStudentsByGroup(group);
    }

    @Autowired
    public Set<Problem> getAllProblemsByDifficulty(String difficulty) {
        return null;
    }

    @Autowired
    public Set<Assignment> getUngradedAssignments() {
        return null;
    }

    @Autowired
    public void setPageSize(int size) {
        clientService.setPageSize(size);
    }

    @Autowired
    public Set<Student> getNextStudents() {
        return clientService.getNextStudents();
    }

    @Autowired
    public Set<Problem> getNextProblems() {
        return null;
    }

    @Autowired
    public Set<Assignment> getNextAssignments() {
        return null;
    }
}
