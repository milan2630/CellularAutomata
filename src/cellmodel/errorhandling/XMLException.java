package cellmodel.errorhandling;

import java.util.ResourceBundle;

public class XMLException extends RuntimeException {
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String XML_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "XMLTagNames";
    private static final ResourceBundle xmlResources= ResourceBundle.getBundle(XML_PROPERTIES_FILENAME);

    public XMLException(String property){
        super("COULD NOT CONFIGURE SIMULATION WITH GIVEN XML FILE: " + xmlResources.getString(property));
    }

    public XMLException(String property1, String property2) {
        super("COULD NOT CONFIGURE SIMULATION WITH GIVEN XML FILE: " + xmlResources.getString(property1) + xmlResources.getString(property2));

    }

}
