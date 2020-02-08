package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.paint.Color;

public class ForagingAnts extends Rules {
  private static final int NUMBER_OF_POSSIBLE_STATES = 4;
  private static final int OPEN = 0;
  private static final int ANT = 1;
  private static final int FOOD = 2;
  private static final int ANTHASFOOD = 3;
  private int pheromone =0;


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


  }

  private void AntFindFoodSource(Cell cell) {
    List<Cell> movableNeighbors = findFoodNeighborOptions(cell);
    if(movableNeighbors.size()!=0){
      Cell cellWithMaxPhermone = movableNeighbors.get(0);
      for(int j=0; j< movableNeighbors.size(); j++) {
        if (getPhermone(movableNeighbors.get(j)) > getPhermone(cellWithMaxPhermone)) {
          cellWithMaxPhermone = movableNeighbors.get(j);
        }
      }
      cellWithMaxPhermone.changeStateAndView(ANT);
      cell.changeStateAndView(OPEN);
    }
  }

  private List<Cell> findFoodNeighborOptions(Cell cell) {
    List<Cell> antNeighbors = cell.getNeighbors();
    List<Cell> movableNeighbors = new ArrayList<Cell>();
    for (int i = 0; i < antNeighbors.size(); i++) {
      Cell neighbor = antNeighbors.get(i);
      if ((neighbor.getRowNumber() > cell.getRowNumber() ^ neighbor.getColNumber() > cell
          .getColNumber()) && neighbor.getState() == OPEN) {
        movableNeighbors.add(neighbor);
      }
    }
    return movableNeighbors;
  }

  
  private int getPhermone(Cell cell){
    return pheromone;
  }

  @Override
  public boolean areCornersNeighbors() {
    return false;
  }

}
