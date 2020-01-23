# DESIGN_EXERCISE.md

Dana doesn't have a Game Class... that's the first thing.

#### Adding powerups
Instead of writing a bunch of if statements to determine the type of ``PowerUp``, each ``PowerUp`` and ``Penalty`` should be a subclass of the superclass ``Package``, where the ``Package`` can 
*  ``public void move()``
*  ``public Group getNode()``
    *  returns the Group of the package to add to root in Main
* ``public void dropPackage()``


sorry we spent a lot of time working on rps design!