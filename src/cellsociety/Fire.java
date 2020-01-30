package cellsociety;

import java.util.HashMap;
import javafx.scene.paint.Color;

public class Fire extends Rules {

  public static final Color BURNING_COLOR = Color.DARKRED;
  public static final Color TREE_COLOR = Color.GREEN;
  public static final Color EMPTY_COLOR = Color.YELLOW;

  private Color[] stateColors;
  private int burning;
  private int tree;
  private  int empty;
  private float probCatch;

  public Fire(HashMap<String, String> setupParameters){
    burning = 2;
    tree = 1;
    empty = 0;
    stateColors = new Color[3];
    stateColors[burning] = BURNING_COLOR;
    stateColors[tree] = TREE_COLOR;
    stateColors[empty] = EMPTY_COLOR;
    probCatch = Float.parseFloat(setupParameters.get("probCatch"));
  }
  @Override
  void changeState(Cell cell) {
    int state = cell.getState();
    //TODO CHECK FOR NEIGHBOR IS BURNING
    if(state == tree && treeBurns()) {
      cell.changeStateAndView(burning, stateColors[burning]);
    }
    if(state == burning){// && cell.numberOfStateChanges()>0){
      //aka it has been more than one round, change it
      cell.changeStateAndView(empty, stateColors[empty]);
    }
  }

  private boolean treeBurns(){
    return Math.random()<=probCatch;
  }

  /**
   * gets the color for a cell that is created with a certain state
   * so that the board can be created
   * @param state
   * @return color of the state, or if it's not a valid state white
   */
  public Color getStateColor(int state){
    if(state >=0 && state <=3)
      return stateColors[state];
    else return Color.WHITE;
  }
}
