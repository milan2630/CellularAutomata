package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains the logic for the Percolation CA
 * Water fills open adjacent cells
 */
public class Percolation extends Rules {

  private static final int NUMBER_OF_POSSIBLE_STATES = 3;
  private static final int OPEN = 1;
  private static final int BLOCKED = 2;
  private static final int FILLED = 0;

  /**
   * Initialize variables, set colors to their respective states
   * @param setupParameters has no set up parameters
   */
  public Percolation(HashMap<String, String> setupParameters){
    super(setupParameters);
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
  }

  @Override
  /**
   * given a certain cell, change its state based on percolation rules
   *  filled -> filled
   *  open -> open if no filled neighbors
   *  eopn -> filled if filled neighbors
   *  blocked -> blocked/open depending on response to piazza post
   * @param cell cell to be updated
   * @param cloneCell copy of the cell in the position as the cell
   **/
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if(state == OPEN && cloneCell.numNeighborsWithGivenState(FILLED)>0){
      cell.changeStateAndView(FILLED);
    }
  }

  /**
   * return the list of state names to be used in the graph
   * @return
   */
  @Override
  public Map<Integer, String> getStateNames() {
    HashMap<Integer, String> stateNames = new HashMap<>();
    stateNames.put(FILLED, "filled");
    stateNames.put(OPEN, "open");
    stateNames.put(BLOCKED, "blocked");
    return stateNames;
  }

  @Override
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return in Percolation, it does not
   */
  public boolean areCornersNeighbors(){
    return false;
  }
}
