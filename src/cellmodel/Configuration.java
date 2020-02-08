package cellmodel;

import cellmodel.rules.Rules;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


/**
 * Class to handle setting up the Board and Simulation from an xml file
 */
public class Configuration {
    public static final String RULES_PACKAGE = "cellmodel.rules"; //Package containing the java files for the different simulation types
    public static final String RULES_XML_TAG = "Simulation_Type"; //Tag in the XML file that has the simulation type
    public static final String RULES_PARAMETERS_XML_TAG = "Rules_Parameters"; //Tag in the xml file that has the setup parameters
    private Element myXML;
    private Rules myRules;

    /**
     * Constructor to create a Configuration object based on a filename
     * @param inputfileName xml file name to parse
     */
    public Configuration(String inputfileName) throws XMLException{
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File xmlFile = new File(inputfileName);
            System.out.println(xmlFile.getCanonicalPath());
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            myXML = document.getDocumentElement();
            myRules = parseRules();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLException("File entered cannot be parsed as an XML file");
        }
        catch (XMLException e){
            throw e;
        }
    }

    /**
     * Parses the information to create a sublcass of Rules based on the xml file
     * @return an object of the specified Rules subclass
     */
    private Rules parseRules() throws XMLException{
        String simType;
        NodeList parametersNode;
        try {
            simType = myXML.getElementsByTagName(RULES_XML_TAG).item(0).getTextContent();
        }
        catch (NullPointerException e){
            throw new XMLException("Cannot find Sim_Type tag, should be: " + RULES_XML_TAG);
        }
        try {
            parametersNode = myXML.getElementsByTagName(RULES_PARAMETERS_XML_TAG).item(0).getChildNodes();
        }
        catch (NullPointerException e){
            throw new XMLException("Cannot find Rules Parameters tag, should be: " + RULES_PARAMETERS_XML_TAG);
        }
        HashMap<String, String> parameters = getParameterVals(parametersNode);

        Object ret;

        try {
            Constructor rulesConstructor = getRulesConstructor(simType);
            ret = rulesConstructor.newInstance(parameters);
        } catch (IllegalAccessException e) {
            throw new XMLException("Simulation Type specified from XML file is not able to be accessed");
        } catch (InstantiationException e) {
            throw new XMLException("Simulation Type specified from XML file is not able to be instantiated");
        } catch (InvocationTargetException e) {
            throw new XMLException("Error in constructor of Simulation Type specified from XML file");
        }
        return (Rules)ret;
    }

    /**
     * @param simulationType is the type of Rules that the xml file specified
     * @return a constructor for the specific Rules
     */
    private Constructor getRulesConstructor(String simulationType){
        try {
            Class simClass = Class.forName(RULES_PACKAGE+"."+simulationType);
            Class[] parameterTypes = {HashMap.class};
            Constructor constructor = simClass.getConstructor(parameterTypes);
            return constructor;
        } catch (ClassNotFoundException e) {
            throw new XMLException("Class does not exist for Simulation Type specified in XML file");
        } catch (NoSuchMethodException e) {
            throw new XMLException("Constructor does not exist for Simulation Type specified in XML file");
        }

    }

    /**
     * @param pNode is the node in the XML file that contains the parameters for the simulation
     * @return a hashmap with keys of input variable names and are linked to their values specified in the xml file
     */
    private HashMap<String, String> getParameterVals(NodeList pNode){
        HashMap<String, String> parameterSet = new HashMap<>();
        for(int i = 0; i < pNode.getLength(); i++){
            Node n = pNode.item(i);
            if(n.getNodeType() == Node.ELEMENT_NODE){
                parameterSet.putIfAbsent(pNode.item(i).getNodeName(), pNode.item(i).getTextContent());
            }
        }
        return parameterSet;
    }

    /**
     * @return a Simulation object based on the xml file
     */
    public Simulation getInitSim(){
        return new Simulation(getInitBoard(),4);
    }

    /**
     * @return a Board object based on the initial configuration from the xml file
     */
    private Board getInitBoard(){
        Board myBoard = new Board(parseBoardWidth(), parseBoardHeight(), myRules);
        NodeList cellList = myXML.getElementsByTagName("Cell");
        for(int i = 0; i < cellList.getLength(); i++){
            Element cellNode = (Element) cellList.item(i);
            myBoard.updateCell(parseState(cellNode), parseRow(cellNode), parseCol(cellNode));
        }
        return myBoard;

    }

    /**
     * @param cell
     * @return the column of the given cell
     */
    private int parseCol(Element cell) {
        return Integer.parseInt(cell.getElementsByTagName("Column").item(0).getTextContent());
    }

    /**
     * @param cell
     * @return the row of the given cell
     */
    private int parseRow(Element cell) {
        return Integer.parseInt(cell.getElementsByTagName("Row").item(0).getTextContent());
    }

    /**
     * @param cell
     * @return the state of the given cell
     */
    private int parseState(Element cell) {
        return Integer.parseInt(cell.getElementsByTagName("State").item(0).getTextContent());
    }

    /**
     * @return the height of the board specified in the xml file
     */
    private int parseBoardHeight() {
        return Integer.parseInt(myXML.getElementsByTagName("Screen_Height").item(0).getTextContent());
    }

    /**
     * @return the width of the board specified in the xml file
     */
    private int parseBoardWidth() {
        return Integer.parseInt(myXML.getElementsByTagName("Screen_Width").item(0).getTextContent());
    }


}
