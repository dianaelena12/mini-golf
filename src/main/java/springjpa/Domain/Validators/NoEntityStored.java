package springjpa.Domain.Validators;

public class NoEntityStored extends RuntimeException {
    public NoEntityStored(String msg){
        super(msg);
    }
}
