package springjpa;

import springjpa.UI.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(
                        "springjpa/Config"
                );

        Console console=context.getBean(Console.class);
        console.runConsole();
//        try {
//            System.out.println(new File(".").getCanonicalPath());
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//
//        Validator<Student> studentValidator = new StudentValidator();
//        Validator<Problem> problemValidator = new ProblemValidator();
//        Validator<Assignment> assignmentValidator = new AssignmentValidator();
//        PagingRepository<Long, Student> studRepo = new StudentDBRepo(studentValidator);
//        PagingRepository<Long, Problem> problemRepo = new ProblemDBRepo(problemValidator);
//        PagingRepository<Long, Assignment> assignmentRepo = new AssignmentDBRepo(assignmentValidator);
//        RepositoryInterface<Long, Student> studRepo = new StudentXMLRepo(studentValidator);
        //      RepositoryInterface<Long,Problem> problemRepo = new ProblemXMLRepo(problemValidator);
        //RepositoryInterface<Long,Assignment> assignmentRepo = new AssignmentXMLRepo(assignmentValidator);
//        RepositoryInterface<Long, Student> studRepo = new StudentFileRepo(studentValidator,
//                "C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Students");
//        RepositoryInterface<Long, Problem> problemRepo = new ProblemFileRepo(problemValidator,
//                "C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Problems");
//        RepositoryInterface<Long, Assignment> assignmentRepo = new AssignmentFileRepo(assignmentValidator,
//                "C:\\Users\\Diana\\Desktop\\mini-golf\\src\\main\\java\\springjpa.Data\\Assignments");
//        springjpa.Service studService = new springjpa.Service(studRepo, problemRepo, assignmentRepo);
//        Console console = new Console(studService);
//        console.runConsole();
    }
}
