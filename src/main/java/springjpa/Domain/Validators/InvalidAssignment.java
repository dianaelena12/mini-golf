package springjpa.Domain.Validators;

public class InvalidAssignment extends RuntimeException {
    public InvalidAssignment(String msg){
        super(msg);
    }
}
