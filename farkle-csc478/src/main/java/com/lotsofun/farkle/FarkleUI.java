package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FarkleUI extends JFrame implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Die[] dice = new Die[6];
	public JButton rollBtn = new JButton("Roll Dice");
	public JButton bankBtn = new JButton("Bank Score");
	public JButton selectAllBtn = new JButton("Select All");
	public FarkleController controller;
	public JLabel playerNameLabel = new JLabel("");
	public JLabel[] scoreLabels = new JLabel[10];
	public JLabel[] playerScores = new JLabel[10];
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

	//My Frame Globals
	JFrame frame = new JFrame("Farkle");
	public JLabel singlePlayerLabel = new JLabel("One Player");
	public JLabel multiplayerLabel = new JLabel("Two Player");
	public JLabel humanPlayerLabel = new JLabel("Human");
	public JLabel computerPlayerLabel = new JLabel("Computer");
	JLabel labelZero = new JLabel ("Names:");
	JLabel labelOne = new JLabel("Player One:");
	JTextField playerOneName = new JTextField(5);
	JLabel labelTwo = new JLabel("Player Two:");
	JTextField playerTwoName = new JTextField(5);
	JButton startButton = new JButton("Start");
	JButton closeButton = new JButton("Close");
	Color defaultColor = singlePlayerLabel.getBackground();


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



		/******************************************
		 * 1.4.1: The title of the window shall display: �Farkle � Single Player
		 * Mode�.
		 ******************************************/
		// TODO: CuBr - Write a test case for the window title
		// based on player mode

		// Create and set up the main Window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1024, 768));
		frame.setResizable(false);
		GridLayout layout = new GridLayout(1, 3, 10, 10);

		// Hide the gridlines
		layout.setHgap(0);
		layout.setVgap(0);
		frame.setLayout(layout);

		//Menu Bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem resetAction = new JMenuItem("Reset");
		JMenuItem exitAction = new JMenuItem("Exit");
		fileMenu.add(newAction);
		fileMenu.add(resetAction);
		fileMenu.add(exitAction);
		newAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.endGame(false, true);
			}
		});
		resetAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.endGame(true, false);
			}
		});
		exitAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pullThePlug();
			}
		});

		frame.setJMenuBar(menuBar);


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
		controller.setupGame();
	}

	/* Reduce Reuse Recycle */
	public void getGameInformation() {

		frame.setEnabled(false);
		final JDialog window = new JDialog(frame);
		window.setAlwaysOnTop(true);
		window.setPreferredSize(new Dimension(750, 250));
		window.setResizable(false);
		window.setUndecorated(true);
		GridLayout layout = new GridLayout(1, 3, 10, 10);
		window.setLayout(layout);

		controller.tempGameInformation[0] = "S";
		controller.tempGameInformation[1] = "";
		controller.tempGameInformation[2] = "";
		controller.tempGameInformation[3] = "";

		//Create panels here
		JPanel gameModePanel = new JPanel();
		singlePlayerLabel.setName("1");
		singlePlayerLabel.setOpaque(true);
		singlePlayerLabel.setBackground(Color.WHITE);
		singlePlayerLabel.addMouseListener(this);			
		multiplayerLabel.setName("2");
		multiplayerLabel.setOpaque(true);
		multiplayerLabel.addMouseListener(this);
		gameModePanel.add(singlePlayerLabel);
		gameModePanel.add(multiplayerLabel);

		JPanel playerTypePanel = new JPanel();
		humanPlayerLabel.setName("3");
		humanPlayerLabel.setOpaque(true);
		humanPlayerLabel.setEnabled(false);
		humanPlayerLabel.addMouseListener(this);			
		computerPlayerLabel.setName("4");
		computerPlayerLabel.setOpaque(true);
		computerPlayerLabel.setEnabled(false);
		computerPlayerLabel.addMouseListener(this);
		playerTypePanel.add(humanPlayerLabel);
		playerTypePanel.add(computerPlayerLabel);

		JPanel playerNamesPanel = new JPanel();	
		playerNamesPanel.add(labelZero);
		playerNamesPanel.add(labelOne);
		playerNamesPanel.add(playerOneName);
		playerOneName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateButtons();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateButtons();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				updateButtons();
			}

			public void updateButtons()
			{
				if (playerOneName.getText().length() == 0)
				{
					startButton.setEnabled(false);
				}
				else
				{
					startButton.setEnabled(true);
				}
			}
		});
		labelTwo.setEnabled(false);
		playerNamesPanel.add(labelTwo);
		playerTwoName.setEnabled(false);
		playerNamesPanel.add(playerTwoName);
		playerTwoName.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateButtons();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateButtons();
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				updateButtons();
			}

			public void updateButtons()
			{
				if (playerTwoName.getText().length() == 0)
				{
					startButton.setEnabled(false);
				}
				else
				{
					startButton.setEnabled(true);
				}
			}
		});

		JPanel buttonPanel = new JPanel();
		startButton.setEnabled(false);
		startButton.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e){
			controller.tempGameInformation[2] = playerOneName.getText();
			controller.tempGameInformation[3] = playerTwoName.getText();
			if (("Ginuwine").equalsIgnoreCase(controller.tempGameInformation[2])) {

				try 
				{
					AudioInputStream audioStream;
					audioStream = AudioSystem.getAudioInputStream(gSound);
					Clip clip = AudioSystem.getClip();
					clip.open(audioStream);
					clip.start();								
				} 
				catch (UnsupportedAudioFileException x) 
				{
					x.printStackTrace();
				} 
				catch (LineUnavailableException y) 
				{
					y.printStackTrace();
				} 
				catch (IOException z) 
				{
					z.printStackTrace();
				}
			}
			controller.newGame();
			frame.setEnabled(true);
			frame.setVisible(true);
			window.dispose();
		}
		});
		buttonPanel.add(startButton);
		closeButton.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e){pullThePlug();}});
		buttonPanel.add(closeButton);

		window.add(gameModePanel);
		window.add(playerTypePanel);
		window.add(playerNamesPanel);
		window.add(buttonPanel);

		//Show the window
		window.setLocationRelativeTo(null);
		window.pack();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - window.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - window.getHeight()) / 2);
		window.setLocation(x, y);
		window.setVisible(true);			

		
	}

	/**
	 * Create a JPanel which contains two buttons and attach a Listener to each
	 * 
	 * @return
	 */
	public JPanel[] createButtonPanel() {
		JPanel buttonPanels[] = { new JPanel(), new JPanel(), new JPanel() };

		/********************************************
		 * 1.3.4: A �Roll� button shall be displayed.
		 ********************************************/
		rollBtn.addActionListener(controller);
		buttonPanels[0].add(rollBtn);
		buttonPanels[0].setBackground(new Color(35, 119, 34));

		selectAllBtn.addActionListener(controller);
		buttonPanels[1].add(selectAllBtn);
		selectAllBtn.setEnabled(false);
		buttonPanels[1].setBackground(new Color(35, 119, 34));


		/********************************************
		 * 1.3.5: A �Bank� button shall be displayed (and shall initially be
		 * disabled).
		 ********************************************/
		bankBtn.addActionListener(controller);
		buttonPanels[2].add(bankBtn);
		buttonPanels[2].setBackground(new Color(35, 119, 34));
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
		JLabel turnScore = new JLabel("<html>Turn Score: </html>");
		turnScore.setForeground(Color.WHITE);
		turnScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		dicePanel.add(turnScore);
		runningScore.setForeground(Color.WHITE);
		runningScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		runningScore.setHorizontalAlignment(JLabel.CENTER);
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
			dice[i].setHorizontalAlignment(JLabel.CENTER);
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
		dicePanel.setBackground(new Color(35, 119, 34));
		dicePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 3),
				BorderFactory.createEmptyBorder(3, 17, 3, 17)));

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
		JLabel nameLbl = new JLabel("Player: ");
		nameLbl.setForeground(Color.WHITE);
		nameLbl.setFont(new Font("Arial Black", Font.BOLD, 16));
		playerPanel.add(nameLbl);
		playerNameLabel.setForeground(Color.WHITE);
		playerNameLabel.setFont(new Font("Arial Black", Font.BOLD, 16));
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
			scoreLabels[i].setForeground(Color.WHITE);
			scoreLabels[i].setFont(new Font("Arial Black", Font.BOLD, 14));
			playerPanel.add(scoreLabels[i]);
			playerScores[i] = new JLabel();
			playerScores[i].setForeground(Color.WHITE);
			playerScores[i].setFont(new Font("Arial Black", Font.BOLD, 14));

			playerPanel.add(playerScores[i]);
		}

		/*****************************************************
		 * 1.4.2: The overall point total shall be displayed.
		 *****************************************************/
		gameScoreTitle.setForeground(Color.WHITE);
		gameScoreTitle.setFont(new Font("Arial Black", Font.BOLD, 14));
		playerPanel.add(gameScoreTitle);
		gameScore.setForeground(Color.WHITE);
		gameScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		playerPanel.add(gameScore);
		/**************************************************
		 * 1.4.5: The current highest achieved score shall be displayed. This
		 * score shall initially be set to 5000 points.
		 ***************************************************/
		highScoreTitle.setForeground(Color.WHITE);
		highScoreTitle.setFont(new Font("Arial Black", Font.BOLD, 14));
		playerPanel.add(highScoreTitle);
		highScore.setForeground(Color.WHITE);
		highScore.setFont(new Font("Arial Black", Font.BOLD, 14));
		playerPanel.add(highScore);
		playerPanel.setBackground(new Color(35, 119, 34));
		playerPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 3),
				BorderFactory.createEmptyBorder(3, 17, 3, 17)));

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
					"/images/FarkleScores.png"));
			scoreGuide = scoreGuide.getScaledInstance(200, 680,
					Image.SCALE_SMOOTH);
			JLabel scoreLabel = new JLabel(new ImageIcon(scoreGuide));
			scorePanel.add(scoreLabel);

			scorePanel.setBackground(new Color(35, 119, 34));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		scorePanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE, 3),
				BorderFactory.createEmptyBorder(3, 17, 3, 17)));
		return scorePanel;
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
//				dice[0].setValue(1);
//				dice[1].setValue(1);
//				dice[2].setValue(1);
//				dice[3].setValue(1);
//				dice[4].setValue(1);
//				dice[5].setValue(1);

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
		for (JLabel score : playerScores) {
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
		playerScores[turn - 1].setText("" + score);
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

		for (int i = 0; i <= playerScores.length - 1; i++) {
			playerScores[i].setOpaque(false);
			playerScores[i].setForeground(Color.WHITE);
			scoreLabels[i].setOpaque(false);
			scoreLabels[i].setForeground(Color.WHITE);
		}
		if (turn != 0 && !isBonusTurn) {
			playerScores[turn - 1].setOpaque(true);
			playerScores[turn - 1].setBackground(Color.WHITE);
			playerScores[turn - 1].setText("");
			playerScores[turn - 1].setForeground(Color.BLACK);

			scoreLabels[turn - 1].setOpaque(true);
			scoreLabels[turn - 1].setBackground(Color.WHITE);
			scoreLabels[turn - 1].setForeground(Color.BLACK);
		} else if (turn != 0) {
			playerScores[turn - 1].setOpaque(true);
			playerScores[turn - 1].setText("BONUS ROLL!");
			playerScores[turn - 1].setBackground(Color.YELLOW);
			playerScores[turn - 1].setForeground(Color.BLACK);
			scoreLabels[turn - 1].setOpaque(true);
			scoreLabels[turn - 1].setBackground(Color.YELLOW);
			scoreLabels[turn - 1].setForeground(Color.BLACK);
		}
	}

	/**
	 * Update the running score label with the specified score
	 * 
	 * @param int score
	 */
	public void setRunningScore(int score) {
		runningScore.setText("" + score);
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

	public boolean gameEnded(boolean resetOnly, boolean mainMenu) {
		boolean retVal = true;
		if ((!resetOnly) && (!mainMenu)){
			/************************************************** 
			 * 1.6.0: At the conclusion of the game, the user shall be greeted with
			 * three options: �Play again?�, �Main Menu�, and �Quit�.
			 *************************************************/
			Object[] options = { "Play Again", "Main Menu", "Exit"};
			int n = JOptionPane.showOptionDialog(this,	 
					"Total Score: " + controller.farkleGame.players[0].gameScore + "\nWhat would you like to do?",
					"Game Over",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[2]);

			/************************************************* 
			 * 2.1.5: If �Play again?� is selected, the game starts over in 1 player
			 * mode.
			 ************************************************/
			if (n == 0) {
				retVal = true;
			}
			/************************************************* 
			 * 2.1.6: If �Main Menu� is selected, the user is taken to the mode
			 * selection screen.
			 ************************************************/
			else if (n == 1) {
				retVal = false;
			}
			/************************************************** 
			 * 2.1.7: If �Quit� is selected, the application immediately closes.
			 *************************************************/
			else {
				pullThePlug();
			}
		}

		if (mainMenu)
		{
			retVal = false;
		}
		runningScore.setText("");
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


	/**
	 * If no dice are held, selects all unheld dice
	 * If no dice are unheld, selects all held dice
	 */
	public void selectAllDice() {
		if(getDice(DieState.HELD).size() == 0) {
			ArrayList<Die> dice = getDice(DieState.UNHELD);
			for(Die d : dice) {
				d.dispatchEvent(new MouseEvent(d, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), MouseEvent.BUTTON1, d.getX(), d.getY(), 1, false));
			}
		} else {
			ArrayList<Die> dice = getDice(DieState.HELD);
			for(Die d : dice) {
				d.dispatchEvent(new MouseEvent(d, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), MouseEvent.BUTTON1, d.getX(), d.getY(), 1, false));
			}
		}
	}

	public void updateGUI(int[] scores, String playerName)
	{
		playerNameLabel.setText(playerName);
		runningScore.setText("");
		resetScores();
		for (int i = 0; i <= 9; i++)
		{
			playerScores[i].setText(String.valueOf(scores[i]));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//SinglePlayer
		if (e.getComponent().getName().equals("1"))
		{
			singlePlayerLabel.setBackground(Color.WHITE);
			multiplayerLabel.setBackground(defaultColor);
			humanPlayerLabel.setBackground(defaultColor);
			humanPlayerLabel.setEnabled(false);
			computerPlayerLabel.setBackground(defaultColor);
			computerPlayerLabel.setEnabled(false);
			labelTwo.setEnabled(false);
			playerTwoName.setEnabled(false);
			playerTwoName.setText("");
			startButton.setEnabled(false);
			controller.tempGameInformation[0] = "S";
			controller.tempGameInformation[1] = "";
		}
		//Multiplayer
		else if (e.getComponent().getName().equals("2"))
		{
			singlePlayerLabel.setBackground(defaultColor);
			multiplayerLabel.setBackground(Color.WHITE);
			humanPlayerLabel.setBackground(Color.WHITE);
			humanPlayerLabel.setEnabled(true);
			computerPlayerLabel.setBackground(defaultColor);
			computerPlayerLabel.setEnabled(true);
			labelTwo.setEnabled(true);
			playerTwoName.setEnabled(true);
			playerTwoName.setText("");
			startButton.setEnabled(false);
			controller.tempGameInformation[0] = "M";
			controller.tempGameInformation[1] = "H";
		}
		//Human
		else if (e.getComponent().getName().equals("3"))
		{
			singlePlayerLabel.setBackground(defaultColor);
			multiplayerLabel.setBackground(Color.WHITE);
			humanPlayerLabel.setBackground(Color.WHITE);
			humanPlayerLabel.setEnabled(true);
			computerPlayerLabel.setBackground(defaultColor);
			computerPlayerLabel.setEnabled(true);
			labelTwo.setEnabled(true);
			playerTwoName.setEnabled(true);
			playerTwoName.setText("");
			startButton.setEnabled(false);
			controller.tempGameInformation[0] = "M";
			controller.tempGameInformation[1] = "H";
		}
		//Computer
		else if (e.getComponent().getName().equals("4"))
		{
			singlePlayerLabel.setBackground(defaultColor);
			multiplayerLabel.setBackground(Color.WHITE);
			humanPlayerLabel.setBackground(defaultColor);
			humanPlayerLabel.setEnabled(true);
			computerPlayerLabel.setBackground(Color.WHITE);
			computerPlayerLabel.setEnabled(true);
			labelTwo.setEnabled(false);
			playerTwoName.setEnabled(false);
			playerTwoName.setText("Computer");
			startButton.setEnabled(false);
			controller.tempGameInformation[0] = "M";
			controller.tempGameInformation[1] = "C";
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {


	}

	@Override
	public void mouseExited(MouseEvent e) {


	}

	@Override
	public void mousePressed(MouseEvent e) {


	}

	@Override
	public void mouseReleased(MouseEvent e) {


	}
}
