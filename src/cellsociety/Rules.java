package cellsociety;

import javafx.scene.paint.Color;

abstract class Rules {

  abstract void changeState(Cell cell, Cell cloneCell);
  abstract boolean areCornersNeighbors();
  abstract Color getStateColor(int state);
}
