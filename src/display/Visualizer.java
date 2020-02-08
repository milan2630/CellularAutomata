package display;

import cellmodel.Board;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/**
 * creates a view of the CA simulation, extends application
 */
public class Visualizer extends Application {

    public static final double CA_WIDTH = 500;
    public static final double CA_HEIGHT = 500;
    public static final Color BACKGROUND = Color.LAVENDERBLUSH;
    public static final Color BORDER_COLOR = Color.RED;
    public static final double GAP = 190;
    public static final int TRIANGLE_CORNER_COUNT = 3; // a triangle has three corners

    private Scene myScene;
    private GridPane grid;
    private Stage myStage;
    private double xPos;
    private double yPos;
    private double width;
    private double height;
    private int cellCornerNumber;

    /**
     * Constructor, creates a scene, a stage, and then set the stage to that scene
     */
    public Visualizer(int cellCorners) {
        makeNewGrid();
        xPos = 0;
        yPos = 0;
        width = 0;
        height = 0;
        myStage = new Stage();
        cellCornerNumber = cellCorners;
    }

    /**
     * @Override Application.start(stage)
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage){
        myStage.setX(0);
        myStage.setScene(myScene);
        myStage.show();
        myStage.setOnCloseRequest(t->stopEverything());
    }

    private void makeNewGrid(){
        grid = new GridPane();
        grid.setHgap(GAP);
        grid.setVgap(GAP);
    }

    /**
     * Close the window
     */
    public void closeWindow(){
        myStage.close();
    }

    /**
     *  Change the display
     */
    public void displayBoard(Board board){
        makeNewGrid();
        width = getIndividualCellWidth(board);
        height = getIndividualCellHeight(board);
        grid.getChildren().addAll(getBoardView(board));
        myScene = new Scene(grid, CA_WIDTH, CA_HEIGHT, BACKGROUND);
        start(new Stage());
    }

    private void stopEverything(){
        System.exit(1);
    }

    private Group getBoardView(Board board){
        //iterate through the board and get all of the states of all of the cells
        //using cellView, and get the color for the cell from a css file?
        Group root = new Group();
        boolean triangle = false;
        if(cellCornerNumber == TRIANGLE_CORNER_COUNT){
            triangle = true;
        }
        List<Integer> states = board.getStates();
        boolean pointyUp = false;
        int colCounter = 0;
        int rowCounter = 0;
        for(Integer i : states){
            //pointy up switches, color comes from css file, xPos and yPos increment by cell width and height respectively
            //if(cellCornerNumber == 3) {xPos+=width/2;}
            //else{xPos+=width;}

            if(colCounter == board.getNumCols()){
                xPos = 0;
                rowCounter++;
                yPos += height;
                colCounter = 0;
            }
            if(colCounter%2==0) {
                xPos += width;
            }
            else{
                xPos += width/2;
            }
            if(colCounter == 0){
                xPos = 0;
            }
            if(colCounter%2==rowCounter%2){
                pointyUp = true;
            } else {
                pointyUp = false;
            }
            Color color = Color.GREEN;
            if(i == 2) color = Color.BLACK;
            if(i == 1) color = Color.WHITE;
            if(i == 0) color = Color.BLUE;

            Polygon cell = cellView(width, height, color, xPos, yPos, triangle, pointyUp);
            root.getChildren().add(cell);
            colCounter++;
        }
        return root;
    }

    private double getIndividualCellWidth(Board board){
        return CA_WIDTH/board.getNumCols();
    }

    private double getIndividualCellHeight(Board board){
        return CA_HEIGHT/board.getNumRows();
    }

    private Polygon cellView(double width, double height, Color color, double xPosition, double yPosition, boolean triangle, boolean pointyUp){
        Polygon cellShape = new Polygon();
        Double[] corners;
        if(triangle){
            corners = new Double[] {
                //leftmost point
                xPosition, yPosition,
                //rightmost point
                xPosition+width, yPosition,
                //middle point
                xPosition+width/2, yPosition + height * getThirdTriangleYPoint(pointyUp)
            };
        } else {
            corners = new Double[] {
                //upper left corner
                xPosition, yPosition,
                //upper right corner
                xPosition+width, yPosition,
                //bottom right corner
                xPosition+width, yPosition+height,
                //bottom left corner
                xPosition, yPosition+height
            };
        }
        cellShape.getPoints().addAll(corners);
        cellShape.setFill(color);
        cellShape.setStroke(BORDER_COLOR);
        return cellShape;
    }

    private int getThirdTriangleYPoint(boolean up){
        if(up){
            return -1;
        }
        return 1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
