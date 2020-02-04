package cellsociety;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class Fire extends Rules {

    private static final Color BURNING_COLOR = Color.DARKRED;
    private static final Color TREE_COLOR = Color.GREEN;
    private static final Color EMPTY_COLOR = Color.YELLOW;
    private static final int BURNING = 0;
    private static final int TREE = 1;
    private static final int EMPTY = 2;
    private static final Color[] STATE_COLORS = {BURNING_COLOR, TREE_COLOR, EMPTY_COLOR};


  private float probCatch;

    /**
     * Initialize variables, get probability of a tree catching fire from setupParameters
     * @param setupParameters
     */
    public Fire(HashMap<String, String> setupParameters){
      probCatch = Float.parseFloat(setupParameters.get("probCatch"));
    }
    @Override
    /**
     * Given a cell, change its state and color based on its current status & neighbor status
     * @param cell cell to be updated
     */
    public void changeState(Cell cell, Cell cloneCell) {
      int state = cell.getState();

      if(state == TREE && cloneCell.numNeighborsWithGivenState(BURNING)>0 && treeBurns()) {
        cell.changeStateAndView(BURNING, STATE_COLORS[BURNING]);
      }
      if(state == BURNING){// && cell.numberOfStateChanges()>0){
        //aka it has been more than one round, change it
        cell.changeStateAndView(EMPTY, STATE_COLORS[EMPTY]);
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

    @Override
    /**
     * gets the color for a cell that is created with a certain state
     * so that the board can be created
     * @param state
     * @return color of the state, or if it's not a valid state white
     */
    public Color getStateColor(int state){
      if(state >=0 && state <=3)
        return STATE_COLORS[state];
      else return Color.WHITE;
    }
}
