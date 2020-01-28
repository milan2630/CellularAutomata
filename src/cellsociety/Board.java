package cellsociety;


import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Board {
    private Cell[][] myCells;
    private int num_rows;
    private int num_cols;
    private int dist_between_bricks;
    private Color cell_color;
    private double yPos;
    private double xPos;
    private double height;

    public Board(int num_Cells_width, int num_Cells_Height){
        for(int i=0; i<num_Cells_Height; i++){
            for (int j=0; j<num_Cells_width; j++){
                Cell myCell = new Cell(state, cell_color, 0, 0);
                myCells[i][j]= myCell;
                yPos = yPos + myCell.getWidth();
                height = myCell.getHeight();
            }
            yPos = 0;
            xPos = xPos + height;
        }
    }
    public Group boardView(){

    }

}
