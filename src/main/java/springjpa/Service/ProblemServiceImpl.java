package springjpa.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springjpa.Domain.Problem;
import springjpa.Repo.JPARepos.ProblemRepoJPA;

import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {
    private static final Logger log = LoggerFactory.getLogger(
            ProblemServiceImpl.class);

    @Autowired
    private ProblemRepoJPA problemRepository;

    @Override
    public List<Problem> getAllProblems() {
        log.trace("getAllProblems --- method entered");

        List<Problem> result = problemRepository.findAll();

        log.trace("getAllProblems: result={}", result);

        return result;
    }

    @Override
    public void saveProblem(Problem problem) {
        log.trace("saveProblem: problem={}", problem);

        problemRepository.save(problem);

        log.trace("saveProblem --- method finished");
    }

    @Override
    @Transactional
    public void updateProblem(Problem problem) {
        log.trace("update: problem={}", problem);

        problemRepository.findById(problem.getId())
                .ifPresent(problem1 -> {
                    problem1.setSubject(problem.getSubject());
                    problem1.setDifficulty(problem.getDifficulty());
                    problem1.setText(problem.getText());
                    log.debug("update --- problem updated? --- " +
                            "student={}", problem1);
                });

        log.trace("update --- method finished");
    }

    @Override
    public void deleteProblem(Long id) {
        log.trace("delete: id={}", id);

        problemRepository.deleteById(id);

        log.trace("delete --- method finished");

    }
}
