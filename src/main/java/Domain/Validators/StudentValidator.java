package Domain.Validators;

import Domain.Student;

public class StudentValidator implements Validator<Student> {

    @Override
    public void validate(Student entity) throws ValidatorException {
        if(entity.getId() < 0)
            throw new ValidatorException("Invalid ID!");
    }
}
