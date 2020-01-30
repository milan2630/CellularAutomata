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
  }

  public int getState(){
    return myState;
  }

  public void addNeighbor(List<Cell> neighbor){
    neighbors.addAll(neighbor);
  }

}
