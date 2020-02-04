package cellmodel;

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
    System.out.println(cell);
    if(cloneCell.getState()!= empty && (cloneCell.numNeighborsOfSameState() < (percentSatisfied*getNotEmptyNeighbors(cloneCell).size()))) {
      System.out.println("yo");
      System.out.println(cell.getState());
      findAndMoveToEmptyCell(cell, cell.getState());
      cell.changeStateAndView(empty, stateColors[empty]);
      System.out.println(cell.getState());
      System.out.println(stateColors[cell.getState()]);
    }
  }

  private void findAndMoveToEmptyCell(Cell cell, int state) {
    List<Cell> cellNeighborsList = cell.getNeighbors();
    List<Cell> emptyNeighborsList = cell.getNeighborsWithState(empty);
    System.out.println(emptyNeighborsList.size());
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
      //List<Cell> cloneNeighbors = cell.getNeighbors();
      //Cell cloneNeigh = cloneNeighbors.get(random2);
      //Cell emptyneigh = cellNeighborsList.get(random2);
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

  private List<Cell> getNotEmptyNeighbors(Cell cell) {
    List<Cell> notEmptyNeighbors = new ArrayList<>();
    for (Cell neighbor : cell.getNeighbors()) {
      if (neighbor.getState() != empty) {
        notEmptyNeighbors.add(neighbor);
      }
    }
    return notEmptyNeighbors;
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
