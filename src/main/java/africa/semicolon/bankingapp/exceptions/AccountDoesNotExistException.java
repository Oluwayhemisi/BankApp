package africa.semicolon.bankingapp.exceptions;

public class AccountDoesNotExistException extends RuntimeException{

    public AccountDoesNotExistException(String message){
        super(message);
    }
}
