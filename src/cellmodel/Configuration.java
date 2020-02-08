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
    public Configuration(String inputfileName){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = null;
        try {
            document = builder.parse(new File(inputfileName));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        assert document != null;
        document.getDocumentElement().normalize();

        myXML = document.getDocumentElement();
        myRules = parseRules();
    }

    /**
     * Parses the information to create a sublcass of Rules based on the xml file
     * @return an object of the specified Rules subclass
     */
    private Rules parseRules(){
        String simType = myXML.getElementsByTagName(RULES_XML_TAG).item(0).getTextContent();
        NodeList parametersNode = myXML.getElementsByTagName(RULES_PARAMETERS_XML_TAG).item(0).getChildNodes();
        HashMap<String, String> parameters = getParameterVals(parametersNode);

        Object ret = new Object();
        Constructor rulesConstructor = getRulesConstructor(simType);
        try {
            assert rulesConstructor != null;
            ret = rulesConstructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return (Rules)ret;
    }

    /**
     * @param simulationType is the type of Rules that the xml file specified
     * @return a constructor for the specific Rules
     */
    private Constructor getRulesConstructor(String simulationType){
        Class simClass = null;
        try {
            simClass = Class.forName(RULES_PACKAGE+"."+simulationType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class[] parameterTypes = {HashMap.class};
        Constructor constructor = null;
        try {
            assert simClass != null;
            constructor = simClass.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return constructor;
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
        return new Simulation(getInitBoard(),3);
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
