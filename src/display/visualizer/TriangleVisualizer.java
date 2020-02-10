package display.visualizer;

public class TriangleVisualizer extends Visualizer {

  private boolean pointUp;
  /**
   * Constructor, creates a scene, a stage, and then set the stage to that scene
   */
  public TriangleVisualizer() {
    super();
    pointUp = false;
  }

  private void setPointDirection() {
    pointUp = getCol()%2 != getRow()%2;
  }

  /**
   * move to the next row, update y position depending on if the triangles touch at the tip or side,
   * reset x position, reset column number, and update the direction the triangle points
   */
  @Override
  protected void moveToNextRow(){
    incrementRow();
    resetXPos();
    if(getRow()%2 == 1){
      incrementYPos(getHeight()+getHeight());
    }
    resetCol();
    setPointDirection();
  }

  /**
   * move to the next column
   * set the x and y position to the "point" of the triangle by
   * incrementing the x position to half the width of the triangle,
   * and incrementing/decrementing the y position by the height of the triangle
   * update point direction for the new triangle
   */
  @Override
  protected void moveOver(){
    incrementXPos(getWidth()/2);
    if(pointUp){
      incrementYPos(-1*getHeight());
    } else {
      incrementYPos(getHeight());
    }
    incrementCol();
    setPointDirection();
  }

  /**
   * make a Double array of all the points of the triangle
   * @return that array
   */
  @Override
  protected Double[] getCorners(){
    return new Double[]{
        //leftmost point
        getXPos(), getYPos(),
        //rightmost point
        getXPos() + getWidth(), getYPos(),
        //middle point
        getXPos() + getWidth() / 2, getYPos() + getHeight() * getThirdTriangleYPoint(pointUp)
    };
  }

  /**
   * reset all of the variables that we need to reset
   */
  @Override
  protected void resetVariables(){
    resetXPos();
    resetCol();
    resetRow();
    pointUp = false;
    resetYPos();
  }

  private int getThirdTriangleYPoint(boolean up){
    if(up){
      return -1;
    }
    return 1;
  }
}
