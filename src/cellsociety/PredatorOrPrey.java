package cellsociety;

import java.util.HashMap;
import java.util.List;
import javafx.scene.paint.Color;

public class PredatorOrPrey extends Rules {
  public static final Color WATER_COLOR = Color.BLUE;
  public static final Color FISH_COLOR = Color.GREEN;
  public static final Color SHARK_COLOR = Color.YELLOW;

  private Color[] stateColors;
  private int water;
  private int fish;
  private  int shark;
  private float probCatch;

  /**
   * Initialize variables, get probability of a tree catching fire from setupParameters
   * @param setupParameters
   */
  public PredatorOrPrey(HashMap<String, String> setupParameters){
    shark = 2;
    fish = 1;
    water = 0;
    stateColors = new Color[3];
    stateColors[shark] = SHARK_COLOR;
    stateColors[fish] = FISH_COLOR;
    stateColors[water] = WATER_COLOR;
    probCatch = Float.parseFloat(setupParameters.get("probCatch"));
  }
  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  public void changeState(Cell cell) {
    int state = cell.getState();
    if (state == shark && cell.neighborsWithGivenState(fish)>0) {
      List<Cell> fish_neighbors = cell.getNeighborOfState(fish);
      int random = (int) (Math.random() * fish_neighbors.size());
      Cell neighbor = fish_neighbors.get(random);
      neighbor.changeStateAndView(water, stateColors[water]);

    }




    }
    if (state==fish && cell.neighborsWithGivenState())

    if(state == tree && cell.neighborsWithGivenState(burning)>0 && treeBurns()) {
      cell.changeStateAndView(burning, stateColors[burning]);
    }
    if(state == burning){// && cell.numberOfStateChanges()>0){
      //aka it has been more than one round, change it
      cell.changeStateAndView(empty, stateColors[empty]);
    }
  }

  private boolean treeBurns(){
    return Math.random()<=probCatch;
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
      return stateColors[state];
    else return Color.WHITE;
  }
}
