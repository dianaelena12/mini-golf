package server;

import common.Domain.Assignment;
import common.Domain.Problem;
import common.Domain.Student;
import common.Domain.Validators.AssignmentValidator;
import common.Domain.Validators.DuplicateException;
import common.Domain.Validators.ProblemValidator;
import common.Domain.Validators.StudentValidator;
import common.Message;
import common.ServiceInterface;
import server.DBRepos.AssignmentDBRepo;
import server.DBRepos.ProblemDBRepo;
import server.DBRepos.StudentDBRepo;
import server.Paging.PagingRepository;
import server.Service.ServiceImpl;
import server.TCP.TCPServer;
import server.TCP.TCPServerException;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ServerApp {
    public static void main(String[] args) {
        ExecutorService executorService =
                Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors());

        TCPServer tcpServer = new TCPServer(executorService,
                ServiceInterface.SERVER_PORT);

        PagingRepository<Long, Student> studRepo = new StudentDBRepo(new StudentValidator());
        PagingRepository<Long, Problem> problemRepo = new ProblemDBRepo(new ProblemValidator());
        PagingRepository<Long, Assignment> assigRepo = new AssignmentDBRepo(new AssignmentValidator());

        ServiceInterface service = new ServiceImpl(executorService, studRepo, problemRepo, assigRepo);


        tcpServer.addHandler(
                ServiceInterface.GET_ALL_STUDENTS, (request) -> {
                    try {
                        Future<Set<Student>> result = service.getAllStudents();
                        String body = result.get().stream().map(s -> "" + s.getId() + ","
                                + s.getSerialNumber() + "," + s.getName() + "," + s.getGroup())
                                .collect(Collectors.joining(";"));
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.ADD_STUDENT, (request) -> {
                    try {
                        String[] list = request.getBody().split(",");
                        Student student = new Student(list[1], list[2], Integer.parseInt(list[3]));
                        student.setId(Long.valueOf(list[0]).longValue());
                        service.addStudent(student);
                        return getMessage(Message.OK, "Student added!");
                    } catch (TCPServerException | DuplicateException ex) {
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.REMOVE_STUDENT, (request) -> {
                    try {
                        service.removeStudent(Long.parseLong(request.getBody()));
                        return getMessage(Message.OK, "Student removed!");
                    } catch (TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.UPDATE_STUDENT, (request) -> {
                    try {
                        String[] list = request.getBody().split(",");
                        Student student = new Student(list[1], list[2], Integer.parseInt(list[3]));
                        student.setId(Long.valueOf(list[0]).longValue());
                        service.updateStudent(student);
                        return getMessage(Message.OK, "Student updated!");
                    } catch (TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.GET_ALL_PROBLEMS, (request) -> {
                    try {
                        Future<Set<Problem>> result = service.getAllProblems();
                        String body = result.get().stream().map(p -> "" + p.getId() + ","
                                + p.getSubject() + "," + p.getDifficulty() + "," + p.getText())
                                .collect(Collectors.joining(";"));
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.ADD_PROBLEM, (request) -> {
                    try {
                        String[] list = request.getBody().split(",");
                        Problem problem = new Problem(list[1], list[2], list[3]);
                        problem.setId(Long.valueOf(list[0]).longValue());
                        service.addProblem(problem);
                        return getMessage(Message.OK, "Problem added!");
                    } catch (TCPServerException | DuplicateException ex) {
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.REMOVE_PROBLEM, (request) -> {
                    try {
                        service.removeProblem(Long.parseLong(request.getBody()));
                        return getMessage(Message.OK, "Problem removed!");
                    } catch (TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );
        tcpServer.addHandler(
                ServiceInterface.UPDATE_PROBLEM, (request) -> {
                    try {
                        String[] list = request.getBody().split(",");
                        Problem problem = new Problem(list[1], list[2], list[3]);
                        problem.setId(Long.valueOf(list[0]).longValue());
                        service.updateProblem(problem);
                        return getMessage(Message.OK, "Problem updated!");
                    } catch (TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.GET_BY_GROUP, (request) -> {
                    try {
                        Future<Set<Student>> result = service.getAllStudentsByGroup(Integer.parseInt(request.getBody()));
                        String body = result.get().stream().map(s -> "" + s.getId() + "," + s.getSerialNumber() + ","
                                + s.getName() + "," + s.getGroup()).collect(Collectors.
                                joining(";"));
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );
        tcpServer.addHandler(
                ServiceInterface.GET_BY_DIFFICULTY, (request) -> {
                    try {
                        Future<Set<Problem>> result = service.getAllProblemsByDifficulty(request.getBody());
                        String body = result.get().stream().map(s -> "" + s.getId() + "," + s.getDifficulty() + ","
                                + s.getSubject() + "," + s.getText()).collect(Collectors.
                                joining(";"));
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );
        tcpServer.addHandler(
                ServiceInterface.ADD_ASSIGNMENT, (request) -> {
                    try {
                        String[] list = request.getBody().split(",");
                        Assignment assignment = new Assignment(Long.parseLong(list[1]), Long.parseLong(list[2]), Integer.parseInt(list[3]));
                        assignment.setId(Long.valueOf(list[0]).longValue());
                        service.addAssignment(assignment);
                        return getMessage(Message.OK, "Assignment added!");
                    } catch (TCPServerException | DuplicateException ex) {
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );
        tcpServer.addHandler(
                ServiceInterface.GET_UNGRADED, (request) -> {
                    try {
                        Future<Set<Assignment>> result = service.getUngradedAssignments();
                        String body = result.get().stream().map(s -> "" + s.getId() + "," + s.getStudentID() + ","
                                + s.getProblemID() + "," + s.getGrade()).collect(Collectors.
                                joining(";"));
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );
        tcpServer.addHandler(
                ServiceInterface.GET_ALL_ASSIGNMENTS, (request) -> {
                    try {
                        Future<Set<Assignment>> result = service.getAllAssignments();
                        String body = result.get().stream().map(s -> "" + s.getId() + "," + s.getStudentID() + ","
                                + s.getProblemID() + "," + s.getGrade()).collect(Collectors.
                                joining(";"));
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.GET_NEXT_STUDENTS, (request) -> {
                    try {
                        Future<Set<Student>> result = service.getNextStudents();
                        String body = result.get().stream().map(s -> "" + s.getId() + "," + s.getSerialNumber() + ","
                                + s.getName() + "," + s.getGroup()).collect(Collectors.joining(";"));
                        System.out.println(body);
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );
        tcpServer.addHandler(
                ServiceInterface.GET_NEXT_PROBLEMS, (request) -> {
                    try {
                        Future<Set<Problem>> result = service.getNextProblems();
                        String body = result.get().stream().map(s -> "" + s.getId() + "," + s.getDifficulty() + ","
                                + s.getSubject() + "," + s.getText()).collect(Collectors.joining(";"));
                        System.out.println(body);
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.GET_NEXT_ASSIGNMENTS, (request) -> {
                    try {
                        Future<Set<Assignment>> result = service.getNextAssignments();
                        String body = result.get().stream().map(s -> "" + s.getId() + "," + s.getStudentID() + ","
                                + s.getProblemID() + "," + s.getGrade()).collect(Collectors.joining(";"));
                        System.out.println(body);
                        return getMessage(Message.OK, body);
                    } catch (InterruptedException | ExecutionException | TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.addHandler(
                ServiceInterface.SET_SIZE, (request) -> {
                    try {
                        service.setPageSize(Integer.parseInt(request.getBody()));
                        return getMessage(Message.OK, "");
                    } catch (TCPServerException ex) {
                        ex.printStackTrace();
                        return getMessage(Message.ERROR, ex.getMessage());
                    }
                }
        );

        tcpServer.startServer();

        System.out.println("server - bye");
    }

    private static Message getMessage(String header, String body) {
        return Message.builder()
                .header(header)
                .body(body)
                .build();
    }


}
