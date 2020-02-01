package cellsociety;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class GameOfLife extends Rules {

  public static final Color ALIVE_COLOR = Color.GREY;
  public static final Color DEAD_COLOR = Color.BLACK;

  private Color[] stateColors;
  private int alive;
  private int dead;

  /**
   * Initialize variables, get probability of a tree catching fire from setupParameters
   * @param setupParameters
   */
  public GameOfLife(HashMap<String, String> setupParameters){
    alive = 1;
    dead = 0;
    stateColors = new Color[2];
    stateColors[alive] = ALIVE_COLOR;
    stateColors[dead] = DEAD_COLOR;
  }
  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  /*public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if (state==dead && cloneCell.numNeighborsWithGivenState(alive)==3){
      cell.changeStateAndView(alive, stateColors[alive]);
    }
    if (state==alive && cloneCell.numNeighborsOfSameState()<2){
      cell.changeStateAndView(dead, stateColors[dead]);
    }
    if (state == alive && cloneCell.numNeighborsOfSameState()>3){
      cell.changeStateAndView(dead, stateColors[dead]);
    }
  }*/

  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    int numNeighborsAlive = cloneCell.numNeighborsWithGivenState(alive);

    if (state == dead && numNeighborsAlive == 3){
      cell.changeStateAndView(alive, stateColors[alive]);
    } else if (state == alive && (numNeighborsAlive <= 1 || numNeighborsAlive > 3)){
      cell.changeStateAndView(dead, stateColors[dead]);
    }
  }
  @Override
  /**
   * gets the color for a cell that is created with a certain state
   * so that the board can be created
   * @param state
   * @return color of the state, or if it's not a valid state white
   */
  public Color getStateColor(int state){
    if(state == 0 || state == 1)
      return stateColors[state];
    else return Color.WHITE;
  }
}
