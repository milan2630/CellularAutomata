package display.visualizer;

public class TriangleVisualizer extends Visualizer {

  private boolean pointyUp;
  /**
   * Constructor, creates a scene, a stage, and then set the stage to that scene
   */
  public TriangleVisualizer() {
    super();
    pointyUp = false;
    System.out.println("initializating pointUp to false");
  }

  private void setPointDirection() {
    pointyUp = col%2 != row%2;
  }

  @Override
  void moveToNextRow(){
    row++;
    xPos = 0;
    if(row%2 == 1){
      yPos=height+height+yPos;
    }
    col = 0;
    setPointDirection();
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

  @Override
  void resetVariables(){
    xPos = 0;
    col = 0;
    row = 0;
    pointyUp = false;
    yPos = 0;
  }
  private int getThirdTriangleYPoint(boolean up){
    if(up){
      return -1;
    }
    return 1;
  }
}
