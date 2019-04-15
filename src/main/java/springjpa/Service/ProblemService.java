package springjpa.Service;

import springjpa.Domain.Problem;

import java.util.List;

public interface ProblemService {
    List<Problem> getAllProblems();

    void saveProblem(Problem problem);

    void updateProblem(Problem problem);

    void deleteProblem(Long id);
}
