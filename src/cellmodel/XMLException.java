package cellmodel;

public class XMLException extends RuntimeException {

    public XMLException(String errorMessage){
        super("COULD NOT CONFIGURE SIMULATION WITH GIVEN XML FILE: " + errorMessage);
    }




}
