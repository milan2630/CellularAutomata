package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;

/**
 * Rules class containing logic for RockPaperScissors CA;
 * if a given state is surrounded by more than the threshold amount of a state that beats it, then it becomes the state of the cell that beat it
 * rock beats scissors
 * scissors beats paper
 * paper beats rock
 */
public class RockPaperScissors extends Rules {
  private static final int ROCK = 0;
  private static final int PAPER = 1;
  private static final int SCISSORS = 2;
  private static float THRESHOLD;
  private static final int NUMBER_OF_POSSIBLE_STATES = 3;

  /**
   * Construct a RockPaperScissors object
   * @param setupParameters sets the number of possible states and the threshold value
   */
  public RockPaperScissors(HashMap<String, String> setupParameters) {
    super(setupParameters);
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
    THRESHOLD = Float.parseFloat(setupParameters.get("threshold"));
  }

  /**
   * Given a cell, change its state and color based on its current status & neighbor status
   * @param cell cell to be updated
   * @param cloneCell copy of the cell in the position as the cell
   */
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
  /**
   * Does this CA simulation count the corners as neighbors?
   * @return true; in RockPaperScissors, they are
   */
  public boolean areCornersNeighbors() {
    return true;
  }
}

