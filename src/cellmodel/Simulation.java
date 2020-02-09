package cellmodel;

import display.visualizer.SquareVisualizer;
import display.visualizer.TriangleVisualizer;
import display.visualizer.Visualizer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Class to run the simulation by repeatedly calling a step function
 */
public class Simulation {
  public static final int DEFAULT_FRAMES_PER_SECOND = 0;
  public static final double MILLIS_PER_SECOND = 1000.0;

  private Board myBoard;
  private Visualizer myVisualizer;
  private Timeline animation;
  private double millisecondDelay;
  /**
   * constructor that takes in a starting board, and number or corners in the cell shape
   * and starts running the simulation
   * @param board incoming board
   */
  public Simulation(Board board, int numCornersOnACell){
    myBoard = board;
    if(numCornersOnACell == Visualizer.TRIANGLE_CORNER_NUMBER) {
      myVisualizer = new TriangleVisualizer();
    } else {
      myVisualizer = new SquareVisualizer();
    }
    myVisualizer.displayBoard(board);
    setFramesPerSec(DEFAULT_FRAMES_PER_SECOND);
    animation = new Timeline();
    animation.setCycleCount(Animation.INDEFINITE);
    setKeyFrame();
  }

  /**
   * @param fps is the number of frames per second to set the animation to
   */
  private void setFramesPerSec(int fps){
          millisecondDelay = MILLIS_PER_SECOND / fps;
      }

  /**
   * Sets the keyframe to running step() after every delay determined by millisecondDelay
   */
  private void setKeyFrame(){
    KeyFrame newFrame = new KeyFrame(Duration.millis(millisecondDelay), e -> step());
    animation.getKeyFrames().add(newFrame);
    animation.play();
  }

  /**
   * Resets the keyframe on user input
   * @param fps is the frames per second to set the animation at
   */
  public void resetKeyFrame(int fps){
    setFramesPerSec(fps);
    animation.getKeyFrames().remove(0);
    animation.stop();
    setKeyFrame();
  }

  /**
   * Runs repeatedly after every millisecondDelay to have the board take a step forward based on the
   * current status of all the cells
   */
  public void step() {
    myBoard.updateBoard();
    myVisualizer.displayBoard(myBoard);
  }

  /**
   * Ends the simulation by closing the visualizer
   */
  public void endSim(){
          myVisualizer.closeWindow();
      }
}
