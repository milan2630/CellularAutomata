package cellmodel;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class GameOfLife extends Rules {

  private static final Color ALIVE_COLOR = Color.GREY;
  private static final Color DEAD_COLOR = Color.BLACK;
  private static final int ALIVE = 1;
  private static final int DEAD = 0;
  private static final Color[] STATE_COLORS = {DEAD_COLOR, ALIVE_COLOR};


  /**
   * Construct a GameOfLife object
   * @param setupParameters has no setup parameters for Game of Life
   */
  public GameOfLife(HashMap<String, String> setupParameters){

  }
  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */

  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    int numNeighborsAlive = cloneCell.numNeighborsWithGivenState(ALIVE);

    if (state == DEAD && numNeighborsAlive == 3){
      cell.changeStateAndView(ALIVE, STATE_COLORS[ALIVE]);
    } else if (state == ALIVE && (numNeighborsAlive <= 1 || numNeighborsAlive > 3)){
      cell.changeStateAndView(DEAD, STATE_COLORS[DEAD]);
    }
  }

  @Override
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return in Game of Life, it does
   */
  public boolean areCornersNeighbors(){
    return true;
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
      return STATE_COLORS[state];
    else return Color.WHITE;
  }
}
