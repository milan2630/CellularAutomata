package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;

public class ForagingAnts extends Rules {

  @Override
  public void changeState(Cell cell, Cell cloneCell) {

  }

  @Override
  public boolean areCornersNeighbors() {
    return false;
  }
}
