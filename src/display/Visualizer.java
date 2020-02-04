package display;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * creates a view of the CA simulation, extends application
 */
public class Visualizer extends Application {

    public static final int CA_WIDTH = 500;
    public static final int CA_HEIGHT = 500;
    public static final Color BACKGROUND = Color.LAVENDERBLUSH;
    public static final double GAP = 190;

    private Scene myScene;
    private GridPane grid;
    private Stage myStage;

    /**
     * Constructor, creates a scene, a stage, and then set the stage to that scene
     */
    public Visualizer() {
        grid = new GridPane();
        grid.setHgap(GAP);
        grid.setVgap(GAP);
    }

    /**
     * @Override Application.start(stage)
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage){
        myStage = primaryStage;
        myStage.setX(0);
        primaryStage.setScene(myScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(t->stopEverything());
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
    public void begin(Group boardRoot, Stage stage){
        grid.getChildren().addAll(boardRoot);
        myScene = new Scene(grid, CA_WIDTH, CA_HEIGHT, BACKGROUND);
        start(stage);
    }

    private void stopEverything(){
        System.exit(1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
