package cellmodel.boardtype;

import cellmodel.celltype.Cell;
import cellmodel.rules.Rules;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * creates a cloneable board object that establishes the positions of each cell, updates the states of the cells based on the rules, and determines the neighbors of the cells
 **/
public abstract class Board{
  private static final String RESOURCES = "resources";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
  private static final String STYLE_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "StyleComponents";
  protected static final int ONE_AWAY = 1;
  protected static final int TWO_AWAY = 2;
  private Cell[][] myCells;
  private Cell[][] cloneCells;
  private int myRows;
  private int myCols;
  private Rules myRules;
  private boolean buildingInitialBoard;
  private String myNeighborhood;
  private Map<Integer,Integer> stateHistory;
  private double percentOfNeighbors;
  private static ResourceBundle styleResource = ResourceBundle.getBundle(STYLE_PROPERTIES_FILENAME);


  /**
   * constructor to create a board
   * takes in the number of columns, rows, and the rules
   * @param numCols number of columns on the board
   * @param numRows number of rows on the board
   * @param rules rules of the simulation
   **/
  public Board(int numCols, int numRows, Rules rules) {
    percentOfNeighbors=Double.parseDouble(styleResource.getString("PercentOfNeighbors"));
    myNeighborhood= styleResource.getString("NeighborhoodType");
    myRules = rules;
    myCells = new Cell[numRows][numCols];
    cloneCells = new Cell[numRows][numCols];
    myRows = numRows;
    myCols = numCols;
    buildingInitialBoard = true;
    buildBoard(myCells);
    buildingInitialBoard = false;
    stateHistory = new HashMap<Integer, Integer>();
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
    removeUnwantedNeighbors(cells);
  }

  /**
   * get rid of the extra neighbors; this is for when there are fewer than max number of neighbors
   * @param cells
   */
  protected void removeUnwantedNeighbors(Cell[][] cells) {
    for (int row = 0; row < myRows; row++) {
      for (int col = 0; col < myCols; col++) {
        int numNeighbors = cells[row][col].getNeighbors().size();
        int [] neighborsToRemove = getWhichNeighborsToRemove(numNeighbors);
        Cell cell = cells[row][col];
        for (int i = cell.getNeighbors().size()-1; i >= 0; i--) {
          if (neighborsToRemove[i] == 0) {
            cell.removeNeighbor(cell.getNeighbors().get(i));
          }
        }
      }
    }
  }

  private int[] getWhichNeighborsToRemove(int numNeighbors) {
    int[] neighborsNotWanted = new int[numNeighbors];
    int counter = (int) Math.round(percentOfNeighbors*((double) numNeighbors));
    while (counter > 0) {
      int randomIndex = (int) (Math.random() * (numNeighbors));
      if (neighborsNotWanted[randomIndex] == 0) {
        neighborsNotWanted[randomIndex] = 1;
        counter--;
      }
    }
    return neighborsNotWanted;
  }

  /**
   * add neighbors to a cell
   * @param cells
   */
  abstract protected void addNeighborsToCells(Cell[][] cells);

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
      }
    }
    findAndAddAssociatedCloneNeighbor();
  }

  /**
   * return neighborhood type
   * @return myNeighborhood
   */
  protected String getMyNeighborhood(){
    return myNeighborhood;
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
        }
      }
    }
  }

  /**
   * cycles through each cell in board and updates it based on a 'clone' of the board
   * so that it has an accurate representation of neighbors
   */
  public void updateBoard(){
    stateHistory = new HashMap<>();
    cloneNeighbors();
    for(int row = 0; row < myRows; row++){
      for(int col = 0; col < myCols; col++){
        int state = getState(row, col);
        stateHistory.putIfAbsent(state, 0);
        int oldNumber = stateHistory.get(state);
        stateHistory.put(state, oldNumber+1);
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

  public void randomizeCellState(int row, int col){
    int randState = (int)(Math.random()*myRules.getNumberOfPossibleStates());
    updateCell(randState, row, col);
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

  /**
   * return the rules parameters
   * @return myRules.getParameters()
   */
  public Map<String, String> getRulesParameters() {
    return myRules.getParameters();
  }

  /**
   * return state of a cell at a certain position, used in Simulation
   * @param row
   * @param col
   * @return state of myCells[row][col]
   */
  public int getState(int row, int col){
    return myCells[row][col].getState();
  }

  /**
   * return a map of all of the states and how many of them exist on the current board
   * @return
   */
  public Map getStateHistory(){
    return stateHistory;
  }

  /**
   * return the name of the rules class
   * @return rules name
   */
  public String getRulesClass() {
    String[] classParts = myRules.getClass().toString().split("\\.");
    return classParts[classParts.length-1];
  }
  public Map<Integer, String> getNames(){
    return myRules.getStateNames();
  }

  /**
   * return the total number of possible states
   * @return the number of states myRules has
   */
  public int getNumPossibleStates(){
    return myRules.getNumberOfPossibleStates();
  }

  /**
   * return the ResourceBundle that has all of the styles specific to that rules class
   * @return
   */
  protected static ResourceBundle getStyleResourceBundle(){
    return styleResource;
  }
}