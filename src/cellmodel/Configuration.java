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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Class to handle setting up the Board and Simulation from an xml file
 */
public class Configuration {
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String XML_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "XMLTagNames";


    private Element myXML;
    private Rules myRules;
    private ResourceBundle xmlResources;

    /**
     * Constructor to create a Configuration object based on a filename
     * @param inputfileName xml file name to parse
     */
    public Configuration(String inputfileName) throws XMLException{
        xmlResources = ResourceBundle.getBundle(XML_PROPERTIES_FILENAME);
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File xmlFile = new File(inputfileName);
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            myXML = document.getDocumentElement();
            myRules = parseRules();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new XMLException(xmlResources.getString("FileErrorMessage"));
        }
    }

    /**
     * Parses the information to create a sublcass of Rules based on the xml file
     * @return an object of the specified Rules subclass
     */
    private Rules parseRules() throws XMLException{
        String simType;
        HashMap<String, String> parameters;
        Object ret;
        simType = parseStringFromXml(("rulesXMLTag"));
        try {
            NodeList parametersNode = myXML.getElementsByTagName(xmlResources.getString("ParametersTag")).item(0).getChildNodes();
            parameters = getParameterVals(parametersNode);
        } catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString("ParametersTag"));
        }

        try {
            Constructor rulesConstructor = getRulesConstructor(simType);
            ret = rulesConstructor.newInstance(parameters);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new XMLException(xmlResources.getString("RulesClassErrorMessage") + e.toString());
        }
        return (Rules)ret;
    }

    /**
     * @param simulationType is the type of Rules that the xml file specified
     * @return a constructor for the specific Rules
     */
    private Constructor getRulesConstructor(String simulationType){
        try {
            Class simClass = Class.forName(xmlResources.getString("RulesPackage")+"."+simulationType);
            Class[] parameterTypes = {HashMap.class};
            Constructor constructor = simClass.getConstructor(parameterTypes);
            return constructor;
        } catch (ClassNotFoundException e) {
            throw new XMLException(xmlResources.getString("SimTypeDoesNotExistErrorMessage"));
        } catch (NoSuchMethodException e) {
            throw new XMLException(xmlResources.getString("RulesClassErrorMessage") + e.toString());
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
    public Simulation getInitSim()throws XMLException{
        return new Simulation(getInitBoard(),4);
    }

    /**
     * @return a Board object based on the initial configuration from the xml file
     */
    private Board getInitBoard() throws XMLException{
        int boardHeight = parseIntFromXML("boardHeightTag");
        int boardWidth = parseIntFromXML("boardWidthTag");
        Board myBoard = new Board(boardHeight, boardWidth, myRules);
        String setupType = parseStringFromXml("setupTypeTag");
        if(setupType.equals(xmlResources.getString("probabilitiesKeyword"))){
            setupBoardWProbs(myBoard, boardHeight, boardWidth);
        }
        else if(setupType.equals(xmlResources.getString("cellListKeyword"))){
            setupBoardWCellList(myBoard, boardHeight, boardWidth);
        }
        return myBoard;

    }

    private void setupBoardWCellList(Board myBoard, int boardHeight, int boardWidth) {
        try {
            NodeList cellList = myXML.getElementsByTagName(xmlResources.getString("cellTag"));
            if(cellList.getLength() != boardHeight*boardWidth){
                throw new NullPointerException();
            }
            for(int i = 0; i < cellList.getLength(); i++){
                Element cellNode = (Element) cellList.item(i);
                myBoard.updateCell(parseIntFromCell(cellNode, "stateTag"), parseIntFromCell(cellNode, "rowTag"),
                        parseIntFromCell(cellNode, "columnTag"));
            }
        }
        catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString("cellTag"));
        }
    }

    private void setupBoardWProbs(Board myBoard, int boardHeight, int boardWidth) {
        try {
            NodeList probList = myXML.getElementsByTagName(xmlResources.getString("probTag"));
            if(probList.getLength() != myRules.getNumberOfPossibleStates()){
                throw new NullPointerException();
            }
            List<Float> chancesOfStates = new ArrayList<>();
            for(int k = 0; k < probList.getLength(); k++){
                Element cellNode = (Element) probList.item(k);
                chancesOfStates.add(k, Float.parseFloat(cellNode.getAttribute(xmlResources.getString("stateAttribute"))));
            }
            for(int i = 0; i < boardHeight; i++){
                for(int j = 0; j < boardWidth; j++){
                    double rand = Math.random();
                    int ind = 0;
                    float counter = 0;
                    while(rand > counter){
                        counter+=chancesOfStates.get(ind);
                        ind++;
                    }
                    myBoard.updateCell(ind,i, j);
                }
            }

        }
        catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString("probTag"));
        }
    }

    private int parseIntFromCell(Element cell, String property) throws XMLException{
        try {
            return Integer.parseInt(cell.getElementsByTagName(xmlResources.getString(property)).item(0).getTextContent());
        }
        catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString(property));
        }
    }

    private int parseIntFromXML(String property){
        try {
            return Integer.parseInt(myXML.getElementsByTagName(xmlResources.getString(property)).item(0).getTextContent());
        }
        catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString(property));
        }
    }

    private String parseStringFromXml(String property){
        try {
            return myXML.getElementsByTagName(xmlResources.getString(property)).item(0).getTextContent();
        }
        catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString(property));
        }
    }
}
