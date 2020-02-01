package cellsociety;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Board implements Cloneable{
  private Cell[][] myCells;
  private int myRows;
  private int myCols;
  private double cellHeight;
  private double cellWidth;
  private Group root;
  private Rules myRules;

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
    myRows = numRows;
    myCols = numCols;
    cellWidth = getIndividualWidth(numCols);
    cellHeight = getIndividualHeight(numRows);

    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        Cell myCell = new Cell(0, Color.BLUE, cellWidth, cellHeight);
        myCells[row][col] = myCell;
        setCellPosition(myCell, row, col);
      }
    }
    addNeighborstoCells();
    addCellsToRoot();
  }

  public Object clone() throws CloneNotSupportedException{return super.clone();}

  private double getIndividualHeight(int numCellsInCol) {
    return (1.0 * Visualizer.CA_HEIGHT) / numCellsInCol;
  }

  private double getIndividualWidth(int numCellsInRow) {
    return (1.0 * Visualizer.CA_WIDTH) / numCellsInRow;
  }

  // has to be changed to take into account the edges
  private void setCellPosition(Cell cell, int col, int row) {
    cell.setX(cellWidth*col);
    cell.setY(cellHeight*row);
    cell.setStroke(Color.WHITE);
  }

  private void addNeighborstoCells() {
    for (int col = 0; col < myCols; col++) {
      for (int row = 0; row < myRows; row++) {
        Cell cell = myCells[row][col];
        if (row + 1 < myRows) {
          cell.addNeighbor(myCells[row+1][col]);
        }
        if (row > 0) {
          cell.addNeighbor(myCells[row-1][col]);
        }
        if (col + 1 < myCols) {
          cell.addNeighbor(myCells[row][col+1]);
        }
        if (col> 0) {
          cell.addNeighbor(myCells[row][col-1]);
        }
      }
    }
  }



  public void insertCell(Cell cell, int row, int col) {
    myCells[col][row] = cell;
  }


  /**
   * cycles through each cell in board and updates it
   */
  public void updateBoard() {
    Board copy = null;
    try {
      copy = (Board) this.clone();
      this.updateCell(2,0,0);
      System.out.println("Copy: "+copy.myCells[0][0].getState());
      System.out.println("This: "+this.myCells[0][0].getState());
      for(int row = 0; row<this.myRows; row++){
        for(int col = 0; col < this.myCols; col++){
          System.out.println("row: "+row+" col: "+col);
          myRules.changeState(this.myCells[row][col], copy.myCells[row][col]);
        }
      }

    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
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
    myCells[col][row].setFill(myRules.getStateColor(state));
    myCells[col][row].setState(state);
  }
}

