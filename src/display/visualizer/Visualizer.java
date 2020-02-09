package display.visualizer;

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
public abstract class Visualizer extends Application {

    public static final double CA_WIDTH = 600;
    public static final double CA_HEIGHT = 600;
    public static final Color BACKGROUND = Color.LAVENDERBLUSH;
    public static final Color BORDER_COLOR = Color.RED;
    public static final int TRIANGLE_CORNER_NUMBER = 3;
    public static final double GAP = 190;

    private Scene myScene;
    private GridPane grid;
    private Stage myStage;
    protected double xPos;
    protected double yPos;
    protected int col;
    protected int row;
    protected double width;
    protected double height;

    /**
     * Constructor, creates a scene, a stage, and then set the stage to that scene
     */
    public Visualizer() {
        makeNewGrid();
        xPos = 0;
        yPos = 0;
        row = 0;
        col = 0;
        width = 0;
        height = 0;
        myStage = new Stage();
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
        Group root = new Group();
        List<Integer> states = board.getStates();
        resetVariables();
        for(Integer i : states){
            //pointy up switches, color comes from css file, xPos and yPos increment by cell width and height respectively

            if(col == board.getNumCols()) {
                col %= board.getNumCols();
                moveToNextRow();
            }

            //TODO REMOVE HARD CODE
            Color color = Color.GREEN;
            if(i == 2) color = Color.BLUE;
            if(i == 1) color = Color.WHITE;
            if(i == 0) color = Color.BLACK;

            Polygon cell = cellView(color);
            root.getChildren().add(cell);

            moveOver();
        }
        return root;
    }

    protected Polygon cellView(Color color){
        Polygon cellShape = new Polygon();
        Double[] corners = getCorners();
        cellShape.getPoints().addAll(corners);
        cellShape.setFill(color);
        cellShape.setStroke(BORDER_COLOR);
        return cellShape;
    }

    private double getIndividualCellWidth(Board board){
        return CA_WIDTH/board.getNumCols();
    }

    private double getIndividualCellHeight(Board board){
        return CA_HEIGHT/board.getNumRows();
    }

    abstract void moveToNextRow();
    abstract void moveOver();
    abstract Double[] getCorners();
    abstract void resetVariables();

    public static void main(String[] args) {
        launch(args);
    }
}
