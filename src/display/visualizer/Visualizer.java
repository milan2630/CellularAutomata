package display.visualizer;

import cellmodel.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import cellmodel.ErrorPopup;
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
    private static final String RESOURCES = "resources";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String STYLE_PROPERTIES_FILENAME = DEFAULT_RESOURCE_PACKAGE + "StyleComponents";
    private static final Color BACKGROUND = Color.LAVENDERBLUSH;
    private static final Color BORDER_COLOR = Color.RED;
    public static final int TRIANGLE_CORNER_NUMBER = 3; //this is public because it is referenced elsewhere, but cannot be changed

    private Scene myScene;
    private GridPane grid;
    private Stage myStage;
    private double xPos;
    private double yPos;
    private int col;
    private int row;
    private double width;
    private double height;
    private ResourceBundle styleResources;
    private Map<Integer, Color> colorMap;
    private double caWidth;
    private double caHeight;

    /**
     * Constructor, creates a scene, a stage, and then set the stage to that scene
     */
    public Visualizer(String rulesClass, int totalNumStates) {
        styleResources = ResourceBundle.getBundle(STYLE_PROPERTIES_FILENAME);
        caHeight = Integer.parseInt(styleResources.getString("ScreenHeight"));
        caWidth = Integer.parseInt(styleResources.getString("ScreenWidth"));
        makeNewGrid();
        initHashmap(rulesClass, totalNumStates);
        xPos = 0;
        yPos = 0;
        row = 0;
        col = 0;
        width = 0;
        height = 0;
        myStage = new Stage();
    }

    private void initHashmap(String rulesClass, int totalNumStates) {
        colorMap = new HashMap<>();
        for(int i = 0; i < totalNumStates; i++){
            colorMap.put(i, getColorFromStyleFile(rulesClass+i, "NoColorError"));
        }
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
        myScene = new Scene(grid, caWidth, caHeight, BACKGROUND);
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
            Polygon cell = cellView(colorMap.get(i));
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
        cellShape.setStroke(getColorFromStyleFile("StrokeColor", "NoStrokeColorError"));
        return cellShape;
    }

    private Color getColorFromStyleFile(String property, String errorMessageProperty){
        try {
            String colorName = styleResources.getString(property);
            if(colorName.equals("null")){
                return null;
            }
            Color color = (Color) Color.class.getField(colorName).get(null);
            return color;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            ErrorPopup errorScreen = new ErrorPopup(new RuntimeException(styleResources.getString(errorMessageProperty)));
            return null;
        }
    }

    private double getIndividualCellWidth(Board board){
        return caWidth /board.getNumCols();
    }

    private double getIndividualCellHeight(Board board){
        return caHeight /board.getNumRows();
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
