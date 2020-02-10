package cellmodel;

public class SaveException extends RuntimeException {
    public SaveException(String errorMessage){
        super("COULD NOT SAVE CURRENT STATE: " + errorMessage);
    }
}
