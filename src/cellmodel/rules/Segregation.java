package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Moves red and blue cells into white cells until the red and blue cells are surrounded by a given percentage of neighbors that are the same color as themselves
 **/
public class Segregation extends Rules {

  private static final int RED = 2;
  private static final int BLUE = 1;
  private static final int EMPTY = 0;
  private static final int NUMBER_OF_POSSIBLE_STATES = 3;

  private float percentSatisfied;

  /**
   * Initialize variables, get the percent of neighbors of the same state needed to satisfy a cell
   * @param setupParameters
   */
  public Segregation(HashMap<String, String> setupParameters){
    percentSatisfied = Float.parseFloat(setupParameters.get("percentSatisfied"));
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
  }

  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  public void changeState(Cell cell, Cell cloneCell) {
    if(cloneCell.getState()!= EMPTY && (cloneCell.numNeighborsOfSameState() < (percentSatisfied*getNotEmptyNeighbors(cloneCell).size()))) {
      findAndMoveToEmptyCell(cell, cell.getState());
      cell.changeStateAndView(EMPTY);
    }
  }

  private List<Cell> getNotEmptyNeighbors(Cell cell) {
    List<Cell> notEmptyNeighbors = new ArrayList<>();
    for (Cell neighbor : cell.getNeighbors()) {
      if (neighbor.getState() != EMPTY) {
        notEmptyNeighbors.add(neighbor);
      }
    }
    return notEmptyNeighbors;
  }

  private void findAndMoveToEmptyCell(Cell cell, int state) {
    List<Cell> cellNeighborsList = cell.getNeighbors();
    List<Cell> emptyNeighborsList = cell.getNeighborsWithState(EMPTY);
    Cell emptyNeighbor;
    if (emptyNeighborsList.size() != 0) {
      int random = getRandomIndex(emptyNeighborsList);
      emptyNeighbor = emptyNeighborsList.get(random);
      for (Cell neighbor : cellNeighborsList) {
          if (neighbor.equals(emptyNeighbor)) {
          neighbor.changeStateAndView(state);
          return;
        }
      }
    }
    if (emptyNeighborsList.size() == 0) {
      int random2 = getRandomIndex(cellNeighborsList);
      Cell cellNeighbor = cellNeighborsList.get(random2);
      findAndMoveToEmptyCell(cellNeighbor,  state);
    }
  }

  @Override
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return true; in Segregation, it does
   */
  public boolean areCornersNeighbors(){
    return true;
  }

}
