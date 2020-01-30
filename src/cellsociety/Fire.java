package cellsociety;

import java.util.HashMap;
import java.util.List;
import javafx.scene.paint.Color;

public class Fire extends Rules {

  public static final Color BURNING_COLOR = Color.DARKRED;
  public static final Color TREE_COLOR = Color.GREEN;
  public static final Color GROUND_COLOR = Color.YELLOW;
  private int burning;
  private int tree;
  private  int ground;
  private float probCatch;

  public Fire(HashMap<String, String> setupParameters){
    burning = 1;
    tree = 2;
    ground = 0;
    probCatch = Float.parseFloat(setupParameters.get("probCatch"));
  }
  @Override
  void changeState(Cell cell) {
    int state = cell.getState();
    if(state == tree && treeBurns()) {
      cell.changeStateAndView(burning, BURNING_COLOR);
    }
    if(state == burning && cell.cycleHasNotChanged()>0){
      //aka it has been two rounds, change it
      cell.changeStateAndView(ground, GROUND_COLOR);
    }
  }
  private boolean treeBurns(){
    return Math.random()<=probCatch;
  }
}
