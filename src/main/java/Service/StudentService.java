package Service;

import Domain.Student;
import Domain.Validators.ValidatorException;
import Repo.RepositoryInterface;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentService {
    private RepositoryInterface<Long, Student> repo;

    public StudentService(RepositoryInterface<Long, Student> repo) {
        this.repo = repo;
    }

    public void addStudent(Student student) throws ValidatorException, sun.security.validator.ValidatorException {
        repo.save(student);
    }

    public Set<Student> getAllStudents() {
        Iterable<Student> students = repo.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }
}
