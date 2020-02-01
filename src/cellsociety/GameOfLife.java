package cellsociety;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class GameOfLife extends Rules {

  public static final Color ALIVE_COLOR = Color.BLACK;
  public static final Color DEAD_COLOR = Color.WHITE;

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
  public void changeState(Cell cell) {
    int state = cell.getState();

    if(state == tree && cell.numNeighborsWithGivenState(burning)>0 && treeBurns()) {
      cell.changeStateAndView(burning, stateColors[burning]);
    }
    if(state == burning){// && cell.numberOfStateChanges()>0){
      //aka it has been more than one round, change it
      cell.changeStateAndView(empty, stateColors[empty]);
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
    if(state >=0 && state <=3)
      return stateColors[state];
    else return Color.WHITE;
  }
}
