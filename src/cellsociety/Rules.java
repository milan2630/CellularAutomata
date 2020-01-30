package cellsociety;

import javafx.scene.paint.Color;

abstract class Rules {

  abstract void changeState(Cell cell);
  abstract public Color getStateColor(int state);

}
