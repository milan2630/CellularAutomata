package cellmodel;

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
    super(numCols, numRows, rules, 0, 1);
  }

 // @Override
  //private void addNeighborsToCells(){

  //}
}
