import java.awt.*;

public class Board {
	// grid line width
	public static final int GRID_WIDTH = 8;
	// grid line half width
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;
	
	//2D array of ROWS-by-COLS Cell instances
	Cell [][] cells;
	
	/** Constructor to create the game board */
	public Board() {
		
	 //TODO: initialise the cells array using ROWS and COLS constants 

		
		for (int row = 0; row < GameMain.ROWS; ++row) {
			for (int col = 0; col < GameMain.COLS; ++col) {
				cells[row][col] = new Cell(row, col);
			}
		}
	}
	

	 /** Return true if it is a draw (i.e., no more EMPTY cells) */ 
public boolean isDraw() {
    // Check if there are any empty cells left
    for (int row = 0; row < GameMain.ROWS; ++row) {
        for (int col = 0; col < GameMain.COLS; ++col) {
            if (cells[row][col].getContent() == Player.EMPTY) {
                return false;  // Found an empty cell, game is not a draw
            }
        }
    }
    return true;  // All cells are filled, game is a draw
}

	
	/** Return true if the current player "thePlayer" has won after making their move  */
public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
    // Check row
    if (cells[playerRow][0].getContent() == thePlayer &&
        cells[playerRow][1].getContent() == thePlayer &&
        cells[playerRow][2].getContent() == thePlayer) {
        return true;
    }
    
    // Check column
    if (cells[0][playerCol].getContent() == thePlayer &&
        cells[1][playerCol].getContent() == thePlayer &&
        cells[2][playerCol].getContent() == thePlayer) {
        return true;
    }
    
    // Check diagonal (top-left to bottom-right)
    if (cells[0][0].getContent() == thePlayer &&
        cells[1][1].getContent() == thePlayer &&
        cells[2][2].getContent() == thePlayer) {
        return true;
    }
    
    // Check diagonal (top-right to bottom-left)
    if (cells[0][2].getContent() == thePlayer &&
        cells[1][1].getContent() == thePlayer &&
        cells[2][0].getContent() == thePlayer) {
        return true;
    }
    
    return false;  // No winning condition found
}

	
	/**
	 * Draws the grid (rows then columns) using constant sizes, then call on the
	 * Cells to paint themselves into the grid
	 */
public void paint(Graphics g) {
    // Draw grid lines
    g.setColor(Color.gray);
    for (int row = 1; row < GameMain.ROWS; ++row) {
        g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF,
                GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                GRID_WIDTH, GRID_WIDTH);
    }
    for (int col = 1; col < GameMain.COLS; ++col) {
        g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0,
                GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                GRID_WIDTH, GRID_WIDTH);
    }
    
    // Draw cells
    for (int row = 0; row < GameMain.ROWS; ++row) {
        for (int col = 0; col < GameMain.COLS; ++col) {
            cells[row][col].paint(g);
        }
    }
}

