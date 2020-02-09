package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;

public class SugarScape extends Rules {
  private static final int NUMBER_OF_POSSIBLE_STATES = 3;
  private static final int OPEN = 0;
  private static final int ANT = 1;
  private static final int FOOD = 2;
  private static final int ANTHASFOOD = 3;
  private static final int pheromoneLookingForFood =1;
  private static final int pheromoneReturningFromFood =3;

  public SugarScape(HashMap<String, String> setupParameters) {
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
  }


    @Override
  public void changeState(Cell cell, Cell cloneCell) {

  }

  @Override
  public boolean areCornersNeighbors() {
    return false;
  }
}
