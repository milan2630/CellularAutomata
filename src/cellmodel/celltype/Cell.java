package cellmodel.celltype;

import java.util.*;

/**
 * creates a cell object that extends rectangle, has an associated state, a list of neighbors, and a number of moves
 **/
public class Cell{

  private int myState;
  private int turnsSinceStateChange;
  private List<Cell> myNeighbors;
 // private Type shape;
  private int myMoves;
  private int rowNumber;
  private int colNumber;

  /**
   * constructor to create a cell takes in the number of columns, rows, and the rules
   * @param initState  initial state of the cell
   *
   **/
  public Cell(int initState, int row, int col) {
    myState = initState;
    myNeighbors = new ArrayList<Cell>();
    myMoves = 0;
    rowNumber = row;
    colNumber = col;
  }

  /**
   * changes the state and color of the cell increments or resets turnsSinceStateChange takes in the
   * number of columns, rows, and the rules
   * @param state     state that the cell is changing to
   **/
  public void changeStateAndView(int state) {
    if (state == myState) {
      turnsSinceStateChange++;
    } else {
      turnsSinceStateChange = 0;
      myState = state;
    }
  }
  /**
   * @return the number of times the board has changed since the state of this cell changed
   **/
  public int getTurnsSinceStateChanges() {
    return turnsSinceStateChange;
  }

  /**
   * sets the number of times the board has changed since the state of this cell changed to a given value
   **/
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
   * get the row the cell is in
   * @return rowNumber
   */
  public int getRowNumber(){
    return rowNumber;
  }

  /**
   * get the column the cell is in
   * @return colNumber
   */
  public int getColNumber(){
    return colNumber;
  }

  /**
   * Add a neighbor to a cell
   * @param neighbor cell to be added as a neighbor
   */
  public void addNeighbor(Cell neighbor) {
    myNeighbors.add(neighbor);
  }

  public void removeNeighbor(Cell neighbor){
    myNeighbors.remove(neighbor);
  }

  /**
   * @return a list of the neighbors of the cell
   **/
  public List<Cell> getNeighbors() {
    return myNeighbors;
  }

  public void setNeighbors(List<Cell> neighbors){
    for(int i =0; i<neighbors.size(); i++){

    }
    myNeighbors = new ArrayList<>();
    myNeighbors= neighbors;
  }

  /**
   * creates a list of neighbor cells that have a given state
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
}