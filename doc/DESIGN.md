# Simulation Design Final
### Names

## Team Roles and Responsibilities

 * Milan Bhat
     * Mainly responsible for the User Interface and the Controller
     * Main 
     * Simulation
     * Configuration
     * User Interface
     * Error handling.

 * Dana Mulligan
     * Mainly responsible for the Visualization (display) and graph of state history
     * Classes
         * Visualizer
             * TriangleVisualizer
             * SquareVisualizer
         * Rules
             * Fire
             * Percolation
         * TriangleBoard
         * HistoryGraph

 * Cayla Schuval
     * Mainly responsible for back end creation of boards, cells, neighbors, and rules for many simulations (including classes and xml files)
     * Classes
         * Board
         * Square Board
         * Cell
         * Rules
             * Foraging Ants
             * Game of Life
             * Rock Paper Scissors
             * Predator or Prey
             * Segregation

## Design goals

The primary goal of our design was to create a functioning simulator that preserved the Model and View as two separate entities with the Controller to act between them. Additionally, building on the lessons from the Breakout game, we wanted to create a design that allowed new features to be added with ease. Both of these objectives were important, but we did not want to sacrifice the first for the second.

New features include adding new simulations, changing the display, changing the neighbors of a cell, changing the grid display type and changing ways to set up the simulations. The last goal was to have the program be efficient and create pleasing visuals, however this definitely was not as important as the first two. Encapsulation, readability, and flexibility for future changes were the main focus while writing the program.

## High-level Design
* `Main`
    * Launches `UserInterface` to start program
* `UserInterface`
    * Purpose: Handle user input and change the simulation
    * Creates a pop up to insert a file name
    * Uses the file to configure a simulation and then start the simulation
    * Creates a popup with buttons for controlling the rate of simulation
    * Interactions with other classes
        * `Configuration`
            * Has a `Configuration` instance variable
            * Passes `Configuration` the file name
            * Gets a `Simulation` from the `Configuration`
        * `Simulation`
            * Takes user input to set the speed of `Simulation`
            * Uses `Simulation`'s `step()` and `resetKeyFrame()` methods to control the rate that the simulation is processing
* `Configuration`
    * Purpose: set up an initial start to a `Simulation` from an XML file
    * Takes a filename string in the constructor and parses out the desired Rules class
    * Only public method is `getInitSim()` which returns a simulation based on the filename from the constructor
        * Private methods create a board and initialize the simulation before returning it
    * Interactions with other classes
        * `UserInterface`
            * Instantiated in `UserInterface` and receives the filename from that class
            * Returns the initial simulation to the `UserInterface`
        * `Rules`
            * Initializes a `Rules` subclass based on the XML file
            * Initializes a `Board` with the `Rules`
        * `Board`
            * Initializes a Board with the Rules
            * Fills the Board with the initial states specified by the XML
        * `Simulation`
            * Returns an initial simulation based on what the XML file specified
* `Simulation`
    * Purpose: Control the update of the Model and View; the main Controller class
    * Runs the `step()` function which makes the simulation process 1 cycle and display the result
    * Has a `Board`, `Visualizer`, and `HistoryGraph` instance variables to update the Model and View
    * Has public methods to control the rate of simulation: `step()` and `resetKeyFrame(int fps)`
    * Public methods also allow saving of the current state to an XML and ending the simulation
    * Interactions with other classes:
        * `Board`
            * Has `myBoard` as instance variable to control the Model
            * Calls `updateBoard()` to have the model update based on the current state
        * `Visualizer`
            * Has `myVisualizer` as instance variable to control the View
            * Calls `displayBoard(myBoard)` to update the display based on the results of the Board
* `Visualizer`
    * Purpose: Display the view of the actual cells based on the simulation being run, and the states of each cell
    * Makes a new scene for every 'new' board it sees
    * Creates the view specified by the number of corners in the shape (if it's three, it'll make a triangle; if any other number it should make a square)
    * Uses `Polygon` objects for each cell, so that more shapes can easily be added
* `Board`
    * Purpose: Create an object that contains all of the cells
    * Connects updating the state of the cell to updating the display
    * Interactions with other classes:
        * `addsNeighbors()` and other neighbor methods modify the list of cells that a cell has as its neighbors
        * has `myRules` as an instance variable and interacts with this class to update the states of the cells
        * sets the row number and column number associated with cells
    
