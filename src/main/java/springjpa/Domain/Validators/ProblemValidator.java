package springjpa.Domain.Validators;

import springjpa.Domain.Problem;

public class ProblemValidator implements Validator<Problem> {

    @Override
    public void validate(Problem entity) throws ValidatorException {
        if(entity.getId() < 0 || entity == null)
            throw new ValidatorException("Invalid ID!");

    }
}
