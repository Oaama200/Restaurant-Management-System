public class NameAlreadyExistsException extends Exception {
        public NameAlreadyExistsException(String message){
            super(message);

        }public NameAlreadyExistsException(){
            super("Item Already Exists Exception");

    }
}
