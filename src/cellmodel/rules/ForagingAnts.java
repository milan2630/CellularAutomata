package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.ArrayList;
import java.util.List;

public class ForagingAnts extends Rules {
  private static final int NUMBER_OF_POSSIBLE_STATES = 4;
  private static final int OPEN = 0;
  private static final int ANT = 1;
  private static final int FOOD = 2;
  private static final int ANTHASFOOD = 3;
  private static final int pheromoneLookingForFood =1;
  private static final int pheromoneReturningFromFood =3;


  @Override
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if(state == ANTHASFOOD) {
      AntReturnToNest(cell);
    }
    else{
      AntFindFoodSource(cell);
    }
  }

  private void AntReturnToNest(Cell cell){
    List<Cell> movableNeighbors = findNeighborOptions(cell, true);
    Cell cellWithMaxPheromone = findMaxPheromone(movableNeighbors);
    if(cellWithMaxPheromone!=null) {
      cellWithMaxPheromone.changeStateAndView(ANTHASFOOD);
      cell.changeStateAndView(OPEN);
      setPheromone(cell, getPheromone(cell) + pheromoneReturningFromFood);
    }
  }

  private void AntFindFoodSource(Cell cell) {
    if (cell.getNeighborsWithState(FOOD).size()>0){
      antEatsFood(cell);
    }
    else{
      antMovesTowardFood(cell);
    }
  }

  private void antEatsFood(Cell cell){
    List <Cell> foodNeighbors = cell.getNeighborsWithState(FOOD);
    int random = getRandomIndex(foodNeighbors);
    Cell food = foodNeighbors.get(random);
    food.changeStateAndView(ANTHASFOOD);
    cell.changeStateAndView(OPEN);
    setPheromone(cell, getPheromone(cell) + pheromoneLookingForFood);
  }

  private void antMovesTowardFood(Cell cell) {
    List<Cell> movableNeighbors = findNeighborOptions(cell, false);
    Cell cellWithMaxPheromone = findMaxPheromone(movableNeighbors);
    if (cellWithMaxPheromone != null) {
      cellWithMaxPheromone.changeStateAndView(ANT);
      cell.changeStateAndView(OPEN);
      setPheromone(cell, getPheromone(cell) + pheromoneLookingForFood);
    }
  }

  private List<Cell> findNeighborOptions(Cell cell, Boolean returning){
    List<Cell> antNeighbors = cell.getNeighbors();
    List<Cell> movableNeighbors = new ArrayList<Cell>();
    for (int i = 0; i < antNeighbors.size(); i++) {
      Cell neighbor = antNeighbors.get(i);
      boolean validNeighbor;
      if(returning){
        validNeighbor = (neighbor.getRowNumber() < cell.getRowNumber() ^ neighbor.getColNumber() < cell.getColNumber());
      }
      else{
        validNeighbor = (neighbor.getRowNumber() > cell.getRowNumber() ^ neighbor.getColNumber() > cell.getColNumber());
      }
      if (validNeighbor && neighbor.getState() == OPEN) {
        movableNeighbors.add(neighbor);
      }
    }
    return movableNeighbors;
  }

  private Cell findMaxPheromone(List<Cell> movableNeighbors) {
    Cell cellWithMaxPheromone= null;
    if(movableNeighbors.size()!=0){
      cellWithMaxPheromone = movableNeighbors.get(0);
      for(int j=0; j< movableNeighbors.size(); j++) {
        if (getPheromone(movableNeighbors.get(j)) > getPheromone(cellWithMaxPheromone)) {
          cellWithMaxPheromone = movableNeighbors.get(j);
        }
      }
    }
    return cellWithMaxPheromone;
  }


  private int getPheromone(Cell cell){
    return cell.getMoves();
  }

  private void setPheromone(Cell cell, int newPheromone){
    cell.setMoves(newPheromone);
  }

  @Override
  public boolean areCornersNeighbors() {
    return false;
  }
}

