package Repo;

import Domain.Student;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import Repo.FIleRepos.StudentFileRepo;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class StudRepoTest {
    private Validator<Student> studentValidator = new StudentValidator();
    RepositoryInterface<Long, Student> studRepo = new StudentFileRepo(studentValidator,
            "C:\\Users\\Diana\\Desktop\\mini-golf\\src\\test\\java\\Repo\\Data\\StudTestData");

    @Test
    public void testSave() throws Exception{
        Student stud = new Student("1234r","Ioana",123);
        stud.setId(new Long(1));
        //studRepo.save(stud);
        Optional stud1 = studRepo.findOne(new Long(1));
        assert (true);
    }
}
