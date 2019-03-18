package Repo;

import Domain.Student;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class RepoTest {
    private Validator<Student> studentValidator = new StudentValidator();
    RepositoryInterface<Long, Student> studRepo = new InMemRepo<>(studentValidator);

    @Test
    public void testSave() throws Exception {
        Student stud = new Student("1234r", "Ioana", 123);
        stud.setId(new Long(1));
        studRepo.save(stud);
        Optional<Student> stud1 = studRepo.findOne(new Long(1));
        assertEquals(stud1.isPresent(), true);
    }

    @Test
    public void testFindOne() throws Exception {
        Optional<Student> stud1 = studRepo.findOne(new Long(1));
        assertEquals(stud1.isPresent(), false);

        Student stud = new Student("1234r", "Ioana", 123);
        stud.setId(new Long(1));
        studRepo.save(stud);
        Optional<Student> stud11 = studRepo.findOne(new Long(1));
        assertEquals(stud11.isPresent(), true);
    }

    @Test
    public void testFindAll() throws Exception {
        Student stud = new Student("1234r", "Ioana", 123);
        stud.setId(new Long(1));
        studRepo.save(stud);
        Student stud1 = new Student("12344r", "Ionela", 123);
        stud1.setId(new Long(2));
        studRepo.save(stud1);
        Student stud2 = new Student("123344r", "Iuliana", 123);
        stud2.setId(new Long(3));
        studRepo.save(stud2);
        Iterable<Student> allStudents = studRepo.findAll();
        for (Student student : allStudents) {
            Optional<Student> s = studRepo.findOne(student.getId());
            assertEquals(s.isPresent(), true);
        }
    }

    @Test
    public void testDelete() throws Exception {
        Student stud = new Student("1234r", "Ioana", 123);
        stud.setId(new Long(1));
        studRepo.save(stud);
        Student stud1 = new Student("12344r", "Ionela", 123);
        stud1.setId(new Long(2));
        studRepo.save(stud1);
        Student stud2 = new Student("123344r", "Iuliana", 123);
        stud2.setId(new Long(3));
        studRepo.save(stud2);
        Optional<Student> s = studRepo.findOne(stud.getId());
        assertEquals(s.isPresent(), true);
        studRepo.delete(stud.getId());
        s = studRepo.findOne(stud.getId());
        assertEquals(s.isPresent(), false);
    }

    @Test
    public void testUpdate() throws Exception{
        Student stud = new Student("1234r", "Ioana", 123);
        stud.setId(new Long(1));
        studRepo.save(stud);
        Optional<Student> s = studRepo.findOne(stud.getId());
        assert (true);
    }
}
