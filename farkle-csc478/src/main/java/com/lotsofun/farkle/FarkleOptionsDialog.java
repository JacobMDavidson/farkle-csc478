package com.lotsofun.farkle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Displays a dialog box used to select game mode options, including single
 * player or two player, human opponent or computer opponent, and setting
 * appropriate names for each player. <br />
 * <br />
 * Fulfills requirement 1.0.0:<br />
 * <br />
 * "Select Game Mode Option Box - Upon opening the application, the user is
 * greeted with an option box that includes all configuration options for
 * gameplay. These options include "1 Player Mode", "2 Player Mode", "Human
 * Opponent" (if two player mode is selected), "Computer Opponent" (if two
 * player mode is selected), and text fields to enter the associated player
 * names. Also included is a "Start" button and a "Close" button (both of which
 * are always enabled). This option dialog box should pop up over the main GUI
 * set to a solid green color."
 * 
 * @author Jacob Davidson
 * @version 3.0.0
 */
public class FarkleOptionsDialog extends JDialog implements MouseListener {

	/** Serialize this to comply with component conventions */
	private static final long serialVersionUID = 1L;

	/** The primary background color */
	private Color greenBackground = new Color(35, 119, 34);

	/** The label for the player mode selection */
	private JLabel playerModeSelectLabel = new JLabel(" Select Player Mode:");

	/** The single player mode selection label */
	private JLabel singlePlayerLabel = new JLabel("One Player Mode",
			SwingConstants.CENTER);

	/** The two player mode selection label */
	private JLabel multiplayerLabel = new JLabel("Two Player Mode",
			SwingConstants.CENTER);

	/** The label for the opponent player type selection */
	private JLabel playerTypeSelectLabel = new JLabel(" Select Opponent Type:");

	/** The human opponent player type selection label */
	private JLabel humanPlayerLabel = new JLabel("Human Opponent",
			SwingConstants.CENTER);

	/** The computer opponent player type selection label */
	private JLabel computerPlayerLabel = new JLabel("Computer Opponent",
			SwingConstants.CENTER);

	/** The label for the title of the game mode options menu */
	private JLabel gameModeOptionTitle = new JLabel("Game Mode Options",
			SwingConstants.CENTER);

	/** The title of the enter player names portion of the JDialog */
	private JLabel playerNamesLabel = new JLabel("Enter Player Names:");

	/** The label for entering player onne's name */
	private JLabel playerOneNameLabel = new JLabel("Player One:");

	/** The label for entering player two's name */
	private JLabel playerTwoNameLabel = new JLabel("Player Two:");

	/** The panel for entering player one's name */
	private JPanel playerOneNamePanel = new JPanel();

	/** The panel for entering player two's name */
	private JPanel playerTwoNamePanel = new JPanel();

	/** The panel for player mode selection */
	private JPanel playerModeSelectionPanel = new JPanel();

	/** The panel for player type selection */
	private JPanel playerTypeSelectionPanel = new JPanel();

	/** The text field for entering player one's name */
	private JTextField playerOneName = new JTextField(5);

	/** The text field for entering player two's name */
	private JTextField playerTwoName = new JTextField(5);

	/** The "Start" button */
	private JButton startButton = new JButton("Start");

	/** The "Close" button */
	private JButton closeButton = new JButton("Close");

	/** The provided name for player 1 */
	private String player1Name = "";

	/** The provided name for player 2 */
	private String player2Name = "";

	/** The selected game mode */
	private GameMode gameMode = GameMode.SINGLEPLAYER;

	/** The selected opponent player type */
	private PlayerType playerType = PlayerType.USER;

