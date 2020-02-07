# REFACTORING_DISCUSSION.md
### Cayla Schuval (cas169), Milan Bhat (mb554), Dana Mulligan (dmm107)


After discussing the results of the design.cs.duke.edu tests and reviewing the static code and refactoring page, we determined that our code is very well designed except for 2 major flaws. The first being that our Cell class, which is part of the model aspect of the project, holds information about it's color and location on the board. This means that the model and view are dependent on each other when they should be separate. To fix this, we have begun refactoring the cell class to no longer extend Rectangle and only contain information about it's state, row, column, and neighbors. This involved a trade-off that allows other classes to loop through the cells in Board and access each one to determine the state. We spent the majority of the time in lab making these changes because it involved changing several classes.

The other major issue is that our error handling is very poor. We did not fully understand how to handle errors until the lessons this week. Based on the static code analysis, this is the most important issue. To fix this, we have begun to talk about how to handle different errors and what different error classes we will need. Based on our discussion, this would take much more time to fix than we had after fixing the previous problem so we decided to make this the first priority outside of class. Instead, with the remaining 15 minutes, we made the following changes:

* Moved a duplicated method in 2 rules subclasses to the Rules abstract class that they inherit
* Removed JavaFx from all the Model classes
* Removed magic numbers from all classes
* Removed unused private variables about debugging from User Interface
* Collapsed a nested if statement
* Changed EMPTY state to OPEN in several rules subclasses because EMPTY is confusing with meaning null