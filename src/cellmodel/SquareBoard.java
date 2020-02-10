package cellmodel;

import cellmodel.celltype.Cell;
import cellmodel.rules.Rules;

public class SquareBoard extends Board {


  /**
   * constructor to create a board takes in the number of columns, rows, and the rules
   *
   * @param numCols number of columns on the board
   * @param numRows number of rows on the board
   * @param rules   rules of the simulation
   **/
  public SquareBoard(int numCols, int numRows, Rules rules) {
    super(numCols, numRows, rules);
  }
  /**
   * add neighbors to a cell
   * @param cells
   */
  @Override
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
    if (myNeighborhood.equals(styleResource.getString("ToroidalTag"))) {
      if (col == 0) {
        cell.addNeighbor(cells[row][getNumCols() - 1]);
        if (myRules.areCornersNeighbors()) {
          cell.addNeighbor(cells[row + 1][getNumCols() - 1]);
          cell.addNeighbor(cells[row - 1][getNumCols() - 1]);
        }
      }
      if (row == 0) {
        cell.addNeighbor(cells[getNumRows() - 1][col]);
        if (myRules.areCornersNeighbors()) {
          cell.addNeighbor(cells[getNumRows() - 1][col - 1]);
          cell.addNeighbor(cells[getNumRows() - 1][col + 1]);
        }
      }
      if (col == getNumCols() - 1) {
        cell.addNeighbor(cells[row][0]);
        if (myRules.areCornersNeighbors()) {
          cell.addNeighbor(cells[row - 1][0]);
          cell.addNeighbor(cells[row + 1][0]);
        }
      }
      if (row == getNumRows() - 1) {
        cell.addNeighbor(cells[0][col]);
        if (myRules.areCornersNeighbors()) {
          cell.addNeighbor(cells[0][col - 1]);
          cell.addNeighbor(cells[0][col + 1]);
        }
      }
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
}