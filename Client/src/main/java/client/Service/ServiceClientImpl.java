//package client.Service;
//
//import client.TCP.TCPClient;
//import common.Domain.Assignment;
//import common.Domain.Problem;
//import common.Domain.Student;
//import common.Message;
//import common.ServerException;
//import common.ServiceInterface;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Future;
//import java.util.stream.Collectors;
//
//public class ServiceClientImpl implements ServiceInterface {
//    private ExecutorService executorService;
//    private TCPClient tcpClient;
//
//    public ServiceClientImpl(
//            ExecutorService executorService, TCPClient tcpClient) {
//        this.executorService = executorService;
//        this.tcpClient = tcpClient;
//    }
//
//    @Override
//    public void addStudent(Student student) {
//        //added a student
//        executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.ADD_STUDENT)
//                    .body("" + student.getId() + "," + student.getSerialNumber() + ","
//                            + student.getName() + "," + student.getGroup())
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//        });
//    }
//
//    @Override
//    public Future<Set<Student>> getAllStudents() {
//        return executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.GET_ALL_STUDENTS)
//                    .build();
//
//            Message response = tcpClient.sendAndReceive(request);
//
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//            if (response.getBody().length() == 0) {
//                return new HashSet<>();
//            }
//            List<String> students = Arrays.asList(response.getBody().split(";"));
//            return students.stream().map((s) -> {
//                String[] list = s.split(",");
//                Student student = new Student(list[1], list[2], Integer.parseInt(list[3]));
//                student.setId(Long.parseLong(list[0]));
//                return student;
//            }).collect(Collectors.toSet());
//        });
//    }
//
//    @Override
//    public void removeStudent(Long id) {
//        executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.REMOVE_STUDENT)
//                    .body(id.toString())
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//        });
//    }
//
//    @Override
//    public void updateStudent(Student student) {
//        executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.UPDATE_STUDENT)
//                    .body("" + student.getId() + "," + student.getSerialNumber() + ","
//                            + student.getName() + "," + student.getGroup())
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//        });
//    }
//
//    @Override
//    public void addProblem(Problem problem) {
//        executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.ADD_PROBLEM)
//                    .body("" + problem.getId() + "," + problem.getSubject() + ","
//                            + problem.getDifficulty() + "," + problem.getText())
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//        });
//    }
//
//    @Override
//    public Future<Set<Problem>> getAllProblems() {
//        return executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.GET_ALL_PROBLEMS)
//                    .build();
//
//            Message response = tcpClient.sendAndReceive(request);
//
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//            if (response.getBody().length() == 0) {
//                return new HashSet<>();
//            }
//            List<String> problems = Arrays.asList(response.getBody().split(";"));
//            return problems.stream().map((p) -> {
//                String[] list = p.split(",");
//                Problem problem = new Problem(list[1], list[2], list[3]);
//                problem.setId(Long.parseLong(list[0]));
//                return problem;
//            }).collect(Collectors.toSet());
//        });
//    }
//
//    @Override
//    public void removeProblem(Long id) {
//        executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.REMOVE_PROBLEM)
//                    .body(id.toString())
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//        });
//    }
//
//    @Override
//    public void updateProblem(Problem problem) {
//        executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.UPDATE_PROBLEM)
//                    .body("" + problem.getId() + "," + problem.getDifficulty() + ","
//                            + problem.getSubject() + "," + problem.getText())
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//        });
//
//    }
//
//    @Override
//    public void addAssignment(Assignment assignment) {
//        executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.ADD_ASSIGNMENT)
//                    .body("" + assignment.getId() + "," + assignment.getStudentID() + ","
//                            + assignment.getProblemID() + "," + assignment.getGrade())
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//        });
//
//    }
//
//    @Override
//    public void assignGrade(Long studentID, Long problemID, int grade) {
//        executorService.submit(() -> {
//            Message request = Message
//                    .builder()
//                    .header(ServiceInterface.ASSIGN_GRADE)
//                    .body("" + studentID + "," + problemID + "," + grade)
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(request.getBody());
//            }
//        });
//
//    }
//
//    @Override
//    public Future<Set<Assignment>> getAllAssignments() {
//        return executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.GET_ALL_ASSIGNMENTS)
//                    .build();
//
//            Message response = tcpClient.sendAndReceive(request);
//
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//            if (response.getBody().length() == 0) {
//                return new HashSet<>();
//            }
//            List<String> assignments = Arrays.asList(response.getBody().split(";"));
//            return assignments.stream().map((p) -> {
//                String[] list = p.split(",");
//                Assignment assignment = new Assignment(Long.parseLong(list[1]), Long.parseLong(list[2]), Integer.parseInt(list[3]));
//                assignment.setId(Long.parseLong(list[0]));
//                return assignment;
//            }).collect(Collectors.toSet());
//        });
//    }
//
//    @Override
//    public Future<Set<Student>> getAllStudentsByGroup(int group) {
//        return executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.GET_BY_GROUP)
//                    .body("" + group)
//                    .build();
//
//            Message response = tcpClient.sendAndReceive(request);
//
//            List<String> students = Arrays.asList(response.getBody().split(";"));
//            return students.stream().map((s) -> {
//                String[] list = s.split(",");
//                Student student = new Student(list[1], list[2], Integer.parseInt(list[3]));
//                student.setId(Long.parseLong(list[0]));
//                return student;
//            }).collect(Collectors.toSet());
//        });
//    }
//
//    @Override
//    public Future<Set<Problem>> getAllProblemsByDifficulty(String difficulty) {
//        return executorService.submit(() -> {
//            Message request = Message
//                    .builder()
//                    .header(ServiceInterface.GET_BY_DIFFICULTY)
//                    .body("" + difficulty)
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            List<String> problems = Arrays.asList(response.getBody().split(";"));
//            return problems.stream().map((p) -> {
//                String[] list = p.split(",");
//                Problem problem = new Problem(list[1], list[2], list[3]);
//                problem.setId(Long.parseLong(list[0]));
//                return problem;
//            }).collect(Collectors.toSet());
//        });
//    }
//
//    @Override
//    public Future<Set<Assignment>> getUngradedAssignments() {
//        return executorService.submit(() -> {
//            Message request = Message
//                    .builder()
//                    .header(ServiceInterface.GET_UNGRADED)
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            List<String> assignments = Arrays.asList(response.getBody().split(";"));
//            return assignments.stream().map((a) -> {
//                String[] list = a.split(",");
//                Assignment assignment = new Assignment(Long.parseLong(list[1]), Long.parseLong(list[2]), Integer.parseInt(list[3]));
//                assignment.setId(Long.parseLong(list[0]));
//                return assignment;
//            }).collect(Collectors.toSet());
//        });
//    }
//
//    @Override
//    public void setPageSize(int size) {
//        executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.SET_SIZE)
//                    .body(String.valueOf(size))
//                    .build();
//
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR))
//                throw new ServerException(response.getBody());
//        });
//    }
//
//    @Override
//    public Future<Set<Student>> getNextStudents() {
//        return executorService.submit(() -> {
//            Message request = Message.builder()
//                    .header(ServiceInterface.GET_NEXT_STUDENTS)
//                    .build();
//
//            Message response = tcpClient.sendAndReceive(request);
//
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//
//            if (response.getBody().length() == 0) {
//                return new HashSet<>();
//            }
//            List<String> students = Arrays.asList(response.getBody().split(";"));
//            return students.stream().map((s) -> {
//                String[] list = s.split(",");
//                Student student = new Student(list[1], list[2], Integer.parseInt(list[3]));
//                student.setId(Long.parseLong(list[0]));
//                return student;
//            }).collect(Collectors.toSet());
//        });
//    }
//
//    @Override
//    public Future<Set<Problem>> getNextProblems() {
//        return executorService.submit(() -> {
//            Message request = Message
//                    .builder()
//                    .header(ServiceInterface.GET_NEXT_PROBLEMS)
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//
//            if (response.getBody().length() == 0) {
//                return new HashSet<>();
//            }
//            List<String> problems = Arrays.asList(response.getBody().split(";"));
//            return problems.stream().map((p) -> {
//                String[] list = p.split(",");
//                Problem problem = new Problem(list[1], list[2], list[3]);
//                problem.setId(Long.parseLong(list[0]));
//                return problem;
//            }).collect(Collectors.toSet());
//        });
//    }
//
//    @Override
//    public Future<Set<Assignment>> getNextAssignments() {
//        return executorService.submit(() -> {
//            Message request = Message
//                    .builder()
//                    .header(ServiceInterface.GET_NEXT_ASSIGNMENTS)
//                    .build();
//            Message response = tcpClient.sendAndReceive(request);
//            if (response.getHeader().equals(Message.ERROR)) {
//                throw new ServerException(response.getBody());
//            }
//
//            if (response.getBody().length() == 0) {
//                return new HashSet<>();
//            }
//            List<String> assignments = Arrays.asList(response.getBody().split(";"));
//            return assignments.stream().map((a) -> {
//                String[] list = a.split(",");
//                Assignment assignment = new Assignment(Long.parseLong(list[1]), Long.parseLong(list[2]), Integer.parseInt(list[3]));
//                assignment.setId(Long.parseLong(list[0]));
//                return assignment;
//            }).collect(Collectors.toSet());
//        });
//    }
//}
