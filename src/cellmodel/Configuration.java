package cellmodel;

import cellmodel.rules.Rules;
import display.visualizer.Visualizer;
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
            NodeList parametersNode = myXML.getElementsByTagName(xmlResources.getString("parametersTag")).item(0).getChildNodes();
            parameters = getParameterVals(parametersNode);
        } catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString("parametersTag"));
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
     * @param numCorners
     */
    public Simulation getInitSim(int numCorners, int neighborhoodType)throws XMLException{
        return new Simulation(getInitBoard(numCorners, neighborhoodType), numCorners);
    }

    /**
     * @return a Board object based on the initial configuration from the xml file
     */

    private Board getInitBoard(int numCornersOnACell, int neighborhoodType) throws XMLException{
        int boardHeight = parseIntFromXML("boardHeightTag");
        int boardWidth = parseIntFromXML("boardWidthTag");
        Board myBoard;
        if(numCornersOnACell==Visualizer.TRIANGLE_CORNER_NUMBER){
            myBoard = new TriangleBoard(boardWidth, boardHeight, myRules, neighborhoodType, 0.6);
        } else {
            myBoard = new Board(boardHeight, boardWidth, myRules, neighborhoodType, 0.6);
        }
        String setupType = parseStringFromXml("setupTypeTag");
        if(setupType.equals(xmlResources.getString("probabilitiesKeyword"))){
            setupBoardWProbs(myBoard, boardHeight, boardWidth);
        }
        else if(setupType.equals(xmlResources.getString("cellListKeyword"))){
            setupBoardWCellList(myBoard, boardHeight, boardWidth);
        }
        else if(setupType.equals(xmlResources.getString("quotasKeyword"))){
            setupBoardWQuotas(myBoard, boardHeight, boardWidth);

        }
        return myBoard;

    }

    private void setupBoardWCellList(Board myBoard, int boardHeight, int boardWidth) {
        try {
            NodeList cellList = myXML.getElementsByTagName(xmlResources.getString("cellTag"));
            if(cellList.getLength() != boardHeight){
                throw new NullPointerException();
            }
            for(int row = 0; row < cellList.getLength(); row++){
                Element cellNode = (Element) cellList.item(row);
                String[] cols = cellNode.getTextContent().split(" ");
                if(cols.length != boardWidth){
                    throw new NullPointerException();
                }
                for(int col = 0; col < cols.length; col++){
                    myBoard.updateCell(Integer.parseInt(cols[col]), row, col);
                }
            }
        }
        catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString("cellTag"));
        }
    }

    private void setupBoardWProbs(Board myBoard, int boardHeight, int boardWidth) {
        try {
            NodeList probList = myXML.getElementsByTagName(xmlResources.getString("probTag"));
            checkAllStatesPresent(probList);
            List<Float> chancesOfStates = createListProbs(probList);
            for(int row = 0; row < boardHeight; row++){
                for(int col = 0; col < boardWidth; col++){
                    initializeCellFromProbs(myBoard, chancesOfStates, row, col);
                }
            }
        }
        catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString("probTag"));
        }
    }

    private void initializeCellFromProbs(Board myBoard, List<Float> chancesOfStates, int row, int col) {
        double rand = Math.random();
        int ind = 0;
        float counter = chancesOfStates.get(0);
        while(rand > counter){
            ind++;
            counter+=chancesOfStates.get(ind);
        }
        myBoard.updateCell(ind,row, col);
    }

    private List<Float> createListProbs(NodeList probList) {
        List<Float> chancesOfStates = new ArrayList<>();
        for(int k = 0; k < probList.getLength(); k++){
            Element cellNode = (Element) probList.item(k);
            float chance = Float.parseFloat(cellNode.getTextContent());
            int stateToAdd = Integer.parseInt(cellNode.getAttribute(xmlResources.getString("stateAttribute")));
            chancesOfStates.add(stateToAdd, chance);
        }
        return chancesOfStates;
    }

    private void setupBoardWQuotas(Board myBoard, int boardHeight, int boardWidth) {
        try {
            NodeList quotaList = myXML.getElementsByTagName(xmlResources.getString("quotaTag"));
            checkAllStatesPresent(quotaList);
            boolean[][] haveVisited = new boolean[boardHeight][boardWidth];
            int totalCount = 0;
            for(int k = 0; k < quotaList.getLength(); k++){
                Element cellNode = (Element) quotaList.item(k);
                int total = Integer.parseInt(cellNode.getTextContent());
                totalCount+=total;
                int stateToAdd = Integer.parseInt(cellNode.getAttribute(xmlResources.getString("stateAttribute")));
                while(total > 0) {
                    total = initializeCellFromProbs(myBoard, boardHeight, boardWidth, haveVisited, total, stateToAdd);
                }
            }
            if(totalCount < boardHeight*boardWidth){
                throw new NullPointerException();
            }
        }
        catch (NullPointerException e){
            throw new XMLException(xmlResources.getString("MissingTagErrorMessage") + xmlResources.getString("probTag"));
        }
    }

    private int initializeCellFromProbs(Board myBoard, int boardHeight, int boardWidth, boolean[][] haveVisited, int total, int stateToAdd) {
        int row = (int) (Math.random() * boardHeight);
        int col = (int) (Math.random() * boardWidth);
        if(!haveVisited[row][col]) {
            myBoard.updateCell(stateToAdd, row, col);
            haveVisited[row][col] = true;
            total--;
        }
        return total;
    }

    private void checkAllStatesPresent(NodeList quotaList) {
        if(quotaList.getLength() != myRules.getNumberOfPossibleStates()){
            throw new NullPointerException();
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
