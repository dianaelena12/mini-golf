import Domain.Student;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import Repo.InMemRepo;
import Repo.RepositoryInterface;
import Service.StudentService;
import UI.Console;

public class Main {
    public static void main(String[] args) {
        Validator<Student> studentValidator = new StudentValidator();
        RepositoryInterface<Long, Student> studRepo = new InMemRepo(studentValidator);
        StudentService studService = new StudentService(studRepo);
        Console console = new Console(studService);
        console.runConsole();
    }
}
