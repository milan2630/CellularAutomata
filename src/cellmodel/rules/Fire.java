package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;

/**
 * Contains logic for Fire CA
 * Trees burn for 1 turn then turn empty
 * Adjacent cells to burning trees may catch fire with probability probCatch determined by xml file
 */
public class Fire extends Rules {

  private static final int BURNING = 0;
  private static final int TREE = 1;
  private static final int EMPTY = 2;

  private float probCatch;

    /**
     * Initialize variables, get probability of a tree catching fire from setupParameters
     * @param setupParameters
     */
    public Fire(HashMap<String, String> setupParameters){
      probCatch = Float.parseFloat(setupParameters.get("probCatch"));
      super.numberOfPossibleStates = 3;
    }
    @Override
    /**
     * Given a cell, change its state and color based on its current status & neighbor status
     * @param cell cell to be updated
     */
    public void changeState(Cell cell, Cell cloneCell) {
      int state = cell.getState();

      if(state == TREE && cloneCell.numNeighborsWithGivenState(BURNING)>0 && treeBurns()) {
        cell.changeStateAndView(BURNING);
      }
      if(state == BURNING){
        //aka it has been more than one round, change it
        cell.changeStateAndView(EMPTY);
      }
    }

    private boolean treeBurns(){
      return Math.random()<=probCatch;
    }

    @Override
    /**
     * Does this CA simulation count the corners as neighbors?
     * @return false; in Fire, it does not
     */
    public boolean areCornersNeighbors(){
      return false;
    }

}
