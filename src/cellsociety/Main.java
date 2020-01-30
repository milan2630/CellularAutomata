package cellsociety;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.Date;

/**
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {
    public static final int DEFAULT_FRAMES_PER_SECOND = 1;
    public static final double MILLIS_PER_SECOND = 1000.0;


    UserInterface u;
    Configuration config;
    Simulation sim;

    Timeline animation;

    private int framesPerSec;
    private double millisecondDelay;


    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*u = new UserInterface();
        config = new Configuration(u.getFileName());
        sim = new Simulation(config.getInitBoard());*/

        framesPerSec = DEFAULT_FRAMES_PER_SECOND;
        millisecondDelay = 1000.0/framesPerSec;


        KeyFrame frame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    int x = 0;

    private void step() {
        //sim.nextCycle();
        if(x == 6) {


        }
        Date d = new Date();
        System.out.println(d.getTime());
        x++;
    }

    private void setFramesPerSec(int fps){
        framesPerSec = fps;
        millisecondDelay = MILLIS_PER_SECOND / framesPerSec;
    }

    private void resetKeyFrame(){
        KeyFrame newFrame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
        animation.getKeyFrames().removeAll();
        animation.getKeyFrames().add(newFrame);
        animation.play();
    }
}
