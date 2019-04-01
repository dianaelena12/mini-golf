package client.Service;

import client.TCP.TCPClient;
import common.Domain.Assignment;
import common.Domain.Problem;
import common.Domain.Student;
import common.Message;
import common.ServerException;
import common.ServiceInterface;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ServiceClientImpl implements ServiceInterface {
    private ExecutorService executorService;
    private TCPClient tcpClient;

    public ServiceClientImpl(
            ExecutorService executorService, TCPClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public void addStudent(Student student) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.ADD_STUDENT)
                    .body("" + student.getId() + "," + student.getSerialNumber()+ ","
                    + student.getName() + "," + student.getGroup())
                    .build();
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR)) {
                throw new ServerException(response.getBody());
            }
        });
    }

    @Override
    public Future<Set<Student>> getAllStudents() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.GET_ALL_STUDENTS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);

            if(response.getHeader().equals(Message.ERROR)) {
                throw new ServerException(response.getBody());
            }
            if(response.getBody().length() == 0) {
                return new HashSet<>();
            }
            List<String> students = Arrays.asList(response.getBody().split(";"));
            return students.stream().map((s) -> {
                String[] list = s.split(",");
                Student student = new Student(list[1], list[2], Integer.parseInt(list[3]));
                student.setId(Long.parseLong(list[0]));
                return student;
            }).collect(Collectors.toSet());
        });
    }

    @Override
    public void removeStudent(Long id) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.REMOVE_STUDENT)
                    .body(id.toString())
                    .build();
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR)){
                throw new ServerException(response.getBody());
            }
        });
    }

    @Override
    public void updateStudent(Student student) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.UPDATE_STUDENT)
                    .body("" + student.getId() + "," + student.getSerialNumber()+ ","
                            + student.getName() + "," + student.getGroup())
                    .build();
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR)){
                throw new ServerException(response.getBody());
            }
        });
    }

    @Override
    public void addProblem(Problem problem) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.ADD_PROBLEM)
                    .body("" + problem.getId() + "," + problem.getSubject()+ ","
                            + problem.getDifficulty() + "," + problem.getText())
                    .build();
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR)) {
                throw new ServerException(response.getBody());
            }
        });
    }

    @Override
    public Future<Set<Problem>> getAllProblems() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.GET_ALL_PROBLEMS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);

            if(response.getHeader().equals(Message.ERROR)) {
                throw new ServerException(response.getBody());
            }
            if(response.getBody().length() == 0) {
                return new HashSet<>();
            }
            List<String> problems = Arrays.asList(response.getBody().split(";"));
            return problems.stream().map((p) -> {
                String[] list = p.split(",");
                Problem problem = new Problem(list[1], list[2], list[3]);
                problem.setId(Long.parseLong(list[0]));
                return problem;
            }).collect(Collectors.toSet());
        });
    }

    @Override
    public void removeProblem(Long id) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.REMOVE_PROBLEM)
                    .body(id.toString())
                    .build();
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(Message.ERROR)){
                throw new ServerException(response.getBody());
            }
        });
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
    public Future<Set<Assignment>> getAllAssignments() {
        return null;
    }

    @Override
    public Future<Set<Student>> getAllStudentsByGroup(int group) {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.GET_BY_GROUP)
                    .body("" + group)
                    .build();

            Message response = tcpClient.sendAndReceive(request);

            List<String> students = Arrays.asList(response.getBody().split(";"));
            return students.stream().map((s) -> {
                String[] list = s.split(",");
                Student student = new Student(list[1], list[2], Integer.parseInt(list[3]));
                student.setId(Long.parseLong(list[0]));
                return student;
            }).collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Problem>> getAllProblemsByDifficulty(String difficulty) {
        return null;
    }

    @Override
    public Future<Set<Assignment>> getUngradedAssignments() {
        return null;
    }

    @Override
    public void setPageSize(int size) {
        executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.SET_SIZE)
                    .body(String.valueOf(size))
                    .build();

            Message response = tcpClient.sendAndReceive(request);
            if (response.getHeader().equals(Message.ERROR))
                throw new ServerException(response.getBody());
        });
    }

    @Override
    public Future<Set<Student>> getNextStudents() {
        return executorService.submit(() -> {
            Message request = Message.builder()
                    .header(ServiceInterface.GET_NEXT_STUDENTS)
                    .build();

            Message response = tcpClient.sendAndReceive(request);

            if(response.getHeader().equals(Message.ERROR)) {
                throw new ServerException(response.getBody());
            }

            if(response.getBody().length() == 0) {
                return new HashSet<>();
            }
            List<String> students = Arrays.asList(response.getBody().split(";"));
            return students.stream().map((s) -> {
                String[] list = s.split(",");
                Student student = new Student(list[1], list[2], Integer.parseInt(list[3]));
                student.setId(Long.parseLong(list[0]));
                return student;
            }).collect(Collectors.toSet());
        });
    }

    @Override
    public Future<Set<Problem>> getNextProblems() {
        return null;
    }

    @Override
    public Future<Set<Assignment>> getNextAssignments() {
        return null;
    }
}
