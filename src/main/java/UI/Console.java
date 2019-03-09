package UI;

import Domain.Student;
import Domain.Validators.DuplicateException;
import Domain.Validators.NoStudentStored;
import Domain.Validators.ValidatorException;
import Service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Console {
    private StudentService studService;

    public Console(StudentService studService) {
        this.studService = studService;
    }

    public void menuText(){
        System.out.println("0.Exit\n1.Add student\n2.Remove student\n" +
                "3.Update student\n4.Print all students");
    }

    public void runConsole(){
        while(true){
            menuText();
            System.out.println("Choose:");
            Scanner in = new Scanner(System.in);
            int option = in.nextInt();
            if(option < 0 || option > 4)
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
            }
            if(option == 0){
                break;
            }
        }
    }

    private void printAllStudents(){
        Set<Student> students = studService.getAllStudents();
        if(students.isEmpty()){
            System.out.println("There are no students!");
            return;
        }
        students.stream().forEach(System.out::println);
    }


    private void addStudent(){
            Student student = readStudent();
            try {
                studService.addStudent(student);
                System.out.println("Student added successfully!");
            } catch (ValidatorException ex) {
                ex.printStackTrace();
            } catch (DuplicateException ex){
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
            studService.removeStudent(id);
            System.out.println("Student removed successfully!");
        } catch (InputMismatchException ex) {
                System.out.println("Invalid id!");
        }catch (NoStudentStored ex){
                System.out.println(ex.getMessage());
        }
    }

    private void updateStudent(){
        Student student = readStudent();
        try {
            studService.updateStudent(student);
            System.out.println("Student updated successfully!");
        } catch (ValidatorException ex) {
            ex.printStackTrace();
        } catch (NoStudentStored ex){
            System.out.println(ex.getMessage());
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

}
