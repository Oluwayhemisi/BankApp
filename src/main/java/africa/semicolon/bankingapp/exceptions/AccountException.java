package africa.semicolon.bankingapp.exceptions;

public class AccountException extends Exception{
    private int statusCode;
    public AccountException(String message,int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode(){
    return statusCode;
    }
}
