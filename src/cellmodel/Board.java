package cellmodel;

import cellmodel.celltype.Cell;
import cellmodel.rules.Rules;
import java.util.ArrayList;
import java.util.List;

/**
 * creates a cloneable board object that establishes the positions of each cell, updates the states of the cells based on the rules, and determines the neighbors of the cells
 **/
public class Board{

  protected static final int ONE_AWAY = 1;
  protected static final int TWO_AWAY = 2;

  private Cell[][] myCells;
  private Cell[][] cloneCells;
  private int myRows;
  private int myCols;
  private Rules myRules;
  private boolean buildingInitialBoard;
  private int myNeighborhood;
  private static final int FINITE = 0;
  private static final int TORODIAL =1;



  /**
   * constructor to create a board
   * takes in the number of columns, rows, and the rules
   * @param numCols number of columns on the board
   * @param numRows number of rows on the board
   * @param rules rules of the simulation
   * @param neighborhoodType
   **/
  public Board(int numCols, int numRows, Rules rules, int neighborhoodType) {
    myRules = rules;
    myCells = new Cell[numRows][numCols];
    cloneCells = new Cell[numRows][numCols];
    myRows = numRows;
    myCols = numCols;
    buildingInitialBoard = true;
    buildBoard(myCells);
    buildingInitialBoard = false;
    myNeighborhood= neighborhoodType;

  }

  private void buildBoard(Cell[][] cells){
    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        Cell myCell;
        if(buildingInitialBoard){
          myCell = new Cell(0, row, col);
        } else {
          //creating a clone board
          Cell cellToCopyFrom = myCells[row][col];
          //we want a copy of the cell, NOT THE SAME OBJECT
          myCell = new Cell(cellToCopyFrom.getState(), row, col);
        }
        cells[row][col] = myCell;
      }
    }
    addNeighborsToCells(cells);
  }

  /**
   * add neighbors to a cell
   * @param cells
   */
  protected void addNeighborsToCells(Cell[][] cells) {
    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        Cell cell = cells[row][col];
        if (row + 1 < myRows) {
          addNeighborCols(cell, col, cells, row+1, myRules.areCornersNeighbors());
        }
        if (row > 0) {
          addNeighborCols(cell, col, cells, row-1, myRules.areCornersNeighbors());
        }
        if (col + 1 < myCols) {
          cell.addNeighbor(cells[row][col+1]);
        }
        if (col > 0) {
          cell.addNeighbor(cells[row][col-1]);
        }
        checkGridTypeAndAddNeighbors(cells, row, col, cell);
      }
    }
  }

  private void checkGridTypeAndAddNeighbors(Cell[][] cells, int row, int col, Cell cell) {
    if(myNeighborhood== TORODIAL && col ==0) {
      cell.addNeighbor(cells[row][getNumCols()-1]);
    }
    if(myNeighborhood== TORODIAL && row ==0) {
      cell.addNeighbor(cells[getNumRows()-1][col]);
    }
    if(myNeighborhood== TORODIAL && col ==getNumCols()-1) {
      cell.addNeighbor(cells[row][0]);
    }
    if(myNeighborhood== TORODIAL && row ==getNumRows()-1) {
      cell.addNeighbor(cells[0][col]);
    }
  }

  private void addNeighborCols(Cell cell, int col, Cell[][] cells, int neighborRow, boolean corners) {
    cell.addNeighbor(cells[neighborRow][col]);
    if(corners) {
      if (col > 0) {
        cell.addNeighbor(cells[neighborRow][col - 1]);
      }
      if (col + 1 < myCols) {
        cell.addNeighbor(cells[neighborRow][col + 1]);
      }
    }
  }

  /**
   * make a copy of the board, with a copy of all of the neighbors of each cell
   */
  private void cloneNeighbors(){
    buildBoard(cloneCells);
  }

  /**
   * cycles through each cell in board and updates it based on a 'clone' of the board
   * so that it has an accurate representation of neighbors
   */
  public void updateBoard(){
        cloneNeighbors();
        for(int row = 0; row < myRows; row++){
          for(int col = 0; col < myCols; col++){
            myRules.changeState(myCells[row][col], cloneCells[row][col]);
          }
        }
  }

  /**
   * fills the cells with the appropriate colors and sets the state of the cell
   * @param state state of cell
   * @param row row position of cell
   * @param col column position of cell
   */
  public void updateCell(int state, int row, int col){
    myCells[row][col].setState(state);
  }

  /**
   * Return the number of rows in the board
   * @return myRows
   */
  public int getNumRows(){
    return myRows;
  }

  /**
   * Return the number of columns in the board
   * @return myCols
   */
  public int getNumCols(){
    return myCols;
  }

  /**
   * returns a list of all states of each cell in the board
   * @return
   */
  public List getStates(){
    List<Integer> cellStates = new ArrayList<Integer>();
    for(Cell[] row : myCells){
      for(Cell c : row){
        cellStates.add(c.getState());
      }
    }
    return cellStates;
  }
}

