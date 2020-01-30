package cellsociety;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Visualizer extends Application {

    public static final int CA_WIDTH = 400; //TODO THIS CAN BE CHANGED LATER
    public static final int CA_HEIGHT = 400;
    public static final Color BACKGROUND = Color.PALETURQUOISE; //TODO THIS CAN BE CHANGED LATER
    private Group root;
    private Scene myScene;
    private Stage myStage;

    /**
     * Constructor, creates a scene, a stage, and then set the stage to that scene
     * @param startRoot the first board
     */
    public Visualizer(Group startRoot) {
        root = startRoot;
        myScene = new Scene(root, CA_WIDTH, CA_HEIGHT, BACKGROUND);
        //super(startRoot, CA_WIDTH, CA_HEIGHT, BACKGROUND);
    }

    /**
     * @Override Application.start(stage)
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        // attach scene to the stage and display it
        stage.setScene(myScene);
        stage.setTitle("test");
        stage.show();
    }


    /**
     *  Change the display
     */
    public void updateDisplay(){//Group currentBoardGroup) { //this root comes from the board
        myScene = new Scene(root);//currentBoardGroup); //create the new scene
       // start(myStage);
    }

    public static void main(String[] args) {

        Group t = new Group();
        t.getChildren().add(new Rectangle(10,10,10,10));
        Visualizer v = new Visualizer(t);
        //v.start(new Stage());

        launch(args);
    }
}
