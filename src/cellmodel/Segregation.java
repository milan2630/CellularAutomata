package cellmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.paint.Color;

public class  Segregation extends Rules {

  public static final Color RED_COLOR = Color.RED;
  public static final Color BlUE_COLOR = Color.BLUE;
  public static final Color EMPTY_COLOR = Color.WHITE;
  private static final int RED = 2;
  private static final int BLUE = 1;
  private static final int EMPTY = 0;
  private static final Color[] stateColors = {EMPTY_COLOR, BlUE_COLOR, RED_COLOR};

  private float percentSatisfied;

  /**
   * Initialize variables, get the percent of neighbors of the same state needed to satisfy a cell
   * @param setupParameters
   */
  public Segregation(HashMap<String, String> setupParameters){
    percentSatisfied = Float.parseFloat(setupParameters.get("percentSatisfied"));
  }

  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  public void changeState(Cell cell, Cell cloneCell) {
    if(cloneCell.getState()!= EMPTY && (cloneCell.numNeighborsOfSameState() < (percentSatisfied*getNotEmptyNeighbors(cloneCell).size()))) {
      findAndMoveToEmptyCell(cell, cell.getState());
      cell.changeStateAndView(EMPTY, stateColors[EMPTY]);
    }
  }

  private List<Cell> getNotEmptyNeighbors(Cell cell) {
    List<Cell> notEmptyNeighbors = new ArrayList<>();
    for (Cell neighbor : cell.getNeighbors()) {
      if (neighbor.getState() != EMPTY) {
        notEmptyNeighbors.add(neighbor);
      }
    }
    return notEmptyNeighbors;
  }

  private void findAndMoveToEmptyCell(Cell cell, int state) {
    List<Cell> cellNeighborsList = cell.getNeighbors();
    List<Cell> emptyNeighborsList = cell.getNeighborsWithState(EMPTY);
    Cell emptyNeighbor = null;
    if (emptyNeighborsList.size() != 0) {
      int random = getRandomIndex(emptyNeighborsList);
      emptyNeighbor = emptyNeighborsList.get(random);
      for (Cell neighbor : cellNeighborsList) {
          if (neighbor.equals(emptyNeighbor)) {
          neighbor.changeStateAndView(state, stateColors[state]);
          return;
        }
      }
    }
    if (emptyNeighborsList.size() == 0) {
      int random2 = getRandomIndex(cellNeighborsList);
      Cell cellNeighbor = cellNeighborsList.get(random2);
      findAndMoveToEmptyCell(cellNeighbor,  state);
    }
  }

  private int getRandomIndex(List<Cell> givenStateNeighbors) {
    int random=0;
    if(givenStateNeighbors.size()!=1) {
      random = (int) (Math.random() * givenStateNeighbors.size());
    }
    return random;
  }

  @Override
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return true; in Segregation, it does
   */
  public boolean areCornersNeighbors(){
    return true;
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
