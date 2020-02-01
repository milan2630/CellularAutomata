package cellsociety;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Date;

public class Simulation {
  public static final int DEFAULT_FRAMES_PER_SECOND = 1;
  public static final double MILLIS_PER_SECOND = 1000.0;

  private Board myBoard;
  private Visualizer myVisualizer;
  private int cycleNumber;

  private Timeline animation;

  private int framesPerSec;
  private double millisecondDelay;


  /**
   * constructor that takes in a starting board,
   * @param b incoming board
   */
  public Simulation(Board b){
      myBoard = b;
      cycleNumber = 0;
      myVisualizer = new Visualizer();
      myVisualizer.sendRoot(b.boardView());
      myVisualizer.start(new Stage());
      setFramesPerSec(DEFAULT_FRAMES_PER_SECOND);
      animation = new Timeline();
      KeyFrame newFrame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
      animation.setCycleCount(Animation.INDEFINITE);
      animation.getKeyFrames().add(newFrame);
      animation.play();
  }

  private void setFramesPerSec(int fps){
      framesPerSec = fps;
      millisecondDelay = MILLIS_PER_SECOND / framesPerSec;
  }

  public void resetKeyFrame(int fps){
      setFramesPerSec(fps);
      KeyFrame newFrame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
      animation.getKeyFrames().remove(0);
      animation.stop();
      animation.getKeyFrames().add(newFrame);
      animation.play();
  }

  public void step() {
      nextCycle();

      Date d = new Date();
      System.out.println(d.getTime());
  }

  /**
   * changes the state of the board depending on the rules,
   * adds a number to the cycle count TODO THIS IS FOR DEBUGGING,
   * updates the visualizer
   */
  private void nextCycle(){
      myBoard.updateBoard();
      cycleNumber++;
      //display();
  }

  /**
   * tell the visualizer to update the display to the new 'image' of the board
   */
  public void display(){
   // myVisualizer.updateDisplay();
  }


}
