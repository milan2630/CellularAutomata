package display.visualizer;

public class TriangleVisualizer extends Visualizer {

  private boolean pointyUp;
  /**
   * Constructor, creates a scene, a stage, and then set the stage to that scene
   */
  public TriangleVisualizer() {
    super();
    pointyUp = true;
  }

  private void setPointDirection() {
    System.out.println(" and points up: "+pointyUp);
    if(col%2 != row%2){
      pointyUp = true;
    } else {
      pointyUp = false;
    }

  }

  @Override
  void moveToNextRow(){
    row++;
    xPos = 0;
    if(row%2 == 1){
      yPos=height+height+yPos;
    }
    col = -1;
  }

  @Override
  void moveOver(){
    xPos += width/2;
    if(pointyUp){
      yPos -= height;
    } else {
      yPos += height;
    }
    col++;
    setPointDirection();
  }

  @Override
  Double[] getCorners(){
    return new Double[]{
        //leftmost point
        xPos, yPos,
        //rightmost point
        xPos + width, yPos,
        //middle point
        xPos + width / 2, yPos + height * getThirdTriangleYPoint(pointyUp)
    };
  }

  private int getThirdTriangleYPoint(boolean up){
    if(up){
      return -1;
    }
    return 1;
  }
}
