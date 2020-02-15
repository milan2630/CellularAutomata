package display.visualizer;

import static display.UserInterface.DEFAULT_RESOURCE_PACKAGE;
import static display.UserInterface.STYLE_PROPERTIES_FILENAME;

import display.UserInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;

/**
 * Create and display a graph that plots the states of all of the cells on the board
 * as time progresses
 */
public class HistoryGraph extends Application {

  private static final int GRAPH_WIDTH = 800;
  private static final int GRAPH_HEIGHT = 600;
  private static final int GAP = 40;

  private int numStates;
  private Map countsOfStates;
  private int time;
  private double viewWidth;
  private static Stage myStage = new Stage();
  private Map<Integer, Series> myLines;
  private ResourceBundle styleResources;
  private ResourceBundle myResources;
  private NumberAxis xAxis;
  private NumberAxis yAxis;
  private LineChart<Number, Number> lineChart;
  private Map<Integer, String> stateNames;

  /**
   * constructor, create a new map to keep track of the lines
   */
  public HistoryGraph(Map<Integer, String> names){
    myLines = new HashMap<>();
    styleResources = ResourceBundle.getBundle(STYLE_PROPERTIES_FILENAME);
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + styleResources.getString("Language"));
    xAxis = new NumberAxis();
    yAxis = new NumberAxis();
    stateNames = names;
    xAxis.setLabel(myResources.getString("ChartXLabel"));
  }

  /**
   * Create a line graph that has the same number of series as the simulation
   * does states
   * Plot the population as time goes on
   * @param stage
   */
  @Override
  public void start(Stage stage) {

    stage.setX(viewWidth+GAP);
    stage.setY(UserInterface.UI_GAP);
    stage.setTitle(myResources.getString("StateHistoryChartTitle"));

    lineChart = new LineChart<>(xAxis,yAxis);
    lineChart.setTitle(myResources.getString("ChartTitle"));

    for(int state = 0; state < numStates; state++){
      XYChart.Series series = new Series();
      series.setName(stateNames.get(state));
      myLines.putIfAbsent(state, series);
      XYChart.Series specificSeries = myLines.get(state);

      if(countsOfStates.containsKey(state)){
        Object population = countsOfStates.get(state);
        specificSeries.getData().add(new XYChart.Data(time, population));
      }
      lineChart.getData().add(specificSeries);
    }

    Scene scene = new Scene(lineChart, GRAPH_WIDTH, GRAPH_HEIGHT);

    stage.setScene(scene);
    stage.show();
  }

  /**
   * close the window if this method is called by an application class
   */
  protected void endGraph(){
    myStage.close();
  }

  public void beginGraph(int numberOfStates){
    numStates = numberOfStates;
  }

  /**
   * get the new map, update cycle, and regraph according to new dimensions
   * @param counts map that holds the counts of the states AT THIS CYCLE
   * @param cycle current cycleNumber
   */
  public void updateGraph(Map counts, int cycle, double screenWidth){
    viewWidth = screenWidth;
    countsOfStates = counts;
    time = cycle;
    start(myStage);
  }

}