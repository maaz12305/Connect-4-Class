package hw3;

public class Board {
	// TODO
	private char[][] table;
	private int count;
	private char player1;
	private char player2;
	private int xCounter;
	private int oCounter;

	/**
	 * Constructs a new empty connect 4 game board with player X being the first player
	 * and player 'O' being the second player.
	 */
	public Board() {
		table = new char[7][6];
		player1 = 'X';
		player2 = 'O';
		count = 0;
	}

	/**
	 * Gets the current player (either 'X' or 'O')
	 * 
	 * @return the current player
	 */
	public char currentPlayer() {
		if (count%2 == 0)
			return player1;
		return player2;
	}

	/**
	 * The current player makes a move in a given column if it is a valid move.
	 * Throws an exception if the game is already over.
	 * 
	 * @param column the column in which to make a move.  For the move to be valid,
	 * the column value must an {@code int} between 1 and 7 inclusive, and
	 * there must have been fewer than 6 moves already made in the given column.
	 * @return {@code true} if the move is valid and false if it is not valid.
	 * @throws RuntimeException if the game is already over.
	 */
	public boolean play(int column) {
		//exception if game is over
		if (gameStatus() == 'X' || gameStatus() == 'O' || gameStatus() == 'D') {
			throw new RuntimeException();
		}
		if (column < 1 || column > 7)
			return false;
		//loop through the column given and inserts currentPlayer in next available slot
		//if column is full, return false
		for (int i = 0; i < 6; i++) {
			if (table[(column - 1)][i] == 'X' || table[(column - 1)][i] == 'O') {
				if (i == 5) {
					return false;
				}
			}
			//if move is valid return true and increase count by 1
			if (table[(column - 1)][i] != 'X' && table[(column - 1)][i] != 'O') {
			table[(column -1)][i] = currentPlayer();
			count++;
			if (currentPlayer()== 'X') {
				xCounter++;
			}
			if (currentPlayer() == 'O') {
				oCounter++;
			}
			return true;
			}
		}
		return false;
	}

	/**
	 * Determine the status of the game.
	 * 
	 * @return {@code 'X'} or {@code 'O'} if either player has won, {@code 'D'} if
	 * the game is a draw, and {@code 'U'} if the game is still undecided.
	 */
	public char gameStatus() {
		//call checkRow, checkColumn, and checkDiagnal
		for (int i = 1; i < 8; i++) {
			if (checkColumn(i) == 'X')
				return 'X';
			if (checkColumn(i) == 'O')
				return 'O';
		}
		for (int i = 1; i < 7; i++) {
			if (checkRow(i) == 'X')
				return 'X';
			if (checkRow(i) == 'O')
				return 'O';
		}
		for (int c = 1; c < 8; c++) {
			for (int r = 1; r < 7; r++) {
				if (checkDiagRight(c, r) == 'X')
					return 'X';
				if (checkDiagRight(c, r) == 'O')
					return 'O';
				
				
			}
		}
		for (int c = 1; c < 8; c++) {
			for (int r = 1; r < 7; r++) {
				if (checkDiagLeft(c, r) == 'X')
					return 'X';
				if (checkDiagLeft(c, r) == 'O')
					return 'O';
			}
		}
		if(boardIsFull()) {
		    return 'D'; 
		  }
		
		return 'U';
	}

