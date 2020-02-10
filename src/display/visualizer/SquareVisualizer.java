package display.visualizer;

public class SquareVisualizer extends Visualizer {

  /**
   * Constructor, creates a scene, a stage, and then set the stage to that scene
   */
  public SquareVisualizer() {
    super();
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
   * reset all of the variables that we need to reset
   */
  @Override
  protected void resetVariables(){
    resetXPos();
    resetYPos();
    resetCol();
    resetRow();
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
