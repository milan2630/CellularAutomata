package cellmodel.rules;

import cellmodel.celltype.Cell;
import java.util.HashMap;
import javafx.scene.paint.Color;

public class RockPaperScissors extends Rules {

  private static final Color ROCK_COLOR = Color.GRAY;
  private static final Color PAPER_COLOR = Color.WHITE;
  private static final Color SCISSORS_COLOR = Color.BLUE;
  private static final int ROCK = 0;
  private static final int PAPER = 1;
  private static final int SCISSORS = 2;
  private static final Color[] STATE_COLORS = {ROCK_COLOR, PAPER_COLOR, SCISSORS_COLOR};
  private static float THRESHOLD;

  public RockPaperScissors(HashMap<String, String> setupParameters) {
    THRESHOLD = Float.parseFloat(setupParameters.get("threshold"));
  }

  @Override
  public void changeState(Cell cell, Cell cloneCell) {
    int state = cell.getState();
    if (state == ROCK && cloneCell.numNeighborsWithGivenState(PAPER) > THRESHOLD) {
      cell.changeStateAndView(PAPER, STATE_COLORS[PAPER]);
    } else if (state == PAPER && cloneCell.numNeighborsWithGivenState(SCISSORS) > THRESHOLD) {
      cell.changeStateAndView(SCISSORS, STATE_COLORS[SCISSORS]);
    } else if (state == SCISSORS && cloneCell.numNeighborsWithGivenState(ROCK) > THRESHOLD) {
      cell.changeStateAndView(ROCK, STATE_COLORS[ROCK]);
    }
  }

  @Override
  public boolean areCornersNeighbors() {
    return true;
  }

  @Override
  public Color getStateColor(int state) {
    if (state >= 0 && state <= 3)
      return STATE_COLORS[state];
    else
      return Color.BLACK;
  }
}

