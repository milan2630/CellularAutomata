package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;

public class RockPaperScissors extends Rules {
  private static final int ROCK = 0;
  private static final int PAPER = 1;
  private static final int SCISSORS = 2;
  private static float THRESHOLD;
  private static final int NUMBER_OF_POSSIBLE_STATES = 3;

  public RockPaperScissors(HashMap<String, String> setupParameters) {
    super(setupParameters);
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
    THRESHOLD = Float.parseFloat(setupParameters.get("threshold"));
  }

  @Override
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if (state == ROCK && cloneCell.numNeighborsWithGivenState(PAPER) > THRESHOLD) {
      cell.changeStateAndView(PAPER);
    } else if (state == PAPER && cloneCell.numNeighborsWithGivenState(SCISSORS) > THRESHOLD) {
      cell.changeStateAndView(SCISSORS);
    } else if (state == SCISSORS && cloneCell.numNeighborsWithGivenState(ROCK) > THRESHOLD) {
      cell.changeStateAndView(ROCK);
    }
  }

  @Override
  public boolean areCornersNeighbors() {
    return true;
  }
}

