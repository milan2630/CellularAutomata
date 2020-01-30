package cellsociety;

import java.util.ArrayList;
import java.util.Arrays;
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

  public Board(int num_Cells_width, int num_Cells_Height) {
    for (int i = 0; i < num_Cells_Height; i++) {
      for (int j = 0; j < num_Cells_width; j++) {
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

  private void setCellPositionsandNeighbors(Cell cell, int col, int row) {
    cell.setX(xPos);
    cell.setY(yPos);
    cell.addNeighbor(Arrays.asList(myCells[col][row+1], myCells[col][row-1], myCells[col+1][row], myCells[col-1][row]));

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
}