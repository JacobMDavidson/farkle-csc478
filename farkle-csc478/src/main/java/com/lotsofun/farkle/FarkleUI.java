package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
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

public class FarkleUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Die[] dice = new Die[6];
	private JButton rollBtn = new JButton("Roll Dice");
	private JButton bankBtn = new JButton("Bank Score");
	private JButton selectAllBtn = new JButton("Select All");
	private FarkleController controller;

	/******************************************************
	 * 1.3.6 - The top of the left hand side of the screen shall display
	 * “Player: “, along with the provided name of the player.
	 ******************************************************/
	private JLabel player1NameLabel = new JLabel("Player 1: ");
	private JLabel player1Name = new JLabel("");
	private JLabel player2NameLabel = new JLabel("Player 2: ");
	private JLabel player2Name = new JLabel("");
	private ArrayList<JLabel> player1ScoreLabels = new ArrayList<JLabel>();
	private ArrayList<JLabel> player2ScoreLabels = new ArrayList<JLabel>();
	private ArrayList<JLabel> player1Scores = new ArrayList<JLabel>();
	private ArrayList<JLabel> player2Scores = new ArrayList<JLabel>();
	private JLabel player1GameScoreLabel = new JLabel("Total Score: ");
	private JLabel player1GameScore = new JLabel("0");
	private JLabel player2GameScoreLabel = new JLabel("Total Score: ");
	private JLabel player2GameScore = new JLabel("0");
	private JLabel highScoreTitle = new JLabel("High Score: ");
	private JLabel highScore = new JLabel();
	private JLabel runningScore = new JLabel("0");
	private JLabel rollScore = new JLabel("0");
	private ArrayList<URL> rollSounds = new ArrayList<URL>();
	private ArrayList<URL> bankSounds = new ArrayList<URL>();
	private URL bonusSound;
	private AudioInputStream audioStream = null;
	private Color greenBackground = new Color(35, 119, 34);
	private JDialog farkleMessage = new FarkleMessage();
	private JPanel player1ScorePanel = null;
	private JScrollBar player1ScrollBar = null;
	private JPanel player2ScorePanel = null;
	private JScrollBar player2ScrollBar = null;
	private boolean isFirstRun = true;

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
	 * Build the UI base
	 */
	public void initUI() {
		// Pass a reference to the controller
		if (isFirstRun) {
			isFirstRun = false;
			controller.setUI(this);

			// Instatiate necessary sounds
			getSounds();
			
			// Create and set up the main Window
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setPreferredSize(new Dimension(1024, 768));
			this.setResizable(false);
			
			this.pack();

			// Center and display the window
			this.setLocationRelativeTo(null);
		}

		// Clear the content if this isn't the first init call
		this.getContentPane().removeAll();

		
		GridLayout layout = new GridLayout(1, 3, 10, 10);

		// Hide the gridlines
		layout.setHgap(0);
		layout.setVgap(0);
		this.setLayout(layout);
		this.getContentPane().setBackground(greenBackground);

		
		
		this.setVisible(true);
		this.setEnabled(true);
		this.setVisible(true);
		
		// Add the menu bar
		this.setJMenuBar(createFarkleMenuBar());
		
		// Call to create dice panel
			buildDicePanel();
		
		// Call to create score panel
		createScoreGuidePanel();

		// Tell the controller we're done
		controller.newGame();

		pack();
	}
	

	/**
	 * Create a JPanel which contains two buttons and attach a Listener to each
	 * 
	 * @return
	 */
	public JPanel[] createButtonPanel() {
		JPanel buttonPanels[] = { new JPanel(), new JPanel(), new JPanel() };

		/********************************************
		 * 1.2.4 - A “Roll Dice” button shall be displayed below the dice in the
		 * middle of the screen.
		 ********************************************/
		if (rollBtn.getActionListeners().length == 0) {
			rollBtn.addActionListener(controller);
		}
		buttonPanels[0].add(rollBtn);
		buttonPanels[0].setBackground(greenBackground);

		/***************************************************
		 * 1.2.8 - A “Select All” button shall be displayed below the dice in
		 * the middle of the screen, and shall be initially disabled.
		 ***************************************************/
		if (selectAllBtn.getActionListeners().length == 0) {
			selectAllBtn.addActionListener(controller);
		}
		buttonPanels[1].add(selectAllBtn);
		selectAllBtn.setEnabled(false);
		buttonPanels[1].setBackground(greenBackground);

		/********************************************
		 * 1.2.5: A “Bank Score” button shall be displayed below the dice in the
		 * middle of the screen, (and shall initially be disabled).
		 ********************************************/
		if (bankBtn.getActionListeners().length == 0) {
			bankBtn.addActionListener(controller);
		}
		buttonPanels[2].add(bankBtn);
		buttonPanels[2].setBackground(greenBackground);
		getBankBtn().setEnabled(false);
		return buttonPanels;
	}

	/*******************************************************************************
	 * TODO: 1.2.1 The center of the screen shall display the six dice used
	 * during gameplay. These dice shall display the name of the game, Farkle,
	 * until the user selects the roll button for the first time.
	 *******************************************************************************/

	/**
	 * Combine the diceHeader and diceGrid panels in to a single panel which can
	 * be added to the frame
	 * 
	 * @param diceHeaderPanel
	 * @param diceGridPanel
	 * @return
	 */
	public void buildDicePanel() {
		JPanel diceHeaderPanel = createDiceHeaderPanel();
		JPanel diceGridPanel = createDiceGridPanel();
		JPanel dicePanel = new JPanel();
		dicePanel.setLayout(new BoxLayout(dicePanel, BoxLayout.Y_AXIS));
		diceHeaderPanel.setBorder(BorderFactory
				.createLineBorder(Color.WHITE, 3));
		dicePanel.add(diceHeaderPanel);
		diceGridPanel.setPreferredSize(diceGridPanel.getMaximumSize());
		dicePanel.add(diceGridPanel);
		this.add(dicePanel);
	}

	/********************************************************************************
	 * 1.2.2 - The total turn score shall be displayed above the dice in the
	 * center of the screen, and tThe score for the selected dice of the current
	 * rollcurrent accumulated turn point total shall be displayed directly
	 * below the turn score in the center of the screen. This score shall be
	 * updated as each die is selected.
	 ********************************************************************************/

	/**
	 * Create a JPanel to hold the Turn Score and Roll Score labels and their
	 * corresponding values
	 * 
	 * @return
	 */
	public JPanel createDiceHeaderPanel() {
		JPanel diceHeaderPanel = new JPanel(new GridLayout(0, 2, 0, 0));
		JLabel turnScore = new JLabel("<html>Turn Score: </html>");
		JLabel rollScoreLabel = new JLabel("<html>Roll Score: </html>");
		turnScore.setForeground(Color.WHITE);
		turnScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		turnScore.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(15, 3, 15, 0)));
		diceHeaderPanel.add(turnScore);
		runningScore.setForeground(Color.WHITE);
		runningScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		runningScore.setHorizontalAlignment(SwingConstants.CENTER);
		runningScore.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(15, 0, 15, 3)));
		diceHeaderPanel.add(runningScore);
		rollScoreLabel.setForeground(Color.WHITE);
		rollScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		rollScoreLabel.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0,
				Color.WHITE));
		rollScoreLabel.setBorder(BorderFactory.createEmptyBorder(15, 3, 15, 0));
		diceHeaderPanel.add(rollScoreLabel);
		rollScore.setForeground(Color.WHITE);
		rollScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		rollScore.setHorizontalAlignment(SwingConstants.CENTER);
		rollScore.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3,
				Color.WHITE));
		rollScore.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 3));
		diceHeaderPanel.add(rollScore);
		diceHeaderPanel.setBackground(greenBackground);
		return diceHeaderPanel;
	}

	/**
	 * Create a JPanel with six Dice, the running score JLabels and the Roll and
	 * Bank buttons
	 * 
	 * @return
	 */
	public JPanel createDiceGridPanel() {
		// Create the panel
		JPanel dicePanel = new JPanel(new GridLayout(0, 3, 0, 0));

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
			dice[i].setHorizontalAlignment(SwingConstants.CENTER);
			dicePanel.add(new JLabel(" "));
		}
		dicePanel.add(new JLabel(" "));
		dicePanel.add(new JLabel(" "));
		dicePanel.add(new JLabel(" "));

		// Call to add buttons and satisfy
		// requirements 1.3.4 and 1.3.5
		JPanel btns[] = createButtonPanel();
		dicePanel.add(btns[0]);
		dicePanel.add(btns[1]);
		dicePanel.add(btns[2]);
		dicePanel.setBackground(greenBackground);
		dicePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 3),
				BorderFactory.createEmptyBorder(3, 17, 3, 17)));

		return dicePanel;
	}

	public void buildPlayerPanel(GameMode gameMode) {
		JPanel playersPanel = new JPanel();
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
		playersPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));

		if (null != gameMode && gameMode == GameMode.SINGLEPLAYER) {
			// Create and add the name and score panels for player 1
			JScrollPane player1ScrollPanel = createPlayerScorePanel(1, 10);
			player1ScrollBar = player1ScrollPanel.getVerticalScrollBar();
			player1ScrollPanel.setBorder(BorderFactory.createEmptyBorder());
			playersPanel.add(createPlayerNamePanel(1));
			player1ScrollPanel.setPreferredSize(new Dimension(350, 568));
			playersPanel.add(player1ScrollPanel);
		} else if (null != gameMode && gameMode == GameMode.MULTIPLAYER) {
			// Create and add the name and score panels for player 1
			JScrollPane player1ScrollPanel = createPlayerScorePanel(1, 5);
			player1ScrollBar = player1ScrollPanel.getVerticalScrollBar();
			player1ScrollPanel.setBorder(BorderFactory.createEmptyBorder());
			player1ScrollPanel.setPreferredSize(new Dimension(350, 249));
			playersPanel.add(createPlayerNamePanel(1));
			playersPanel.add(player1ScrollPanel);

			// Create and add the name and score panels for player 2
			JScrollPane player2ScrollPanel = createPlayerScorePanel(2, 5);
			player2ScrollBar = player2ScrollPanel.getVerticalScrollBar();
			player2ScrollPanel.setBorder(BorderFactory.createEmptyBorder());
			player2ScrollPanel.setPreferredSize(new Dimension(350, 249));
			playersPanel.add(createPlayerNamePanel(2));
			playersPanel.add(player2ScrollPanel);
			playersPanel.setBackground(greenBackground);
		}
		
		/**********************************************************
		 * 1.3.5 - The current highest achieved score shall be displayed on the
		 * lower left hand corner of the screen below the overall point total
		 * for the current game. This score shall initially be set to 5000
		 * points.
		 **********************************************************/
		addHighScore(playersPanel);
		playersPanel.setBackground(greenBackground);
		
		this.add(playersPanel, 0);
	}

	/***********************************************************
	 * 1.3.2 - The overall point total for the current game shall be displayed
	 * on the lower left hand corner of the screen.
	 ***********************************************************/

	private JPanel createPlayerNamePanel(int playerNumber) {
		JPanel playerNamePanel = new JPanel(new GridLayout(0, 2, 0, 0));
		JLabel playerNameLabel = (playerNumber == 1) ? player1NameLabel
				: player2NameLabel;
		JLabel playerName = (playerNumber == 1) ? player1Name : player2Name;
		JLabel playerGameScoreLabel = (playerNumber == 1) ? player1GameScoreLabel
				: player2GameScoreLabel;
		JLabel playerGameScore = (playerNumber == 1) ? player1GameScore
				: player2GameScore;

		playerNameLabel.setForeground(Color.WHITE);
		playerNameLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		if (playerNumber == 1) {
			playerNameLabel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
					BorderFactory.createEmptyBorder(12, 3, 15, 0)));
		} else {
			playerNameLabel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(6, 0, 1, 0, Color.WHITE),
					BorderFactory.createEmptyBorder(12, 3, 15, 0)));
		}
		playerNamePanel.add(playerNameLabel);

		playerName.setForeground(Color.WHITE);
		playerName.setFont(new Font("Arial Black", Font.BOLD, 14));
		if (playerNumber == 1) {
			playerName.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
					BorderFactory.createEmptyBorder(17, 3, 15, 3)));
		} else {
			playerName.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createMatteBorder(6, 0, 1, 0, Color.WHITE),
					BorderFactory.createEmptyBorder(17, 3, 15, 3)));
		}
		playerNamePanel.add(playerName);

		/*****************************************************
		 * 1.3.3 - The left side of the screen shall have an area to display the
		 * point total for each of the ten turns taken in single player mode.
		 *****************************************************/
		playerGameScoreLabel.setForeground(Color.WHITE);
		playerGameScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		playerGameScoreLabel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 6, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(15, 3, 15, 3)));
		playerNamePanel.add(playerGameScoreLabel);
		playerGameScore.setForeground(Color.WHITE);
		playerGameScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		playerGameScore.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 6, 0, Color.WHITE),
				BorderFactory.createEmptyBorder(15, 3, 15, 3)));
		playerNamePanel.add(playerGameScore);
		playerNamePanel.setBackground(greenBackground);
		playerNamePanel.setPreferredSize(new Dimension(350, 110));
		playerNamePanel.setMaximumSize(new Dimension(350, 110));
		return playerNamePanel;
	}

	/**
	 * Create a new JPanel that contains all the JLabels necessary to represent
	 * a player with 10 turns
	 * 
	 * @param playerName
	 * @param playerNumber
	 * @return
	 */
	private JScrollPane createPlayerScorePanel(int playerNumber,
			int scoreLabelCount) {
		JPanel playerPanel = new JPanel(new GridLayout(0, 2, 0, 2));
		if (playerNumber == 1) {
			player1ScorePanel = playerPanel;
		} else {
			player2ScorePanel = playerPanel;
		}

		/***************************************************
		 * 1.4.3: The left side of the screen shall have an area to display the
		 * point total for each of the ten turns taken in single player mode.
		 ***************************************************/
		for (int i = 0; i < scoreLabelCount; i++) {
			addTurnScore(playerNumber, i + 1);
		}

		playerPanel.setBackground(greenBackground);
		playerPanel.setBorder(BorderFactory.createEmptyBorder(3, 17, 3, 17));

		JScrollPane playerScrollPane = new JScrollPane(playerPanel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		return playerScrollPane;
	}

	public void addHighScore(JPanel panel) {
		/**************************************************
		 * 1.4.5: The current highest achieved score shall be displayed. This
		 * score shall initially be set to 5000 points.
		 ***************************************************/
		JPanel highScorePanel = new JPanel(new GridLayout(0, 2, 0, 0));
		highScoreTitle.setForeground(Color.WHITE);
		highScoreTitle.setFont(new Font("Arial Black", Font.BOLD, 14));
		highScorePanel.add(highScoreTitle);
		highScore.setForeground(Color.WHITE);
		highScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		highScorePanel.add(highScore);
		highScorePanel.setBackground(greenBackground);
		highScorePanel.setMaximumSize(new Dimension(350, 50));
		highScorePanel.setBorder(BorderFactory.createMatteBorder(6, 0, 0, 0,
				Color.WHITE));
		panel.add(highScorePanel);
	}

	public void addTurnScore(int playerNumber, int turnNumber) {
		ArrayList<JLabel> playerScoreLabels = (playerNumber == 1) ? player1ScoreLabels
				: player2ScoreLabels;
		ArrayList<JLabel> playerScores = (playerNumber == 1) ? player1Scores
				: player2Scores;
		JPanel playerPanel = (playerNumber == 1) ? player1ScorePanel
				: player2ScorePanel;

		// Add the labels for each turn
		JLabel turnLabel = new JLabel("Roll " + (turnNumber) + ": ");
		turnLabel.setForeground(Color.WHITE);
		turnLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		turnLabel.setMaximumSize(new Dimension(175, 20));
		turnLabel.setPreferredSize(new Dimension(175, 20));
		playerScoreLabels.add(turnLabel);
		playerPanel.add(playerScoreLabels.get(turnNumber - 1));

		// Add the score label for each turn
		JLabel turnScoreLabel = new JLabel();
		turnScoreLabel.setForeground(Color.WHITE);
		turnScoreLabel.setFont(new Font("Arial Black", Font.BOLD, 14));
		turnLabel.setMaximumSize(new Dimension(175, 20));
		turnLabel.setPreferredSize(new Dimension(175, 20));
		playerScores.add(turnScoreLabel);
		playerPanel.add(playerScores.get(turnNumber - 1));
	}

	/*****************************************************************
	 * 1.2.3 - Rules for the scoring combinations shall be displayed on the
	 * right side of the screen.
	 *****************************************************************/
	
	/**
	 * Create a new JPanel that displays the score guide image
	 * 
	 * @return JLabel
	 */
	private void createScoreGuidePanel() {

		/*****************************************************
		 * 1.3.3: Rules for the scoring combinations shall be displayed on the
		 * right side of the screen.
		 *****************************************************/
		JPanel scorePanel = new JPanel();
		try {
			Image scoreGuide = ImageIO.read(getClass().getResource(
					"/images/FarkleScores.png"));
			scoreGuide = scoreGuide.getScaledInstance(200, 680,
					Image.SCALE_SMOOTH);
			JLabel scoreLabel = new JLabel(new ImageIcon(scoreGuide));
			scorePanel.add(scoreLabel);

			scorePanel.setBackground(greenBackground);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		scorePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 3),
				BorderFactory.createEmptyBorder(3, 17, 3, 17)));
		this.add(scorePanel);
	}

	public JButton getRollBtn() {
		return rollBtn;
	}

	public JButton getBankBtn() {
		return bankBtn;
	}

	public JButton getSelectAllBtn() {
		return selectAllBtn;
	}

	public void setGameScore(int playerNumber, int score) {
		JLabel gameScore = (playerNumber == 1) ? player1GameScore
				: player2GameScore;
		gameScore.setText("" + score);
	}

	public void setHighScore(int score) {
		this.highScore.setText("" + score);
	}

	public int getRunningScore() {
		try {
			return Integer.valueOf(runningScore.getText());
		} catch(NumberFormatException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void rollDice() {
		// Test farkle roll
		 /*dice[0].setValue('f');
		 dice[1].setValue('a');
		 dice[2].setValue('r');
		 dice[3].setValue('k');
		 dice[4].setValue('l');
		 dice[5].setValue('e');*/

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
	
	public void resetDicePanel() {
		resetDice();
		this.rollBtn.setEnabled(true);
		this.selectAllBtn.setEnabled(false);
		this.bankBtn.setEnabled(false);
	}
	
	public void diceFirstRun() {
		 dice[0].setValue('f');
		 dice[1].setValue('a');
		 dice[2].setValue('r');
		 dice[3].setValue('k');
	 	 dice[4].setValue('l');
	 	 dice[5].setValue('e');
	}

	public void resetScores(int playerNumber) {
		ArrayList<JLabel> scores = getPlayerScores(playerNumber);
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
	 * TODO: Is turn an index here or not?
	 * 
	 * @param int player
	 * @param int turn
	 * @param int score
	 */
	public void setTurnScore(int player, int turn, int score) {
		ArrayList<JLabel> scores = getPlayerScores(player);
		scores.get(turn - 1).setText("" + score);
	}

	/**
	 * Update the running score label with the specified score
	 * 
	 * @param int score
	 */
	public void setRunningScore(int score) {
		runningScore.setText("" + score);
	}

	public void setRollScore(int score) {
		rollScore.setText("" + score);
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
	@SuppressWarnings("deprecation")
	public void displayMessage(String message, String title) {
		JOptionPane pane = new JOptionPane(message);
		JDialog dialog = pane.createDialog(title);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		dialog.show();
	}

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

	@Override
	public void dispose() {
		super.dispose();
		System.exit(0);
	}

	public void selectDice(List<Integer> valuesNeeded) throws InterruptedException {
		List<Die> dice = getDice(DieState.UNHELD);
		for (Die d : dice) {
			for (Integer i : valuesNeeded) {
				if (d.getValue() == i) {
					Thread.sleep(500);
					d.dispatchEvent(new MouseEvent(d, MouseEvent.MOUSE_PRESSED,
							System.currentTimeMillis(), MouseEvent.BUTTON1, d
									.getX(), d.getY(), 1, false));
					valuesNeeded.remove(i);
					break;
				}
			}
		}
		Thread.sleep(1500);
	}

	/**
	 * If no dice are held, selects all unheld dice If no dice are unheld,
	 * selects all held dice
	 */
	public void selectAllDice() {
		if (getDice(DieState.HELD).size() == 0) {
			ArrayList<Die> dice = getDice(DieState.UNHELD);
			for (Die d : dice) {
				d.dispatchEvent(new MouseEvent(d, MouseEvent.MOUSE_PRESSED,
						System.currentTimeMillis(), MouseEvent.BUTTON1, d
								.getX(), d.getY(), 1, false));
			}
		} else {
			ArrayList<Die> dice = getDice(DieState.HELD);
			for (Die d : dice) {
				d.dispatchEvent(new MouseEvent(d, MouseEvent.MOUSE_PRESSED,
						System.currentTimeMillis(), MouseEvent.BUTTON1, d
								.getX(), d.getY(), 1, false));
			}
		}
	}

	public void setPlayerName(int playerNumber, String name) {
		if (null != name && playerNumber >= 1 && playerNumber <= 2) {
			if (playerNumber == 1) {
				this.player1Name.setText(name);
			} else {
				this.player2Name.setText(name);
			}
		}
	}

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

	public void highlightTurnScore(int playerNumber, int turn,
			boolean isBonusTurn) {
		// Unhighlight all turns
		unHighlightAllTurnScores(playerNumber);

		// Get the labels and their scores for the given player
		ArrayList<JLabel> scoreLabels = getPlayerScoreLabels(playerNumber);
		ArrayList<JLabel> scores = getPlayerScores(playerNumber);

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

	public void unHighlightTurn(JLabel scoreLabel, JLabel score) {
		scoreLabel.setOpaque(false);
		scoreLabel.setForeground(Color.WHITE);
		score.setOpaque(false);
		score.setForeground(Color.WHITE);
	}

	public void highlightTurn(JLabel scoreLabel, JLabel score,
			boolean isBonusTurn) {

		/***********************************************************************
		 * 1.3.4 - The current turn shall be indicated by highlighting that turn
		 * on the left side of the screen. This turn shall be highlighted as
		 * soon as the previous turn ends (which occurs after the player selects
		 * the “Bank Score” button, or the user selects the “OK” button in the
		 * Farkle message dialog box), and before the player selects the “Roll
		 * Dice” button for the current turn.
		 ************************************************************************/

		// Highlight given turn in white if it's not a bonus turn
		if (!isBonusTurn) {
			scoreLabel.setOpaque(true);
			scoreLabel.setBackground(Color.WHITE);
			scoreLabel.setForeground(Color.BLACK);

			score.setOpaque(true);
			score.setBackground(Color.WHITE);
			score.setText("");
			score.setForeground(Color.BLACK);
		} else {
			// Else highlight given turn yellow for bonus turn
			scoreLabel.setOpaque(true);
			scoreLabel.setBackground(Color.YELLOW);
			scoreLabel.setForeground(Color.BLACK);

			score.setOpaque(true);
			score.setText("BONUS ROLL!");
			score.setBackground(Color.YELLOW);
			score.setForeground(Color.BLACK);
		}
	}

	public ArrayList<JLabel> getPlayerScoreLabels(int playerNumber) {
		ArrayList<JLabel> scoreLabels = (playerNumber == 1) ? player1ScoreLabels
				: player2ScoreLabels;
		return scoreLabels;
	}

	public ArrayList<JLabel> getPlayerScores(int playerNumber) {
		ArrayList<JLabel> scores = (playerNumber == 1) ? player1Scores
				: player2Scores;
		return scores;
	}

	private void getSounds() {
		// Initialize the sounds
		rollSounds.add(getClass().getResource("/sounds/roll1.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll2.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll3.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll4.wav"));

		bankSounds.add(getClass().getResource("/sounds/bank1.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank2.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank3.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank4.wav"));

		bonusSound = getClass().getResource("/sounds/bonus.wav");
	}

	public JMenuBar createFarkleMenuBar() {
		// Menu Bar
		/******************************************************
		 * 1.2.10 A menu shall be displayed at the top of the main GUI with one
		 * main option, File, and three sub options: New Game, Restart
		 * Game, and Quit.
		 ******************************************************/
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		
		menuBar.add(fileMenu);
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
		
		fileMenu.addMenuListener(new MenuListener() {

			@Override
			public void menuSelected(MenuEvent e) {
				hintAction.setEnabled(controller.isHintAvailable());
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
		 * mode option box (section 1.1.0) is displayed.
		 ********************************************************/
		if (newAction.getActionListeners().length == 0) {
			newAction.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					controller.endGame(false, true);
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
					controller.endGame(true, false);
				}
			});
		}
		
		if(resetHighScoreAction.getActionListeners().length == 0) {
			resetHighScoreAction.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.resetHighScore();
				}
			});
		}

		/****************************************************************
		 * 1.2.10.c If the user selects the Quit option, the application is
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

	public JDialog getFarkleMessage() {
		return farkleMessage;
	}

	public String getRollBtnText() {
		return this.rollBtn.getText();
	}
}
