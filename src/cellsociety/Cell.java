package cellsociety;

import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

  private int myState;
  private int turns_since_state_change;
  private List<Cell> neighbors;
  private Color state_color;


  public Cell(int init_state, Color disp_color) {
    super();
    myState = init_state;
    state_color = disp_color;
    setFill(disp_color);
    //setX(xPos);
    //setY(yPos);
  }

  public void changeStateAndView(int state) {
    myState = state;
  }
}
