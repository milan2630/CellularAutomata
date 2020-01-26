# Simulation Design Plan
### Team Number 22 
### Milan Bhat (mb554), Cayla Schuval (cas169), Dana Mulligan (dmm107)

## Introduction
The purpose of this assignment is to write a Java program using OpenJFX that can animate a 2 dimensional Cellular Automata simulation, including [spreading fire,](https://www2.cs.duke.edu/courses/spring20/compsci308/assign/02_simulation/nifty/shiflet-fire/) [game of life](https://bitstorm.org/gameoflife/), [percolation](https://www2.cs.duke.edu/courses/compsci308/spring20/assign/02_simulation/PercolationCA.pdf), [segregation](https://www2.cs.duke.edu/courses/compsci308/spring20/assign/02_simulation/nifty/mccown-schelling-model-segregation/), and [predator-prey](https://www2.cs.duke.edu/courses/compsci308/spring20/assign/02_simulation/nifty/scott-wator-world/). The primary design goal of the project is for the program to be able to run the five different simulations by only changing the possible states and probability of changing states associated with each simulation. The program will be designed so that there is a grid of cells where each cell is at a specific state. Based upon the cell's state and the state of its neighbors, the state of the cell will change over time. The states will be displayed through different colors. The user will be able to interact with the simulation through the implementation of buttons that alter the speed, pause, or reset the simulation.

## Overview
The program will contain `Cell`, `Board`, `Rules`, `Configuration`, `Simulation`, `Visualization`, `UserInterface`, and `Main` classes. 

#### Data Flow
The Main class will create a `UserInterface` to accept a filename as an input. The Main class will then use that file to create a `Configuration` with that file. `Configuration` will parse the Board information and create a Board based on the positions and states in the XML file. The configuration will also create a specific `Rules` class based on the type of CA and use these rules to set the colors of the states. The `Main` class then creates a new `Simulation` using the `Board` and `Rules` from `Configuration`. The `Simulation` class initialies a `Visualization` based on the initial board. The `Main` class also changes the `UserInterface` to have buttons for restarting, pausing, continuing, and stepping through the configuration, and an input for the speed of simulation. The `Main` class then runs the `Simulation`'s `step()` function once over every time interval based on the speed specified by the user. The `UserInterface` class is constantly checking for user input and returning that information to the `Main` class which will update how the `step()` function is being called, or if the program needs to be restarted. 

![Class Structure Diagram](https://i.imgur.com/Qe9fkC3.png)


#### Discussion of two different implementations - 2D array versus 2D list
Changing the implementation of the Board from holding a 2D array of Cells to a 2D list of cells is as simple as changing the instance variable type inside `Board` and how the board is actually created within that class. 

pseudo code:
```
making it w/ 2d arrays
for(row, row++)
    for (col, col++)
        myCells[row][col] = new Cell(...)

making it w/ 2d list
for(int boardDiminsions, boardDiminsions++)
    innerlist = new list<cell>
    for(int boardDiminsions, boardDiminsions++)
        innerlist.add(new Cell)
    outerlist.add(innerlist)
```
All looping through the cells will occur inside of `Board`, where Board's methods are (as described in **Design Details**):
* `public Group boardView()`
* `public Board(int numCellsWidth, int numCellHeight)`,
which do not reveal anything about the Board's implementation.



## User Interface

The user interface will be a separate window from the simulation. When the program starts, it will be a text input box that allows the user to input the file with which the program will set up the CA simulation. This screen will remain up until the user inputs a valid file. If an invalid file is provided, the screen will display an error message and ask for another file. While the simulation is running, the interface will have 4 buttons: Pause, Step, Continue, and Reconfigure. Pause will pause the simulation, Step will move forward one generation, Continue will play the simulation again, and Reconfigure will restart the program and allow the user to input a new file. There will also be a numer input box that will allow the user to set the speed of simulation by specifying the number of steps per second.
### Prompting UI
![](https://i.imgur.com/91FTsgr.png)

### UI while simulation is running
![](https://i.imgur.com/LT6ynLt.png)




## Design Details - Components

The program will have `Cell`, `Board`, `Rules`, `Configuration`, `Simulation`, `Visualization`, `UserInterface`, and `Main` classes. These classes are described below. 

**`Cell extends Rectangle`**
The purpos of the `Cell` class will have the following instance variables:
* `private int state` (stores an integer that is representative of the current state of the cell)
* `private Color stateColor` 
    * contains the display color of a cell
* `private int turnsSinceStateChange`
    * keeps track of how long a cell is been in the current state
* `private List<Cell>Neighbors`
    * a list of the cells that have the ability to impact the state of the current cell.

The `Cell` class will have the following public methods:
* ``public Cell(int init_state, Color view, int xPos, int yPos)``
    * used to create a `Cell` object
* ``public Shape getCellView()``
    * gets the color of the cell
* ``public void changeStateAndView(int state)``
    * sets the integer value of state to a new value
    * changes the color of the cell
    * resets the counter of how long it has been since the state of the cell change
  
**`Board`**
The purpose of the `Board` class is to set up a 2-dimensional array of cells. The Board class will have the following instance variables:
* `Cell[][] myCells`
    * stores a 2 dimensional array of cells
* `int rows`
    * integer value of the number of rows of cells
* `int columns`
    * integer value of the number of columns of cells

The `Board` class will have the following public methods:
* `public Group boardView()`
    * returns a group that can be passed to the visualizer
* `public Board(int numCellsWidth, int numCellHeight)`
    * creates a 2d `Cell` array or 2d `Cell` List of size `numCells`

**`Rules`**
The `Rules` class will be an abstract class. There will be subclasses that inherit rules. These classes wil be:
* `GameOfLife`
* `Percolation`
* `Segregation`
* `PredatorPrey`
* `Fire`
The `Rules` class will hold the rules that state what stages changes to another state based upon the simulation type.It will also contain a list of colors that are associated with each state. The `Rules` class will have the following public method:
* `public abstract void changeState(Cell)`
    * Takes in a cell and alters it based on the CA


**`Configuration`**
    The purpose of the `Configuration` class is to make a board based upon the initial states that are given in an XML file and to set the rules based upon the type of game. The Configuration class will have the following instance variable
* `File myXML`
    * contains the intial state of all of the cells, the type of simulation, and the width and height of the grid

The `Configuration` class has the following public methods:
* `public Configuration(File)`
    * creates a Configuration based upon the information in the file
* `Board getBoard()`
    * Parses cell initial states and creates a board
* `Rules getRules()`
    * Returns Rules class based on CA type


**`Simulation`**
The purpose of the Simulation class is in charge of changing the state of each cell, aka running through cycles. The Simulation class has the following instance variables:
* `Board myBoard`
* `Visualizer myVisualizer`
* `Rules myRules`

The Simulation class has the following public methods:
* `public Simulation(Board b, Rules r)`
    * creates a Simumulation based upon the given board and the rules
* `void step()`
    * Changes the states of the cells in `myBoard` based on `myRules`
    * Updates `myVisualizer` based upon the updated board
* `void display()`
    * `myVisualizer` sets stage based on `myBoard`

**`Visualizer`**
The purpose of the `Visualizer` class is to set up the display. This includes getting a group containing all of state views of the cells, adding the group to a new scene, and setting the stage to the new scene. The visualizer has the following instance variables:
* `Group root`
* `Stage myStage`
* `Scene myScene`

The `Visualizer` class has the following public methods:
* ``public Visualizer()``
    * creates a blank stage
* ``public void updateDisplay()``
    * update the scene and stage based on the root
* `public void setGroup(Board)`
    * sets the group based on the state of the board


**`UserInterface`**
The purpose of the `UserInterface` class is to control the user inputs. The UserInterface class has the following instance variables:
* `Group root`
* `Stage inputStage`

The `UserInterface` class contains the following methods:
* ``public void setFileInputScreen()``
    * creates the initial display for the user controls
* ``String getFileInput()``
    * gets string from user saying which simulation they want to display
* `public void setButtonScreen()`
    * creates and displays a button on the screen
       
**`Main`**
The purpose of the `Main` class will be to get the XML file name input from user. Main will then create a configuration based on a xml file, a simulation based on the board and rules from the configuration. It will also repeatedly runs simulation.step() and start the user interface stage. Main will have the following instance variables:
* ``Configuration myConfiguration``
* ``Simulation mySimulation``
* ``Visualization myVisualization``

#### Use Cases
* Apply the rules to a middle cell
    * In simulation.step(), it loops through the cells in Board and updates their status based on the rules, the cell itself, and their neighbors
* Apply the rules to an edge cell
    * The cells each store their neighbors and the rules will be able to handle if a cell has less than the maximum amount of neighbors
* Move to the next generation
    * Run simulation.step() which updates all the cells
* Set a simulation parameter
    * The Configuration class will parse all the information from the XML and create a rules class based on it. If the CA is Fire, it will know to look for a parameter in the XML for the probabilty probCatch and set the rules' value to that desired one.
* Switch simulations
    * Clicking on the "Reconfigure" button on the user interface will restart the program and allow a new simulation to be loaded


## Design Considerations
Before we make a complete design, we want to make sure that we understand how to make the configuration (creating the neighborhood of a cell, and making sure the rules are correct). We discussed at length how to do this, and have currently settled on making a `Rules` class that controls how each cell updates based on the specific CA that is being simulated. 

We discussed two different methods of updating the state of a cell:
```
board -> rules -> cells
board cycles through and updates each cell's state by calling Rules,
where Rules knows what state comes next

board -> cells (using rules)
board cycles through and updates each cell's state individually,
where cell knows what state comes next
```

While the first design would create a dependancy between `Rules` and `Cell`, it would allow us to create one Cell class that can be easily adapted to different Cellular Automata simulations. `Rules` would be an abstract class that has different methods specific to each CA (eg. SpreadingFireRules could have a method ``private boolean treeBurns()`` that returns `true` if a random number generated from 0 to 1 is less than the probability of a tree catching fire) and abstract methods that have to be overwritten (such as `public void updateCell(Cell c)`). `Rules` would then depend on a `Cell`, and would need to be able to see a cell's state in order to direct it to the correct following state. Unfortunately that would require us to reveal the implementation of `Cell`, which is something that we'd like to avoid. 

The alternative design would be to have `Cell` be an abstract class with a method such as `public void updateCellState(boolean change)`, that needs to be overwritten. Inside of that, it would change from one state to another if `change` was true. This way, `Cell`'s implementation would not need to be revealed to any other classes.

For example (thinking about the starting fire CA):
Rules would have a method that returns `boolean` if a tree should burn, such as `treeBurns()`

pseudo code:
```
board->cell
cell.updateCellState(myRules.treeBurns());

inside Cell:
public void updateCellState(boolean change){
    if(change){
        if(state=tree) state=burning
        if(state=burning) state=ground
    }
}
```
but, this would cause issues if one state was able to go to multiple different state depending on the conditions of its neighbors. This could be solved by adding extra parameters, but that would have to be extended through each class. We would end up with several if statements and conditions, and it could get messy quickly. Additionally, we would still need to have specific `Rules` classes so that the correct conditions could be passed in, and we wouldn't be able to get that without *knowing the states of all of the neighbors*, so the state would actually need to be revealed after all.

Because of this, we have decided that we are going to try and implement the first design, as the `Cell`'s implementation is going to be revealed regardless, and this is the design that makes the most sense to us logically.


## Team Responsibilities
 * Team Member #1: Milan
    * Primary Responsibilities
        * `Configuration`
        * `Main`
        * `UserInterface`
        * `Rules`
    * Secondary Responsibilities
        * `Cell` 
        * `Board`
 * Team Member #2: Cayla
    * Primary Responsibilities
        * `Cell`
        * `Board`
        * `Rules`
    * Secondary Responsibilities
        * `Visualizer`
        * `Simulation`
 * Team Member #3: Dana
    * Primary Responsibilities
        * `Visualizer`
        * `Simulation`
        * `Rules`
    * Secondary Responsibilities
        * `Configuration`
        * `Main`
        * `UserInterface`