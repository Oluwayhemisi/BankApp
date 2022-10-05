package africa.semicolon.bankingapp.exceptions;

public class CustomerDoesNotExistException extends RuntimeException{
    public CustomerDoesNotExistException(String message){
        super(message);
    }
}
