package cellmodel.boardtype;

import cellmodel.celltype.Cell;
import cellmodel.rules.Rules;

public class TriangleBoard extends Board {

  /**
   * constructor to create a board takes in the number of columns, rows, and the rules
   *
   * @param numCols number of columns on the board
   * @param numRows number of rows on the board
   * @param rules   rules of the simulation
   **/
  public TriangleBoard(int numCols, int numRows, Rules rules) {
    super(numCols, numRows, rules);
    //percentOfNeighbors=Double.parseDouble(styleResource.getString("PercentOfNeighbors"));
  }

  /**
   * add neighbors to a triangle cell
   *
   * @param cells
   */
  @Override
  protected void addNeighborsToCells(Cell[][] cells) {
    Cell[] rows;
    for (int row = 0; row < this.getNumRows(); row++) {
      for (int col = 0; col < this.getNumCols(); col++) {
        Cell cell = cells[row][col];
        addMyRow(cells, cell, row, col, false);
        if (row + ONE_AWAY < this.getNumRows()) {
          addNeighborRow(cells, cell, row + ONE_AWAY, col, ONE_AWAY, true);
        }
        if (row > 0) {
          addNeighborRow(cells, cell, row - ONE_AWAY, col, ONE_AWAY, true);
        }
        addNeighborsSpecificToOrientation(cells, cell, row, col);
        //checkTriangleTypeForGridType(cells, cell, row, col);
        //toroidal for triangle currently not working :(
        removeUnwantedNeighbors(cells);
      }
    }
  }

  private void addNeighborRow(Cell[][] cells, Cell cell, int neighborRow, int col, int dif,
      boolean addMiddleCell) {
    if (col - dif + ONE_AWAY > 0) { //col>0
      cell.addNeighbor(cells[neighborRow][col - dif]);
    }
    if (addMiddleCell) {
      cell.addNeighbor(cells[neighborRow][col]);
    }
    if (col + dif < this.getNumCols()) { //col<myCols
      cell.addNeighbor(cells[neighborRow][col + dif]);
    }
  }

  private void addMyRow(Cell[][] cells, Cell cell, int row, int col, boolean addMiddle) {
    addNeighborRow(cells, cell, row, col, ONE_AWAY, addMiddle);
    addNeighborRow(cells, cell, row, col, TWO_AWAY, addMiddle);
  }

  private void addNeighborsSpecificToOrientation(Cell[][] cells, Cell cell, int row, int col) {
    if (row % 2 == col % 2 && row > 0) {
      addNeighborRow(cells, cell, row - ONE_AWAY, col, TWO_AWAY, false);

    } else if (row % 2 != col % 2 && row + ONE_AWAY < this.getNumRows()) {
      addNeighborRow(cells, cell, row + ONE_AWAY, col, TWO_AWAY, false);
    }
    //gridTypeAddNeighborsTriangle1(cells, row, col, cell);
    //toroidal is currently not working :(
  }
/*
  private void checkTriangleTypeForGridType(Cell[][] cells, Cell cell, int row, int col) {
    if (row % 2 == col % 2) {
      gridTypeAddNeighborsTriangle1(cells, row, col, cell);
    } else {
      gridTypeAddNeighborsTriangle2(cells, row, col, cell);
    }
  }

  private void gridTypeAddNeighborsTriangle1(Cell[][] cells, int row, int col, Cell cell) {
    if (getMyNeighborhood().equals(getStyleResourceBundle().getString("ToroidalTag"))) {
      if (col == 0) {
        addNeighborsOnOtherSide(cells, cell, row, getNumCols() - 1, ONE_AWAY);
        addNeighborsOnOtherSide(cells, cell, row, getNumCols() - 2, ONE_AWAY);
        cell.addNeighbor(cells[row - ONE_AWAY][getNumCols() - ONE_AWAY]);
      }
      if (row == 0) {
        addMyRow(cells, cell, getNumRows() - ONE_AWAY, col, true);
      }
      if (col == getNumCols() - ONE_AWAY) {
        addNeighborsOnOtherSide(cells, cell, row, 0, ONE_AWAY);
        addNeighborsOnOtherSide(cells, cell, row, ONE_AWAY, ONE_AWAY);
        cell.addNeighbor((cells[row - ONE_AWAY][0]));
      }
      if (row == getNumRows() - ONE_AWAY) {
        addNeighborRow(cells, cell, 0, col, ONE_AWAY, true);
      }
      if (col == ONE_AWAY) {
        cell.addNeighbor(cells[row][getNumCols() - ONE_AWAY]);
        cell.addNeighbor(cells[row + ONE_AWAY][getNumCols() - ONE_AWAY]);
        addNeighborsOnOtherSide(cells, cell, row, getNumCols() - ONE_AWAY, ONE_AWAY);
      }
      if (col == getNumCols() - TWO_AWAY) {
        cell.addNeighbor(cells[row][0]);
        cell.addNeighbor(cells[row + ONE_AWAY][0]);
        addNeighborsOnOtherSide(cells, cell, row, 0, ONE_AWAY);
      }
    }
  }
  private void gridTypeAddNeighborsTriangle2(Cell[][] cells, int row, int col, Cell cell) {
    if (getMyNeighborhood().equals(getStyleResourceBundle().getString("ToroidalTag"))) {
      if (col == 0) {
        addNeighborsOnOtherSide(cells, cell, row, getNumCols() - ONE_AWAY, -ONE_AWAY);
        addNeighborsOnOtherSide(cells, cell, row, getNumCols() - TWO_AWAY, -ONE_AWAY);
        cell.addNeighbor(cells[row + ONE_AWAY][getNumCols() - ONE_AWAY]);
      }
      if (row == 0) {
        addNeighborRow(cells, cell, getNumCols() - ONE_AWAY, col, ONE_AWAY, true);
      }
      if (col == getNumCols() - ONE_AWAY) {
        addNeighborsOnOtherSide(cells, cell, row, 0, -ONE_AWAY);
        addNeighborsOnOtherSide(cells, cell, row, ONE_AWAY, -ONE_AWAY);
        cell.addNeighbor((cells[row + ONE_AWAY][0]));
      }
      if (row == getNumRows() - ONE_AWAY) {
        addMyRow(cells, cell, 0, col, true);
      }
      if (col == 1) {
        addNeighborsOnOtherSide(cells, cell, row, getNumCols() - ONE_AWAY, -ONE_AWAY);
      }
      if (col == getNumCols() - TWO_AWAY) {
        addNeighborsOnOtherSide(cells, cell, row, 0, -ONE_AWAY);
      }
    }
  }

  private void addNeighborsOnOtherSide(Cell[][] cells,  Cell cell, int row, int col, int rowChange){
    cell.addNeighbor(cells[row][col]);
    cell.addNeighbor(cells[row + rowChange][col]);
  }

 */
}
