package cellsociety;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.css.Rule;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Board {

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
    addNeighborstoCells(num_Cells_Height, num_Cells_Width);
  }

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

  private void addNeighborstoCells(int col, int row) {
    for (int i = 0; i < col; i++) {
      for (int j = 0; j < row; j++) {
        Cell cell = myCells[i][j];
        if (row + 1 < num_rows) {
          cell.addNeighbor(myCells[col][row + 1]);
        }
        if (row - 1 > num_rows) {
          cell.addNeighbor(myCells[col][row - 1]);
        }
        if (col + 1 < num_cols) {
          cell.addNeighbor(myCells[col + 1][row]);
        }
        if (col - 1 > num_cols) {
          cell.addNeighbor(myCells[col - 1][row]);
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
    for(Cell[] col :myCells){
      for(Cell cell : col){
          myRules.changeState(cell);
      }
    }
  }

  public Group boardView() {

    addCellsToRoot();
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

