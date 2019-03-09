import Domain.Problem;
import Domain.Student;
import Domain.Validators.ProblemValidator;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import Repo.ProblemFileRepo;
import Repo.RepositoryInterface;
import Repo.StudentFileRepo;
import Service.Service;
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
        Validator<Problem> problemValidator = new ProblemValidator();
        RepositoryInterface<Long, Student> studRepo = new StudentFileRepo(studentValidator,
                "C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students");
        RepositoryInterface<Long, Problem> problemRepo = new ProblemFileRepo(problemValidator,
                "C:\\Users\\Alex\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems");
        Service studService = new Service(studRepo, problemRepo);
        Console console = new Console(studService);
        console.runConsole();
    }
}
