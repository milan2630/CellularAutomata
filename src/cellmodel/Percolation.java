package cellmodel;

import java.util.HashMap;
import javafx.scene.paint.Color;

/**
 * Contains the logic for the Percolation CA
 * Water fills empty adjacent cells
 */
public class Percolation extends Rules {

  private static final Color FILLED_COLOR = Color.BLUE;
  private static final Color EMPTY_COLOR = Color.WHITE;
  private static final Color BLOCKED_COLOR = Color.BLACK;
  private static final int EMPTY = 1;
  private static final int BLOCKED = 2;
  private static final int FILLED = 0;
  private static final Color[] STATE_COLORS = {FILLED_COLOR, EMPTY_COLOR, BLOCKED_COLOR};

  /**
   * Initialize variables, set colors to their respective states
   * @param setupParameters has no set up parameters
   */
  public Percolation(HashMap<String, String> setupParameters){
  }

  /**
   * given a certain cell, change its state based on percolation rules
   *  filled -> filled
   *  empty -> empty if no filled neighbors
   *  empty -> filled if filled neighbors
   *  blocked -> blocked/open depending on response to piazza post
   * @param cell
   * @param cloneCell
   */
  @Override
  void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if(state == EMPTY && cloneCell.numNeighborsWithGivenState(FILLED)>0){
      cell.changeStateAndView(FILLED, STATE_COLORS[FILLED]);
    }
  }

  @Override
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return in Percolation, it does not
   */
  public boolean areCornersNeighbors(){
    return false;
  }

  /**
   * Return a color given a certain state, used for setting up the initial board
   * @param state state to get the color of
   * @return color associated with state
   */
  @Override
  public Color getStateColor(int state) {
    return STATE_COLORS[state];
  }

}
