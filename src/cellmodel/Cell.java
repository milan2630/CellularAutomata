package cellmodel;

import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

  private int myState;
  private int turnsSinceStateChange;
  private List<Cell> myNeighbors;
  private int myMoves;

  /**
   * constructor to create a cell takes in the number of columns, rows, and the rules
   *
   * @param initState  initial state of the cell
   * @param disp_color color displayed for the associated state
   * @param cellWidth  width of the cell
   * @param cellHeight height of the cell
   **/
  public Cell(int initState, Color disp_color, double cellWidth, double cellHeight) {
    super(cellWidth, cellHeight);
    myState = initState;
    setFill(disp_color);
    myNeighbors = new ArrayList<Cell>();
    myMoves = 0;
  }

  /**
   * constuctor to create a cell where state information is important, but nothing else used
   * specifically for clone cells
   *
   * @param initState state to set the cell to
   */
  public Cell(int initState) {
    this(initState, null, 0, 0); //TODO CHANGE THESE LATER?
  }

  /**
   * changes the state and color of the cell increments or resets turnsSinceStateChange takes in the
   * number of columns, rows, and the rules
   *
   * @param state     state that the cell is changing to
   * @param viewColor color that the cell is changing to
   **/
  public void changeStateAndView(int state, Color viewColor) {
    if (state == myState) {
      turnsSinceStateChange++;
    } else {
      turnsSinceStateChange = 0;
      myState = state;
      this.setFill(viewColor);
    }
  }

  public int numberOfStateChanges() {
    return turnsSinceStateChange;
  }

  public void setTurnsSinceStateChange(int val){
    turnsSinceStateChange =val;
  }
  /**
   * @return the state of the cell
   **/
  public int getState() {
    return myState;
  }

  /**
   * Add a neighbor to a cell
   *
   * @param neighbor cell to be added as a neighbor
   */
  public void addNeighbor(Cell neighbor) {
    myNeighbors.add(neighbor);
  }

  /**
   * @return a list of the neighbors of the cell
   **/
  public List<Cell> getNeighbors() {
    return myNeighbors;
  }

  /**
   * creates a list of neighbor cells that have a given state
   *
   * @param state the state that is wanted in the neighbors
   * @return the list of neighbors with the desired state
   **/
  public List<Cell> getNeighborsWithState(int state) {
    List<Cell> stateNeighbors = new ArrayList<Cell>();
    for (Cell neighbor : myNeighbors) {
      if (neighbor.getState() == state) {
        stateNeighbors.add(neighbor);
      }
    }
    return stateNeighbors;
  }

  /**
   * @return the number of neighbors that have the same state as the cell
   **/
  public int numNeighborsOfSameState() {
    int counter = 0;
    for (Cell neighbor : myNeighbors) {
      if (neighbor.getState() == myState) {
        counter++;
      }
    }
    return counter;
  }

  /**
   * creates a counter to keep track of the number of neighbors that have a given state
   *
   * @param state the state that is wanted in the neighbors
   * @return the number of neighbors with the desired state
   **/
  public int numNeighborsWithGivenState(int state) {
    int counter = 0;
    for (Cell neighbor : myNeighbors) {
      if (neighbor.getState() == state) {
        counter++;
      }
    }
    return counter;
  }

  /**
   * sets the state of the cell to a given state
   *
   * @param state state that the cell is being set to
   **/
  public void setState(int state) {
    myState = state;
  }

  /**
   * @return the number of moves that the cell has gone through
   **/
  public int getMoves() {
    return myMoves;
  }

  /**
   * sets the number of moves completed to a given integer
   *
   * @param moves the number of moves that the cell has gone through
   **/
  public void setMoves(int moves) {
    myMoves = moves;
  }

  @Override
  public boolean equals (Object obj){
    if (obj instanceof Cell){
      Cell cell = (Cell) obj;
      return cell.getX()==this.getX() && cell.getY()==this.getY();
    }
    else{
      return false;
    }
  }
}