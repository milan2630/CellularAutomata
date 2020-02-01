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
  private int shark;
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
    //probCatch = Float.parseFloat(setupParameters.get("probCatch"));
  }
//change the state and put that cell in the previous cells spot
  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  public void changeState(Cell cell) {
    int state = cell.getState();
    if (state == shark && cell.numNeighborsWithGivenState(fish)>0) {
      sharkEatsFish(cell);
    }
    else if (state == shark && 0<cell.numNeighborsWithGivenState(water)){
      mover(cell, shark);
    }
    else if (state == fish && cell.numNeighborsWithGivenState(shark)==0 && cell.numNeighborsWithGivenState(water)>0){
      mover(cell, fish);
    }
  }

  private void sharkEatsFish(Cell cell) {
    List<Cell> fish_neighbors = cell.getNeighborsWithState(fish);
    int random = getRandomIndex(fish_neighbors);
    Cell fishEaten = fish_neighbors.get(random);
    fishEaten.changeStateAndView(water, stateColors[water]);
    moveOtherFish(fish_neighbors, fishEaten);
  }

  private int getRandomIndex(List<Cell> given_state_neighbors) {
    int random =1;
    if(given_state_neighbors.size()!=1) {
      random = (int) (Math.random() * given_state_neighbors.size());
    }
    return random;
  }

  private void moveOtherFish(List<Cell> fish_neighbors, Cell fishEaten) {
    for (Cell fishNotEaten : fish_neighbors){
      if (! fishNotEaten.equals(fishEaten)){
        mover(fishNotEaten, fish);
      }
    }
  }

  private void mover(Cell cell, int state) {
    List<Cell> water_neighbors = cell.getNeighborsWithState(water);
    int random = getRandomIndex(water_neighbors);
    Cell neighbor = water_neighbors.get(random);
    neighbor.changeStateAndView(state, stateColors[state]);
    cell.changeStateAndView(water, stateColors[water]);
  }

/*  private boolean treeBurns(){
    return Math.random()<=probCatch;
  }*/

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
