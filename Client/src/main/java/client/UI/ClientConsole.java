package client.UI;

import common.Domain.Problem;
import common.Domain.Student;
import common.Domain.Validators.NoEntityStored;
import common.Domain.Validators.ValidatorException;
import common.ServiceInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ClientConsole {
    private ServiceInterface service;

    public ClientConsole(ServiceInterface service) {
        this.service = service;
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
        while(true) {
            menuText();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                int option = Integer.parseInt(bufferedReader.readLine());
                switch (option){
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
                        break;
                    }
                    case 8: {
                        printProblems();
                        break;
                    }
                    case 9: {
                        break;
                    }
                    case 10: {
                        break;
                    }
                    case 11: {
                        break;
                    }
                    case 12: {
                        filterByGrade();
                        break;
                    }
                    case 13: {
                        break;
                    }
                    case 14: {
                        break;
                    }
                    case 15: {
                        studentsPagination();
                        break;
                    }
                    case 16: {
                        break;
                    }
                    case 17: {

                    }
                }
            }catch (IOException | RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
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

    private void addStudent(){
        try{
            Optional<Student> student = readStudent();
            student.orElseThrow(() -> new ValidatorException("Invalid input!"));
            service.addStudent(student.get());
        }catch (ValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void removeStudent(){
        System.out.println("Enter student ID:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            Long id = Long.valueOf(bufferedReader.readLine());
            service.removeStudent(id);
        }catch (IOException | NoEntityStored ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void updateStudent(){
        try{
            Optional<Student> student = readStudent();
            student.orElseThrow(() -> new ValidatorException("Invalid input!"));
            service.updateStudent(student.get());
        }catch (ValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printStudents(){
        try{
            Future<Set<Student>> students = service.getAllStudents();
            students.get().forEach(System.out::println);
        }catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    private void addProblem(){
        try{
            Optional<Problem> problem = readProblem();
            problem.orElseThrow(() -> new ValidatorException("Invalid input!"));
            service.addProblem(problem.get());
        }catch (ValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void removeProblem(){
        System.out.println("Enter problem ID:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            Long id = Long.valueOf(bufferedReader.readLine());
            service.removeProblem(id);
        }catch (IOException | NoEntityStored ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void printProblems(){
        try{
            Future<Set<Problem>> problems = service.getAllProblems();
            problems.get().forEach(System.out::println);
        }catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }

    private void filterByGrade(){
        System.out.println("Enter group:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try{
            Future<Set<Student>> students = service.getAllStudentsByGroup(Integer.parseInt(bufferedReader.readLine()));
            students.get().forEach(System.out::println);
        }catch (IOException | InterruptedException | ExecutionException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void studentsPagination(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter page size: ");
        int size = scanner.nextInt();

        System.out.println(size);

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

            Future<Set<Student>> students = service.getNextStudents();
            try {
                if (students.get().size() == 0) {
                    System.out.println("No more students!");
                    break;
                }
                students.get().forEach(System.out::println);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
