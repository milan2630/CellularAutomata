package cellsociety;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.css.Rule;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Board implements Cloneable{

  private Cell[][] myCells;
  private int num_rows;
  private int num_cols;
  private double cellHeight;
  private double cellWidth;
  private Group root;

  private Rules myRules;


  public Board(int num_Cells_Width, int num_Cells_Height, Rules rules) {
    root = new Group();
    myRules=rules;
    myCells = new Cell[num_Cells_Height][num_Cells_Width];
    num_rows = num_Cells_Height;
    num_cols= num_Cells_Width;
    cellWidth = getIndividualWidth(num_Cells_Width);
    cellHeight = getIndividualHeight(num_Cells_Height);

    for (int i = 0; i < num_Cells_Height; i++) {
      for (int j = 0; j < num_Cells_Width; j++) {
        Cell myCell = new Cell(0, Color.BLUE, cellWidth, cellHeight);
        myCells[i][j] = myCell;
        setCellPosition(myCell, i, j);
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
    for (int i = 0; i < num_cols; i++) {
      for (int j = 0; j < num_rows; j++) {
        Cell cell = myCells[i][j];
        if (j + 1 < num_rows) {
          cell.addNeighbor(myCells[i][j + 1]);
        }
        if (j > 0) {
          cell.addNeighbor(myCells[i][j - 1]);
        }
        if (i + 1 < num_cols) {
          cell.addNeighbor(myCells[i + 1][j]);
        }
        if (i > 0) {
          cell.addNeighbor(myCells[i - 1][j]);
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
      for(int row = 0; row<this.num_rows; row++){
        for(int col = 0; col < this.num_cols; col++){
          System.out.println("row: "+row+" col: "+col);
          myRules.changeState(this.myCells[row][col], copy.myCells[row][col]);
        }
      }

    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

  }

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

  public void updateCell(int state, int row, int col){
    myCells[col][row].setFill(myRules.getStateColor(state));
    myCells[col][row].setState(state);
  }
}

