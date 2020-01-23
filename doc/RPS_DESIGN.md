RPS_DESIGN.md

Names: Dana Mulligan (dmm107), Milan Bhat (mb554), Cayla Schuval (cas169)


Classes:

* Main
    * Creates a new game from a text file with weapon rules, an array of player names from user input, and a number of rounds for the game
* Game 
    * Class Purpose:
        * manages game play
        * makes the players 
        * Makes the weapons from text file
        * tells the results after the play has happened
    * Private variables:
        * ``List<Player> activePlayers``
        * ``int numRounds``
    * Public methods:
        * ``public Game(File weaponRules, int numberOfGames, String[] playerNames)``
            * makes a new list of new Weapons from reading the file
            * sets ``numRounds`` to ``numberOfGames``
            * makes a new list of players that gets the choices of weapons
        * ``public void playRound()``
            * Calls ``.makeMove()`` on each player to return a weapon for each player 
            * calls ``thisBeats()`` on the weapons to determine which weapon is superior
            * Updates scores by calling ``.updateScore()`` on each player
            * Prints winner and game status by calling ``printPointStatus()`` on each Player in activePlayers
            * ``numRounds--;``
        * ``public void weaponFileUpdated(File weaponRules)``
            * update defeats list in each of the weapons by calling ``Weapon.updateDefeats``
            * update each player with the new weapon by calling ``Player.updateChoices()``
    * Collaborating classes:
        * Makes a list of Weapons
        * Makes a list of Players

* Player
    * Purpose:
        * A player choses a weapon for a move, and keeps track of how many points it has
    * Private variables:
        * ``int score``
        * ``String playerName``
        * ``Weapon move``
        * ``List<Weapon> weaponChoices``
    * Public methods:
        * ``public Player(List<Weapon> choices, String name)``
            * set ``score`` to 0
            * set ``playerName`` to ``name``
            * set ``weaponChoices`` to ``choices``
        * ``public Weapon makeMove()``
            * choses a weapon randomly from the list of choices
            * Can be altered to create a "smart" player with strategy behind choice of weapon
            * returns the player's Weapon
        * ``public void updateScore(int points)``
            * add points to score
        * ``public void updateChoices(Weapon additionalWeapon)``
            * add ``additionalWeapon`` to ``weaponChoices``
        * ``public void printPointStatus``
            * print ``playerName`` and ``score``
    * Collaborating classes:
        *  Game calls ``makeMove``
        *  ``makeMove`` returns a ``Weapon``
    
* Weapon
    * Class Purpose:
        * Holds information about each weapon relative to the rest 
    * Private variables:
        * ``String myWeapon``
        * ``List<Weapon> defeats``
            * List of weapons that this weapon defeats
    * Public methods:
        * ``public Weapon(String weapon, List<Weapon> thisBeats)``
            * Initializes instance variables
        * ``public boolean thisBeats(Weapon otherWeapon)``
            * returns ``defeats.contains(otherWeapon)``
        * ``public void updateDefeats(List<Weapon> newThisBeats)``
            * update ``defeats`` if the weapons rules text file is changed
    * Collaborating classes:
        * Game calls ``thisBeats``