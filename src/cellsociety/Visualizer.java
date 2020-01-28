package cellsociety;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Visualizer extends Application {

    private Group root;
    private Scene myScene;
    private Stage myStage;

    /**
     * Constructor, creates a scene, a stage, and then set the stage to that scene
     * @param startRoot the first board
     */
    public Visualizer(Group startRoot) {
        root = startRoot;
        myScene = new Scene(root);
        myStage = new Stage();
        start(myStage);
    }

    /**
     * @Override Application.start(stage)
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        // attach scene to the stage and display it
        stage.setScene(myScene);
        stage.show();
    }

    /**
     *  Given a new root, change the display
     * @param root
     */
    public void updateDisplay(Group root) { //this root comes from the board
        myScene = new Scene(root); //create the new scene
        start(myStage);
    }

    /**
     * not written yet
     * @return
     */
    public String getFileInput() {
        return "";
    }

    /**
     * not written yet
     */
    public void setButtonScreen() {

    }

}
