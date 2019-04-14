package springjpa.Domain.Validators;

import springjpa.Domain.Student;

public class StudentValidator implements Validator<Student> {

    @Override
    public void validate(Student entity) throws ValidatorException {
        if(entity.getId() < 0 || entity == null)
            throw new ValidatorException("Invalid ID!");
    }
}
