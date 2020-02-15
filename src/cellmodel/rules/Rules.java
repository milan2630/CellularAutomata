package cellmodel.rules;

import cellmodel.celltype.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * abstract class; easy to add another CA simulation from this class
 */
abstract public class Rules {

  protected int numberOfPossibleStates;
  private HashMap<String, String> parameters;

  public Rules(HashMap<String, String> setupParameters){
    parameters =setupParameters;
  }

  /**
   * Change the state of a cell to the next one based on its state and the states of its neighbors
   * @param cell the cell to change
   * @param cloneCell the cell's clone in the clone board
   */
  abstract public void changeState(Cell cell, Cell cloneCell);

  /**
   * @return whether corners are considered neighbors in this simulation type
   */
  abstract public boolean areCornersNeighbors();


  /**
   * @return the number of possible states for a given rules
   */
  public int getNumberOfPossibleStates(){
    return numberOfPossibleStates;
  }

  /**
   * @param cellList a lit of cells
   * @return the a random index of a given list of cells
   */
  protected int getRandomIndex(List<Cell> cellList) {
    int random = 0;
    if(cellList.size()!=1) {
      random = (int) (Math.random() * cellList.size());
    }
    return random;
  }

  abstract public Map<Integer, String> getStateNames();

  /**
   * @return the parameters for rules
   */
  public HashMap<String, String> getParameters(){
    return parameters;
  }
}
