package display.visualizer;

import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;


public class HistoryGraph extends Application {

  private int numStates;
  private Map countsOfStates;
  private int time;
  private static Stage myStage = new Stage();
  private Map<Integer, Series> myLines;

  public HistoryGraph(){
    myLines = new HashMap<>();
  }

  @Override
  public void start(Stage stage) {
    stage.setTitle("StateHistoryChartTitle");
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("ChartXLabel");
    final LineChart<Number, Number> lineChart =
        new LineChart<Number, Number>(xAxis,yAxis);

    lineChart.setTitle("ChartTitle");

    for(int state = 0; state < numStates; state++){
      XYChart.Series series = new Series();
      myLines.putIfAbsent(state, series);
      XYChart.Series specificSeries = myLines.get(state);

      if(countsOfStates.containsKey(state)){
        Object population = countsOfStates.get(state);
        specificSeries.getData().add(new XYChart.Data(time, population));
      }
      lineChart.getData().add(specificSeries);
    }

    Scene scene = new Scene(lineChart,800,600);

    stage.setScene(scene);
    stage.show();
  }

  public void beginGraph(int numberOfStates){
    numStates = numberOfStates;
  }

  /**
   * get the new map, update cycle, and regraph according to new dimensions
   * @param counts map that holds the counts of the states AT THIS CYCLE
   * @param cycle current cycleNumber
   */
  public void updateGraph(Map counts, int cycle){
    countsOfStates = counts;
    time = cycle;
    start(myStage);
  }

}