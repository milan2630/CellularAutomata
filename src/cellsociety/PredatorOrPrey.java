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
  private float fishBreed;
  private float sharkBreed;
  private float sharkDie;

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
    fishBreed = Float.parseFloat(setupParameters.get("fishBreed"));
    sharkBreed = Float.parseFloat(setupParameters.get("sharkBreed"));
    sharkDie = Float.parseFloat(setupParameters.get("sharkDie"));

  }
//change the state and put that cell in the previous cells spot

  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if (state == shark && cloneCell.numNeighborsWithGivenState(fish)>0) {
      sharkEatsFish(cell, cloneCell);
    }
    else if (state == shark && 0<cloneCell.numNeighborsWithGivenState(water)){
      mover(cell, cloneCell);
    }
    else if (state == fish && cloneCell.numNeighborsWithGivenState(shark)==0 && cloneCell.numNeighborsWithGivenState(water)>0){
      mover(cell, cloneCell);
    }
    if (state == shark && numMovesSinceEaten(cell)>=sharkDie){
      cell.changeStateAndView(water, stateColors[water]);
    }
    if ((state == fish && numMoves(cell)%fishBreed==0)){
      findWaterCellAndCreateOrganism(cell, cloneCell);

    }
    if ((state == shark && numMoves(cell)%sharkBreed==0)){
      findWaterCellAndCreateOrganism(cell, cloneCell);
    }
  }

  private void sharkEatsFish(Cell cell, Cell cloneCell) {
    List<Cell> fish_neighbors = cloneCell.getNeighborsWithState(fish);
    int random = getRandomIndex(fish_neighbors);
    Cell fishEaten = cell.getNeighbors().get(random);
    fishEaten.changeStateAndView(water, stateColors[water]);
    //cell.setMoves(cell.getMoves() +10);
    cell.setMoves(numMoves(cell));
    moveOtherFish(cell, cloneCell, fishEaten);
  }

  private int getRandomIndex(List<Cell> given_state_neighbors) {
    int random =1;
    if(given_state_neighbors.size()!=1) {
      random = (int) (Math.random() * given_state_neighbors.size());
    }
    return random;
  }

  private void moveOtherFish(Cell cell, Cell cloneCell, Cell fishEaten) {
    List<Cell> fishNeighbors = cell.getNeighbors();
    List<Cell> cloneFishNeighbors = cloneCell.getNeighborsWithState(fish);
    for (int movingFish = 0; movingFish < fishNeighbors.size(); movingFish++) {
      Cell fishNotEaten = fishNeighbors.get(movingFish);
      Cell cloneFishNotEaten = null;
      for (int wantFish=0; wantFish< cloneFishNeighbors.size(); wantFish++){
        if (cloneFishNeighbors.get(wantFish).equals(fishNotEaten)){
          cloneFishNotEaten = cloneFishNeighbors.get(wantFish);
        }
      }
      if (!fishNotEaten.equals(fishEaten)) {
        mover(fishNotEaten, cloneFishNotEaten);
      }
    }
  }

  private void mover(Cell cell, Cell cloneCell) {
    List<Cell> water_neighbors = cloneCell.getNeighborsWithState(water);
    int random = getRandomIndex(water_neighbors);
    Cell neighbor = cell.getNeighbors().get(random);
    neighbor.changeStateAndView(cell.getState(), stateColors[cell.getState()]);
    neighbor.setMoves(neighbor.getMoves()+11);
    cell.changeStateAndView(water, stateColors[water]);
  }

  private int numMovesSinceEaten(Cell cell){
   return cell.getMoves()/10;
  }
  private int numMoves(Cell cell){
    return cell.getMoves()%10;
  }


  private void findWaterCellAndCreateOrganism(Cell cell, Cell cloneCell) {
    for (int i=0; i< cell.getNeighbors().size(); i++){
      List<Cell> cloneNeighborsList = cloneCell.getNeighbors();
      List<Cell> cellNeighborsList = cell.getNeighbors();
      Cell cloneNeighbor = cloneNeighborsList.get(i);
      Cell cellNeighbor = cellNeighborsList.get(i);
      if (cloneNeighbor.getState()==water) {
        cellNeighbor.changeStateAndView(cell.getState(), stateColors[cell.getState()]);
      }
      else {
        findWaterCellAndCreateOrganism(cellNeighbor, cloneNeighbor);
      }
    }
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
