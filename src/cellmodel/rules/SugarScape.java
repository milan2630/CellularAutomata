package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SugarScape extends Rules {

  private static final int NUMBER_OF_POSSIBLE_STATES = 3;
  private static final int SUGAR1 = 0;
  private static final int SUGAR2 = 1;
  private static final int SUGAR3 = 2;
  private static final int AGENT = 3;
  private static final int maxSugar = 9;
  private int vision;
  private static final int maxVision = 3;
  private int initSugar = 10;
  private static final int sugarMetabolism = 2;
  private HashSet<Cell> blocked = new HashSet<>();


  public SugarScape(HashMap<String, String> setupParameters) {
    super(setupParameters);
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
  }

  @Override
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if (state == SUGAR1) {
      addSugar(cell, SUGAR2);
    }
    if (state == SUGAR2) {
      if (cell.getMoves() == 0) {
        cell.setMoves(3);
      }
      addSugar(cell, SUGAR3);
    }
    if (state == SUGAR3) {
      if (cell.getMoves() == 0) {
        cell.setMoves(6);
      }
      if (cell.getMoves() < maxSugar) {
        cell.setMoves(cell.getMoves() + 1);
      }
    }
    if (state == AGENT) {
      vision = maxVision;
      if (cell.getMoves() == 0) {
        cell.setMoves(initSugar);
      }
      eatSugar(cell, cloneCell);
    }
  }

  private void addSugar(Cell cell, int sugarAmount) {
    cell.setMoves(cell.getMoves() + 1);
    if (cell.getMoves() % 3 == 0) {
      cell.changeStateAndView(sugarAmount);
    }
  }

  private void eatSugar(Cell cell, Cell cloneCell) {
    List<Cell> agentNeighbors = cell.getNeighbors();
    Cell cellwithMaxSugar = agentNeighbors.get(0);
    blocked.add(cell);
    cellwithMaxSugar = Max(cell.getNeighbors(), cellwithMaxSugar, cell);
    cellwithMaxSugar.setMoves(cellwithMaxSugar.getMoves() + cell.getMoves() - sugarMetabolism);
    if (cellwithMaxSugar.getMoves() <= 0) {
      cellwithMaxSugar.changeStateAndView(SUGAR1);
    } else {
      cellwithMaxSugar.changeStateAndView(AGENT);
    }
    cell.changeStateAndView(SUGAR1);
  }

/*  private Cell findMaxSugar(Cell cell){
    HashSet<Cell> listofneighbors = new HashSet<>();
    int counter = 4;
    listofneighbors.addAll(cell.getNeighbors());
    for(Cell neighbor : cell.getNeighbors()){
      if(counter!=0) {
        findMaxSugar(neighbor);
      }
    }

  }*/


  private Cell Max(List<Cell> agentNeighbors, Cell cellwithMaxSugar, Cell cell) {
    for (Cell neighbor : agentNeighbors) {
      if (!blocked.contains(neighbor)) {
        blocked.add(neighbor);
        if (neighbor.getMoves() > cellwithMaxSugar.getMoves() && (
            neighbor.getRowNumber() != cell.getRowNumber() || neighbor.getColNumber() != cell
                .getColNumber())) {
          cellwithMaxSugar = neighbor;
        }
        if (neighbor.getColNumber() == cell.getColNumber() && neighbor.getRowNumber() != cell
            .getRowNumber() && !blocked.contains(neighbor)) {
          vision--;
        }
      }
      System.out.println(vision);
      if (vision >= 0) {
        for (Cell neighbortwo : agentNeighbors) {
          cellwithMaxSugar = Max(neighbortwo.getNeighbors(), cellwithMaxSugar, cell);
        }
      }
    }
    return cellwithMaxSugar;
  }

  @Override
  public boolean areCornersNeighbors() {
    return false;
  }
}
