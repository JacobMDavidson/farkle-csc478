package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * The primary graphic user interface class for the Farkle application, representing the view
 * in a model view controller architecture. 
 * @author Curtis Brown
 * @version 3.0.0
 */
public class FarkleUI extends JFrame {

	/** Serialize this to comply with component conventions */
	private static final long serialVersionUID = 1L;
	
	/** The array of Die objects */
	private Die[] dice = new Die[6];
	
	/** The "Roll Dice" button */
	private JButton rollBtn = new JButton("Roll Dice");
	
	/** The "Bank Score" button */
	private JButton bankBtn = new JButton("Bank Score");
	
	/** The "Select All" button */
	private JButton selectAllBtn = new JButton("Select All");
	
	/** The controller for the Farkle application */
	private FarkleController controller;

	/** Player one label */
	private JLabel player1NameLabel = new JLabel("Player 1: ");
	
	/** Player one's name */
	private JLabel player1Name = new JLabel("");
	
	/** Player two label */
	private JLabel player2NameLabel = new JLabel("Player 2: ");
	
	/** Player two's name */
	private JLabel player2Name = new JLabel("");
	
	/** Player one turn score number labels */
	private ArrayList<JLabel> player1ScoreLabels = new ArrayList<JLabel>();
	
	/** Player two turn score number labels */
	private ArrayList<JLabel> player2ScoreLabels = new ArrayList<JLabel>();
	
	/** Player one turn scores */
	private ArrayList<JLabel> player1Scores = new ArrayList<JLabel>();
	
	/** Player two turn scores */
	private ArrayList<JLabel> player2Scores = new ArrayList<JLabel>();
	
	/** Player one game score label */
	private JLabel player1GameScoreLabel = new JLabel("Total Score: ");
	
	/** Player one game score */
	private JLabel player1GameScore = new JLabel("0");
	
	/** Player two game score label */
	private JLabel player2GameScoreLabel = new JLabel("Total Score: ");
	
	/** Player two game score */
	private JLabel player2GameScore = new JLabel("0");
	
	/** High score label */
	private JLabel highScoreTitle = new JLabel("High Score: ");
	
	/** High score */
	private JLabel highScore = new JLabel();
	
	/** Score for selected dice */
	private JLabel runningScore = new JLabel("0");
	
	/** Score for the current roll */
	private JLabel rollScore = new JLabel("0");
	
	/** Sounds used during the animated roll */
	private ArrayList<URL> rollSounds = new ArrayList<URL>();
	
	/** Sounds used during the banking action */
	private ArrayList<URL> bankSounds = new ArrayList<URL>();
	
	/** Sound used indicating a bonus turn was earned */
	private URL bonusSound;
	
	/** AudioStream for played sounds */
	private AudioInputStream audioStream = null;
	
	/** Standard background color used */
	private Color greenBackground = new Color(35, 119, 34);
	
	/** Farkle message */
	private JDialog farkleMessage = new FarkleMessage();
	
	/** Player one scores panel */
	private JPanel player1ScorePanel = null;
	
	/** Player one scores scroll bar */
	private JScrollBar player1ScrollBar = null;
	
	/** Player two scores panel */
	private JPanel player2ScorePanel = null;
	
	/** Player two scores scroll bar */
	private JScrollBar player2ScrollBar = null;
	
	/** Flag set to indicate this is the first time the game is initialized */
	private boolean isFirstRun = true;
	
	/** Flag used for testing only */
	private boolean isTest;

	/**
	 * Constructor: Get a reference to the controller object and fire up the UI
	 * 
	 * @param f the farkle controller used for the application
	 */
	public FarkleUI(FarkleController f) {
		
		// Set the reference to the farkle controller
		controller = f;
		
		// Determine if this instance is used for testing
		isTest = controller.isTest;
		
		// Initialize the user interface
		initUI();
	}

	/**
	 * Build the UI base
	 */
	public void initUI() {
		/* If this is the first run, pass a reference to the controller 
		 * and build the window.
		 */ 
		if (isFirstRun) {
			
			// Toggle the isFirstRun flag
			isFirstRun = false;
			
			// Pass the reference to the controller
			controller.setUI(this);

			// Instantiate the necessary sounds
			getSounds();
			
			// Create and set up the main Window
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setPreferredSize(new Dimension(1024, 768));
			this.setResizable(false);
			
			// Pack the main window
			this.pack();

			// Center and display the window
			this.setLocationRelativeTo(null);
		}

		/* Remove all components from this container which may exist
		 * if it is not the first init() call
		 */
		this.getContentPane().removeAll();
		if(null != player1ScoreLabels) {
			player1ScoreLabels.clear();
		}
		
		if(null != player1Scores) {
			player1Scores.clear();
		}
		
		if(null != player2ScoreLabels) {
			player2ScoreLabels.clear();
		}
		
		if(null!= player2Scores) {
			player2Scores.clear();
		}

		// Create the layout for the window
		GridLayout layout = new GridLayout(1, 3, 10, 10);

		// Hide the grid lines
		layout.setHgap(0);
		layout.setVgap(0);
		
		// Set the layout and background color
		this.setLayout(layout);
		this.getContentPane().setBackground(greenBackground);
		
		// Enable the window
		this.setEnabled(true);
		
		// If this is not a test, make the window visible
		if(isTest == false) {
			this.setVisible(true);
		
		// Else, this is a test instance so hide the window
		} else {
			this.setVisible(false);
		}
		
		// Add the menu bar
		this.setJMenuBar(createFarkleMenuBar());

		// If this is not a test, create a new game
		if(isTest == false) {
			controller.newGame(null);
		}
		
		// Pack the window
		pack();
	}
	

