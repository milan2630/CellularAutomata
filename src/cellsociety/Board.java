package cellsociety;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Board implements Cloneable{
  private Cell[][] myCells;
  private Cell[][] cloneCells;
  private int myRows;
  private int myCols;
  private double cellHeight;
  private double cellWidth;
  private Group root;
  private Rules myRules;
  private boolean buildingInitialBoard;

  /**
   * constructor to create a board
   * takes in the number of columns, rows, and the rules
   * @param numCols number of columns on the board
   * @param numRows number of rows on the board
   * @param rules rules of the simulation
   **/
  public Board(int numCols, int numRows, Rules rules) {
    root = new Group();
    myRules=rules;
    myCells = new Cell[numRows][numCols];
    cloneCells = new Cell[numRows][numCols];
    myRows = numRows;
    myCols = numCols;
    cellWidth = getIndividualWidth(numCols);
    cellHeight = getIndividualHeight(numRows);
    buildingInitialBoard = true;
    buildBoard(myCells);
    buildingInitialBoard = false;
    addCellsToRoot();
  }

  private void buildBoard(Cell[][] cells){
    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        Cell myCell;
        if(buildingInitialBoard){
          myCell = new Cell(0, Color.BLUE, cellWidth, cellHeight);
        } else{
          Cell cellToCopyFrom = myCells[row][col];
          myCell = new Cell(cellToCopyFrom.getState());
        }
        cells[row][col] = myCell;
        setCellPosition(myCell, row, col);
      }
    }
    addNeighborsToCells(cells);
  }

  private double getIndividualHeight(int numCellsInCol) {
    return (1.0 * Visualizer.CA_HEIGHT) / numCellsInCol;
  }

  private double getIndividualWidth(int numCellsInRow) {
    return (1.0 * Visualizer.CA_WIDTH) / numCellsInRow;
  }

  // has to be changed to take into account the edges
  private void setCellPosition(Cell cell, int row, int col) {
    cell.setX(cellWidth*col);
    cell.setY(cellHeight*row);
    cell.setStroke(Color.WHITE);
  }

  private void addNeighborsToCells(Cell[][] cells) {
    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        Cell cell = cells[row][col];
        if (row + 1 < myRows) {
          addNeighborCols(cell, col, cells, row+1);
        }
        if (row > 0) {
          addNeighborCols(cell, col, cells, row-1);
        }
        if (col + 1 < myCols) {
          addNeighborRows(cell, row, cells, col+1);
        }
        if (col > 0) {
          addNeighborRows(cell, row, cells, col-1);
        }
      }
    }
  }

  private void addNeighborCols(Cell cell, int col, Cell[][] cells, int i) {
    cell.addNeighbor(cells[i][col]);
    if (col > 0) {
      cell.addNeighbor(cells[i][col-1]);
    }
    if (col + 1 < myCols) {
      cell.addNeighbor(cells[i][col+1]);
    }
  }

  private void addNeighborRows(Cell cell, int row, Cell[][] cells, int j) {
    cell.addNeighbor(cells[row][j]);
    if (row > 0) {
      cell.addNeighbor(cells[row-1][j]);
    }
    if (row + 1 < myRows) {
      cell.addNeighbor(cells[row+1][j]);
    }
  }

  /**
   * make a copy of the board, with a copy of all of the neighbors of each cell
   */
  public void cloneNeighbors(){
    buildBoard(cloneCells);
  }

  /**
   * cycles through each cell in board and updates it based on a 'clone' of the board
   * so that it has an accurate representation of neighbors
   */
  public void updateBoard(){
    for(int row = 0; row < myRows; row++){
      for(int col = 0; col < myCols; col++){
        myRules.changeState(myCells[row][col], cloneCells[row][col]);
      }
    }
  }

  /**
   * @return group to set the scene/ stage
   */
  public Group boardView() {
    return root;
  }

  private void addCellsToRoot() {
    for (Cell[] cell_row : myCells) {
      for (Cell cell : cell_row) {
        root.getChildren().add(cell);
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
    myCells[row][col].setFill(myRules.getStateColor(state));
    myCells[row][col].setState(state);
  }
}

