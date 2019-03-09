package Domain.Validators;

public class NoStudentStored extends RuntimeException {
    public NoStudentStored(String msg){
        super(msg);
    }
}
