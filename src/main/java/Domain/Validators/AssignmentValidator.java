package Domain.Validators;

import Domain.Assignment;

public class AssignmentValidator implements Validator<Assignment>{

    @Override
    public void validate(Assignment entity) throws ValidatorException {
        if(entity.getId() < 0 || entity == null)
            throw new ValidatorException("Invalid ID!");
    }
}
