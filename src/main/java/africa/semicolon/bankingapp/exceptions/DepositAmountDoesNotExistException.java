package africa.semicolon.bankingapp.exceptions;

public class DepositAmountDoesNotExistException extends RuntimeException{
    private int statusCode;
    public DepositAmountDoesNotExistException(String message, int statusCode) {

        super(message);
        this.statusCode = statusCode;
    }
}
