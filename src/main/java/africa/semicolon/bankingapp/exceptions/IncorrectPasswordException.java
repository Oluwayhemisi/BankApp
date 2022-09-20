package africa.semicolon.bankingapp.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException(String message){
        super(message);
    }
}
