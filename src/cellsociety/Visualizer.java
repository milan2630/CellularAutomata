package cellsociety;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Visualizer extends Application {

    public static final int CA_WIDTH = 400; //TODO THIS CAN BE CHANGED LATER
    public static final int CA_HEIGHT = 400;
    public static final Color BACKGROUND = Color.PALETURQUOISE; //TODO THIS CAN BE CHANGED LATER
    private Group root;
    private Scene myScene;
    private Stage myStage;

    private String intro = "Welcome to Dana's Game!";
    private int introTextHeight = 20;
    private String rules = "Some Rules:\n1. Press space to begin moving the ball!\n2. Use left and right arrow keys to move the paddle\n3. You get three lives at the beginning of each level\n4. Hit bricks with the ball to break them\n5. Bounce the ball on the left third to move it left,\n    middle to go straight up,\n    and right third to move the ball to the right";
    private int rulesTextHeight = 120;
    private String startingInstructions = "Click to begin!";

    /**
     * Constructor, creates a scene, a stage, and then set the stage to that scene
     * @param startRoot the first board
     */
    public Visualizer(Group startRoot) {
        root = new Group();
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
        int size = CA_WIDTH;
        Text introText = new Text(intro);
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setStroke(Color.BLACK);
        introText.setLayoutX(size/3);
        introText.setLayoutY(size/3);
        root.getChildren().add(introText);

        // set rules text
        Text rulesText = new Text(rules);
        rulesText.setTextAlignment(TextAlignment.LEFT);
        rulesText.setX(size/5);
        rulesText.setY(size/3+introTextHeight);
        root.getChildren().add(rulesText);

        primaryStage.setScene(myScene);
        primaryStage.setTitle(intro);

        primaryStage.show();
    }


    /**
     *  Change the display
     */
    public void updateDisplay(){//Group currentBoardGroup) { //this root comes from the board
        myScene = new Scene(root);//currentBoardGroup); //create the new scene
       // start(myStage);
    }

    public static void main(String[] args) {

        //Group t = new Group();
        //t.getChildren().add(new Rectangle(10,10,10,10));
        //Visualizer v = new Visualizer(t);
        //v.start(new Stage());

        launch(args);
    }
}
