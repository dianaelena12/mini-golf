package Service;

import Domain.Problem;
import Domain.Student;
import Domain.Validators.ValidatorException;
import Repo.RepositoryInterface;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Service {
    private RepositoryInterface<Long, Student> studentRepo;
    private RepositoryInterface<Long, Problem> problemRepo;

    public Service(RepositoryInterface<Long, Student> studentRepo, RepositoryInterface<Long, Problem> problemRepo) {
        this.studentRepo = studentRepo;
        this.problemRepo = problemRepo;
    }

    public void addStudent(Student student) throws ValidatorException{
        studentRepo.save(student);
    }

    public Set<Student> getAllStudents() {
        Iterable<Student> students = studentRepo.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    public void removeStudent(Long id){
        studentRepo.delete(id);
    }

    public void updateStudent(Student student){
        studentRepo.update(student);
    }

    public void addProblem(Problem problem) throws ValidatorException{
        problemRepo.save(problem);
    }

    public Set<Problem> getAllProblems(){
        Iterable<Problem> problems = problemRepo.findAll();
        return StreamSupport.stream(problems.spliterator(), false).collect(Collectors.toSet());
    }

    public void removeProblem(Long id){
        problemRepo.delete(id);
    }

    public void updateProblem(Problem problem){
        problemRepo.update(problem);
    }
}
