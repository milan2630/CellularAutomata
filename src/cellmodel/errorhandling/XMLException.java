package cellmodel.errorhandling;

import java.util.ResourceBundle;

/**
 * Used to throw an Exception when parsing the XML
 */
public class XMLException extends RuntimeException {
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String XML_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "XMLTagNames";
    private static final ResourceBundle xmlResources= ResourceBundle.getBundle(XML_PROPERTIES_FILENAME);

    /**
     * Throws exception based on the properties file
     * @param property the key in the properties file leading to an error message
     */
    public XMLException(String property){
        super("COULD NOT CONFIGURE SIMULATION WITH GIVEN XML FILE: " + xmlResources.getString(property));
    }

    /**
     * Often called with 2 strings, so created a special constructor to print two things
     * @param property1 first string of message
     * @param property2 second string of message
     */
    public XMLException(String property1, String property2) {
        super("COULD NOT CONFIGURE SIMULATION WITH GIVEN XML FILE: " + xmlResources.getString(property1) + xmlResources.getString(property2));

    }

}
