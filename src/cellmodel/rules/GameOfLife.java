package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;
import java.util.Map;

/**
 * Extends the Rules class, holds the rules for the Game of Life CA simulation
 * a cell becomes alive if it has three live neighbors.
 * if a cell has more than 3 or fewer than 2 alive neighbors, it dies.
 */
public class GameOfLife extends Rules {

  private static final int BIRTH_THRESHOLD = 3;
  private static final int NUMBER_OF_POSSIBLE_STATES = 2;
  private static final int ALIVE = 1;
  private static final int DEAD = 0;

  /**
   * Construct a GameOfLife object
   * @param setupParameters set the number of possible states
   */
  public GameOfLife(HashMap<String, String> setupParameters){
    super(setupParameters);
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
  }

  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   * @param cloneCell copy of the cell in the position as the cell
   */
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    int numNeighborsAlive = cloneCell.numNeighborsWithGivenState(ALIVE);

    if (state == DEAD && numNeighborsAlive == BIRTH_THRESHOLD){
      cell.changeStateAndView(ALIVE);
    } else if (state == ALIVE && (numNeighborsAlive <= 1 || numNeighborsAlive > BIRTH_THRESHOLD)){
      cell.changeStateAndView(DEAD);
    }
  }

  /**
   * return the list of state names to be used in the graph
   * @return stateNames
   */
  @Override
  public Map<Integer, String> getStateNames() {
    HashMap<Integer, String> stateNames = new HashMap<>();
    stateNames.put(ALIVE, "alive");
    stateNames.put(DEAD, "dead");
    return stateNames;
  }

  @Override
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return in Game of Life, it does
   */
  public boolean areCornersNeighbors(){
    return true;
  }

}
