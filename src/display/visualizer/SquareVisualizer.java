package display.visualizer;

import java.util.Map;

public class SquareVisualizer extends Visualizer {

  /**
   * Constructor, creates a scene, a stage, and then set the stage to that scene
   * specifically for creating squares
   */
  public SquareVisualizer(String rulesClass, Map<Integer, String> names, int numPossibleStates) {
    super(rulesClass, names, numPossibleStates);
  }

  /**
   * move to the next row, update y position, and reset x position to 0
   */
  @Override
  protected void moveToNextRow(){
    incrementRow();
    incrementYPos(getHeight());
    resetXPos();
  }

  /**
   * move to the next column, increment x position by the width
   */
  @Override
  protected void moveOver(){
    incrementXPos(getWidth());
    incrementCol();
  }

  /**
   * make a Double array of all the points of the square
   * @return that array
   */
  @Override
  protected Double[] getCorners(){
    return new Double[]{
        //upper left corner
        getXPos(), getYPos(),
        //upper right corner
        getXPos() + getWidth(), getYPos(),
        //bottom right corner
        getXPos() + getWidth(), getYPos() + getHeight(),
        //bottom left corner
        getXPos(), getYPos() + getHeight()
    };
  }
}
