public class InvalidSelectionException extends Exception{
    public InvalidSelectionException(){
        super("INVALID INPUT");
    }
    public InvalidSelectionException(String message){
        super(message);
    }

}
