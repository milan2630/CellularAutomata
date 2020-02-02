package cellsociety;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class Fire extends Rules {

  public static final Color BURNING_COLOR = Color.DARKRED;
  public static final Color TREE_COLOR = Color.GREEN;
  public static final Color EMPTY_COLOR = Color.YELLOW;

  private Color[] stateColors;
  private int burning;
  private int tree;
  private  int empty;
  private float probCatch;

  /**
   * Initialize variables, get probability of a tree catching fire from setupParameters
   * @param setupParameters
   */
  public Fire(HashMap<String, String> setupParameters){
    burning = 0;
    tree = 1;
    empty = 2;
    stateColors = new Color[3];
    stateColors[burning] = BURNING_COLOR;
    stateColors[tree] = TREE_COLOR;
    stateColors[empty] = EMPTY_COLOR;
    probCatch = Float.parseFloat(setupParameters.get("probCatch"));
  }
  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();

    if(state == tree && cloneCell.numNeighborsWithGivenState(burning)>0 && treeBurns()) {
      cell.changeStateAndView(burning, stateColors[burning]);
    }
    if(state == burning){// && cell.numberOfStateChanges()>0){
      //aka it has been more than one round, change it
      cell.changeStateAndView(empty, stateColors[empty]);
    }
  }

  private boolean treeBurns(){
    return Math.random()<=probCatch;
  }

  @Override
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return false; in Fire, it does not
   */
  public boolean areCornersNeighbors(){
    return false;
  }

  @Override
  /**
   * gets the color for a cell that is created with a certain state
   * so that the board can be created
   * @param state
   * @return color of the state, or if it's not a valid state white
   */
  public Color getStateColor(int state){
    if(state >=0 && state <=3)
      return stateColors[state];
    else return Color.WHITE;
  }
}
