import Domain.Student;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import Repo.InMemRepo;
import Repo.RepositoryInterface;
import Repo.StudentFileRepo;
import Service.StudentService;
import UI.Console;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try{
            System.out.println(new File(".").getCanonicalPath());
        }catch (IOException ex){
            ex.printStackTrace();
        }

        Validator<Student> studentValidator = new StudentValidator();
        RepositoryInterface<Long, Student> studRepo = new StudentFileRepo(studentValidator,
                "C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students");
        StudentService studService = new StudentService(studRepo);
        Console console = new Console(studService);
        console.runConsole();
    }
}
