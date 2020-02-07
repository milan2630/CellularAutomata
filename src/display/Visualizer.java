package display;

import cellmodel.Board;
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
        grid.getChildren().addAll(getBoardView(board));
        myScene = new Scene(grid, CA_WIDTH, CA_HEIGHT, BACKGROUND);
        width = getIndividualCellWidth(board);
        height = getIndividualCellHeight(board);
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
        //TODO LOOP THROUGH THE BOARD
            //pointy up is dependant on the column number, color comes from css file, xPos and yPos increment by cell width and height respectively
            boolean pointyUp = false;
            Color color = Color.GREY;
            Polygon cell = cellView(width, height, color, xPos, yPos, triangle, pointyUp);
            root.getChildren().add(cell);
        return root;
    }

    private double getIndividualCellWidth(Board board){
        return CA_WIDTH/board.getNumRows();
    }

    private double getIndividualCellHeight(Board board){
        return CA_HEIGHT/board.getNumCols();
    }

    /*
    private Rectangle cellSquareView(double width, double height, Color color, double xPosition, double yPosition){
        Rectangle square = new Rectangle(width, height, color);
        square.setX(xPosition);
        square.setY(yPosition);
        square.setStroke(Color.WHITE);
        return square;
    }*/

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
                3/2 * xPosition, yPosition + height * getThirdTriangleYPoint(pointyUp)
            };
        } else {
            corners = new Double[] {
                //upper left corner
                xPosition, yPosition,
                //upper right corner
                xPosition+width, yPosition,
                //bottom left corner
                xPosition, yPosition+height,
                //bottom right corner
                xPosition+width, yPosition+height
            };
        }
        cellShape.getPoints().addAll(corners);
        cellShape.setFill(color);
        cellShape.setStroke(Color.WHITE);
        return cellShape;
    }

    private int getThirdTriangleYPoint(boolean pointyUp){
        if(pointyUp){
            return -1;
        }
        return 1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
