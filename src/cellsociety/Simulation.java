package cellsociety;

import display.Visualizer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Date;

public class Simulation {
    public static final int DEFAULT_FRAMES_PER_SECOND = 1;
    public static final double MILLIS_PER_SECOND = 1000.0;

    private Board myBoard;
    private Visualizer myVisualizer;

    private Timeline animation;

    private double millisecondDelay;


      /**
       * constructor that takes in a starting board,
       * @param b incoming board
       */
      public Simulation(Board b){
          myBoard = b;
          myVisualizer = new Visualizer();
          myVisualizer.begin(b.boardView(), new Stage());
          setFramesPerSec(DEFAULT_FRAMES_PER_SECOND);
          animation = new Timeline();
          animation.setCycleCount(Animation.INDEFINITE);
          setKeyFrame();
      }

      private void setFramesPerSec(int fps){
          millisecondDelay = MILLIS_PER_SECOND / fps;
      }

      private void setKeyFrame(){
          KeyFrame newFrame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
          animation.getKeyFrames().add(newFrame);
          animation.play();
      }

      public void resetKeyFrame(int fps){
          setFramesPerSec(fps);
          animation.getKeyFrames().remove(0);
          animation.stop();
          setKeyFrame();
      }


     /**
      * changes the state of the board depending on the rules,
      * adds a number to the cycle count TODO THIS IS FOR DEBUGGING,
      * updates the visualizer
      */
      public void step() {
          myBoard.updateBoard();
      }

      public void endSim(){
          myVisualizer.closeWindow();
      }
}
