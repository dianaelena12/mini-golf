package springjpa.Domain.Validators;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String msg){
        super(msg);
    }
}
