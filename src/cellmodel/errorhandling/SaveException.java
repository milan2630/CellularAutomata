package cellmodel.errorhandling;

/**
 * Used to throw an error while saving the current state of the board
 */
public class SaveException extends RuntimeException {
    public SaveException(String errorMessage){
        super("COULD NOT SAVE CURRENT STATE: " + errorMessage);
    }
}
