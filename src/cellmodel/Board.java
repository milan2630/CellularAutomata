package cellmodel;

import cellmodel.celltype.Cell;
import cellmodel.rules.Rules;
import java.util.ArrayList;
import java.util.List;

/**
 * creates a cloneable board object that establishes the positions of each cell, updates the states of the cells based on the rules, and determines the neighbors of the cells
 **/
public class Board{
  private Cell[][] myCells;
  private Cell[][] cloneCells;
  private int myRows;
  private int myCols;
  private Rules myRules;
  private boolean buildingInitialBoard;
  private int myNeighborhood;
  private static final int finite = 0;
  private static final int torodial =1;
  private double percentOfNeighbors=.6;
  //private int totalNeighbors;
  //private static int[] neighborsToRemove;



  /**
   * constructor to create a board
   * takes in the number of columns, rows, and the rules
   * @param numCols number of columns on the board
   * @param numRows number of rows on the board
   * @param rules rules of the simulation
   * @param neighborhoodType
   * @param percentOfNeighborsWanted
   **/
  public Board(int numCols, int numRows, Rules rules, int neighborhoodType,
      double percentOfNeighborsWanted) {
    myRules = rules;
    myCells = new Cell[numRows][numCols];
    cloneCells = new Cell[numRows][numCols];
    myRows = numRows;
    myCols = numCols;
    buildingInitialBoard = true;
    buildBoard(myCells);
    buildingInitialBoard = false;
    myNeighborhood= neighborhoodType;
    //percentOfNeighbors=percentOfNeighborsWanted;
    //neighborsToRemove = new int[totalNeighbors];
    //neighborsToRemove = getWhichNeighborsToRemove();
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
    //neighborsToRemove = getWhichNeighborsToRemove();
    removeUnwantedNeighbors(cells);
  }

  private void removeUnwantedNeighbors(Cell[][] cells) {
    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        int numNeighbors = cells[row][col].getNeighbors().size();
        int [] neighborsToRemove = getWhichNeighborsToRemove(numNeighbors);
        Cell cell = cells[row][col];
        for (int i = cell.getNeighbors().size()-1; i >= 0; i--) {
          System.out.println( "num " + neighborsToRemove[i]);
          if (neighborsToRemove[i] == 0) {
            cell.removeNeighbor(cell.getNeighbors().get(i));
          }
        }
        //System.out.println(cell.getNeighbors());
      }
    }
  }

  private int[] getWhichNeighborsToRemove(int numNeighbors) {
    int[] neighborsNotWanted = new int[numNeighbors];
    System.out.println(numNeighbors);
    System.out.println(percentOfNeighbors);
    double count = (percentOfNeighbors*((double) numNeighbors));
    System.out.println(count);
    int counter = (int) Math.round(count);
    System.out.println(counter);
    while (counter > 0) {
      int randomIndex = (int) (Math.random() * (numNeighbors));
      System.out.println("rand" + randomIndex);
      if (neighborsNotWanted[randomIndex] == 0) {
        neighborsNotWanted[randomIndex] = 1;
        counter--;
      }
    }
    return neighborsNotWanted;
  }

  private void addNeighborsToCells(Cell[][] cells) {
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
        //System.out.println(cell.getNeighbors());
      }
    }
  }

  private void checkGridTypeAndAddNeighbors(Cell[][] cells, int row, int col, Cell cell) {
    if(myNeighborhood==torodial && col ==0) {
      cell.addNeighbor(cells[row][getNumCols()-1]);
    }
    if(myNeighborhood==torodial && row ==0) {
      cell.addNeighbor(cells[getNumRows()-1][col]);
    }
    if(myNeighborhood==torodial && col ==getNumCols()-1) {
      cell.addNeighbor(cells[row][0]);
    }
    if(myNeighborhood==torodial && row ==getNumRows()-1) {
      cell.addNeighbor(cells[0][col]);
    }
    //totalNeighbors=cell.getNeighbors().size();
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
  private void cloneNeighbors() {
    //buildBoard(cloneCells);
    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        Cell myCell;
        Cell cellToCopyFrom = myCells[row][col];
        //we want a copy of the cell, NOT THE SAME OBJECT
        myCell = new Cell(cellToCopyFrom.getState(), row, col);
        cloneCells[row][col] = myCell;
        //myCell= cloneCells[row][col];
        //List<Cell> copiedCellsNeighbors = cellToCopyFrom.getNeighbors();
      }
    }
    findAndAddAssociatedCloneNeighbor();
  }

  private void findAndAddAssociatedCloneNeighbor() {
    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        Cell cellToCopyFrom = myCells[row][col];
        Cell cloneCell = cloneCells[row][col];
        List<Cell> copiedCellsNeighbors = cellToCopyFrom.getNeighbors();
        for (Cell copiedCellsNeighbor : copiedCellsNeighbors) {
          int r = copiedCellsNeighbor.getRowNumber();
          int c = copiedCellsNeighbor.getColNumber();
          cloneCell.addNeighbor(cloneCells[r][c]);
      /*for (int cloneRow = 0; cloneRow < myRows; cloneRow++) {
        for (int cloneCol = 0; cloneCol < myCols; cloneCol++) {
          if (cloneRow == r && cloneCol == c) {
            myCell.addNeighbor(cloneCells[cloneRow][cloneCol]);
          }
        }
      }*/
        }
      }
    }
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

