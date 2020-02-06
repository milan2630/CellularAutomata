package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Rules class containing logic for PredatorOrPrey CA;
 * shark eats one fish if it's next to it;
 * fish and shark populate after a certain number of cycles
 * sharks die after a certain number of turns
 */
public class PredatorOrPrey extends Rules {

  private static final int WATER = 0;
  private static final int FISH = 1;
  private static final int SHARK = 2;
  private float fishBreed;
  private float sharkBreed;
  private float sharkDie;
  private HashSet<Cell> blacklist;

  /**
   * Initialize variables, get probability of various global parameters
   * get how many cycles it takes for a shark to die, for a shark to be born,
   * and for a fish to be born
   * @param setupParameters
   */
  public PredatorOrPrey(HashMap<String, String> setupParameters){
    fishBreed = Float.parseFloat(setupParameters.get("fishBreed"));
    sharkBreed = Float.parseFloat(setupParameters.get("sharkBreed"));
    sharkDie = Float.parseFloat(setupParameters.get("sharkDie"));
    blacklist = new HashSet<>();
    super.numberOfPossibleStates = 3;
  }

  @Override
  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   */
  public void changeState(Cell cell, Cell cloneCell) {
    if(blacklist.contains(cell)){
      blacklist.remove(cell);
    }
    else{
      int state = cell.getState();
      if(state == SHARK){
        sharkAct(cell);
      } else if(state == FISH){
        peacefulSwimmerAct(cell);
      }
    }
  }

  private void peacefulSwimmerAct(Cell herbivore) {
    if(herbivore.numNeighborsWithGivenState(WATER) > 0){
      determineMove(herbivore, herbivore.getNeighborsWithState(WATER));
    }
  }

  private void sharkAct(Cell shark){
    if(shark.numNeighborsWithGivenState(FISH) > 0){
      determineMove(shark, shark.getNeighborsWithState(FISH));
    } else {
      peacefulSwimmerAct(shark);
    }
  }

  private void determineMove(Cell mover, List<Cell> potentialCells){
    int random = getRandomIndex(potentialCells);
    moveIntoCell(mover, potentialCells.get(random));
  }

  private void moveIntoCell(Cell source, Cell target){
    if(source.getState() == SHARK){
      if(target.getState() == FISH){
        source.setMoves(0);
      } else {
        source.setMoves(source.getMoves()+1);
      }
    }
    if(target.getRowNumber() > source.getRowNumber()){
      blacklist.add(target);
    } else if(target.getRowNumber() == source.getRowNumber()){
      if(target.getColNumber() > source.getColNumber()){
        blacklist.add(target);
      }
    }
    target.changeStateAndView(source.getState());
    target.setMoves(source.getMoves());
    target.setTurnsSinceStateChange(source.getTurnsSinceStateChanges()+1);
    source.changeStateAndView(WATER);
    source.setMoves(0);

    checkSharkDeath(target);
    checkBirth(target);
  }

  //following two methods are
  private void checkBirth(Cell cell) {
    if((cell.getState() == SHARK && cell.getTurnsSinceStateChanges() > sharkBreed) || (cell.getState() == FISH && cell.getTurnsSinceStateChanges() > fishBreed)){
      createSwimmer(cell);
    }
  }

  private void createSwimmer(Cell swimmer) {
    Cell newSwimmer = new Cell(swimmer.getState(), swimmer.getRowNumber(), swimmer.getColNumber());
    newSwimmer.setTurnsSinceStateChange(-1);
    if(swimmer.numNeighborsWithGivenState(WATER)>0) {
      determineMove(newSwimmer, swimmer.getNeighborsWithState(WATER));
    }
    swimmer.setTurnsSinceStateChange(0);
  }

  private void checkSharkDeath(Cell shark) {
    if(shark.getState() == SHARK && shark.getMoves() > sharkDie){
      organismGone(shark);
    }
  }

  private void organismGone(Cell cell) {
    cell.changeStateAndView(WATER);
  }

  @Override
  /**
   * returns whether or not a corner of a cell is a neighbor
   * @return true; in predator or prey, they are
   */
  public boolean areCornersNeighbors(){
    return true;
  }

}