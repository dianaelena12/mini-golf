import Domain.Assignment;
import Domain.Problem;
import Domain.Student;
import Domain.Validators.AssignmentValidator;
import Domain.Validators.ProblemValidator;
import Domain.Validators.StudentValidator;
import Domain.Validators.Validator;
import Repo.DBRepos.AssignmentDBRepo;
import Repo.DBRepos.ProblemDBRepo;
import Repo.DBRepos.StudentDBRepo;
import Repo.Paging.PagingRepository;
import Service.Service;
import UI.Console;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            System.out.println(new File(".").getCanonicalPath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Problem> problemValidator = new ProblemValidator();
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        PagingRepository<Long, Student> studRepo = new StudentDBRepo(studentValidator);
        PagingRepository<Long, Problem> problemRepo = new ProblemDBRepo(problemValidator);
        PagingRepository<Long, Assignment> assignmentRepo = new AssignmentDBRepo(assignmentValidator);
//        RepositoryInterface<Long, Student> studRepo = new StudentXMLRepo(studentValidator);
        //      RepositoryInterface<Long,Problem> problemRepo = new ProblemXMLRepo(problemValidator);
        //RepositoryInterface<Long,Assignment> assignmentRepo = new AssignmentXMLRepo(assignmentValidator);
//        RepositoryInterface<Long, Student> studRepo = new StudentFileRepo(studentValidator,
//                "C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Students");
//        RepositoryInterface<Long, Problem> problemRepo = new ProblemFileRepo(problemValidator,
//                "C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Problems");
//        RepositoryInterface<Long, Assignment> assignmentRepo = new AssignmentFileRepo(assignmentValidator,
//                "C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\Data\\Assignments");
        Service studService = new Service(studRepo, problemRepo, assignmentRepo);
        Console console = new Console(studService);
        console.runConsole();
    }
}
