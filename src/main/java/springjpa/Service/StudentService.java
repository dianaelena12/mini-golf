package springjpa.Service;

import springjpa.Domain.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    void saveStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Long id);
}
