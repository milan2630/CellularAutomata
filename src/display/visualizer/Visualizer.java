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

    private static final double CA_WIDTH = 600;
    private static final double CA_HEIGHT = 600;
    private static final Color BACKGROUND = Color.LAVENDERBLUSH;
    private static final Color BORDER_COLOR = Color.RED;
    public static final int TRIANGLE_CORNER_NUMBER = 3; //this is public because it is referenced elsewhere, but cannot be changed
    private static final double GAP = 190;

    private Scene myScene;
    private GridPane grid;
    private Stage myStage;
    private double xPos;
    private double yPos;
    private int col;
    private int row;
    private double width;
    private double height;

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
        myScene.setFill(Color.BLACK);
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
            if(i == 0) color = Color.BLUE;
            if(i == 1) color = Color.WHITE;
            if(i == 2) color = Color.BLACK;

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

    abstract protected void moveToNextRow();
    abstract protected void moveOver();
    abstract protected Double[] getCorners();
    abstract protected void resetVariables();

    /**
     * add dif to xPos
     * @param dif
     */
    protected void incrementXPos(double dif){
        xPos += dif;
    }

    /**
     * set xPos to 0
     */
    protected void resetXPos(){
        xPos = 0;
    }

    /**
     * return xPos
     * @return xPos
     */
    protected double getXPos(){
        return xPos;
    }

    /**
     * set yPos to 0
     */
    protected void resetYPos(){
        yPos = 0;
    }

    /**
     * add dif to yPos
     * @param dif
     */
    protected void incrementYPos(double dif){
        yPos += dif;
    }

    /**
     * return yPos
     * @return yPos
     */
    protected double getYPos(){
        return yPos;
    }

    /**
     * reset row to 0
     */
    protected void resetRow(){
        row = 0;
    }

    /**
     * increment row by 1
     */
    protected void incrementRow(){
        row++;
    }

    /**
     * return row value
     * @return row
     */
    protected int getRow(){
        return row;
    }

    /**
     * reset col to 0
     */
    protected void resetCol(){
        col = 0;
    }

    /**
     * increment col by 1
     */
    protected void incrementCol(){
        col++;
    }

    /**
     * return col value
     * @return col
     */
    protected int getCol(){
        return col;
    }

    /**
     * get width value
     * @return width
     */
    protected double getWidth(){
        return width;
    }

    /**
     * get height value
     * @return height
     */
    protected double getHeight(){
        return height;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
