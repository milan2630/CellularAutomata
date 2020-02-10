package cellmodel;

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
  public TriangleBoard(int numCols, int numRows, Rules rules, int neighborhoodType, double percentWanted) {
    super(numCols, numRows, rules, neighborhoodType, percentWanted);
    super.myNeighborhood = neighborhoodType;
  }

  /**
   * add neighbors to a triangle cell
   * @param cells
   */
  @Override
  protected void addNeighborsToCells(Cell[][] cells){
    Cell[] rows;
    for (int row = 0; row < this.getNumRows(); row++) {
      for (int col = 0; col < this.getNumCols(); col++) {
        Cell cell = cells[row][col];
        addMyRow(cells, cell, row, col);
        if (row + ONE_AWAY < this.getNumRows()) {
          addNeighborRow(cells, cell, row+ONE_AWAY, col, ONE_AWAY, true);
        }
        if (row > 0) {
          addNeighborRow(cells, cell, row-ONE_AWAY, col, ONE_AWAY, true);
        }
        addNeighborsSpecificToOrientation(cells, cell, row, col);
        removeUnwantedNeighbors(cells);
      }
    }
  }

  private void addNeighborRow(Cell[][] cells, Cell cell, int neighborRow, int col, int dif, boolean addMiddleCell){
    if(col - dif + ONE_AWAY > 0) { //col>0
      cell.addNeighbor(cells[neighborRow][col - dif]);
    }
    if(addMiddleCell) {
      cell.addNeighbor(cells[neighborRow][col]);
    }
    if(col + dif < this.getNumCols()) { //col<myCols
      cell.addNeighbor(cells[neighborRow][col + dif]);
    }
  }

  private void addMyRow(Cell[][] cells, Cell cell, int row, int col) {
    addNeighborRow(cells, cell, row, col, ONE_AWAY, false);
    addNeighborRow(cells, cell, row, col, TWO_AWAY, false);
  }

  private void addNeighborsSpecificToOrientation(Cell[][] cells, Cell cell, int row, int col){
    if(row%2==col%2 && row > 0) {
        addNeighborRow(cells, cell, row-ONE_AWAY, col, TWO_AWAY, false);
      checkGridTypeAndAddNeighbors(cells, row, col, cell);
    } else if(row%2!=col%2 && row+1 < this.getNumRows()) {
      addNeighborRow(cells, cell, row+ONE_AWAY, col, TWO_AWAY, false);
    }
    checkGridTypeAndAddNeighbors(cells, row, col, cell);
  }

  private void checkGridTypeAndAddNeighbors(Cell[][] cells, int row, int col, Cell cell) {
    if(myNeighborhood== TORODIAL && col ==0) {
      cell.addNeighbor(cells[row][getNumCols()-1]);
      cell.addNeighbor(cells[row][getNumCols()-2]);
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
    //totalNeighbors=cell.getNeighbors().size();
  }
}
