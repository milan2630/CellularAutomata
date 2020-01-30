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

    Element myXML;
    Rules myRules;

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
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        document.getDocumentElement().normalize();

        myXML = document.getDocumentElement();

        myRules = parseRules();
    }

    private Rules parseRules(){
        String simType = myXML.getElementsByTagName("Simulation_Type").item(0).getTextContent();
        NodeList parametersNode = myXML.getElementsByTagName("Rules_Parameters").item(0).getChildNodes();
        HashMap<String, String> parameters = new HashMap<>();
        for(int i = 0; i < parametersNode.getLength(); i++){
            Node n = parametersNode.item(i);
            if(n.getNodeType() == Node.ELEMENT_NODE){
                parameters.putIfAbsent(parametersNode.item(i).getNodeName(), parametersNode.item(i).getTextContent());
            }
        }
        Object ret = new Object();
        Class simClass = null;
        try {
            simClass = Class.forName("cellsociety."+simType);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Class[] parameterTypes = {HashMap.class};
        Constructor constructor = null;
        try {
            constructor = simClass.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            assert constructor != null;
            ret = constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return (Rules)ret;
    }

    public Rules getInitRules(){
        return myRules;
    }

    public Board getInitBoard(){
        Board myBoard = new Board(parseCellWidth(), parseCellHeight());
        /*for(line in myXML){
            Cell c = new Cell(parseState(), parseColor());
            myBoard.insertCell(c, parseRow(), parseCol());
        }*/

    }

    private int parseCol() {

    }

    private int parseRow() {

    }

    private Color parseColor() {

    }

    private int parseState() {

    }

    private int parseCellHeight() {

    }

    private int parseCellWidth() {

    }


}
