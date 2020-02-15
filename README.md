simulation
====

This project implements a cellular automata simulator.

Names: Dana Mulligan(dmm107), Milan Bhat (mb554), Cayla Schuval (cas169)

### Timeline

Start Date: Tuesday Jan 23rd, 2020

Finish Date: Sunday Feb 9th, 2020

Hours Spent: 140 (total)

### Primary Roles
Milan: Responsible for Main, Simulation, Configuration, User Interface, and error handling.

Dana: Responsible for Visualizer and its subclasses, HistoryGraph, several Rules subclasses, and making sure all classes work smoothly with one another.

Cayla: Responsible for Board and its subclasses, Cell, and Rules and most of its subclasses.

### resources Used
[Creating a Line Chart in javafx](https://docs.oracle.com/javafx/2/charts/line-chart.htm#CIHGBCFI)

### Running the Program

Main class:
`Main.java`

Data files needed: 
Any XMLFiles you wish to run should be in the XMLFiles folder. This folder can also be specified in the XMLTagNames.properties file.


Features implemented:
* Clicking on a cell changes it to a random state
* Several changes can be made to setup in StyleComponents.properties file. These include:
    * Changing colors of simulation cells 
    * Changing shape of cells from square to triangle by changing number of corners from 4 to 3.
    * Defining grid edges to be toroidal or finite
    * Defining what percent of total neighbors you want to consider as actual neighbors
    * Screen dimensions
    * Language
    * Grid line color which is StrokeColor and can be set to null to have no gridlines
* Different neighbor arrangments
* Triangular cells
* Additional simulations
    * RPS
    * Foraging Ants
* Error checking on XML files
* Allow initial settings to be set different ways by changing the setup_type tag in the xml file:
    * CellList: each tag is row and contains the states for that row separated by states
    * Probabilities: each tag is Prob and has an attribute of the state and text content is the probability that the state is made
    * Quotas: each tag is Quota with an attribute for the state and the text content is the total number of each states desired
    * Examples of these files are the 3 Fire files
* Save current board to a runnable xml saved in the XMLFiles folder by clicking save
* Displaying a graph of changing populations over time. The graph appears after the first step
* Create multiple simulations by clicking create new simulation and typing in another file name. Both simulations will have their own controllerers and are controlled entirely separately except for the fact that if one closes they both close. 


### Notes/Assumptions

Assumptions or Simplifications:
* User must properly use the StyleComponents.properties file
* Toroidal and triangular cells do not work, code is commented but if that is specified by the StyleComponents it will not run.
* Only 3 or 4 will be entered for number of corners to specify if it is a triangle or a rectangle
* User will not restart without pausing first

Interesting data files:
* `fire`
* `fireprobabilities`
* `firequotas`
* `percolation`
* `predatorprey`
* `foragingants`
* `percolationprobabilities`

Known Bugs:
* Toroidal neighborhoods in triangle boards do not work
* Restarting without pausing causes the screen to appear again
* GameOfLifeBlinker does not work

Extra credit: NA

### Impressions
This was a very challenging project mostly because of the emphasis on encapsulation. Keeping the Model, Controller, and View separate made us really have to plan out how data would be transfered. Most of our deliberations occurred over how to properly protect data. We are very proud of the final product and it has a lot of capability to be changed by the user and run in different ways. The bugs that come up are in very specific situations but for most circumstances it will run properly. Despite the few issues, we believe that the code is very well designed in a readable and concise manner.