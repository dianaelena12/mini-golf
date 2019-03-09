package UI;

import Domain.Assignment;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.DuplicateException;
import Domain.Validators.NoEntityStored;
import Domain.Validators.ValidatorException;
import Service.Service;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Console {
    private Service service;

    public Console(Service service) {
        this.service = service;
    }

    public void menuText(){
        System.out.println("0.Exit\n1.Add student\n2.Remove student\n" +
                "3.Update student\n4.Print all students\n5.Add problem" +
                "\n6.Remove problem\n7.Update problem\n8.Print all problems" +
                "\n9.Assign problem to student\n10.Assign grade\n11.Print all assignments");
    }

    public void runConsole(){
        while(true){
            menuText();
            System.out.println("Choose:");
            try{
                Scanner in = new Scanner(System.in);
                int option = in.nextInt();
                if(option < 0 || option > 11)
                    System.out.println("Invalid number!");
                switch (option){
                    case 1:{
                        addStudent();
                        break;
                    }
                    case 2:{
                        removeStudent();
                        break;
                    }
                    case 3: {
                        updateStudent();
                        break;
                    }
                    case 4: {
                        printAllStudents();
                        break;
                    }
                    case 5:{
                        addProblem();
                        break;
                    }
                    case 6:{
                        removeProblem();
                        break;
                    }
                    case 7:{
                        updateProblem();
                        break;
                    }
                    case 8:{
                        printAllProblems();
                        break;
                    }
                    case 9:{
                        addAssignment();
                        break;
                    }
                    case 10:{
                        assignGrade();
                        break;
                    }
                    case 11:{
                        printAllAssignments();
                        break;
                    }
                }
                if(option == 0){
                    break;
                }
            } catch (InputMismatchException ex){
                System.out.println("Invalid option!");
            }
        }
    }

    private void printAllStudents(){
        Set<Student> students = service.getAllStudents();
        if(students.isEmpty()){
            System.out.println("There are no students!");
            return;
        }
        students.stream().forEach(System.out::println);
    }

    private void printAllProblems(){
        Set<Problem> problems = service.getAllProblems();
        if(problems.isEmpty()){
            System.out.println("There are no problems!");
            return;
        }
        problems.stream().forEach(System.out::println);
    }

    private void printAllAssignments(){
        Set<Assignment> assignments = service.getAllAssignments();
        if(assignments.isEmpty()){
            System.out.println("There are no assignments!");
            return;
        }
        assignments.stream().forEach(System.out::println);
    }

    private void addStudent(){
        Student student = readStudent();
        try {
            service.addStudent(student);
            System.out.println("Student added successfully!");
        } catch (ValidatorException ex) {
            ex.printStackTrace();
        } catch (DuplicateException ex){
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void addProblem(){
        Problem problem = readProblem();
        try{
            service.addProblem(problem);
            System.out.println("Problem added successfully!");
        }catch (ValidatorException ex){
            ex.printStackTrace();
        } catch (DuplicateException ex){
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void addAssignment(){
        Assignment assignment = readAssignment();
        try{
            service.addAssignment(assignment);
            System.out.println("Assignment added successfully!");
        }catch (ValidatorException ex){
            ex.printStackTrace();
        } catch (DuplicateException ex){
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void removeStudent(){
        System.out.println("ID: ");
        Scanner in = new Scanner(System.in);
        try {
            Long id = in.nextLong();
            if(id < 0){
                System.out.println("Invalid id!");
            }
            service.removeStudent(id);
            System.out.println("Student removed successfully!");
        } catch (InputMismatchException ex) {
                System.out.println("Invalid id!");
        }catch (NoEntityStored ex){
                System.out.println(ex.getMessage());
        }
    }

    private void removeProblem(){
        System.out.println("ID: ");
        Scanner in = new Scanner(System.in);
        try{
            Long id = in.nextLong();
            if(id < 0){
                System.out.println("Invalid id!");
            }
            service.removeProblem(id);
            System.out.println("Problem removed successfully!");
        } catch (InputMismatchException ex) {
            System.out.println("Invalid id!");
        } catch (NoEntityStored ex){
            System.out.println(ex.getMessage());
        }
    }

    private void updateStudent(){
        Student student = readStudent();
        try {
            service.updateStudent(student);
            System.out.println("Student updated successfully!");
        } catch (ValidatorException ex) {
            ex.printStackTrace();
        }catch (NoEntityStored ex){
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void updateProblem(){
        Problem problem = readProblem();
        try{
            service.updateProblem(problem);
            System.out.println("Problem updated successfully!");
        }catch (ValidatorException ex){
            ex.printStackTrace();
        } catch (NoEntityStored ex){
            System.out.println(ex.getMessage());
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void assignGrade(){
        Scanner in = new Scanner(System.in);

        try{
            System.out.println("Student ID: ");
            Long studentID = in.nextLong();
            in.nextLine();

            System.out.println("Problem ID: ");
            Long problemID = in.nextLong();
            in.nextLine();

            System.out.println("Grade: ");
            int grade = in.nextInt();

            service.assignGrade(studentID, problemID, grade);
            System.out.println("Grade assigned!");
        }catch (ValidatorException ex){
            ex.printStackTrace();
        } catch (NoEntityStored ex){
            System.out.println(ex.getMessage());
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        }catch (InputMismatchException ex){
            System.out.println("Invalid data!");
        }
    }

    private Student readStudent(){
        System.out.println("Read student");

        Scanner in = new Scanner(System.in);
        try {
            System.out.println("ID: ");
            Long id = in.nextLong();
            in.nextLine();

            System.out.println("Serial number: ");
            String sn = in.nextLine();

            System.out.println("Name: ");
            String name = in.nextLine();

            System.out.println("Group: ");
            int group = in.nextInt();

            Student student = new Student(sn, name, group);
            student.setId(id);

            return student;
        }catch (java.util.InputMismatchException ex){
            System.out.println("Invalid data!");
        }
        return null;
    }

    private Problem readProblem(){
        System.out.println("Read problem");

        Scanner in = new Scanner(System.in);
        try{
            System.out.println("ID: ");
            Long id = in.nextLong();
            in.nextLine();

            System.out.println("Subject: ");
            String subject = in.nextLine();

            System.out.println("Difficulty: ");
            String difficulty = in.nextLine();

            System.out.println("Text: ");
            String text = in.nextLine();

            Problem problem = new Problem(subject, difficulty, text);
            problem.setId(id);

            return problem;
        }catch (InputMismatchException ex){
            System.out.println("Invalid data!");
        }
        return null;
    }

    private Assignment readAssignment(){
        System.out.println("Read assignment");

        Scanner in = new Scanner(System.in);
        try{
            System.out.println("ID: ");
            Long id = in.nextLong();
            in.nextLine();

            System.out.println("Student ID: ");
            Long studentID = in.nextLong();
            in.nextLine();

            System.out.println("Problem ID: ");
            Long problemID = in.nextLong();
            in.nextLine();

            Assignment assignment = new Assignment(studentID, problemID);
            assignment.setId(id);

            return assignment;
        }catch (InputMismatchException ex){
            System.out.println("Invalid data!");
        }
        return null;
    }

}