	/**
	 * Create a JPanel that contains the "Roll Dice", "Select All", and "Bank Score" buttons 
	 * and attach a Listener to each
	 * 
	 * @return the created JPanel with the three buttons
	 */
	public JPanel[] createButtonPanel() {
		
		// An array of JPanels each of which holds a button
		JPanel buttonPanels[] = { new JPanel(), new JPanel(), new JPanel() };

		/********************************************
		 * 1.2.4 - A “Roll Dice” button shall be displayed below the dice in the
		 * middle of the screen.
		 ********************************************/
		
		// Add an action listener to the "Roll Dice" button
		if (rollBtn.getActionListeners().length == 0) {
			rollBtn.addActionListener(controller);
		}
		
		// Set the preferred size and center the button
		rollBtn.setPreferredSize(bankBtn.getMinimumSize());
		rollBtn.setAlignmentX(CENTER_ALIGNMENT);
		rollBtn.setAlignmentY(CENTER_ALIGNMENT);
		
		// Add the button and set the background color
		buttonPanels[0].add(rollBtn);
		buttonPanels[0].setBackground(greenBackground);

		/***************************************************
		 * 1.2.8 - A “Select All” button shall be displayed below the dice in
		 * the middle of the screen, and shall be initially disabled.
		 ***************************************************/
		
		// Add an action listener to the "Select All" button
		if (selectAllBtn.getActionListeners().length == 0) {
			selectAllBtn.addActionListener(controller);
		}
		
		// Set the preferred size and center the button
		selectAllBtn.setPreferredSize(bankBtn.getMinimumSize());
		selectAllBtn.setAlignmentX(CENTER_ALIGNMENT);
		selectAllBtn.setAlignmentY(CENTER_ALIGNMENT);
		
		// Add the button and set the background color
		buttonPanels[1].add(selectAllBtn);
		buttonPanels[1].setBackground(greenBackground);
		
		// Disable the button
		selectAllBtn.setEnabled(false);

		/********************************************
		 * 1.2.5: A “Bank Score” button shall be displayed below the dice in the
		 * middle of the screen, (and shall initially be disabled).
		 ********************************************/
		// Add an action listener to the "Bank Score" button
		if (bankBtn.getActionListeners().length == 0) {
			bankBtn.addActionListener(controller);
		}
		
		// Set the preferred size and center the button
		bankBtn.setPreferredSize (bankBtn.getMinimumSize());
		bankBtn.setAlignmentX(CENTER_ALIGNMENT);
		bankBtn.setAlignmentY(CENTER_ALIGNMENT);
		
		// Add the button and set the background color
		buttonPanels[2].add(bankBtn);
		buttonPanels[2].setBackground(greenBackground);
		
		// Disable the button
		getBankBtn().setEnabled(false);
		
		// Return the buttons
		return buttonPanels;
	}

	/**
	 * Combine the diceHeader and diceGrid panels in to a single panel which can
	 * be added to the frame
	 */
	public void buildDicePanel() {
		
		// Create the dice header panel
		JPanel diceHeaderPanel = createDiceHeaderPanel();
		
		// Create the dice grid panel
		JPanel diceGridPanel = createDiceGridPanel();
		
		// Create a new panel that will combine the header and grid panels
		JPanel dicePanel = new JPanel();
		
		// Set the layout of the panel
		dicePanel.setLayout(new BoxLayout(dicePanel, BoxLayout.Y_AXIS));
		
		// Add a border
		diceHeaderPanel.setBorder(BorderFactory
				.createLineBorder(Color.WHITE, 3));
		
		// Add the dice header
		dicePanel.add(diceHeaderPanel);
		
		// Set the preferred size of the dice grid panel and add it
		diceGridPanel.setPreferredSize(diceGridPanel.getMaximumSize());
		dicePanel.add(diceGridPanel);
		
		// Add the dice panel to this frame
		this.add(dicePanel);
	}

	/********************************************************************************
	 * 1.2.2 - The total turn score shall be displayed above the dice in the
	 * center of the screen, and the score for the selected dice of the current
	 * roll shall be displayed directly below the turn score in the center of the 
	 * screen. This score shall be updated as each die is selected.
	 ********************************************************************************/