	/**
	 * The Farkle Options Dialog box constructor
	 * 
	 * @param frame
	 *            The parent JFrame for this dialog
	 */
	public FarkleOptionsDialog(JFrame frame) {
		// Pass the JFrame to the super constructor
		super(frame);

		// Set the modality to block all top level windows
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		// Set the layout of the JDialog box
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		// Set the size and decoration properties of the JDialog box
		this.setResizable(false);
		this.setUndecorated(true);
		this.setPreferredSize(new Dimension(750, 250));
		this.setBackground(greenBackground);

		// window is the main JPanel used to create a border
		JPanel window = new JPanel(new BorderLayout());
		window.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.YELLOW, 3),
				BorderFactory.createLineBorder(greenBackground, 5)));

		// The gameModePanel is the center of the window panel, and will house
		// the
		// game mode selection options, opponent type selection options, and
		// player
		// name text fields
		JPanel gameModePanel = new JPanel();
		gameModePanel.setLayout(new GridLayout(1, 2, 10, 0));
		gameModePanel
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		gameModePanel.setBackground(greenBackground);

		/****************************************************
		 * 1.1.2 - The "1 Player Mode" is highlighted by default when the
		 * application is first opened, and a blank text field for player one's
		 * name is displayed.
		 ***************************************************/
		// Create the single player option and highlight it as the default
		singlePlayerLabel.setName("1");
		singlePlayerLabel.setOpaque(true);
		singlePlayerLabel.setBackground(Color.YELLOW);
		singlePlayerLabel.addMouseListener(this);

		// Create the two player option
		multiplayerLabel.setName("2");
		multiplayerLabel.setOpaque(true);
		multiplayerLabel.addMouseListener(this);
		multiplayerLabel.setBackground(greenBackground);

		// The playerTypePanel is the panel beneath the player mode options, and
		// is only displayed
		// if 2 player mode is selected.
		JPanel playerTypePanel = new JPanel();
		playerTypePanel.setLayout(new GridLayout(2, 1, 0, 5));
		playerTypePanel.setBackground(greenBackground);

		// The playerModeSelectionPanel contains the player modes
		playerModeSelectionPanel.setLayout(new GridLayout(3, 1, 0, 5));
		playerModeSelectionPanel.setBackground(greenBackground);

		// The playerTypeSelectionPanel contains the player types
		playerTypeSelectionPanel.setLayout(new GridLayout(3, 1, 0, 5));
		playerTypeSelectionPanel.setBackground(greenBackground);

		// Create the human player type option
		humanPlayerLabel.setName("3");
		humanPlayerLabel.setOpaque(true);
		humanPlayerLabel.addMouseListener(this);
		humanPlayerLabel.setBackground(greenBackground);

		// Create the computer player type option
		computerPlayerLabel.setName("4");
		computerPlayerLabel.setOpaque(true);
		computerPlayerLabel.addMouseListener(this);
		computerPlayerLabel.setBackground(greenBackground);

		// Set the font and colors for the playerModeSelectLabel and add to
		// the playerModeSelectionPanel
		playerModeSelectLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		playerModeSelectLabel.setForeground(Color.WHITE);
		playerModeSelectionPanel.add(playerModeSelectLabel);

		// Set the font for the singlePlayerLabel and add to the
		// playerModeSelectionPanel
		singlePlayerLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerModeSelectionPanel.add(singlePlayerLabel);

		// Set the font and color for the multiPlayerLabel and add to the
		// playerModeSelectionPanel
		multiplayerLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		multiplayerLabel.setForeground(Color.WHITE);
		playerModeSelectionPanel.add(multiplayerLabel);

		// Set the font and color for the playerTypeSelectLabel and add to the
		// playerTypeSelectionPanel
		playerTypeSelectLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		playerTypeSelectLabel.setForeground(Color.WHITE);
		playerTypeSelectionPanel.add(playerTypeSelectLabel);

		// Set the font for the humanPlayerLabel and add to the
		// playerTypeSelectionPanel
		humanPlayerLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerTypeSelectionPanel.add(humanPlayerLabel);

		// Set the font and color for the computerPlayerLabel and add to the
		// playerTypeSelectionPanel
		computerPlayerLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		computerPlayerLabel.setForeground(Color.WHITE);
		playerTypeSelectionPanel.add(computerPlayerLabel);

		// Add the playerModeSelectionPanel and the playerTypeSelectionPanel to
		// the playerTypePanel. The playerTypeSelectionPanel will initially be
		// hidden
		playerTypePanel.add(playerModeSelectionPanel);
		playerTypePanel.add(playerTypeSelectionPanel);
		playerTypeSelectionPanel.setVisible(false);

		// Create the playerNamesPanel that will house the player name text
		// boxes on the right side of the JDialog box
		JPanel playerNamesPanel = new JPanel();
		playerNamesPanel.setLayout(new GridLayout(6, 1, 0, 5));
		playerNamesPanel.setBackground(greenBackground);

		// Set the font and color for the playerNames label and add it to the
		// playerNamesPanel
		playerNamesLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		playerNamesLabel.setForeground(Color.WHITE);
		playerNamesPanel.add(playerNamesLabel);

		// Create the playerOneNamePanel that will house the player one name
		// text box
		playerOneNamePanel.setLayout(new BorderLayout(10, 0));
		playerOneNamePanel.setBackground(greenBackground);

		// Set the font and color for the playerOneNameLabel, and add it to the
		// playerOneNamePanel
		playerOneNameLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerOneNameLabel.setForeground(Color.WHITE);
		playerOneNamePanel.add(playerOneNameLabel, BorderLayout.WEST);

		// Add the playerOneName text box to the playerOneNamePanel
		playerOneNamePanel.add(playerOneName, BorderLayout.CENTER);

		// Add the playerOneName panel to the playerNamesPanel
		playerNamesPanel.add(playerOneNamePanel);

		// Create the playerTwoNamePanel that will house the player two name
		// text box
		playerTwoNamePanel.setLayout(new BorderLayout(8, 0));
		playerTwoNamePanel.setBackground(greenBackground);

		// Set the font and color for the playerTwoNameLabel, and add it to the
		// playerTwoNamePanel
		playerTwoNameLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerTwoNameLabel.setForeground(Color.WHITE);
		playerTwoNamePanel.add(playerTwoNameLabel, BorderLayout.WEST);

		// Add the playerTwoName text box to the playerTwoNamePanel
		playerTwoNamePanel.add(playerTwoName, BorderLayout.CENTER);

		// Add the playerTwoNamePanel to the playerNamesPanel
		playerNamesPanel.add(playerTwoNamePanel);

		// The playerTwoNamePanel will initially be hidden
		playerTwoNamePanel.setVisible(false);

		// Add the playerTypePanel and playerNamesPanel to the gameModePanel
		gameModePanel.add(playerTypePanel);
		gameModePanel.add(playerNamesPanel);

		// Create the buttonPanel that will house the "start" and "close"
		// buttons.
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);

		// Create the "Start" button, and add the actionListener
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Set the provided player names
				setPlayer1Name(playerOneName.getText());
				setPlayer2Name(playerTwoName.getText());

				// Hide this panel
				FarkleOptionsDialog.this.setVisible(false);
			}
		});

		// Add the "start" button to the buttonPanel
		buttonPanel.add(startButton);

		/****************************************************
		 * 1.1.1 If the user selects the "Close" button at any time, the
		 * application closes.
		 ****************************************************/
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Dispose this window
				FarkleOptionsDialog.this.dispose();

				// Close the application
				System.exit(0);
			}
		});

		// Add the "close" button to the button panel
		buttonPanel.add(closeButton);

		// Create the gameModeTitlePanel that will display the title of this
		// JDialog box,
		// and set the background color
		JPanel gameModeTitlePanel = new JPanel();
		gameModeTitlePanel.setBackground(Color.WHITE);

		// Set the font of the gameModeOptionTitle and add it to the
		// gameModeTitlePanel
		gameModeOptionTitle.setFont(new Font("Arial Black", Font.PLAIN, 14));
		gameModeTitlePanel.add(gameModeOptionTitle);

		// Add the gameModeTitlePanel to the top of the window panel
		window.add(gameModeTitlePanel, BorderLayout.NORTH);

		// Add the gameModePanel to the center of the window panel
		window.add(gameModePanel, BorderLayout.CENTER);

		// Add the buttonPanel to the bottom of the window panel
		window.add(buttonPanel, BorderLayout.SOUTH);

		// Add the window panel to this JDialog box
		this.add(window, BorderLayout.CENTER);

		// Size the window to fit the preferred size and layouts of its
		// subcomponents
		this.pack();

		// Set the location of the window relative to the parent frame
		this.setLocationRelativeTo(frame);
	}

	/**
	 * Make the Farkle Options Dialog box visible
	 */
	public void showWindow() {
		this.setVisible(true);
	}

	/**
	 * Get the selected opponent player type
	 * 
	 * @return PlayerType - Opponent Player Type selection (PlayerType.USER or
	 *         PlayerType.COMPUTER)
	 */
	public PlayerType getPlayerType() {
		return playerType;
	}

	/**
	 * Set the opponent player type
	 * 
	 * @param playerType
	 *            PlayerType.USER or PlayerType.COMPUTER
	 */
	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	/**
	 * Get the provided player 1 name String
	 * 
	 * @return The provided player 1 name
	 */
	public String getPlayer1Name() {
		return player1Name;
	}

	/**
	 * Set the player 1 name String
	 * 
	 * @param player1Name
	 *            Set the player 1 name String
	 */
	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	/**
	 * Get the provided player 2 name String
	 * 
	 * @return The provided player 2 name
	 */
	public String getPlayer2Name() {
		return player2Name;
	}

	/**
	 * Set the player 2 name String
	 * 
	 * @param player2Name
	 *            Set the player 1 name String
	 */
	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	/**
	 * Get the selected Game Mode
	 * 
	 * @return The selected GameMode (GameMode.SINGLEPLAYER or
	 *         GameMode.MULTIPLAYER)
	 */
	public GameMode getGameMode() {
		return gameMode;
	}

	/**
	 * Set the Game Mode
	 * 
	 * @param gameMode
	 *            GameMode.SINGLEPLAYER or GameMode.MULTIPLAYER
	 */
	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	/**
	 * Change the settings based on the user's selection
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

		// The single player game mode has been selected
		if (e.getComponent().getName().equals("1")) {

			// Highlight the singlePLayerLabel
			singlePlayerLabel.setBackground(Color.YELLOW);
			singlePlayerLabel.setForeground(Color.BLACK);

			// Deselect the multiPlayerLabel
			multiplayerLabel.setBackground(greenBackground);
			multiplayerLabel.setForeground(Color.WHITE);

			// Hide the playerTypeSelectionPanel
			playerTypeSelectionPanel.setVisible(false);

			// Deselect all player types
			humanPlayerLabel.setBackground(greenBackground);
			computerPlayerLabel.setBackground(greenBackground);

			// Hide the playerTwoNamePanel
			playerTwoNamePanel.setVisible(false);
			playerTwoName.setText("");

			// Set the game mode to GameMode.SINGLEPLAYER
			setGameMode(GameMode.SINGLEPLAYER);
		}

		/****************************************************
		 * 1.1.3 - If the user highlights the "2 Player Mode" option, the
		 * "1 Player Mode" option is deselected, and two more options appear
		 * ("Human Opponent" and "Computer Opponent"). The "Human Opponent"
		 * option is highlighted by default.
		 ****************************************************/
		// Else if the two player game mode has been selected
		else if (e.getComponent().getName().equals("2")) {

			// Deselect the singlePlayerLabel
			singlePlayerLabel.setBackground(greenBackground);
			singlePlayerLabel.setForeground(Color.WHITE);

			// Highlight the multiPlayerLabel
			multiplayerLabel.setBackground(Color.YELLOW);
			multiplayerLabel.setForeground(Color.BLACK);

			// Highlight the humanPlayerLabel
			humanPlayerLabel.setForeground(Color.BLACK);
			humanPlayerLabel.setBackground(Color.YELLOW);

			// Deselect the computerPlayerLabel
			computerPlayerLabel.setForeground(Color.WHITE);
			computerPlayerLabel.setBackground(greenBackground);

			// Make the playerTypeSelectionPanel visible
			playerTypeSelectionPanel.setVisible(true);

			// Make the playerTwoNamePanel visible, enabled, and clear the name
			playerTwoNamePanel.setVisible(true);
			playerTwoNameLabel.setEnabled(true);
			playerTwoNamePanel.setEnabled(true);
			playerTwoName.setEnabled(true);
			playerTwoName.setText("");

			// Set the game mode to GameMode.MULTIPLAYER
			setGameMode(GameMode.MULTIPLAYER);
		}

		/****************************************************
		 * 1.1.4 - When the "Human Opponent" option is highlighted, two text
		 * fields are displayed, labeled "Player One Name", and
		 * "Player Two Name".
		 ****************************************************/
		// Else if the human opponent player type has been selected
		else if (e.getComponent().getName().equals("3")) {

			// Highlight the humanPlayerLabel
			humanPlayerLabel.setForeground(Color.BLACK);
			humanPlayerLabel.setBackground(Color.YELLOW);

			// Deselect the computerPlayerLabel
			computerPlayerLabel.setForeground(Color.WHITE);
			computerPlayerLabel.setBackground(greenBackground);

			// Clear the playerTwoName text field and enable it
			playerTwoName.setText("");
			playerTwoNameLabel.setEnabled(true);
			playerTwoNamePanel.setEnabled(true);
			playerTwoName.setEnabled(true);

			// Set the opponent player type to PlayerType.USER
			setPlayerType(PlayerType.USER);
		}

		/****************************************************
		 * 1.1.5 - When the "Computer Opponent" option is highlighted, the text
		 * fields for "Player One Name" and "Player Two Name" are displayed, but
		 * the "Player Two Name" field is disabled, and "Computer" is supplied
		 * for the "Player Two Name".
		 ****************************************************/
		// Else if the Computer opponent player type has been selected
		else if (e.getComponent().getName().equals("4")) {

			// Deselect the humanPlayerLabel
			humanPlayerLabel.setForeground(Color.WHITE);
			humanPlayerLabel.setBackground(greenBackground);

			// Highlight the computerPlayerLabel
			computerPlayerLabel.setForeground(Color.BLACK);
			computerPlayerLabel.setBackground(Color.YELLOW);

			// Set the player two name text field to "Computer" and disable it
			playerTwoName.setText("Computer");
			playerTwoNameLabel.setEnabled(false);
			playerTwoNamePanel.setEnabled(false);
			playerTwoName.setEnabled(false);

			// Set the opponent player type to PlayerType.COMPUTER
			setPlayerType(PlayerType.COMPUTER);
		}

	}

	/**
	 * Not used
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Not used
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Not used
	 */
	@Override
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Not used
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * This method is used to get JLabels for testing
	 * 
	 * @param labelNumber
	 *            Specifies the JLabel to return
	 * @return Component representing an option the user selected
	 */
	public JLabel getJLabel(int labelNumber) {

		// Return the selected JLabel
		switch (labelNumber) {
		case 1:
			return singlePlayerLabel;
		case 2:
			return multiplayerLabel;
		case 3:
			return humanPlayerLabel;
		case 4:
			return computerPlayerLabel;
		case 5:
			return playerTwoNameLabel;
		default:
			return null;
		}

	}

	/**
	 * This method is used to get JPanels for testing
	 * 
	 * @param panelNumber
	 *            Specifies the JPanel to return
	 * @return JPanel
	 */
	public JPanel getJPanel(int panelNumber) {

		// Return the selected JPanel
		switch (panelNumber) {
		case 1:
			return playerTypeSelectionPanel;
		case 2:
			return playerTwoNamePanel;
		default:
			return null;
		}

	}

	/**
	 * This method is used to get JTextFields for testing
	 * 
	 * @param textFieldNumber
	 *            Specifies the TextField to return
	 * @return JTextField
	 */
	public JTextField getJTextField(int textFieldNumber) {

		// Return the selected JTextField
		switch (textFieldNumber) {
		case 1:
			return playerTwoName;
		default:
			return null;
		}

	}
}
