/*
package cellmodel.celltype;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class TriangleCell extends Cell {

  private double sideLength;
  private Polygon body;
  private double triangleHeight;

  public TriangleCell(int initState, double cellLength) {
    super(initState);
    sideLength = cellLength;
    body = new Polygon();
    body.setStroke(Color.WHITE);
    triangleHeight = triangleHeightFormula();
  }

  public TriangleCell(int initState) {
    this(initState, 0);
  }

  private void makeCellBody(double xStart, double yStart, boolean pointyUp){
    body.getPoints().addAll(new Double[]{
        xStart, yStart,
        xStart+sideLength, yStart,
        3*xStart/2, yStart+triangleHeight});
    
  }
  private double triangleHeightFormula(){
    return sideLength * Math.sqrt(3) / 2;
  }
}
*/
