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
  private Map<String, String> parameters;

  public Rules(Map<String, String> setupParameters){
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

  public int getNumberOfPossibleStates(){
    return numberOfPossibleStates;
  }

  protected int getRandomIndex(List<Cell> givenStateNeighbors) {
    int random = 0;
    if(givenStateNeighbors.size()!=1) {
      random = (int) (Math.random() * givenStateNeighbors.size());
    }
    return random;
  }

  public Map<String, String> getParameters(){
    return parameters;
  }

}