* `Rules`
    * Purpose: An abstract class that can be extended to define the specifications of each simulation type
    * tells the cell what state to change and when to change to that stae
    * Interactions with other classes:
        * `areCornersNeighbors()` is called by the board class to determine if the simulation that is being run categorizes corner cells as neighbors
        * `changeStateAndView(Cell cell)`` is called with in sub rules classes to change the state of a given cell
* `Cell`
    * Purpose: Create an object that can store a specific state
    * Interactions with other classes:
        * The board consists of a 2d array of cells
        * rules calls `changeStateAndView` which alters the state of the cell


#### Core Classes
The core classes in this program are the `User Interface`, `Configuration`, `Simulation`, the abstract `Board` and its subclasses, `Cell`, the abstract `Rules` and its subclasses, and the abstract `Visualizer` and its subclasses. `Main` does nothing except launch the `UserInterface`, which in turn takes input to begin the simulation.

## Assumptions that Affect the Design
* User must properly use the `StyleComponents.properties` file
    * As a new simulation is added, add the new colors to the file
* Toroidal and triangular cells do not work, code is commented but if that is specified by the StyleComponents it will not run as a toroidal neighborhood.
* Only 3 or 4 will be entered for number of corners to specify if it is a triangle or a rectangle
* User will not restart without pausing first
* All cells have neighbors


#### Features Affected by Assumptions
* Any styling of the different aspects
* Toroidal, triangular cells
* Specification of shape
* Restarting the program


## New Features HowTo
* **How to add a new way to setup the cells**
    * Create a new XML file with the desired setup
    * In the XMLTagNames.properties file, add in the new tags used in the creation of your XML as well as a way to describe your setup method such as "Quotas" or "Probabilities"
    * Make sure your description of your method is in the <Setup_Level> tag and that the other essential parts to the XML are in there
        * Example:
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <Root>
          <Simulation_Type>Fire</Simulation_Type>
          <Title>fire</Title>
          <Setup_Type> TYPE </Setup_Type> //TYPE = CellList, Probabilities, Quotas
              ...
            <Cells>
                //CellList
                <Row>1 0 0 1 1 1</Row>
                <Row>1 2 0 2 1 1</Row>
                <Row>1 0 2 1 1 1</Row>
                <Row>2 0 0 1 2 1</Row>
                <Row>1 1 0 1 1 1</Row>
                <Row>1 0 2 1 1 1</Row>
    
                //Probabilities
                <Probabilites>
                  <Prob state="0">0.2</Prob>
                  <Prob state="1">0.5</Prob>
                  <Prob state="2">0.3</Prob>
                </Probabilites>
   
                //Quotas
                <Quotas>
                  <Quota state="0">4</Quota>
                  <Quota state="1">20</Quota>
                  <Quota state="2">12</Quota>
                </Quotas>
            </Cells>
            ...
        </Root>
        ``` 
    * Write a method in `Configuration` to parse your XML and initialize the `Board` values with it
    * In `getInitBoard()` add another section to the if else tree checking if the setup type is the one you created and calling your parsing method
* **How to add a new button to the Use Interface**
    * In English.properties create a label for the words on the button
    * Write a method to perform whatever the desired function is on the button click
    * In the `createController()` method, add a new statement: `addButtonToHBox()` and pass it the key to the label you created, an event handler pointing to your function, and the name of the HBox to which it is being added (controls). 
* **How to add a new Shape of `Cell`** 
    * Create a new `Visualizer` class that returns a different double array of points to join to create the shape (Override `getCorners()`), as well as correctly moving from column to column (override `moveOver()`), and row to row (override `moveToNextRow()`)
        * for example:
        ```java
        @Override
        protected Double[] getCorners(){
            return new Double[]{
            x1, y1,
            x2, y2,
            x3, y3,
            // etc
            }
        }
        @Override
        protected void moveOver(){
            // change xPosition
            // any extra stuff specific to that shape
        }
        @Override
        protected void moveToNextRow(){
            // change yPosition
            // reset xPosition
            // any extra stuff specific to that shape
        }
        ```
    * Create a new `Board` class that handles adding neighbors of that shape
    ```java
    @Override
  protected void addNeighborsToCells(Cell[][] cells) {...}
    ```
    
* **How to add a new Simulation** 
In order to create a new simulation to the project, a new class would have to be created that extends rules. The parameter to this class would have to be a HashMap containing the setup parameters and would also have to set the number of possible states. Further, the top of this new class would have to create private static final int values that associate a given state with a number. This class would the have to override change state to detail when a cell should change to a different state and what state the cell should change to. This may require the implementation of other methods depending on the complexity of the simulation. The class would also have to have a method that overrides if corners are neighbors since that is specific to the rules of each simulation. 
    * for example
    ```java
    Public class nameOfSimulation {

 
  private static final int STATE1 = 0;
  private static final int STATE2 = 1;
  private static final int STATE3 = 2;
  private static final int NUMBER_OF_POSSIBLE_STATES = 3;
  
  private HashMap<String, String> parameters;
  public Rules(HashMap<String, String> setupParameters){
    super(setupParameters);
    super.numberOfPossibleStates = NUMBER_OF_POSSIBLE_STATES;
  }
  
  abstract public void changeState(Cell cell, Cell cloneCell);
 
  abstract public boolean areCornersNeighbors();
  ``
After this class is written, an XML file would have to be created that specifies the type of simulation, the title of the simulation, how the initial states of the cells would be determined, the authors of the file, and any parameters. Then the xml file would either contain rows that display the initial cell states, a quota of how many cells there should be for each state, or a probability that a cell is a given state. Lastly, the simulation name with the numbers associated with each state would have to be added to the style components properties file along with the color that is associated with each state. 

#### Other Features not yet Done
- [ ] Simulation
    - [ ] toroidal TRIANGLES
    - [ ] infinite: growing grid
- [ ] Allow any aspect of a simulation to be "styled", such as size of each grid location (instead of it being calculated, requires that scrolling is implemented), and sliders to change probabilities of a simulation