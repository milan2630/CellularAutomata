package cellsociety;

import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

  private int myState;
  private int turns_since_state_change;
  private List<Cell> neighbors;
  private Color myColor;



  public Cell(int init_state, Color disp_color) {
    super();
    myState = init_state;
    myColor = disp_color;
    setFill(disp_color);
    //setX(xPos);
    //setY(yPos);
  }

  public void changeStateAndView(int state, Color viewColor) {
    if(state == myState) {
      turns_since_state_change++;
    }
    else {
      turns_since_state_change=0;
      myState = state;
      myColor=viewColor;
    }
  }

  public int numberOfStateChanges(){
    return turns_since_state_change;
<<<<<<< HEAD
=======
  }

  public int getState(){
    return myState;
  }

  public void addNeighbor(Cell neighbor) {
    neighbors.add(neighbor);
>>>>>>> d5cac44516ca2dd187923f1e04d5e3966dae4460
  }

  public int getState(){
    return myState;
  }

}
