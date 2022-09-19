package africa.semicolon.bankingapp.exceptions;

public class InsufficientDepositException extends Exception{
    public InsufficientDepositException(String message) {
        super(message);
    }
}
