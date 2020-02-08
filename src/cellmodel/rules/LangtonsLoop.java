package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;

public class LangtonsLoop extends Rules {

  @Override
  public void changeState(Cell cell, Cell cloneCell) {

  }

  @Override
  public boolean areCornersNeighbors() {
    return false;
  }
}
