package cellsociety;

public class Simulation {
  private Board myBoard;
  private Visualizer myVisualizer;
  private Rules myRules;
  private int cycleNumber;

  /**
   * constructor that takes in a starting board,
   * @param b incoming board
   * @param r specific CA rules
   */
  public Simulation(Board b, Rules r){
    myBoard = b;
    myRules = r;
    cycleNumber = 0;
    myVisualizer = new Visualizer(myBoard.boardView());
  }

  /**
   * changes the state of the board depending on the rules,
   * adds a number to the cycle count TODO THIS IS FOR DEBUGGING,
   * updates the visualizer
   */
  public void nextCycle(){
    //myBoard.update
    cycleNumber++;
    display();
  }

  /**
   * tell the visualizer to update the display to the new 'image' of the board
   */
  public void display(){
    myVisualizer.updateDisplay(myBoard.boardView());
  }

}
