package display.visualizer;

public class SquareVisualizer extends Visualizer {

  /**
   * Constructor, creates a scene, a stage, and then set the stage to that scene
   */
  public SquareVisualizer() {
    super();
  }

  @Override
  void moveToNextRow(){
    row++;
    yPos += height;
    xPos = 0;
  }

  @Override
  void moveOver(){
    xPos += width;
    col++;
  }

  @Override
  void resetVariables(){
    xPos = 0;
    yPos = 0;
    col = 0;
    row = 0;
  }

  @Override
  Double[] getCorners(){
    return new Double[]{
        //upper left corner
        xPos, yPos,
        //upper right corner
        xPos + width, yPos,
        //bottom right corner
        xPos + width, yPos + height,
        //bottom left corner
        xPos, yPos + height
    };
  }
}
