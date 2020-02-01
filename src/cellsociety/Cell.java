package cellsociety;

import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

  private int myState;
  private int turns_since_state_change;
  private List<Cell> myNeighbors;
  private int myMoves;


  public Cell(int init_state, Color disp_color, double cellWidth, double cellHeight) {
    super(cellWidth, cellHeight);
    myState = init_state;
    setFill(disp_color);
    myNeighbors = new ArrayList<Cell>();
    myMoves=0;
  }

  public void changeStateAndView(int state, Color viewColor) {
    if (state == myState) {
      turns_since_state_change++;
    } else {
      turns_since_state_change = 0;
      myState = state;
      this.setFill(viewColor);
    }
  }

  public int numberOfStateChanges() {
    return turns_since_state_change;
  }

  public int getState() {
    return myState;
  }

  public void addNeighbor(Cell neighbor) {
    myNeighbors.add(neighbor);
  }

  public List<Cell> getNeighbors(){
    return myNeighbors;
  }

  public List<Cell> getNeighborsWithState(int state){
      List<Cell> state_neighbors = new ArrayList<Cell>();
      for(Cell neighbor : myNeighbors){
        if (neighbor.getState() == myState) {
          state_neighbors.add(neighbor);
        }
      }
    return state_neighbors;
  }

  public int numNeighborsOfSameState() {
    int counter = 0;
    for (Cell neighbor : myNeighbors) {
      if (neighbor.getState() == myState) {
        counter++;
      }
    }
    return counter;
  }

  public int numNeighborsWithGivenState(int state) {
    int counter = 0;
    for (Cell neighbor : myNeighbors) {
      if (neighbor.getState() == state) {
        counter++;
      }
    }
    return counter;
  }

  public void setState(int state){
    myState = state;
  }

  public int getMoves(){
    return myMoves;
  }

  public void setMoves(int moves){
    myMoves = moves;

  }
}