	/**
	 * Construct a string that depicts the sate of the game.
	 * (See the writeup for what that string should look like.)
	 * 
	 * @return a string depicting the game board
	 */
	public String toString() {
		String board = "";

	    for (int row = 5; row >= 0; row--) {
	        for (int col = 0; col < 7; col++) {
	            char value = table[col][row];
	            board += value + " ";
	        }
	        board += "\n";
	    }
	    board += "_ _ _ _ _ _ _ \n";
	    board += "1 2 3 4 5 6 7\n";
	    return board;
	}
	private char checkColumn(int column) {
		//loops through column to check for 4 in a row
		int xCount = 0;
		int oCount = 0;
		if (xCounter < 4 && oCounter < 4) {
			return 'U';
		}
		if (xCounter >= 4) {
			for (int i = 0; i < 6; i++) {
				if (table[column - 1][i] == 'X') {
					xCount ++;
					if (xCount >= 4) {
						return 'X';
					}
				}else {
					xCount = 0;
				}
			}
			
		}
		if (oCounter >= 4) {

			for (int i = 0; i < 6; i++) {
				if (table[column - 1][i] == 'O') {
					oCount ++;
					if (oCount >= 4) {
						return 'O';
					}
				}else {
						oCount = 0;
					}
			}
			if (oCount >= 4)
				return 'O';
		}
		//if 4 in a row, return who won: x or o
		//if the game is not over return 'U'
		//repeat for o
		return 'U';
		
		
	}
	private char checkRow(int row) {
		//loops through row to check for 4 in a row
		int xCount = 0;
		int oCount = 0;
		if (xCounter < 4 && oCounter < 4) {
			return 'U';
		}
		if (xCounter >= 4) {
			for (int i = 0; i < 7; i++) {
				if (table[i][row - 1] == 'X') {
					xCount ++;
					if (xCount >= 4) {
						return 'X';
					}
				}else {
					xCount = 0;
				}
			}
			
		}
		if (oCounter >= 4) {

			for (int i = 0; i < 7; i++) {
				if (table[i][row - 1] == 'O') {
					oCount ++;
					if (oCount >= 4) {
						return 'O';
					}
				}else {
						oCount = 0;
					}
			}
			if (oCount >= 4)
				return 'O';
		}
		//if 4 in a row, return who won: x or o
		//if the game is not over return 'U'
		//repeat for o
		return 'U';
		
		
	}
	private char checkDiagRight(int column, int row) {
		//checks for 4 in a row diagonally, starting at column, row going to the right
		int xCount = 0;
		int oCount = 0;
		int maxColumn = 7 - column + 1;
		int maxRow = 6 - row + 1;
		if (table[column - 1][row - 1] == 'X') {
			xCount = 1;
			for (int c = 1, r = 1; c < maxColumn && r < maxRow;c++, r++) {
				if (table[column - 1 + c][row - 1 + r] == 'X') {
					xCount++;
					if (xCount == 4)
						return 'X';
				}else {
					xCount = 0;
				}
			}
		}else if (table[column - 1][row - 1] == 'O') {
			oCount = 1;
			for (int c = 1, r = 1; c < maxColumn && r < maxRow;c++, r++) {
				if (table[column - 1 + c][row - 1 + r] == 'O') {
					oCount++;
					if (oCount == 4)
						return 'O';
				}else {
					oCount = 0;
				}
			}
		}else {
			return 'U';
		}
		return 'U';

	}
	private char checkDiagLeft(int column, int row) {
		//checks for 4 in a row diagonally, starting at column, row going to the left
		int xCount = 0;
		int oCount = 0;
		if (xCounter < 4 && oCounter < 4) {
		    return 'U';
		  }
		for (int c = column - 1, r = row - 1; c >= 0 && r < 6; c--, r++) {
		    
		    if (table[c][r] == 'X') {
		      xCount++;
		      if (xCount >= 4) {
		        return 'X';
		      }
		    } else {
		      xCount = 0;
		    }
		    
		    if (table[c][r] == 'O') {
		      oCount++;
		      if (oCount >= 4) { 
		        return 'O';
		      }
		    } else {
		      oCount = 0;
		    }

		  }

		  return 'U';
	}
	private boolean boardIsFull() {
		int boardCounter = 0;
		  for (int c = 0; c < 7; c++) {
		    for (int r = 0; r < 6; r++) {
		      if (table[c][r] == 'X' || table[c][r] == 'O') {
		    	  boardCounter++;
		      }
		    }
		  }
		  if (boardCounter == 42)
			  return true;
		  return false;
		}
}
