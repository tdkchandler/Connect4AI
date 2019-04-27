# Connect4AI
A Connect 4 GUI which allows a player to play against a competitive AI. 
The AI plays itself after the user chooses a move to find the most optimal move. 
Once the AI finds the optimal move, it plays the move against the player.


The AI class has 4 levels of difficulty a player can play against.
Ranking difficulties from least to greatest:
1. makeChoiceRandom()       // Picks a random available option.
2. makeChoiceSmartRandom()  // Check to see if there is a possible immediate win. 
                            // Will also check which moves will result in an immediate loss.
3. makeChoiceSmart()        // Plays itself 4 moves deep and chooses the option with most chance to win.
4. makeChoiceSmart2()        // Plays itself 6 moves deep and chooses the option with most chance to win.


If ran in Eclipse, the console will display the weighing system after it finishes generating its data set.
It will also print a console connect 4 board which mirrors the GUI.
A sample shown below:
- - - - - - - 
- - - - - - - 
- - 2 - - - - 
- - 1 2 - - - 
- - 1 1 - - - 
- - 2 1 1 - 2 
_____________
1 2 3 4 5 6 7

1 -1442
2 -3054
3 -1028
4 -978
5 282
6 -1740
7 -1030

Above, the a board is displayed. The AI(2) has determined the best move will be in slot 5.
This results in 5 being played, giving the player the following board for their turn:

- - - - - - - 
- - - - - - - 
- - 2 - - - - 
- - 1 2 - - - 
- - 1 1 2 - - 
- - 2 1 1 - 2 
_____________
1 2 3 4 5 6 7


Along with playing with an AI, a player can choose to play against another player on the same local computer.

I have also included a funtion which allows an 2 AI functions to play agains eachother. 
Used to compare dificulties of AI functions.

