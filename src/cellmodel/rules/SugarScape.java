package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;
import javafx.scene.paint.Color;

public class SugarScape extends Rules {

  @Override
  public void changeState(Cell cell, Cell cloneCell) {

  }

  @Override
  public boolean areCornersNeighbors() {
    return false;
  }
}
