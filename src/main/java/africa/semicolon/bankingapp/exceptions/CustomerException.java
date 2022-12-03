package africa.semicolon.bankingapp.exceptions;

public class CustomerException extends Exception{
    private int statusCode;
    public CustomerException(String message,int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode(){
        return statusCode;
    }
}
