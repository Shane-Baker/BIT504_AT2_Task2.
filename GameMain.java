import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    // Constants for the game 
    public static final int ROWS = 3;     
    public static final int COLS = 3;  
    public static final String TITLE = "Tic Tac Toe";

    // Constants for dimensions used for drawing
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int CELL_PADDING = CELL_SIZE / 6;    
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;    
    public static final int SYMBOL_STROKE_WIDTH = 8;

    /* Declare game object variables */
    private Board board; // the game board

    public enum GameState {
        Playing, Draw, Cross_won, Nought_won
    }
    private GameState currentState; 

    private Player currentPlayer; // the current player
    private JLabel statusBar; // for displaying game status message       

    /** Constructor to setup the UI and game components on the panel */
    public GameMain() {       
        addMouseListener(this);

        // Setup the status bar (JLabel) to display status message       
        statusBar = new JLabel("         ");       
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));       
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));       
        statusBar.setOpaque(true);       
        statusBar.setBackground(Color.LIGHT_GRAY);  

        // Layout of the panel is in BorderLayout
        setLayout(new BorderLayout());       
        add(statusBar, BorderLayout.SOUTH);
        // Account for statusBar height in overall height
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        board = new Board();

        initGame();
    }

    public static void main(String[] args) {
        // Run GUI code in Event Dispatch thread for thread safety.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create a main window to contain the panel
                JFrame frame = new JFrame(TITLE);

                GameMain gameMainPanel = new GameMain();
                frame.add(gameMainPanel);

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.pack();             
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /** Custom painting codes on this JPanel */
    public void paintComponent(Graphics g) {
        // Fill background and set color to white
        super.paintComponent(g);
        setBackground(Color.WHITE);

        // Ask the game board to paint itself
        board.paint(g);

        // Set status bar message
        if (currentState == GameState.Playing) {          
            statusBar.setForeground(Color.BLACK);          
            if (currentPlayer == Player.Cross) {   
                statusBar.setText("X's Turn");
            } else {    
                statusBar.setText("O's Turn");
            }       
        } else if (currentState == GameState.Draw) {          
            statusBar.setForeground(Color.RED);          
            statusBar.setText("It's a Draw! Click to play again.");       
        } else if (currentState == GameState.Cross_won) {          
            statusBar.setForeground(Color.RED);          
            statusBar.setText("'X' Won! Click to play again.");       
        } else if (currentState == GameState.Nought_won) {          
            statusBar.setForeground(Color.RED);          
            statusBar.setText("'O' Won! Click to play again.");       
        }
    }

    /** Initialize the game-board contents and the current status of GameState and Player */
    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {          
            for (int col = 0; col < COLS; ++col) {  
                // All cells empty
                board.cells[row][col].content = Player.Empty;           
            }
        }
        currentState = GameState.Playing;
        currentPlayer = Player.Cross;
    }

    /** After each turn check to see if the current player has won by putting their symbol in that position */
    public void updateGame(Player thePlayer, int row, int col) {
        // Check for win after play
        if (board.hasWon(thePlayer, row, col)) {
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) {
            currentState = GameState.Draw;
        }
        // Otherwise, no change to current state of Playing
    }

    /** Event handler for the mouse click on the JPanel */
    public void mouseClicked(MouseEvent e) {  
        // Get the coordinates of where the click event happened            
        int mouseX = e.getX();             
        int mouseY = e.getY();             
        // Get the row and column clicked             
        int rowSelected = mouseY / CELL_SIZE;             
        int colSelected = mouseX / CELL_SIZE;               

        if (currentState == GameState.Playing) {                
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && 
                board.cells[rowSelected][colSelected].content == Player.Empty) {
                // Move  
                board.cells[rowSelected][colSelected].content = currentPlayer; 
                // Update currentState                  
                updateGame(currentPlayer, rowSelected, colSelected); 
                // Switch player
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            }             
        } else {        
            // Game over and restart              
            initGame();            
        }   
         
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Auto-generated, event not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Auto-generated, event not used
    }
}
