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
  private static int swimMoves = 1;
  private static int noFoodMoves = 10;

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
     System.out.println(cell);
    System.out.println(cell.getMoves());
    int state = cell.getState();
    if (state == shark && cloneCell.numNeighborsWithGivenState(fish)>0) {
      sharkEatsFish(cell, cloneCell);
    }
    else if (state == shark && 0<cell.numNeighborsWithGivenState(water)){
      mover(cell);
    }
    else if (state == fish && cell.numNeighborsWithGivenState(shark)==0 && cell.numNeighborsWithGivenState(water)>0){
      mover(cell);
    }
    /*if (state == shark && numMovesSinceEaten(cell)>=sharkDie){
      organismGone(cell);
      cell.setMoves(0);
    }*/
    /*if ((state == fish && numMoves(cell)%fishBreed==0 && numMoves(cell)!=0)){
      findWaterCellAndCreateOrganism(cell, state);
      System.out.println("fish");

    }
    if ((state == shark && numMoves(cell)%sharkBreed==0 && numMoves(cell)!=0)){
      System.out.println("shark");
      findWaterCellAndCreateOrganism(cell, state);
    }*/
  }

  private void organismGone(Cell cell) {
    cell.changeStateAndView(water, stateColors[water]);
  }

  private void sharkEatsFish(Cell cell, Cell cloneCell) {
    System.out.println("hi");
    List<Cell> fishNeighborsList = cell.getNeighborsWithState(fish);
    List<Cell> cellNeighborsList = cell.getNeighbors();
    int random = getRandomIndex(fishNeighborsList);
    Cell fishEaten = fishNeighborsList.get(random);
    /*Cell fishEaten = null;
    for (int wantedNeighbor=0; wantedNeighbor< cellNeighborsList.size(); wantedNeighbor++) {
      if (cellNeighborsList.get(wantedNeighbor).equals(fishNeighbor)) {
        fishEaten = cellNeighborsList.get(wantedNeighbor);
      }
    }*/
    if(fishEaten!=null){
      organismGone(fishEaten);
      cell.setMoves(numMoves(cell));
      moveOtherFish(cell, cloneCell, fishEaten);
    }

  }

  private int getRandomIndex(List<Cell> givenStateNeighbors) {
    int random = 0;
    if(givenStateNeighbors.size()!=1) {
      random = (int) (Math.random() * givenStateNeighbors.size());
    }
    return random;
  }

  private void moveOtherFish(Cell cell, Cell cloneCell, Cell fishEaten) {
    List<Cell> fishNeighbors = cell.getNeighborsWithState(fish);
    //List<Cell> cloneFishNeighbors = cloneCell.getNeighborsWithState(fish);
    Cell fishNotEaten = null;
    for (int movingFish = 0; movingFish < fishNeighbors.size(); movingFish++) {
      if (!fishNeighbors.get(movingFish).equals(fishEaten))
      fishNotEaten = fishNeighbors.get(movingFish);
      /*Cell cloneFishNotEaten = null;
      for (int wantedFish=0; wantedFish< cloneFishNeighbors.size(); wantedFish++){
        if (cloneFishNeighbors.get(wantedFish).equals(fishNotEaten)){
          cloneFishNotEaten = cloneFishNeighbors.get(wantedFish);
        }
      }*/
      if (fishNotEaten!= null) {
        mover(fishNotEaten);
      }
    }
  }

  private void mover(Cell cell) {
    List<Cell> waterNeighborsList = cell.getNeighborsWithState(water);
    List<Cell> cellNeighborsList = cell.getNeighbors();
    int random = getRandomIndex(waterNeighborsList);
    Cell waterNeighbor = waterNeighborsList.get(random);
    //Cell neighbor = null;
    /*for (int wantedNeighbor = 0; wantedNeighbor < cellNeighborsList.size(); wantedNeighbor++) {
      if (cellNeighborsList.get(wantedNeighbor).equals(waterNeighbor)) {
        neighbor = cellNeighborsList.get(wantedNeighbor);
      }
    }*/
    if (waterNeighbor != null) {
      waterNeighbor.changeStateAndView(cell.getState(), stateColors[cell.getState()]);
      //neighbor.setMoves(cell.getMoves());
      waterNeighbor.setMoves(cell.getMoves() + swimMoves);
      cell.setMoves(0);
      if (cell.getState() == shark) {
        waterNeighbor.setMoves(waterNeighbor.getMoves() + noFoodMoves);
      }
      organismGone(cell);

      if (waterNeighbor.getState() == shark && numMovesSinceEaten(waterNeighbor) >= sharkDie) {
        organismGone(waterNeighbor);
        waterNeighbor.setMoves(0);
      }
      if ((waterNeighbor.getState() == fish && numMoves(waterNeighbor) % fishBreed == 0
          && numMoves(waterNeighbor) != 0)) {
        findWaterCellAndCreateOrganism(waterNeighbor, waterNeighbor.getState());
        System.out.println("fish");
        System.out.println(waterNeighbor.getMoves());
      }
      if ((waterNeighbor.getState() == shark && numMoves(waterNeighbor) % sharkBreed == 0
          && numMoves(waterNeighbor) != 0)) {
        System.out.println("shark");
        findWaterCellAndCreateOrganism(waterNeighbor, waterNeighbor.getState());
      }
    }
  }

  private int numMovesSinceEaten(Cell cell){
   return cell.getMoves()/10;
  }
  private int numMoves(Cell cell){
    return cell.getMoves()%10;
  }


/*  private void findWaterCellAndCreateOrganism(Cell cell, Cell cloneCell) {
    List<Cell> cloneNeighborsList = cloneCell.getNeighbors();
    List<Cell> cellNeighborsList = cell.getNeighbors();
    for (int i=0; i< cellNeighborsList.size(); i++){
      Cell cloneNeighbor = cloneNeighborsList.get(i);
      Cell cellNeighbor = cellNeighborsList.get(i);
      if (cloneNeighbor.getState()==water) {
        cellNeighbor.changeStateAndView(cell.getState(), stateColors[cell.getState()]);
      }
      else {
        findWaterCellAndCreateOrganism(cellNeighbor, cloneNeighbor);
      }
    }
  }*/
private void findWaterCellAndCreateOrganism(Cell cell, int state) {
  List<Cell> cellNeighborsList = cell.getNeighbors();
  List<Cell> waterNeighborsList = cell.getNeighborsWithState(water);
  Cell waterNeighbor = null;
  if (waterNeighborsList.size() != 0) {
    int random = getRandomIndex(waterNeighborsList);
    waterNeighbor = waterNeighborsList.get(random);
    for (Cell neighbor : cellNeighborsList) {
      if (neighbor.equals(waterNeighbor)) {
        System.out.println("reached");
        //cellNeighbor = neighbor;
        neighbor.changeStateAndView(state, stateColors[state]);
        return;
      }
    }
  }
  if (waterNeighborsList.size() == 0) {
    int random2 = getRandomIndex(cellNeighborsList);
    Cell cellNeighbor = cellNeighborsList.get(random2);
    findWaterCellAndCreateOrganism(cellNeighbor, state);
  }
}
  @Override
  /**
   * returns whether or not a corner of a cell is a neighbor
   * @return true; in predator or prey, they are
   */
  public  boolean areCornersNeighbors(){
    return true;
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