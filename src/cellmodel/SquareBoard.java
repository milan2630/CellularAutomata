package cellmodel;

import cellmodel.celltype.Cell;
import cellmodel.rules.Rules;
import java.util.ResourceBundle;

public class SquareBoard extends Board {

  private static String myNeighborhood = getStyleResourceBundle().getString("NeighborhoodType");
  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String STYLE_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "StyleComponents";
  private boolean cornersAreNeighbors;

  /**
   * constructor to create a board takes in the number of columns, rows, and the rules
   *
   * @param numCols number of columns on the board
   * @param numRows number of rows on the board
   * @param rules   rules of the simulation
   **/
  public SquareBoard(int numCols, int numRows, Rules rules) {
    super(numCols, numRows, rules);
    cornersAreNeighbors = rules.areCornersNeighbors();
  }
  /**
   * add neighbors to a cell
   * @param cells
   */
  @Override
  protected void addNeighborsToCells(Cell[][] cells) {
    for (int row = 0; row < getNumRows(); row++) {
      for (int col = 0; col < getNumCols(); col++) {
        Cell cell = cells[row][col];
        if (row + 1 < getNumRows()) {
          addNeighborCols(cell, col, cells, row+1, cornersAreNeighbors);
        }
        if (row > 0) {
          addNeighborCols(cell, col, cells, row-1, cornersAreNeighbors);
        }
        if (col + 1 < getNumCols()) {
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
    if (myNeighborhood.equals(getStyleResourceBundle().getString("ToroidalTag"))) {
      if (col == 0) {
        cell.addNeighbor(cells[row][getNumCols() - 1]);
        if (cornersAreNeighbors) {
          cell.addNeighbor(cells[row + 1][getNumCols() - 1]);
          cell.addNeighbor(cells[row - 1][getNumCols() - 1]);
        }
      }
      if (row == 0) {
        cell.addNeighbor(cells[getNumRows() - 1][col]);
        if (cornersAreNeighbors) {
          cell.addNeighbor(cells[getNumRows() - 1][col - 1]);
          cell.addNeighbor(cells[getNumRows() - 1][col + 1]);
        }
      }
      if (col == getNumCols() - 1) {
        cell.addNeighbor(cells[row][0]);
        if (cornersAreNeighbors) {
          cell.addNeighbor(cells[row - 1][0]);
          cell.addNeighbor(cells[row + 1][0]);
        }
      }
      if (row == getNumRows() - 1) {
        cell.addNeighbor(cells[0][col]);
        if (cornersAreNeighbors) {
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
      if (col + 1 < getNumCols()) {
        cell.addNeighbor(cells[neighborRow][col + 1]);
      }
    }
  }
}