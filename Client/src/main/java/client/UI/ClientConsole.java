package client.UI;

import client.Service.ClientServiceClient;
import common.Domain.Assignment;
import common.Domain.Problem;
import common.Domain.Student;
import common.Domain.Validators.NoEntityStored;
import common.Domain.Validators.ValidatorException;
import common.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@ComponentScan
@Component
public class ClientConsole {
    @Autowired
    private ServiceInterface service;
    @Autowired
    private ExecutorService executorService;

    @Autowired
    public ClientConsole(ClientServiceClient service, ExecutorService executorService) {
        this.service = service;
        this.executorService = executorService;
    }

    public ClientConsole() {
    }

    private static Optional<Student> readStudent() {
        System.out.println("Read student");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID:");
            Long id = Long.valueOf(bufferedReader.readLine());
            System.out.println("Serial Number:");
            String sn = bufferedReader.readLine();
            System.out.println("Name:");
            String name = bufferedReader.readLine();
            System.out.println("Group:");
            int group = Integer.parseInt(bufferedReader.readLine());
            Student student = new Student(sn, name, group);
            student.setId(id);
            return Optional.of(student);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private static Optional<Problem> readProblem() {
        System.out.println("Read problem");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("ID:");
            Long id = Long.valueOf(bufferedReader.readLine());
            System.out.println("Subject:");
            String subject = bufferedReader.readLine();
            System.out.println("Difficulty:");
            String diff = bufferedReader.readLine();
            System.out.println("Text:");
            String text = bufferedReader.readLine();
            Problem problem = new Problem(subject, diff, text);
            problem.setId(id);
            return Optional.of(problem);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private void menuText() {
        System.out.println("0.Exit\n1.Add student\n2.Remove student\n" +
                "3.Update student\n4.Print all students\n5.Add problem" +
                "\n6.Remove problem\n7.Update problem\n8.Print all problems" +
                "\n9.Assign problem to student\n10.Assign grade\n11.Print all assignments" +
                "\n12.Print all students from a given group\n13.Print all problems with a " +
                "certain difficulty\n14.Print all ungraded assignments\n15.Print students " +
                "with pagination" + "\n16.Print problems with pagination" + "\n17.Print assignments with pagination");
    }

    public void runConsole() {
        while (true) {
            menuText();
            try {
                System.out.println("Choose:");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(bufferedReader.readLine());
                switch (option) {
                    case 0: {
                        System.exit(0);
                        break;
                    }
                    case 1: {
                        addStudent();
                        break;
                    }
                    case 2: {
                        removeStudent();
                        break;
                    }
                    case 3: {
                        updateStudent();
                        break;
                    }
                    case 4: {
                        printStudents();
                        break;
                    }
                    case 5: {
                        addProblem();
                        break;
                    }
                    case 6: {
                        removeProblem();
                        break;
                    }
                    case 7: {
                        updateProblem();
                        break;
                    }
                    case 8: {
                        printProblems();
                        break;
                    }
                    case 9: {
                        addAssignment();
                        break;
                    }
                    case 10: {
                        assignGrade();
                        break;
                    }
                    case 11: {
                        printAllAssignments();
                        break;
                    }
                    case 12: {
                        filterByGrade();
                        break;
                    }
                    case 13: {
                        printProblemsFilter();
                        break;
                    }
                    case 14: {
                        printAssignmentsFilter();
                        break;
                    }
                    case 15: {
                        studentsPagination();
                        break;
                    }
                    case 16: {
                        printProblemsWithPaging();
                        break;
                    }
                    case 17: {
                        printAssignmentsWithPaging();
                        break;
                    }
                }
            } catch (IOException | RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void addStudent() {
        try {
            Optional<Student> student = readStudent();
            student.orElseThrow(() -> new ValidatorException("Invalid input!"));
            executorService.submit(() -> {
                service.addStudent(student.get());
                System.out.println("Student added successfully!");
            });
        } catch (ValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void removeStudent() {
        System.out.println("Enter student ID:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferedReader.readLine());
            executorService.submit(() -> {
                service.removeStudent(id);
                System.out.println("Student removed successfully!");
            });
        } catch (IOException | NoEntityStored ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateStudent() {
        try {
            Optional<Student> student = readStudent();
            student.orElseThrow(() -> new ValidatorException("Invalid input!"));
            executorService.submit(() -> {
                service.updateStudent(student.get());
                System.out.println("Student updated successfully!");
            });
        } catch (ValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printStudents() {
        executorService.submit(() -> {
            Set<Student> students = service.getAllStudents();
            students.forEach(System.out::println);
        });

    }

    private void filterByGrade() {
        System.out.println("Enter group:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            int group = Integer.parseInt(bufferedReader.readLine());
            executorService.submit(() ->
            {
                Set<Student> students = service.getAllStudentsByGroup(group);
                students.forEach(System.out::println);
            });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void studentsPagination() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter page size: ");
        int size = scanner.nextInt();

        service.setPageSize(size);

        System.out.println("Enter 'n' - for next; 'x' - for exit: ");

        while (true) {
            String cmd = scanner.next();
            if (cmd.equals("x")) {
                System.out.println("exit");
                break;
            }
            if (!cmd.equals("n")) {
                System.out.println("Invalid Option");
                continue;
            }

            Set<Student> students = service.getNextStudents();
            if (students.size() == 0) {
                System.out.println("No more students!");
                break;
            }
            students.forEach(System.out::println);
        }
    }

    private void addProblem() {
//        try {
//            Optional<Problem> problem = readProblem();
//            problem.orElseThrow(() -> new ValidatorException("Invalid input!"));
//            service.addProblem(problem.get());
//        } catch (ValidatorException ex) {
//            System.out.println(ex.getMessage());
//        }
    }

    private void removeProblem() {
//        System.out.println("Enter problem ID:");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            Long id = Long.valueOf(bufferedReader.readLine());
//            service.removeProblem(id);
//        } catch (IOException | NoEntityStored ex) {
//            System.out.println(ex.getMessage());
//        }
    }

    private void printProblems() {
//        try {
//            Future<Set<Problem>> problems = service.getAllProblems();
//            problems.get().forEach(System.out::println);
//        } catch (InterruptedException | ExecutionException ex) {
//            ex.printStackTrace();
//        }
    }


    private void printAssignmentsWithPaging() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("enter page size: ");
//        int size = scanner.nextInt();
//        service.setPageSize(size);
//
//        System.out.println("enter 'n' - for next; 'x' - for exit: ");
//
//        try {
//            while (true) {
//                String cmd = scanner.next();
//                if (cmd.equals("x")) {
//                    System.out.println("exit");
//                    break;
//                }
//                if (!cmd.equals("n")) {
//                    System.out.println("this option is not yet implemented");
//                    continue;
//                }
//
//                Future<Set<Assignment>> assignments = service.getNextAssignments();
//                if (assignments.get().size() == 0) {
//                    System.out.println("no more assignments");
//                    break;
//                }
//                assignments.get().forEach(System.out::println);
//
//            }
//        } catch (InterruptedException | ExecutionException ex) {
//            ex.printStackTrace();
//        }
    }

    private void printProblemsWithPaging() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("enter page size: ");
//        int size = scanner.nextInt();
//        service.setPageSize(size);
//
//        System.out.println("enter 'n' - for next; 'x' - for exit: ");
//
//        try {
//            while (true) {
//                String cmd = scanner.next();
//                if (cmd.equals("x")) {
//                    System.out.println("exit");
//                    break;
//                }
//                if (!cmd.equals("n")) {
//                    System.out.println("this option is not yet implemented");
//                    continue;
//                }
//
//                Future<Set<Problem>> problems = service.getNextProblems();
//                if (problems.get().size() == 0) {
//                    System.out.println("no more problems");
//                    break;
//                }
//                problems.get().forEach(problem -> System.out.println(problem));
//            }
//        } catch (InterruptedException | ExecutionException ex) {
//            ex.printStackTrace();
//        }
    }

    private void printProblemsFilter() {
//        System.out.println("Enter difficulty:");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            Future<Set<Problem>> problems = service.getAllProblemsByDifficulty(bufferedReader.readLine());
//            problems.get().forEach(System.out::println);
//        } catch (IOException | InterruptedException | ExecutionException ex) {
//            System.out.println(ex.getMessage());
//        }
    }

    private void printAssignmentsFilter() {
//        Future<Set<Assignment>> assignments = service.getUngradedAssignments();
//        try {
//            if (assignments.get().isEmpty()) {
//                System.out.println("There are no ungraded assignments left");
//                return;
//            }
//            assignments.get().forEach(System.out::println);
//        } catch (InterruptedException | ExecutionException ex) {
//            ex.printStackTrace();
//        }
    }

    private void addAssignment() {
//        try {
//            Optional<Assignment> assignment = readAssignment();
//            assignment.orElseThrow(() -> new ValidatorException("Invalid input!"));
//            service.addAssignment(assignment.get());
//        } catch (ValidatorException ex) {
//            System.out.println(ex.getMessage());
//        }
    }

    private void updateProblem() {
//        try {
//            Optional<Problem> problem = readProblem();
//            problem.orElseThrow(() -> new ValidatorException("Invalid input!"));
//            service.updateProblem(problem.get());
//        } catch (ValidatorException ex) {
//            System.out.println(ex.getMessage());
//        }
    }

    private void assignGrade() {
//        Scanner in = new Scanner(System.in);
//
//        try {
//            System.out.println("Student ID: ");
//            Long studentID = in.nextLong();
//            in.nextLine();
//
//            System.out.println("Problem ID: ");
//            Long problemID = in.nextLong();
//            in.nextLine();
//
//            System.out.println("Grade: ");
//            int grade = in.nextInt();
//
//            service.assignGrade(studentID, problemID, grade);
//            System.out.println("Grade assigned!");
//        } catch (ValidatorException ex) {
//            ex.printStackTrace();
//        } catch (NoEntityStored ex) {
//            System.out.println(ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());
//        } catch (InputMismatchException ex) {
//            System.out.println("Invalid data!");
//        }
    }

    private Optional<Assignment> readAssignment() {
//        System.out.println("Read assignment");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        try {
//            System.out.println("ID:");
//            Long id = Long.valueOf(bufferedReader.readLine());
//            System.out.println("StudentID:");
//            String sid = bufferedReader.readLine();
//            System.out.println("ProblemID:");
//            String pid = bufferedReader.readLine();
//            Assignment assignment = new Assignment(Long.parseLong(sid), Long.parseLong(pid));
//            assignment.setId(id);
//            return Optional.of(assignment);
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
        return Optional.empty();

    }

    private void printAllAssignments() {
//        Future<Set<Assignment>> assignments = service.getAllAssignments();
//        try {
//            if (assignments.get().isEmpty()) {
//                System.out.println("There are no assignments!");
//                return;
//            }
//            assignments.get().forEach(System.out::println);
//        } catch (InterruptedException | ExecutionException ex) {
//            ex.printStackTrace();
//        }
    }
}
