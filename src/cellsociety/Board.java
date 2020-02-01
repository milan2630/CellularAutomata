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
  private int dist_between_bricks;
  private Color cell_color;
  private double height;
  private double width;
  private Group root;
  private double xPos = 0;
  private double yPos = 0;
  private Rules myRules;
  public Board(int num_Cells_Width, int num_Cells_Height, Rules rules) {
    myRules=rules;
    myCells = new Cell[num_Cells_Height][num_Cells_Width];
    num_rows = num_Cells_Height;
    num_cols= num_Cells_Width;
    for (int i = 0; i < num_Cells_Height; i++) {
      for (int j = 0; j < num_Cells_Width; j++) {
        Cell myCell = new Cell(0, Color.BLUE);
        myCells[i][j] = myCell;
        height = myCell.getHeight();
        width = myCell.getWidth();
        setCellPositionsandNeighbors(myCell, i, j);
        updateYPos();
      }
      updateXPos();
    }
  }

  // has to be changed to take into account the edges
  private void setCellPositionsandNeighbors(Cell cell, int col, int row) {
    cell.setX(xPos);
    cell.setY(yPos);
    addNeighborstoCell(cell, col, row);

  }

  private void addNeighborstoCell(Cell cell, int col, int row) {
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

  private void updateYPos() {
    yPos = yPos + width;
  }

  private void updateXPos() {
    xPos = xPos + height;
  }


  public void insertCell(Cell cell, int row, int col) {
    myCells[col][row] = cell;
  }


  // did not write yet
  public void updateBoard() {
  }

  public Group boardView() {
    root = new Group();
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
    myRules.changeState(myCells[col][row]);
    //myCells[col][row].setState(state); is this needed?
  }
}

