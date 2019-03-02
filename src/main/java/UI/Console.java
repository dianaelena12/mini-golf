package UI;

import Domain.Student;
import Domain.Validators.ValidatorException;
import Service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Set;

public class Console {
    private StudentService studService;

    public Console(StudentService studService) {
        this.studService = studService;
    }

    public void runConsole(){
        addStudent();
        printAllStudents();
    }

    private void printAllStudents(){
        Set<Student> students = studService.getAllStudents();
        students.stream().forEach(System.out::println);
    }


    private void addStudent(){
        while(true) {
            Student student = readStudent();
            if (student == null || student.getId() < 0)
                break;

            try {
                studService.addStudent(student);
            } catch (ValidatorException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Student readStudent(){
        System.out.println("Read student");

        Scanner in = new Scanner(System.in);
        try {
            System.out.println("ID: ");
            Long id = in.nextLong();

            System.out.println("Serial number: ");
            String sn = in.next();

            System.out.println("Name: ");
            String name = in.next();

            System.out.println("Group: ");
            int group = in.nextInt();

            Student student = new Student(sn, name, group);
            student.setId(id);

            return student;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
