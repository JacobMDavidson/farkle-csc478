package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FarkleUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Die[] dice = new Die[6];
	public JButton rollBtn = new JButton("Roll Dice");
	public JButton bankBtn = new JButton("Bank Score");
	public FarkleController controller;
	public JLabel playerNameLabel = new JLabel("");
	public JLabel[] scoreLabels = new JLabel[10];
	public JLabel[] player1Scores = new JLabel[10];
	public JLabel[] player2Scores = new JLabel[10];
	public JLabel gameScoreTitle = new JLabel("Total Score: ");
	public JLabel gameScore = new JLabel("0");
	public JLabel highScoreTitle = new JLabel("High Score: ");
	public JLabel highScore = new JLabel("5000");
	public JLabel runningScore = new JLabel();
	public ArrayList<URL> rollSounds = new ArrayList<URL>();
	public ArrayList<URL> bankSounds = new ArrayList<URL>();
	public URL bonusSound;
	public URL gSound;
	public AudioInputStream audioStream = null;

	/**
	 * Constructor Get a reference to the controller object and fire up the UI
	 * 
	 * @param f
	 */
	public FarkleUI(FarkleController f) {
		controller = f;
		initUI();
	}

	/**
	 * Build the UI
	 */
	public void initUI() {

		// Initialize the sounds
		rollSounds.add(getClass().getResource("/sounds/roll1.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll2.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll3.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll4.wav"));
		gSound = getClass().getResource("/sounds/roll5.wav");
		bankSounds.add(getClass().getResource("/sounds/bank1.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank2.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank3.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank4.wav"));
		bonusSound = getClass().getResource("/sounds/bonus.wav");

		// Pass a reference to the controller
		controller.setUI(this);

		controller.newGame();

		/******************************************
		 * 1.4.1: The title of the window shall display: “Farkle – Single Player
		 * Mode”.
		 ******************************************/
		// TODO: CuBr - Write a test case for the window title
		// based on player mode

		// Create and set up the main Window
		JFrame frame = new JFrame("Farkle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1024, 768));
		frame.setLayout(new GridLayout(1, 3, 10, 10));

		// Build the UI
		frame.add(createPlayerPanel());

		// Call to create dice
		frame.add(createDicePanel());
		frame.add(createScorePanel());

		// Center and display the window
		frame.setLocationRelativeTo(null);
		frame.pack();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);

		frame.setVisible(true);
	}

	/* Reduce Reuse Recycle */
	public String[] getGameInformation() {

		/**************************************************
		 * 1.1.0 Upon opening the application, the user is greeted with a screen
		 * that has two options, 1 player mode or 2 player mode.
		 ***************************************************/
		Object countOps[] = { "1 Player", "2 Players", "Cancel" };
		Object modeOps[] = { "Human Opponent", "Computer Opponent", "Cancel" };
		String[] playerNames = new String[2];
		int playerCount = JOptionPane.showOptionDialog(this,
				"Choose the number of players.", "Player Mode",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, countOps, countOps[0]);
		int playerMode = -1;

		if (playerCount != JOptionPane.CANCEL_OPTION) {
			playerNames[0] = JOptionPane.showInputDialog(this,
					"Please enter your name.", "Player 1",
					JOptionPane.OK_CANCEL_OPTION);
			if (("Ginuwine").equalsIgnoreCase(playerNames[0])) {

				try {
					AudioInputStream audioStream;
					audioStream = AudioSystem.getAudioInputStream(gSound);
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

			/***************************************************************
			 * 1.2.0: If the user selects two player mode, the user is asked if
			 * player 2 is a live player or a computer player.
			 ***************************************************************/
			// Player chooses 2 player game mode
			if (playerCount == JOptionPane.NO_OPTION) {

				// Prompt player 1 for type of player they wish player 2 to be
				playerMode = JOptionPane
						.showOptionDialog(this,
								"What type of player is player 2?.",
								"Player Type",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, modeOps,
								modeOps[0]);

				// If they choose for player 2 to be human
				if (playerMode == JOptionPane.YES_OPTION) {
					playerNames[1] = JOptionPane.showInputDialog(this,
							"Please enter your name.", "Player 2",
							JOptionPane.OK_CANCEL_OPTION);
				}
			}
		}

		else {
			System.exit(0);
		}

		return playerNames;
	}

	/**
	 * Create a JPanel which contains two buttons and attach a Listener to each
	 * 
	 * @return
	 */
	public JPanel[] createButtonPanel() {
		JPanel buttonPanels[] = { new JPanel(), new JPanel() };

		/********************************************
		 * 1.3.4: A “Roll” button shall be displayed.
		 ********************************************/
		rollBtn.addActionListener(controller);
		buttonPanels[0].add(rollBtn);

		/********************************************
		 * 1.3.5: A “Bank” button shall be displayed (and shall initially be
		 * disabled).
		 ********************************************/
		bankBtn.addActionListener(controller);
		buttonPanels[1].add(bankBtn);
		getBankBtn().setEnabled(false);
		return buttonPanels;
	}

	/**
	 * Create a JPanel with six Dice, the running score JLabels and the Roll and
	 * Bank buttons
	 * 
	 * @return
	 */
	public JPanel createDicePanel() {
		// Create the panel
		JPanel dicePanel = new JPanel(new GridLayout(0, 3, 0, 0));
		dicePanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		dicePanel.setAlignmentY(JPanel.CENTER_ALIGNMENT);

		dicePanel.add(new JLabel("Turn Score:"));
		dicePanel.add(runningScore);
		dicePanel.add(new JLabel());

		/***********************************************
		 * 1.3.1: The center of the screen shall display the six dice used
		 * during gameplay. These dice shall display nothing until the user
		 * presses the roll button for the first time.
		 ***********************************************/

		/***********************************************
		 * 3.1.0: Farkle is played with six standard 6 sided dice with each side
		 * numbered from 1 through 6 (inclusive).
		 ***********************************************/

		// Initialize the dice and add to panel
		for (int i = 0; i < dice.length; i++) {
			dice[i] = new Die(controller);
			dicePanel.add(new JLabel(" "));
			dicePanel.add(dice[i]);
			dicePanel.add(new JLabel(" "));
		}
		dicePanel.add(new JLabel(" "));
		dicePanel.add(new JLabel(" "));
		dicePanel.add(new JLabel(" "));

		// Call to add buttons and satisfy
		// requirements 1.3.4 and 1.3.5
		JPanel btns[] = createButtonPanel();
		dicePanel.add(btns[0]);
		dicePanel.add(new JLabel(" "));
		dicePanel.add(btns[1]);

		return dicePanel;
	}

	/**
	 * Create a new JPanel that contains all the JLabels necessary to represent
	 * a player with 10 turns
	 * 
	 * @param playerName
	 * @param playerNumber
	 * @return
	 */
	private JPanel createPlayerPanel() {
		JPanel playerPanel = new JPanel(new GridLayout(0, 2, 0, 2));
		JLabel nameLbl = new JLabel("Player ");
		playerPanel.add(nameLbl);
		playerPanel.add(playerNameLabel);
		JLabel filler0 = new JLabel();
		playerPanel.add(filler0);
		JLabel filler1 = new JLabel();
		playerPanel.add(filler1);

		/***************************************************
		 * 1.4.3: The left side of the screen shall have an area to display the
		 * point total for each of the ten turns taken in single player mode.
		 ***************************************************/
		for (int i = 0; i < scoreLabels.length; i++) {
			scoreLabels[i] = new JLabel();
			scoreLabels[i].setText("Roll " + (i + 1) + ": ");
			scoreLabels[i].setOpaque(true);
			playerPanel.add(scoreLabels[i]);
			player1Scores[i] = new JLabel();
			player1Scores[i].setOpaque(true);
			playerPanel.add(player1Scores[i]);
		}

		/*****************************************************
		 * 1.4.2: The overall point total shall be displayed.
		 *****************************************************/
		playerPanel.add(gameScoreTitle);
		gameScoreTitle.setOpaque(true);
		playerPanel.add(gameScore);
		gameScore.setOpaque(true);
		/**************************************************
		 * 1.4.5: The current highest achieved score shall be displayed. This
		 * score shall initially be set to 5000 points.
		 ***************************************************/
		playerPanel.add(highScoreTitle);
		highScoreTitle.setOpaque(true);
		playerPanel.add(highScore);
		highScore.setOpaque(true);
		playerPanel.setPreferredSize(new Dimension(500, 300));
		playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		return playerPanel;
	}

	/**
	 * Create a new JPanel that displays the score guide image
	 * 
	 * @return JLabel
	 */
	private JPanel createScorePanel() {

		/*****************************************************
		 * 1.3.3: Rules for the scoring combinations shall be displayed on the
		 * right side of the screen.
		 *****************************************************/
		JPanel scorePanel = new JPanel();
		try {
			Image scoreGuide = ImageIO.read(getClass().getResource(
					"/images/FarkleScores.jpg"));
			scoreGuide = scoreGuide.getScaledInstance(200, 680,
					Image.SCALE_SMOOTH);
			JLabel scoreLabel = new JLabel(new ImageIcon(scoreGuide));
			scorePanel.add(scoreLabel);

			scorePanel
					.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return scorePanel;
	}

	public JButton getRollBtn() {
		return rollBtn;
	}

	public JButton getBankBtn() {
		return bankBtn;
	}

	public JLabel[] getScores() {
		return player1Scores;
	}

	public void setScores(JLabel[] scores) {
		this.player1Scores = scores;
	}

	public JLabel getGameScore() {
		return gameScore;
	}

	public void setGameScore(JLabel gameScore) {
		this.gameScore = gameScore;
	}

	public JLabel getHighScore() {
		return highScore;
	}

	public void setHighScore(JLabel highScore) {
		this.highScore = highScore;
	}

	public JLabel getRunningScore() {
		return runningScore;
	}

	public void rollDice() {
		// Test farkle roll
//		dice[0].setValue(1);
//		dice[1].setValue(2);
//		dice[2].setValue(3);
//		dice[3].setValue(4);
//		dice[4].setValue(5);
//		dice[5].setValue(6);

		// Roll all the dice
		 for (Die d : dice) {
		 d.roll();
		 }
	}

	/**
	 * Get the dice with the specified DieStates
	 * 
	 * @param dieState
	 *            One or more DieState types
	 * @return the dice with the specified DieStates
	 */
	public ArrayList<Die> getDice(DieState... dieState) {
		ArrayList<DieState> dieStates = new ArrayList<DieState>();
		for (DieState state : dieState) {
			dieStates.add(state);
		}
		ArrayList<Die> retDice = new ArrayList<Die>();
		for (Die d : dice) {
			if (dieStates.contains(d.getState())) {
				retDice.add(d);
			}
		}
		return retDice;
	}

	/**
	 * Get the values from the dice with the specified DieStates
	 * 
	 * @param dieState
	 *            One or more DieState types
	 * @return List<Integer> values from the dice with the specified DieStates
	 */
	public List<Integer> getDieValues(DieState... dieState) {
		List<Integer> retVals = new ArrayList<Integer>();
		ArrayList<Die> diceToCheck = getDice(dieState);
		for (Die d : diceToCheck) {
			retVals.add(d.getValue());
		}
		return retVals;
	}

	/**
	 * Set all six dice back to 0 and their states back to UNHELD
	 */
	public void resetDice() {
		for (Die d : dice) {
			d.setValue(0);
			d.setState(DieState.UNHELD);
		}
	}

	public void resetScores() {
		for (JLabel score : player1Scores) {
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
	 * @param int player
	 * @param int turn
	 * @param int score
	 */
	public void setTurnScore(int player, int turn, int score) {
		JLabel scoreLbls[] = (player == 0) ? player1Scores : player2Scores;
		scoreLbls[turn - 1].setText(String.valueOf(score));
	}

	/**
	 * Sets the background colors of the score labels based on the turn number.
	 * If 0 is passed as the turn number all colors will be reset to default.
	 * 
	 * @param turn
	 *            - Current turn number. (0 for reset)
	 * @param isBonusTurn
	 *            - Bonus turns have the color set to yellow.
	 */
	public void setTurnHighlighting(int turn, boolean isBonusTurn) {
		Color defaultColor = new Color(238, 238, 238);
		for (int i = 0; i <= 9; i++) {
			player1Scores[i].setBackground(defaultColor);
			scoreLabels[i].setBackground(defaultColor);
		}
		if (turn != 0 && !isBonusTurn) {
			player1Scores[turn - 1].setBackground(Color.WHITE);
			player1Scores[turn - 1].setText("");
			scoreLabels[turn - 1].setBackground(Color.WHITE);
		} else if (turn != 0) {
			player1Scores[turn - 1].setBackground(Color.YELLOW);
			player1Scores[turn - 1].setText("BONUS ROLL!");
			scoreLabels[turn - 1].setBackground(Color.YELLOW);
		} else {
			gameScoreTitle.setBackground(defaultColor);
			gameScore.setBackground(defaultColor);
			highScoreTitle.setBackground(defaultColor);
			highScore.setBackground(defaultColor);
		}
	}

	/**
	 * Update the running score label with the specified score
	 * 
	 * @param int score
	 */
	public void setRunningScore(int score) {
		runningScore.setText(String.valueOf(score));
	}

	/**
	 * Randomly return one of the four dice roll sounds
	 * 
	 * @return
	 */
	public void playRollSound() {
		try {
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
	 * 
	 * @param message
	 *            - Message to be displayed in the main box.
	 * @param title
	 *            - Title of the JOptionPane window.
	 * 
	 */
	public void displayMessage(String message, String title) {
		JOptionPane.showMessageDialog(this, message, title,
				JOptionPane.DEFAULT_OPTION);
	}
	
	public boolean displayYesNoMessage (String message, String title)
	{
		boolean retVal;
		int dialogResult = JOptionPane.showConfirmDialog(this, message, title, JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			retVal = true;
		}
		else
		{
			retVal = false;
		}
		return retVal;
	}

	public boolean gameEnded() {
		boolean retVal = true;
		/************************************************** 
		 * 1.6.0: At the conclusion of the game, the user shall be greeted with
		 * three options: “Play again?”, “Main Menu”, and “Quit”.
		 *************************************************/
		Object[] options = { "Play Again", "Main Menu", "Exit" };
		int n = JOptionPane.showOptionDialog(this,
				"What would you like to do?", "Game Over",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options, options[2]);
		
		/************************************************* 
		 * 2.1.5: If “Play again?” is selected, the game starts over in 1 player
		 * mode.
		 ************************************************/
		if (n == 0) {
			retVal = true;
		}
		/************************************************* 
		 * 2.1.6: If “Main Menu” is selected, the user is taken to the mode
		 * selection screen.
		 ************************************************/
		else if (n == 1) {
			retVal = false;
		}
		/************************************************** 
		 * 2.1.7: If “Quit” is selected, the application immediately closes.
		 *************************************************/
		else {
			pullThePlug();
		}
		runningScore.setText("0");
		gameScore.setText("0");
		getBankBtn().setEnabled(false);
		setTurnHighlighting(0, false);
		resetScores();
		return retVal;
	}

	public void pullThePlug() {
		dispose();
		System.exit(0);
	}
}
