package cellsociety;

import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Visualizer extends Application {

    public static final int CA_WIDTH = 1000; //TODO THIS CAN BE CHANGED LATER
    public static final int CA_HEIGHT = 1000;
    public static final Color BACKGROUND = Color.LAVENDERBLUSH; //TODO THIS CAN BE CHANGED LATER
    public static final double GAP = 190;
    private Group root;
    private Scene myScene;
    private GridPane grid;


    /**
     * Constructor, creates a scene, a stage, and then set the stage to that scene
     */
    public Visualizer(){
        root = new Group();
        grid = new GridPane();
        grid.setHgap(GAP);
        grid.setVgap(GAP);
        myScene = new Scene(root, CA_WIDTH, CA_HEIGHT, BACKGROUND);
        //super(startRoot, CA_WIDTH, CA_HEIGHT, BACKGROUND);
    }

    /**
     * @Override Application.start(stage)
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage){
        // set intro text
       // Percolation perc = new Percolation(new HashMap<String, String>());
        //Configuration config = new Configuration();
        //Board test = config.createDebugBoard();
        //Board test = new Board(8,8, perc);
        //test.updateBoard();
        //root.getChildren().add(test.boardView());
        grid.getChildren().addAll(root);

        primaryStage.setScene(myScene);
        primaryStage.show();

    }


    /**
     *  Change the display
     */

    public void sendRoot(Group boardRoot){
        root = boardRoot;
    }

    public static void main(String[] args) {

        //Group t = new Group();
        //Percolation perc = new Percolation();
       // Board b = new Board(20,20, perc);
        //.setConstrants
        //t.getChildren().add(b.boardView());

        //Visualizer v = new Visualizer();
       // v.sendRoot(t);

        launch(args);
    }
}
