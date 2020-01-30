package cellsociety;

import javafx.scene.paint.Color;

public class Percolation extends Rules {

  public static final Color FILLED_COLOR = Color.AZURE;
  public static final Color EMPTY_COLOR = Color.WHITE;
  public static final Color BLOCKED_COLOR = Color.BLACK;

  private Color[] stateColors;
  private int empty;
  private int blocked;
  private int filled;

  /**
   * Initialize variables, set colors to their respective states
   */
  public Percolation(){
    empty = 1;
    blocked = 2;
    filled = 0;
    stateColors = new Color[3];
    stateColors[empty] = EMPTY_COLOR;
    stateColors[blocked] = BLOCKED_COLOR;
    stateColors[filled] = FILLED_COLOR;
  }

  /**
   * given a certain cell, change its state based on percolation rules
   *  filled -> filled
   *  empty -> empty if no filled neighbors
   *  empty -> filled if filled neighbors
   *  blocked -> blocked/open depending on response to piazza post
   * @param cell
   */
  @Override
  void changeState(Cell cell) {
    int state = cell.getState();
    //
    if(state == empty && cell.neighborsWithGivenState(filled)>0){
      cell.changeStateAndView(filled, stateColors[filled]);
    }
  }

  /**
   * Return a color given a certain state, used for setting up the initial board
   * @param state state to get the color of
   * @return color associated with state
   */
  @Override
  public Color getStateColor(int state) {
    return stateColors[state];
  }

}
