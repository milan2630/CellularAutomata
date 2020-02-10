package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;

/**
 * Extends the Rules class, holds the rules for the Game of Life CA simulation
 * a cell becomes alive if it has three live neighbors.
 * if a cell has more than 3 or fewer than 2 alive neighbors, it dies.
 */
public class GameOfLife extends Rules {

  private static final int BIRTH_THRESHOLD = 3;
  private static final int NUMBER_OF_POSSIBLE_STATES = 3;
  private static final int ALIVE = 1;
  private static final int DEAD = 0;

  /**
   * Construct a GameOfLife object
   * @param setupParameters has no setup parameters for Game of Life
   */
  public GameOfLife(HashMap<String, String> setupParameters){
    super(setupParameters);
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
  }

  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
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

  @Override
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return in Game of Life, it does
   */
  public boolean areCornersNeighbors(){
    return true;
  }

}
