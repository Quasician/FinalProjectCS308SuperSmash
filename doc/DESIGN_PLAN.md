Two main components

* Player class
    * Moves
    * Stocks (lives)
    * Stamina
    * Character class
        * Has basic moves: (Up, down side) attacks, side-to-side movement, and jumping
* Stage class
    * Boundaries
    * Background
        * Static or dynamic image 
    * Foreground class
        * Platforms  
        
* Menu Screen
    * Character Selection screen
    * Stage Selection Screen
    * Game Screen
        * Results screen
        * Once finished go back to character selection screen
        
* Overall UI flow
    * SelectionScreen class (Abstract)
        * CharacterScreen class
        * StageScreen class
    * GameScreenVis class (bound)
    * GameScreenModel class (bound)