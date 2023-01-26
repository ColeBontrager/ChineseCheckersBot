All Code Written in JAVA
Chinese Checkers Bot using minimax algorithm
Run TestBoard.java to demo project

CLASSES:

#BOARD#

This class represents a single board state in a game of Chinese Checkers

Member Variables:

-Space grid[][]
	2D array of Spaces representing position of each hole and piece on the board

-List<Coordinate> player1Positions
-List<Coordinate> player2Positions
	Stores positions of each player's pieces on the board for use with the heuristic
	functions

-int playerTurn
	Tracks whose turn it is

-int winState
	Set to 1 or 2 if player 1 or 2 has won the game, set to 0 if game is not complete

Functions:

-Board()
	Creates a fresh board with player 1 starting the game

-Board(Space[][] grid, int playerTurn)
	Creates board with given grid, used by the makeMove function

-Board(Coordinate[] p1Pos, Coordinate[] p2Pos, int playerTurn)
	Creates board with specified positions of each player's pieces. Useful for testing
	specific piece configurations

-void printBoard()
	Prints string representation of board with rows marked to aid user input

-void updatePlayerPositions()
	Iterates through grid and stores the positions of each player's pieces

-void updateWinState()
	Checks if a player has won the game and updates winState variable accordingly

-boolean inBounds()
	Returns if given coordinate is a valid position within the grid

-Coordinate toGridPos()
	Given the coordinate of a piece given by a user, returns coordinate representing
	position within the grid. Returns null if invalid coordinate is given

-List<Coordinate> getAdjMoves()
	Given the position of a piece on the grid, returns all empty adjacent positions
	that the piece could move to.

-List<Coordinate> getHopMoves()
	Given the position of a piece on the grid, return all positions the piece can
	reach by hopping over an adjacent piece

-List<Coordinate> validMoves()
	Given the position of a piece on the grid, returns all valid positions that piece
	can move to

-List<Move> getAllValidMoves()
	Returns list of all valid moves the player whose turn it is can make

-Board makeMove()
	Returns new board where the piece at the given start position has been moved to the 
	given end position. Throws exception if the given positions do not result in a legal move

	
#SPACE#

The Space class represents one hole on the board

Member Variables:

-int player
	Represents if a player's piece is currently occupying the position. Set to 0 if empty

-int home
	Represents if this Space is a part of a players home. Set to 0 if not


#COORDINATE#

The coordinate class store the position of a space on the board. This class is hashable so 
AI players can remember previously visited piece configurations.

Member Variables:

-int row
	Stores row of space

-int col
	Stores column of space


#MOVE#

The move class represents a potential move that can be made on a board

Member Variables:

-Cooridinate startPos
	Starting position of piece to be moved

-Coordinate endPos
	Position where piece will be moved to

-Integer score
	Represents evaluation score of board resulting from the move


#HEURISTIC#

The heuristic class is an interface. All classes which can be passed to minimax in order to
evaluate board states must implement this class.

Functions:

-evaluateBoard()
	Returns integer score representing desirability of given board state. More positive values
	represent states more desirable for player 1 and more negative values represent states
	more desirable for player 2.


#VERTICALDISPLACEMENT#

The VerticalDisplacement class represents a heuristic used by the minimax function to evaluate
board states and implements the Heuristic interface.

Functions:

-evaluateBoard()
	Returns integer representing desirability of given board state. Calculates score based
	on each piece's distance from the end of the board with the opponent's home. This
	prioritizes moving pieces that can move the farthest forward in a single turn.


#VERTICALHORIZONTALDISPLACEMENT#

The VerticalHorizontalDisplacement class represents a heuristic used by the minimax function to evaluate
board states and implements the Heuristic interface.

Member Variables:

-int weight
	Weight can be specified when instantiating this class and represents the importance of
	the vertical distance of a piece when evaluating a board state.

Functions:

-evaluateBoard()
	Returns integer representing desirability of given board state. Calculates score based
	on each piece's distance from the end of the board with the opponent's home and the its
	distance from the center of the board. This prioritizes moving pieces that can move the 
	farthest forward in a single turn while staying close to the center.

#BACKPIECESTRATEGY#

The BackPieceStrategy class represents a heuristic used by the minimax function to evaluate
board states and implements the Heuristic interface.

Functions:

-evaluateBoard()
	Returns integer representing desirability of given board state. Calculates score based
	on the square of each piece's distance from the end of the board with the opponent's home.
	This prioritizes moving pieces which are closer to the player's home.
 

#TESTBOARD#

The TestBoard class is used to facilitate games of Chinese Checkers between AI and/or users. 

Member Variables:

-int player1WinScore
	Constant integer that represents the evaluation score of a board state where player 1
	has won the game.

-int player2WinScore
	Constant integer that represents the evaluation score of a board state where player 2
	has won the game.

Functions:

-void TestGames()
	TestGames prints the average time and moves taken to complete the given number of games
	between two AIs using minimax with the given heuristics, searching to the given depth limit.
	The number of random moves taken at the beginning of each game can also be specified.

-int AIPlayers
	AIPlayers completes one game between two AIs using minimax with the given heuristics, 
	searching to the given depth limit. The number of random moves taken at the beginning
	of each game can be specified and is used to simulate random board states in different
	stages of a Chinese Checkers game. Random moves will always result in a piece being
	moved forward towards the opponents goal if one is available. The total number of moves
	taken during the game is returned.

-void humanVSAI()
	This function takes user input and allows a human player to play a game of Chinese Checkers
	against an AI using the minimax algorithm to completion. The heuristic function and depth
	limit of the AI opponent must be specified.
	
-void humanPlayers()
	This function takes user input and allows two human player to play a game of Chinese Checkers
	to completion. The current player is prompted to input a move until a valid move is entered.

-Move miniMax()
	Performs minimax algorithm with alpha beta pruning on given board state and returns the best
	available move. The depth limit and heuristic used to evaluate board states can be set.
	Also takes two HashSets storing previous piece configurations for each player. Moves
	that result in a previous piece configuration are not considered.

-Move max()
	Helper function used by miniMax() that returns the best available move for the maximizing player

-Move min()
	Helper function used by miniMax() that returns the best available move for the minimizing player