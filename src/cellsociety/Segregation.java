package cellsociety;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.paint.Color;

public class  Segregation extends Rules {

  public static final Color RED_COLOR = Color.RED;
  public static final Color BlUE_COLOR = Color.BLUE;
  public static final Color EMPTY_COLOR = Color.WHITE;

  private Color[] stateColors;
  private int red;
  private int blue;
  private  int empty;
  private float percentSatisfied;

  /**
   * Initialize variables, get probability of a tree catching fire from setupParameters
   * @param setupParameters
   */
  public Segregation(HashMap<String, String> setupParameters){
    red = 2;
    blue = 1;
    empty = 0;
    stateColors = new Color[3];
    stateColors[red] = RED_COLOR;
    stateColors[blue] = BlUE_COLOR;
    stateColors[empty] = EMPTY_COLOR;
    percentSatisfied = Float.parseFloat(setupParameters.get("percentSatisfied"));
  }

  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if(cloneCell.numNeighborsOfSameState() < (percentSatisfied*cell.getNeighbors().size())) {
      cell.changeStateAndView(empty, stateColors[empty]);
      findAndMoveToEmptyCell(cell, cloneCell);
    }
  }

  private void findAndMoveToEmptyCell(Cell cell, Cell cloneCell) {
    for (int i=0; i< cell.getNeighbors().size(); i++){
      List<Cell> cloneNeighborsList = cloneCell.getNeighbors();
      List<Cell> cellNeighborsList = cell.getNeighbors();
      Cell cloneNeighbor = cloneNeighborsList.get(i);
      Cell cellNeighbor = cellNeighborsList.get(i);
      if (cloneNeighbor.getState()==empty){
        cellNeighbor.changeStateAndView(cell.getState(), stateColors[cell.getState()]);
      }
      else {
      findAndMoveToEmptyCell(cellNeighbor, cloneNeighbor);
      }
    }
  }

  @Override
  /**
   * gets the color for a cell that is created with a certain state
   * so that the board can be created
   * @param state
   * @return color of the state, or if it's not a valid state black
   */
  public Color getStateColor(int state){
    if(state >=0 && state <=3)
      return stateColors[state];
    else return Color.BLACK;
  }
}
