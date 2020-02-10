package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;
import java.util.List;

public class LangtonsLoop extends Rules {
  private static final int NUMBER_OF_POSSIBLE_STATES = 5;
  private static final int DEAD = 0;
  private static final int WIRE = 1;
  private static final int OPEN = 2;
  private static final int EXTENDER = 3;
  private static final int TURNER = 4;

  public LangtonsLoop(HashMap<String, String> setupParameters) {
    super(setupParameters);
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
  }

  @Override
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cloneCell.getState();
    if (state == EXTENDER || state == TURNER) {
      mover(cell, cloneCell);
    }
  }

  private void mover(Cell cell, Cell cloneCell){
    List<Cell> openNeighbor = cell.getNeighborsWithState(OPEN);
    List<Cell> deadNeighbor = cell.getNeighborsWithState(DEAD);
    openNeighbor.get(0).changeStateAndView(cell.getState());
    cell.changeStateAndView(DEAD);
    deadNeighbor.get(0).changeStateAndView(OPEN);
  }

  private void moveExtender(Cell cell, Cell clonecCell){
    List<Cell> wireNeighborsList = cell.getNeighborsWithState(WIRE);
    for(int i=0; i< wireNeighborsList.size(); i++){
      if(Math.abs(wireNeighborsList.get(i).getRowNumber()-cell.getRowNumber())==1){
        moveLeft(cell, clonecCell);
        break;
      }
    }
  }

  private void moveLeft(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    Cell cellLeft = getCellLeft(cell);
    if (cellLeft!=null){
      System.out.println(cellLeft);
    }
    if (cellLeft.getState() == WIRE) {
      Cell leftOfNeighbor = getCellLeft(cellLeft);
      leftOfNeighbor.changeStateAndView(WIRE);
    }
    cell.changeStateAndView(OPEN);
    System.out.println(cell.getState());
    cellLeft.changeStateAndView(state);
    System.out.println(cellLeft.getState());
    addWire(cellLeft);
  }

  private Cell getCellLeft(Cell cell) {
    List<Cell> neighborsList = cell.getNeighbors();
    Cell cellLeft = null;
    for (Cell neighbor : neighborsList) {
      if (neighbor.getColNumber() == cell.getColNumber() - 1) {
        cellLeft=neighbor;
      }
    }
    return cellLeft;
  }

  private void addWire(Cell cell){
    List<Cell> neighborsList = cell.getNeighbors();
    for (Cell neighbor : neighborsList) {
      if(Math.abs(neighbor.getRowNumber()-cell.getRowNumber())==1){
        neighbor.changeStateAndView(WIRE);
      }
    }
  }

  @Override
  public boolean areCornersNeighbors() {
    return false;
  }
}