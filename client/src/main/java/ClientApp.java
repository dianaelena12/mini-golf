package java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import web.StudentDto;
import web.StudentsDto;

public class ClientApp {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "java.config"
                );

        RestTemplate restTemplate = context.getBean(RestTemplate.class);


        StudentDto newStudent = StudentDto.builder()
                .name("sNEW")
                .grade(9)
                .build();
        StudentDto savedStudent = restTemplate.postForObject(
                "http://localhost:8080/api/students",
                newStudent,
                StudentDto.class
        );

        savedStudent.setGrade(10);
        restTemplate.put(
                "http://localhost:8080/api/students/{id}",
                savedStudent,
                savedStudent.getId()
        );

        restTemplate.delete(
                "http://localhost:8080/api/students/{id}",
                savedStudent.getId()
        );

        StudentsDto studentsDto = restTemplate.getForObject(
                "http://localhost:8080/api/students",
                StudentsDto.class
        );
        System.out.println(studentsDto);

        System.out.println("bye ");
    }
}
