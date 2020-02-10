package cellmodel;

import display.visualizer.SquareVisualizer;
import display.visualizer.TriangleVisualizer;
import display.visualizer.Visualizer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Class to run the simulation by repeatedly calling a step function
 */
public class Simulation {
    public static final int DEFAULT_FRAMES_PER_SECOND = 0;
    public static final double MILLIS_PER_SECOND = 1000.0;
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String XML_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "XMLTagNames";
    private ResourceBundle xmlResources;

    private Board myBoard;
    private Visualizer myVisualizer;
    private Timeline animation;
    private double millisecondDelay;
    /**
     * constructor that takes in a starting board, and number or corners in the cell shape
     * and starts running the simulation
     * @param board incoming board
     */
    public Simulation(Board board, int numCornersOnACell){
      xmlResources = ResourceBundle.getBundle(XML_PROPERTIES_FILENAME);
      myBoard = board;
      if(numCornersOnACell == Visualizer.TRIANGLE_CORNER_NUMBER) {
        myVisualizer = new TriangleVisualizer();
      } else {
        myVisualizer = new SquareVisualizer();
      }
      myVisualizer.displayBoard(board);
      setFramesPerSec(DEFAULT_FRAMES_PER_SECOND);
      animation = new Timeline();
      animation.setCycleCount(Animation.INDEFINITE);
      setKeyFrame();
    }

    /**
     * @param fps is the number of frames per second to set the animation to
     */
    private void setFramesPerSec(int fps){
            millisecondDelay = MILLIS_PER_SECOND / fps;
        }

    /**
     * Sets the keyframe to running step() after every delay determined by millisecondDelay
     */
    private void setKeyFrame(){
      KeyFrame newFrame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
      animation.getKeyFrames().add(newFrame);
      animation.play();
    }

    /**
     * Resets the keyframe on user input
     * @param fps is the frames per second to set the animation at
     */
    public void resetKeyFrame(int fps){
      setFramesPerSec(fps);
      animation.getKeyFrames().remove(0);
      animation.stop();
      setKeyFrame();
    }

    /**
     * Runs repeatedly after every millisecondDelay to have the board take a step forward based on the
     * current status of all the cells
     */
    public void step() {
      myBoard.updateBoard();
      myVisualizer.displayBoard(myBoard);
    }

    /**
     * Ends the simulation by closing the visualizer
     */
    public void endSim(){
          myVisualizer.closeWindow();
      }

    public void saveCurrent() {
      try {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();


        Element rootElement = doc.createElement("Root");
        doc.appendChild(rootElement);

        String simType = myBoard.getRulesClass();
        Element simTypeNode = doc.createElement(xmlResources.getString("rulesXMLTag"));
        simTypeNode.setTextContent(simType);
        rootElement.appendChild(simTypeNode);

        Element setupTypeNode = doc.createElement(xmlResources.getString("setupTypeTag"));
        setupTypeNode.setTextContent(xmlResources.getString("cellListKeyword"));
        rootElement.appendChild(setupTypeNode);



        Element rulesInfoNode = doc.createElement(xmlResources.getString("rulesInfoTag"));
        Element rulesParamNode = doc.createElement(xmlResources.getString("parametersTag"));

        HashMap<String, String> parameters = myBoard.getRulesParameters();
        for(String key: parameters.keySet()){
            Element paramElem = doc.createElement(key);
            paramElem.setTextContent(parameters.get(key));
            rulesParamNode.appendChild(paramElem);
        }

        rulesInfoNode.appendChild(rulesParamNode);
        rootElement.appendChild(rulesInfoNode);

        Element gridNode = doc.createElement(xmlResources.getString("gridTag"));
        Element screenHeightNode = doc.createElement(xmlResources.getString("boardHeightTag"));
        screenHeightNode.setTextContent(""+myBoard.getNumRows());
        gridNode.appendChild(screenHeightNode);
        Element screenWidthNode = doc.createElement(xmlResources.getString("boardWidthTag"));
        screenWidthNode.setTextContent(""+myBoard.getNumCols());
        gridNode.appendChild(screenWidthNode);

        Element cellsNode = doc.createElement(xmlResources.getString("generalCellTag"));
        for(int row = 0; row < myBoard.getNumRows(); row++){
          Element rowNode = doc.createElement(xmlResources.getString("cellTag"));
          String text = "";
          for(int col = 0; col < myBoard.getNumCols(); col++){
              text = text + myBoard.getState(row, col)+" ";
          }
          rowNode.setTextContent(text);
          cellsNode.appendChild(rowNode);
        }

        gridNode.appendChild(cellsNode);
        rootElement.appendChild(gridNode);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(xmlResources.getString("XMLFilesFolder") + simType +
                System.currentTimeMillis() + xmlResources.getString("fileExtension")));

        transformer.transform(source, result);


      } catch (ParserConfigurationException | TransformerConfigurationException e) {
        e.printStackTrace();
      } catch (TransformerException e) {
        e.printStackTrace();
      }
    }

}
