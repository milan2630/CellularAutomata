package cellsociety;

import javafx.scene.paint.Color;
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

public class Configuration {

    private Element myXML;
    private Rules myRules;

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


        document.getDocumentElement().normalize();

        myXML = document.getDocumentElement();

        myRules = parseRules();
    }

    private Rules parseRules(){
        String simType = myXML.getElementsByTagName("Simulation_Type").item(0).getTextContent();
        NodeList parametersNode = myXML.getElementsByTagName("Rules_Parameters").item(0).getChildNodes();
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

    private Constructor getRulesConstructor(String simulationType){
        Class simClass = null;
        try {
            simClass = Class.forName("cellsociety."+simulationType);
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


    public Board getInitBoard(){
        Board myBoard = new Board(parseBoardWidth(), parseBoardHeight(), myRules);
        NodeList cellList = myXML.getElementsByTagName("Cell");
        for(int i = 0; i < cellList.getLength(); i++){
            Element cellNode = (Element) cellList.item(i);
            myBoard.updateCell(parseState(cellNode), parseRow(cellNode), parseCol(cellNode));
        }
        return myBoard;

    }

    private int parseCol(Element cell) {
        return Integer.parseInt(cell.getElementsByTagName("Column").item(0).getTextContent());
    }

    private int parseRow(Element cell) {
        return Integer.parseInt(cell.getElementsByTagName("Row").item(0).getTextContent());
    }

    private int parseState(Element cell) {
        return Integer.parseInt(cell.getElementsByTagName("State").item(0).getTextContent());
    }

    private int parseBoardHeight() {
        return Integer.parseInt(myXML.getElementsByTagName("Screen_Height").item(0).getTextContent());
    }

    private int parseBoardWidth() {
        return Integer.parseInt(myXML.getElementsByTagName("Screen_Width").item(0).getTextContent());
    }


}