	/**
	 * Create a JPanel to hold the Turn Score, and Roll Score labels and their
	 * corresponding values which will be displayed above the dice in the center
	 * of the screen.
	 * @return JPanel of the information displayed above the dice
	 */
	public JPanel createDiceHeaderPanel() {
		
		// Instantiate the JPanel for the dice header
		JPanel diceHeaderPanel = new JPanel(new GridLayout(0, 2, 0, 0));
		
		// JLabels for the turn score and roll score
		JLabel turnScore = new JLabel("<html>Turn Score: </html>");
		JLabel rollScoreLabel = new JLabel("<html>Roll Score: </html>");
		
		// Set the font for the turn score label
		turnScore.setForeground(Color.WHITE);
		turnScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Create a border for the turn score label
		turnScore.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(15, 3, 15, 0)));
		
		// Add the turn score label to the panel
		diceHeaderPanel.add(turnScore);
		
		// Set the font for the running turn score and center it
		runningScore.setForeground(Color.WHITE);
		runningScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		runningScore.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set the border for the running turn score
		runningScore.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(15, 0, 15, 3)));
		
		// Add the running turn score to the header panel
		diceHeaderPanel.add(runningScore);
		
		// Set the font for the roll score label
		rollScoreLabel.setForeground(Color.WHITE);
		rollScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Set the border for the roll score label
		rollScoreLabel.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0,
				Color.WHITE));
		rollScoreLabel.setBorder(BorderFactory.createEmptyBorder(15, 3, 15, 0));
		
		// Add the roll score label to the header panel
		diceHeaderPanel.add(rollScoreLabel);
		
		// Set the font for the roll score and center it
		rollScore.setForeground(Color.WHITE);
		rollScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		rollScore.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set the border for the roll score
		rollScore.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3,
				Color.WHITE));
		rollScore.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 3));
		
		// Add the roll score to the header panel
		diceHeaderPanel.add(rollScore);
		
		// Set the background color for the header panel and return it
		diceHeaderPanel.setBackground(greenBackground);
		return diceHeaderPanel;
	}

	/*******************************************************************************
	 * 1.2.1 The center of the screen shall display the six dice used
	 * during gameplay. These dice shall display the name of the game, "Farkle",
	 * until the user selects the roll button for the first time.
	 *******************************************************************************/
	/**
	 * Create a JPanel with six Dice, the running score JLabels and the Roll and
	 * Bank buttons
	 * 
	 * @return
	 */
	public JPanel createDiceGridPanel() {
		
		/***********************************************
		 * 3.0.0: Dice
		 ***********************************************/
		// Create the panel
		JPanel dicePanel = new JPanel(new GridLayout(0, 3, 0, 0));

		/***********************************************
		 * 3.1.0: Farkle is played with six standard 6 sided dice with each side
		 * numbered from 1 through 6 (inclusive).
		 ***********************************************/

		// Initialize the dice and add to panel
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die(controller);
			dicePanel.add(new JLabel(" "));
			dicePanel.add(dice[i]);
			dice[i].setHorizontalAlignment(SwingConstants.CENTER);
			dicePanel.add(new JLabel(" "));
		}
		
		// Add some spacing below the dice
		dicePanel.add(new JLabel(" "));
		dicePanel.add(new JLabel(" "));
		dicePanel.add(new JLabel(" "));

		// Add the buttons below the dice
		JPanel btns[] = createButtonPanel();
		dicePanel.add(btns[0]);
		dicePanel.add(btns[1]);
		dicePanel.add(btns[2]);
		
		// Set the background color of the dice panel
		dicePanel.setBackground(greenBackground);
		
		// Add a border to the panel
		dicePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 3),
				BorderFactory.createEmptyBorder(3, 10, 3, 10)));

		// Return the created panel
		return dicePanel;
	}

	/**
	 * 
	 * @param gameMode The selected game mode (GameMode.SINGLEPLAYER or GameMode.MULTIPLAYER)
	 */
	public void buildPlayerPanel(GameMode gameMode) {
		
		// Create the main player panel
		JPanel playersPanel = new JPanel();
		
		// Set the layout and border for the panel
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
		playersPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

		/*************************************************
		 * 1.3.0 - One player mode graphic user interface
		 *************************************************/
		// If the selected game mode is GameMode.SINGLEPLAYER
		if (null != gameMode && gameMode == GameMode.SINGLEPLAYER) {
			
			/******************************************************
			 * 1.3.6 - The top of the left hand side of the screen shall display
			 * “Player: “, along with the provided name of the player.
			 ******************************************************/
			
			// Create the scroll pane for player 1 and add an empty border
			JScrollPane player1ScrollPanel = createPlayerScorePanel(1, 10);
			player1ScrollBar = player1ScrollPanel.getVerticalScrollBar();
			player1ScrollPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));
			
			// Create the player 1 name panel
			JPanel player1NamePanel = createPlayerNamePanel(1);
			
			// Set the border for the player 1 name panel
			player1NamePanel.setBorder(BorderFactory
					.createMatteBorder(0, 0, 6, 0, Color.WHITE));
			
			// Add the player 1 name panel to the players panel
			playersPanel.add(player1NamePanel);
			
			// Set the preferred size and background for the player 1 scroll panel
			player1ScrollPanel.setPreferredSize(new Dimension(350, 568));
			player1ScrollPanel.setBackground(greenBackground);
			
			// Add the player 1 scroll panel to the players panel
			playersPanel.add(player1ScrollPanel);
			
			/**********************************************************
			 * 1.3.5 - The current highest achieved score shall be displayed on the
			 * lower left hand corner of the screen below the overall point total
			 * for the current game. This score shall initially be set to 0
			 * points.
			 **********************************************************/
			// Add the high score to the players panel
			addHighScore(playersPanel);
		
		/*************************************************
		 * 1.4.0 - Two player mode graphic user interface
		 *************************************************/
		// Else if the game mode is GameMode.MULTIPLAYER
		} else if (null != gameMode && gameMode == GameMode.MULTIPLAYER) {
	
			/*************************************************
			 * 1.4.2.a - Each player shall be indicated in the following manner: 
			 * “Player: “ along with the provided player’s name, or “Computer” 
			 * if a “Computer Opponent” has been selected, followed by the 
			 * running point total for the current game for that player.
			 *************************************************/
			
			/*************************************************
			 * 1.4.5 - The turn totals for each player shall be displayed in a 
			 * scroll pane below that player’s name and game score. This scroll 
			 * pane shall initially display 5 turns, adding additional turns 
			 * after they are taken. The scrolling ability shall be enabled at 
			 * the beginning of the 11th turn.
			 *************************************************/
			// Create the scroll pane for player 1 and add an empty border
			JScrollPane player1ScrollPanel = createPlayerScorePanel(1, 5);
			player1ScrollBar = player1ScrollPanel.getVerticalScrollBar();
			player1ScrollPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));
			
			// Create the player 1 name panel
			JPanel player1NamePanel = createPlayerNamePanel(1);
			
			// Set the border for the player 1 name panel
			player1NamePanel.setBorder(BorderFactory
					.createMatteBorder(0, 0, 6, 0, Color.WHITE));
			
			// Add the player 1 name panel to the players panel
			playersPanel.add(player1NamePanel);
			
			// Set the preferred size and background for the player 1 scroll panel
			player1ScrollPanel.setPreferredSize(new Dimension(350, 249));
			player1ScrollPanel.setBackground(greenBackground);
			
			// Add the player 1 scroll panel to the players panel
			playersPanel.add(player1ScrollPanel);

			// Create the scroll pane for player 2 and add an empty border
			JScrollPane player2ScrollPanel = createPlayerScorePanel(2, 5);
			player2ScrollBar = player2ScrollPanel.getVerticalScrollBar();
			player2ScrollPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 6));
			
			// Create the player 2 name panel
			JPanel player2NamePanel = createPlayerNamePanel(2);
			
			// Set the border for the player 2 name panel
			player2NamePanel.setBorder(BorderFactory
					.createMatteBorder(6, 0, 6, 0, Color.WHITE));
			
			// Add the player 2 name panel to the players panel
			playersPanel.add(player2NamePanel);
			
			// Set the preferred size and background for the player 2 scrolll panel
			player2ScrollPanel.setPreferredSize(new Dimension(350, 249));
			player2ScrollPanel.setBackground(greenBackground);
			
			// Add the player 2 scroll panel to the players panel
			playersPanel.add(player2ScrollPanel);
			
			// Set the background for the players panel
			playersPanel.setBackground(greenBackground);
		}
		
		// Set the background color
		playersPanel.setBackground(greenBackground);
		
		// Add this panel to the window
		this.add(playersPanel, 0);
	}

	/**
	 * Create the panel that displays the player name and total game score for
	 * that player.
	 * @param playerNumber int representing the player for which this panel is created
	 * @return constructed JPanel with player name and game score
	 */
	private JPanel createPlayerNamePanel(int playerNumber) {
		
		// Create the layout for the panel
		GridLayout gridLayout = new GridLayout(0, 2, 0, 0);
		gridLayout.setHgap(0);
		gridLayout.setVgap(0);
		
		// Create the panel with the layout
		JPanel playerNamePanel = new JPanel(gridLayout);
		
		// Add the player name label and the player name
		JLabel playerNameLabel = (playerNumber == 1) ? player1NameLabel
				: player2NameLabel;
		JLabel playerName = (playerNumber == 1) ? player1Name : player2Name;
		
		/***********************************************************
		 * 1.3.2 - The overall point total for the current game shall be displayed
		 * on the upper left hand corner of the screen, just below the player's 
		 * name.
		 ***********************************************************/
		/***********************************************************
		 * 1.4.2 - The left side of the screen shall have an area to display the 
		 * overall accumulated point total for each player. This takes the place 
		 * of the area displaying the point total for each turn in the one player 
		 * mode graphic user interface. 
		 ***********************************************************/
		// Add the player score label and the player score
		JLabel playerGameScoreLabel = (playerNumber == 1) ? player1GameScoreLabel
				: player2GameScoreLabel;
		JLabel playerGameScore = (playerNumber == 1) ? player1GameScore
				: player2GameScore;

		// Set the font for the player name label
		playerNameLabel.setForeground(Color.WHITE);
		playerNameLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Set the border for the player name label
		playerNameLabel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(15, 3, 15, 0)));
	
		
		// Add the player name label to the pabel
		playerNamePanel.add(playerNameLabel);

		// Set the font for the player name
		playerName.setForeground(Color.WHITE);
		playerName.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Set the border for the player name
		playerName.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(15, 13, 15, 3)));
			
		// Add the player name
		playerNamePanel.add(playerName);

		/*****************************************************
		 * 1.3.3 - The left side of the screen shall have an area to display the
		 * point total for each of the ten turns taken in single player mode.
		 *****************************************************/
		
		// Set the font for the game sscore label
		playerGameScoreLabel.setForeground(Color.WHITE);
		playerGameScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Set the border for the game score label
		playerGameScoreLabel.setBorder(
				BorderFactory.createEmptyBorder(15, 3, 15, 3));
		
		// Add the game score label to the player name panel
		playerNamePanel.add(playerGameScoreLabel);
		
		// Set the font for the game score
		playerGameScore.setForeground(Color.WHITE);
		playerGameScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Set the border for the game score
		playerGameScore.setBorder(
				BorderFactory.createEmptyBorder(15, 13, 15, 3));
		
		// Add the game score to the player name panel
		playerNamePanel.add(playerGameScore);
		
		// Set the background for the player name panel
		playerNamePanel.setBackground(greenBackground);
		
		// Set the preferred and maximum size for the player name panel
		playerNamePanel.setPreferredSize(new Dimension(350, 110));
		playerNamePanel.setMaximumSize(new Dimension(350, 110));
		
		// Return the created panel
		return playerNamePanel;
	}

	/**
	 * Create a new JPanel that contains all the JLabels necessary to represent
	 * a player with 10 turns
	 * 
	 * @param playerNumber player number for which this Scroll Pane is created
	 * @param scoreLabelCount number of turn score labels to include
	 * @return Constructed JScrollPane with player turn information
	 */
	private JScrollPane createPlayerScorePanel(int playerNumber,
			int scoreLabelCount) {
		
		// Create the player panel
		JPanel playerPanel = new JPanel(new GridLayout(0, 2, 0, 2));
		
		// Set the player panel according to the player number parameter
		if (playerNumber == 1) {
			player1ScorePanel = playerPanel;
		} else {
			player2ScorePanel = playerPanel;
		}

		/***************************************************
		 * 1.4.3: The left side of the screen shall have an area to display the
		 * point total for each of the ten turns taken in single player mode.
		 ***************************************************/
		
		// Add the requested number of turn score labels
		for (int i = 0; i < scoreLabelCount; i++) {
			addTurnScore(playerNumber, i + 1);
		}

		// Set the background for the player panel and add an empty border
		playerPanel.setBackground(greenBackground);
		playerPanel.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 17));

		// Create the JScrollPane from the player panel
		JScrollPane playerScrollPane = new JScrollPane(playerPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// Return the created scroll pane
		return playerScrollPane;
	}

	/**
	 * Add the high score panel to a panel
	 * @param panel JPanel to which the high score panel is to be added
	 */
	public void addHighScore(JPanel panel) {
		/**************************************************
		 * 1.4.5: The current highest achieved score shall be displayed. This
		 * score shall initially be set to 5000 points.
		 ***************************************************/
		
		// Create the high score panel
		JPanel highScorePanel = new JPanel(new GridLayout(0, 2, 0, 0));
		
		// Set the font for the high score title
		highScoreTitle.setForeground(Color.WHITE);
		highScoreTitle.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Add the high score title to the high score panel
		highScorePanel.add(highScoreTitle);
		
		// Set the font for the high score
		highScore.setForeground(Color.WHITE);
		highScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Add the high score to the high score panel
		highScorePanel.add(highScore);
		highScorePanel.setBackground(greenBackground);
		
		// Set the maximum size of the panel
		highScorePanel.setMaximumSize(new Dimension(350, 50));
		
		// Add a border to the panel
		highScorePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(6, 0, 0, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(0, 3, 0, 0)));
		
		// Add the high score panel to the supplied panel
		panel.add(highScorePanel);
	}

	/**
	 * Add a turn score to the player score panel for a given player
	 * @param playerNumber Player number for which the turn score is added
	 * @param turnNumber The turn number to include with this turn score
	 */
	public void addTurnScore(int playerNumber, int turnNumber) {
		
		// Get a reference to the player score labels for the provided player
		ArrayList<JLabel> playerScoreLabels = (playerNumber == 1) ? player1ScoreLabels
				: player2ScoreLabels;
		
		// Get a reference to the player scores for the provided player
		ArrayList<JLabel> playerScores = (playerNumber == 1) ? player1Scores
				: player2Scores;
		
		// Get a reference to the player score panel for the provided player
		JPanel playerPanel = (playerNumber == 1) ? player1ScorePanel
				: player2ScorePanel;

		// Add turn label for the provided turn
		JLabel turnLabel = new JLabel("Turn " + (turnNumber) + ": ");
		
		// Create an empty border for the label
		turnLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		// Set the font for the turn label
		turnLabel.setForeground(Color.WHITE);
		turnLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Set the maximum and preferred sizes for the turn label
		turnLabel.setMaximumSize(new Dimension(175, 20));
		turnLabel.setPreferredSize(new Dimension(175, 20));
		
		// Add the turn label to the player score labels
		playerScoreLabels.add(turnLabel);
		
		// Add the turn number to the player panel
		playerPanel.add(playerScoreLabels.get(turnNumber - 1));

		// Add the score label for each turn
		JLabel turnScoreLabel = new JLabel();
		
		// Set the turn score label font
		turnScoreLabel.setForeground(Color.WHITE);
		turnScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		
		// Set the maximum and preferred sizes for the score label
		turnLabel.setMaximumSize(new Dimension(175, 20));
		turnLabel.setPreferredSize(new Dimension(175, 20));
		
		// Add the turn score label to the panel
		playerScores.add(turnScoreLabel);
		
		// add the player scores to the player panel
		playerPanel.add(playerScores.get(turnNumber - 1));
	}

	/*****************************************************************
	 * 1.2.3 - Rules for the scoring combinations shall be displayed on the
	 * right side of the screen.
	 *****************************************************************/
	
	/**
	 * Create a new JPanel that displays the score guide image
	 */
	public void createScoreGuidePanel() {

		/*****************************************************
		 * 1.3.3: Rules for the scoring combinations shall be displayed on the
		 * right side of the screen.
		 *****************************************************/
		
		// Create the score panel
		JPanel scorePanel = new JPanel();
		
		// Add the FarkleScores.png image to the score panel
		try {
			Image scoreGuide = ImageIO.read(getClass().getResource(
					"/images/FarkleScores.png"));
			scoreGuide = scoreGuide.getScaledInstance(200, 680,
					Image.SCALE_SMOOTH);
			JLabel scoreLabel = new JLabel(new ImageIcon(scoreGuide));
			scorePanel.add(scoreLabel);
			
			// Set the background of the panel
			scorePanel.setBackground(greenBackground);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		// Add a border to the score panel
		scorePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 3),
				BorderFactory.createEmptyBorder(3, 17, 3, 17)));
		
		// Add the score panel to the window
		this.add(scorePanel);
	}

	/**
	 * Get the "Roll Dice" button
	 * @return The "Roll Dice" button
	 */
	public JButton getRollBtn() {
		return rollBtn;
	}

	/**
	 * Get the "Bank Score" button
	 * @return The "Bank Score" button
	 */
	public JButton getBankBtn() {
		return bankBtn;
	}

	/**
	 * Get the "Select All" button
	 * @return The "Select All" button
	 */
	public JButton getSelectAllBtn() {
		return selectAllBtn;
	}

	/**
	 * Set the game score for a given player
	 * @param playerNumber Player number for which to set the game score
	 * @param score The score to set
	 */
	public void setGameScore(int playerNumber, int score) {
		JLabel gameScore = (playerNumber == 1) ? player1GameScore
				: player2GameScore;
		gameScore.setText("" + score);
	}
	
	/**
	 * Get the game score for a chosen player
	 * @param playerNumber Player number for which the game score is sought
	 * @return The game score for the chosen player
	 */
	public int getGameScore(int playerNumber) {
		
		// Get the game score JLabel for the chosen player
		JLabel gameScore = (playerNumber == 1) ? player1GameScore
				: player2GameScore;
		
		try {
			// Parse the game score as an Integer
			return Integer.parseInt(gameScore.getText());
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return -10000;
		}
	}

	/**
	 * Set the high score label text
	 * @param score High score for which to set
	 */
	public void setHighScore(int score) {
		this.highScore.setText("" + score);
	}

	/**
	 * Get the high score displayed in the high score label
	 * @return The high score
	 */
	public int getHighScore() {
		try {
			// Parse the high score label text as an integer and return it
			return Integer.parseInt(this.highScore.getText());
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return -5000;
		}
	}
	
	/**
	 * Get the running score displayed in the running score label
	 * @return The running score
	 */
	public int getRunningScore() {
		try {
			// Parse the running score label text as an integer and return it
			return Integer.valueOf(runningScore.getText());
		} catch(NumberFormatException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Randomly roll all of the dice
	 */
	public void rollDice() {

		// Roll all of the dice
		for (Die d : dice) {
			d.roll();
		}
	}

	/**
	 * Get the dice with the specified DieStates
	 * 
	 * @param dieState One or more DieState types
	 * @return the dice with the specified DieStates
	 */
	public ArrayList<Die> getDice(DieState... dieState) {
		
		// Create a list of the provided die states
		ArrayList<DieState> dieStates = new ArrayList<DieState>();
		for (DieState state : dieState) {
			dieStates.add(state);
		}
		
		// Build a list of all dice that are in one of the specified die states
		ArrayList<Die> retDice = new ArrayList<Die>();
		for (Die d : dice) {
			if (dieStates.contains(d.getState())) {
				retDice.add(d);
			}
		}
		
		// Return the list of dice in the specified states
		return retDice;
	}

	/**
	 * Get the values from the dice with the specified DieStates
	 * 
	 * @param dieState One or more DieState types
	 * @return List<Integer> values from the dice with the specified DieStates
	 */
	public List<Integer> getDieValues(DieState... dieState) {
	
		// Get the list of dice that are in one of the specified die states
		ArrayList<Die> diceToCheck = getDice(dieState);
		
		// Build the list of die values that are in one of the specified die states
		List<Integer> retVals = new ArrayList<Integer>();
		for (Die d : diceToCheck) {
			retVals.add(d.getValue());
		}
		
		// Return the list of die values
		return retVals;
	}

	/**
	 * Set the die state of all six dice UNHELD
	 */
	public void resetDice() {
		for (Die d : dice) {
			d.setState(DieState.UNHELD);
		}
	}
	
	
	/**
	 * Prevents dice from being selected
	 */
	public void disableDice() {
		for (Die d : dice) {
			d.setState(DieState.DISABLED);
		}
	}
	
	/**
	 * Resets dice, enables the roll button, and disables the select all and 
	 * bank buttons 
	 */
	public void resetDicePanel() {
		resetDice();
		this.rollBtn.setEnabled(true);
		this.selectAllBtn.setEnabled(false);
		this.bankBtn.setEnabled(false);
	}
	
	/*******************************************************************************
	 * 1.2.1 The center of the screen shall display the six dice used
	 * during gameplay. These dice shall display the name of the game, "Farkle",
	 * until the user selects the roll button for the first time.
	 *******************************************************************************/
	/**
	 * Initialize the dice to display the word "Farkle" at the beginning 
	 * of a game
	 */
	public void diceFirstRun() {
		 dice[0].setValue('f');
		 dice[1].setValue('a');
		 dice[2].setValue('r');
		 dice[3].setValue('k');
	 	 dice[4].setValue('l');
	 	 dice[5].setValue('e');
	}

	/**
	 * Reset all scores for a specified player
	 * @param playerNumber The specified player number
	 */
	public void resetScores(int playerNumber) {
		
		// Get a reference to the scores
		ArrayList<JLabel> scores = getPlayerScores(playerNumber);
		
		// Reset the scores
		for (JLabel score : scores) {
			score.setText("");
		}
	}

	/**
	 * Update all dice with a HELD state to a SCORED state
	 */
	public void lockScoredDice() {
		for (Die d : dice) {
			if (d.getState() == DieState.HELD) {
				d.setState(DieState.SCORED);
			}
		}
	}

	/**
	 * Update the turn label for the specified player with the specified score
	 * 
	 * @param int player Specified player number
	 * @param int turn Specified turn number
	 * @param int score Turn score to set
	 */
	public void setTurnScore(int player, int turn, int score) {
		ArrayList<JLabel> scores = getPlayerScores(player);
		scores.get(turn - 1).setText("" + score);
	}
	
	/**
	 * Get the turn score for a specified player and specified turn number
	 * @param player The specified player
	 * @param turn The specified turn number
	 * @return The turn score for the specified player and turn number
	 */
	public int getTurnScore(int player, int turn) {
		
		// Get a reference to the turn scores
		ArrayList<JLabel> scores = getPlayerScores(player);
		
		// Range check for the provided turn number
		if(scores.size() >= turn) {
			
			// If the turn score for the provided turn number is empty, return 0
			if(null != scores.get(turn - 1).getText() && "".equals(scores.get(turn - 1).getText())) {
				return 0;
				
			// Else, return it as a parsed integer
			} else {
				try {
					return Integer.parseInt(scores.get(turn - 1).getText());
				} catch(NumberFormatException e) {
					e.printStackTrace();
					return 0;
				}
			}
		
		// Else, the range is out of bounds
		} else {
			return -5000;
		}
	}

	/**
	 * Update the running score label with the specified score
	 * @param score The score to set
	 */
	public void setRunningScore(int score) {
		runningScore.setText("" + score);
	}

	/**
	 * Update the roll score label with the specified score
	 * @param score The score to set
	 */
	public void setRollScore(int score) {
		rollScore.setText("" + score);
	}
	
	/**
	 * Get the roll score from the roll score label text
	 * @return The roll score
	 */
	public int getRollScore() {
		try {
			// Return the roll score label text parsed as an integer
			return Integer.parseInt(this.rollScore.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return -10000;
		}
	}

	/**
	 * Randomly play one of the four dice roll sounds
	 */
	public void playRollSound() {
		try {
			
			// Randomly play one of the three roll sounds
			audioStream = AudioSystem.getAudioInputStream(rollSounds
					.get(new Random().nextInt(3)));
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Play the bonus roll sound
	 */
	public void playBonusSound() {
		try {
			audioStream = AudioSystem.getAudioInputStream(bonusSound);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Play the bank sound
	 */
	public void playBankSound() {
		try {
			audioStream = AudioSystem.getAudioInputStream(bankSounds
					.get(new Random().nextInt(3)));
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display a message to the user via JOptionPane
	 * @param message Message to be displayed in the main box.
	 * @param title Title of the JOptionPane window.
	 */
	@SuppressWarnings("deprecation")
	public void displayMessage(String message, String title) {
		
		// Set the message for the JOptionPane
		JOptionPane pane = new JOptionPane(message);
		
		// Create a JDialog, and set the title
		JDialog dialog = pane.createDialog(title);
		
		// Set the dialog to display in front of all other windows
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		
		// Show the dialog
		dialog.show();
	}

	/**
	 * Display a Yes/No JOption pane to the user
	 * @param message Message to be displayed in the main box
	 * @param title Title of the JOptionPane window
	 * @return boolean true if the user selected yes, false otherwise
	 */
	public boolean displayYesNoMessage(String message, String title) {
		boolean retVal;
		int dialogResult = JOptionPane.showConfirmDialog(this, message, title,
				JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION) {
			retVal = true;
		} else {
			retVal = false;
		}
		return retVal;
	}

	/**
	 * Dispose the window and close the application
	 */
	@Override
	public void dispose() {
		super.dispose();
		System.exit(0);
	}

	/**
	 * Select dice animation used for a computer players dice selection
	 * @param valuesNeeded List of dice to select
	 * @throws InterruptedException
	 */
	public void selectDice(List<Integer> valuesNeeded) throws InterruptedException {
		
		// Retrieve a list of all unheld dice
		List<Die> dice = getDice(DieState.UNHELD);
		
		// For each die in dice, select it if it is in the list of values needed
		for (Die d : dice) {
			for (Integer i : valuesNeeded) {
				
				// If the current dice matches the current value needed
				if (d.getValue() == i) {
					
					// Pause the animation for a half second
					Thread.sleep(500);
					
					// Select the die
					d.dispatchEvent(new DieClickEvent(d, MouseEvent.MOUSE_PRESSED,
							System.currentTimeMillis(), MouseEvent.BUTTON1, d
									.getX(), d.getY(), 1, false));
					
					// Remove the value needed
					valuesNeeded.remove(i);
					
					// Break the inner for loop
					break;
				}
			}
		}
		
		// Pause for 1.5 seconds after all dice are selected
		Thread.sleep(1500);
	}

	/**
	 * Selects all unheld dice
	 */
	public void selectAllDice() {
		
		// Get the list of all unheld dice
		ArrayList<Die> dice = getDice(DieState.UNHELD);
		
		// Simulate a mouse click selecting each unheld die
		for (Die d : dice) {
		d.dispatchEvent(new MouseEvent(d, MouseEvent.MOUSE_PRESSED,
				System.currentTimeMillis(), MouseEvent.BUTTON1, d
						.getX(), d.getY(), 1, false));
		}					
	}

	/**
	 * Set the player name label text for a specified player
	 * @param playerNumber The number of the specified player
	 * @param name The name to set
	 */
	public void setPlayerName(int playerNumber, String name) {
		if (null != name && playerNumber >= 1 && playerNumber <= 2) {
			if (playerNumber == 1) {
				this.player1Name.setText(name);
			} else {
				this.player2Name.setText(name);
			}
		}
	}

	/**
	 * Unhighlight all player turn scores for a specified player
	 * @param playerNumber The number of the specified player
	 */
	public void unHighlightAllTurnScores(int playerNumber) {
		
		// Get the labels and their scores for the given player
		ArrayList<JLabel> scoreLabels = getPlayerScoreLabels(playerNumber);
		ArrayList<JLabel> scores = getPlayerScores(playerNumber);

		// Make sure it's a 1:1 relationship
		assert (scoreLabels.size() == scores.size());

		// Unhighlight each set
		for (int i = 0; i < scoreLabels.size(); i++) {
			unHighlightTurn(scoreLabels.get(i), scores.get(i));
		}
	}

	/**
	 * Highlight a specified turn score for a specified player
	 * @param playerNumber The number of the specified player
	 * @param turn The specified turn number
	 * @param isBonusTurn true if this is a bonus turn
	 */
	public void highlightTurnScore(int playerNumber, int turn,
			boolean isBonusTurn) {
		
		// Unhighlight all turns
		unHighlightAllTurnScores(playerNumber);

		// Get the labels and their scores for the given player
		ArrayList<JLabel> scoreLabels = getPlayerScoreLabels(playerNumber);
		ArrayList<JLabel> scores = getPlayerScores(playerNumber);

		// Add a turn score if it does not already exist in the turn scores panel
		if (turn > 0 && turn > scoreLabels.size()) {
			addTurnScore(playerNumber, turn);
		}

		// Highlight the desired turn
		highlightTurn(scoreLabels.get(turn - 1), scores.get(turn - 1),
				isBonusTurn);

		// Scroll to the bottom of the panel;
		JScrollBar playerScrollBar = (playerNumber == 1) ? player1ScrollBar
				: player2ScrollBar;
		if (null != playerScrollBar) {
			playerScrollBar.getParent().validate();
			playerScrollBar.setValue(playerScrollBar.getMaximum());
		}
	}

	/**
	 * Unhighlight a specified score label and score
	 * @param scoreLabel label to unhighlight
	 * @param score label to unhighlight
	 */
	public void unHighlightTurn(JLabel scoreLabel, JLabel score) {
		
		/* Remove the white background, and set the font to white for each
		 * JLabel component
		 */
		scoreLabel.setOpaque(false);
		scoreLabel.setForeground(Color.WHITE);
		score.setOpaque(false);
		score.setForeground(Color.WHITE);
	}

	/**
	 * Highlight a specified score label and score
	 * @param scoreLabel label to highlight
	 * @param score label to highlight
	 * @param isBonusTurn set to true if this is to be highlighted as a bonus turn
	 */
	public void highlightTurn(JLabel scoreLabel, JLabel score,
			boolean isBonusTurn) {

		/*****************************************
		 * 1.3.4 - The current turn shall be indicated by highlighting that turn 
		 * on the left side of the screen. This turn shall be highlighted as soon 
		 * as the previous turn ends (which occurs after the player selects the 
		 * “Bank Score” button, or after the Farkle message animation completes), 
		 * and before the player selects the “Roll Dice” button for the current turn.
		 *****************************************/

		/* Highlight given turn in white, and set font to black if it's not a 
		 * bonus turn
		 */
		if (!isBonusTurn) {
			scoreLabel.setOpaque(true);
			scoreLabel.setBackground(Color.WHITE);
			scoreLabel.setForeground(Color.BLACK);

			score.setOpaque(true);
			score.setBackground(Color.WHITE);
			score.setText("");
			score.setForeground(Color.BLACK);
			
		/* Else, this is a bonus turn. Highlight given turn in yellow, set font
		 * to black, and display the bonus turn text.
		 */
		} else {
			scoreLabel.setOpaque(true);
			scoreLabel.setBackground(Color.YELLOW);
			scoreLabel.setForeground(Color.BLACK);

			score.setOpaque(true);
			score.setText("BONUS ROLL!");
			score.setBackground(Color.YELLOW);
			score.setForeground(Color.BLACK);
			score.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 3));
		}
	}

	/**
	 * Get all score labels for a specified player
	 * @param playerNumber The number of the specified player
	 * @return An ArrayList<JLabel> of the score labels
	 */
	public ArrayList<JLabel> getPlayerScoreLabels(int playerNumber) {
		ArrayList<JLabel> scoreLabels = (playerNumber == 1) ? player1ScoreLabels
				: player2ScoreLabels;
		return scoreLabels;
	}

	/**
	 * Get all turn scores for a specified player
	 * @param playerNumber The number of the specified player
	 * @return An ArrayList<JLabel> of the player scores
	 */
	public ArrayList<JLabel> getPlayerScores(int playerNumber) {
		ArrayList<JLabel> scores = (playerNumber == 1) ? player1Scores
				: player2Scores;
		return scores;
	}

	/**
	 * Initialize all sounds used for the roll, bank, and bonus roll
	 * actions.
	 */
	private void getSounds() {
		// Initialize the roll sounds
		rollSounds.add(getClass().getResource("/sounds/roll1.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll2.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll3.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll4.wav"));

		// Initialize the bank sounds
		bankSounds.add(getClass().getResource("/sounds/bank1.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank2.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank3.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank4.wav"));

		// Initialize the bonus turn sound
		bonusSound = getClass().getResource("/sounds/bonus.wav");
	}

	/**
	 * Create a file menu with five sub menus:
	 * Hint - displays the maximum scoring dice combination for a given roll.
	 * New Game - Resets the application to the game mode options menu
	 * Reset Game - Resets the game with the current configuration
	 * Reset High Score - Resets the stored high score to 0
	 * Exit - Closes the application
	 * @return The Farkle menu bar
	 */
	public JMenuBar createFarkleMenuBar() {
		
		// Menu Bar
		/******************************************************
		 * 1.2.10 - A menu shall be displayed at the top of the main GUI 
		 * with one main option, “File”, and four five options: “Hint”, 
		 * “New Game”, “Restart Game”, “Reset High Score”, and “Quit”.
		 ******************************************************/
		
		// Instantiate the JMenuBar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Instantiate the JMenu and add it to the JMenu Bar
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		// Create the JMenu Items and add them to the JMenu
		final JMenuItem hintAction = new JMenuItem("Hint");
		final JMenuItem newAction = new JMenuItem("New Game");
		final JMenuItem resetAction = new JMenuItem("Reset Game");
		final JMenuItem resetHighScoreAction = new JMenuItem("Reset High Score");
		final JMenuItem exitAction = new JMenuItem("Exit");
		fileMenu.add(hintAction);
		fileMenu.add(newAction);
		fileMenu.add(resetAction);
		fileMenu.add(resetHighScoreAction);
		fileMenu.add(exitAction);
		
		/* Add a menu listener that determines if the hint, new, and reset actions
		 * are available whenever the file menu is opened
		 */
		fileMenu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				
				/********************************************************
				 * 1.2.10.e - The “Hint” option shall only be available after a 
				 * player has rolled, and before that player has selected any dice.
				 ********************************************************/
				hintAction.setEnabled(controller.isHintAvailable());
				
				/********************************************************
				 * 1.2.10.g - The “New Game” option and the “Reset Game” option 
				 * shall be disabled while the computer is taking its turn.
				 ********************************************************/
				newAction.setEnabled(controller.isResetOrNewGameAvailable());
				resetAction.setEnabled(controller.isResetOrNewGameAvailable());
			}

			@Override
			public void menuDeselected(MenuEvent e) {
			}

			@Override
			public void menuCanceled(MenuEvent e) {
			}
			
		});

		/********************************************************
		 * 1.2.10.a If the user selects the New Game option, the select game
		 * mode option box is displayed.
		 ********************************************************/
		if (newAction.getActionListeners().length == 0) {
			newAction.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					controller.endGame(false, true, false);
				}
			});
		}

		/**************************************************************
		 * 1.2.10.b If the user selects the Restart Game option, the current
		 * game with all current configurations (player mode, player names, and
		 * player types) is restarted.
		 **************************************************************/
		if (resetAction.getActionListeners().length == 0) {
			resetAction.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					controller.endGame(true, false, false);
				}
			});
		}
		
		/****************************************************************
		 * 1.2.10.f - If the user selects the “Reset High Score” option, the 
		 * high score is reset to 0.
		 ****************************************************************/
		if(resetHighScoreAction.getActionListeners().length == 0) {
			resetHighScoreAction.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.resetHighScore();
				}
			});
		}

		/****************************************************************
		 * 1.2.10.c If the user selects the "Quit" option, the application is
		 * closed.
		 ****************************************************************/
		if (exitAction.getActionListeners().length == 0) {
			exitAction.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					FarkleUI.this.dispose();
				}
			});
		}

		/****************************************************************
		 * 1.2.10.d - If the user selects the “Hint” option, the dice combination 
		 * for the highest possible score for the current roll is displayed.
		 ****************************************************************/
		if (hintAction.getActionListeners().length == 0) {
			hintAction.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					List<Integer> highestScoringDieValues = controller.getHighestScoringDieValues();
					if (highestScoringDieValues.size() != 0) {
						displayMessage("Select "
								+ highestScoringDieValues + " to score "
								+ controller.getHighestPossibleScore()
								+ " points.", "Hint");
					}
				}
			});
		}

		return menuBar;
	}

	/**
	 * Get the farkle message
	 * @return The farkleMessage JDialog
	 */
	public JDialog getFarkleMessage() {
		return farkleMessage;
	}
}
