package web.converter;

import core.model.Problem;
import org.springframework.stereotype.Component;
import web.dto.ProblemDto;

@Component
public class ProblemConverter extends BaseConverter<Problem, ProblemDto> {
    @Override
    public ProblemDto convertModelsToDtos(Problem problem) {
        ProblemDto problemDto = new ProblemDto(
                problem.getSerialNumber(),
                problem.getName(),
                problem.getGr()
        );
        problemDto.setId(problem.getId());
        return problemDto;
    }

    public Problem convertDtoToModel(ProblemDto dto) {
        Problem problem = new ProblemConverter(
                dto.getSerialNumber(),
                dto.getName(),
                dto.getGroup()
        );
        problem.setId(dto.getId());
        return problem;
    }
}
