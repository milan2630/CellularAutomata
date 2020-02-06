package cellmodel.rules;

import cellmodel.celltype.Cell;
import javafx.scene.paint.Color;

/**
 * abstract class; easy to add another CA simulation from this class
 */
abstract public class Rules {

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
   * @param state
   * @return the color associated with a given state for this simulation type
   */
  abstract public Color getStateColor(int state);
}
