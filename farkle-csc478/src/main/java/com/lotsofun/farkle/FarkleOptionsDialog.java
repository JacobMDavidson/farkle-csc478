package com.lotsofun.farkle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/****************************************************************************
 * 1.0.0
 * 
 * Select Game Mode Option Box - Upon opening the application, the user is
 * greeted with an option box that includes all configuration options for
 * gameplay. These options include "1 Player Mode", "2 Player Mode", "Human
 * Opponent" (if two player mode is selected), "Computer Opponent" (if two
 * player mode is selected), and text fields to enter the associated player
 * names. Also included is a "Start" button and a "Close" button (both of which
 * are always enabled). This option dialog box should pop up over the main GUI
 * (section 1.2.0).
 *
 *****************************************************************************/
public class FarkleOptionsDialog extends JDialog implements MouseListener {
	private static final long serialVersionUID = 1L;
	private Color greenBackground = new Color(35, 119, 34);
	private JLabel playerModeSelectLabel = new JLabel(" Select Player Mode:");
	private JLabel singlePlayerLabel = new JLabel("One Player Mode",
			SwingConstants.CENTER);
	private JLabel multiplayerLabel = new JLabel("Two Player Mode",
			SwingConstants.CENTER);
	private JLabel playerTypeSelectLabel = new JLabel(" Select Opponent Type:");
	private JLabel humanPlayerLabel = new JLabel("Human Opponent",
			SwingConstants.CENTER);
	private JLabel computerPlayerLabel = new JLabel("Computer Opponent",
			SwingConstants.CENTER);
	private JLabel gameModeOptionTitle = new JLabel("Game Mode Options",
			SwingConstants.CENTER);
	private JPanel playerOneNamePanel = new JPanel();
	private JPanel playerTwoNamePanel = new JPanel();
	private JPanel playerModeSelectionPanel = new JPanel();
	private JPanel playerTypeSelectionPanel = new JPanel();
	private JLabel playerNamesLabel = new JLabel("Enter Player Names:");
	private JLabel playerOneNameLabel = new JLabel("Player One:");
	private JTextField playerOneName = new JTextField(5);
	private JLabel playerTwoNameLabel = new JLabel("Player Two:");
	private JTextField playerTwoName = new JTextField(5);
	private JButton startButton = new JButton("Start");
	private JButton closeButton = new JButton("Close");
	private GameMode gameMode = null;
	private PlayerType playerType = null;
	private String player1Name = "";
	private String player2Name = "";

	/* Reduce Reuse Recycle */
	public FarkleOptionsDialog(JFrame frame) {
		// Pass the JFrame to the super constructor
		super(frame);
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setPreferredSize(new Dimension(750, 250));
		this.setBackground(greenBackground);

		JPanel window = new JPanel(new BorderLayout());
		window.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.YELLOW, 3),
				BorderFactory.createLineBorder(greenBackground, 5)));

		// Create panels here
		JPanel gameModePanel = new JPanel();
		gameModePanel.setLayout(new GridLayout(1, 2, 10, 0));
		gameModePanel
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/********************************************************
		 * 1.1.2 - The �1 Player Mode� is highlighted by default when the
		 * application is first opened, and a blank text field for player one�s
		 * name is displayed.
		 ********************************************************/
		singlePlayerLabel.setName("1");
		singlePlayerLabel.setOpaque(true);
		singlePlayerLabel.setBackground(Color.YELLOW);
		singlePlayerLabel.addMouseListener(this);
		multiplayerLabel.setName("2");
		multiplayerLabel.setOpaque(true);
		multiplayerLabel.addMouseListener(this);
		multiplayerLabel.setBackground(greenBackground);

		JPanel playerTypePanel = new JPanel();
		playerTypePanel.setLayout(new GridLayout(2, 1, 0, 5));
		playerModeSelectionPanel.setLayout(new GridLayout(3, 1, 0, 5));
		playerModeSelectionPanel.setBackground(greenBackground);
		playerTypeSelectionPanel.setLayout(new GridLayout(3, 1, 0, 5));
		playerTypeSelectionPanel.setBackground(greenBackground);
		humanPlayerLabel.setName("3");
		humanPlayerLabel.setOpaque(true);

		humanPlayerLabel.addMouseListener(this);
		humanPlayerLabel.setBackground(greenBackground);
		computerPlayerLabel.setName("4");
		computerPlayerLabel.setOpaque(true);

		computerPlayerLabel.addMouseListener(this);
		computerPlayerLabel.setBackground(greenBackground);

		playerModeSelectLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		playerModeSelectLabel.setForeground(Color.WHITE);
		playerModeSelectionPanel.add(playerModeSelectLabel);

		singlePlayerLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerModeSelectionPanel.add(singlePlayerLabel);

		multiplayerLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		multiplayerLabel.setForeground(Color.WHITE);
		playerModeSelectionPanel.add(multiplayerLabel);

		playerTypeSelectLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		playerTypeSelectLabel.setForeground(Color.WHITE);
		playerTypeSelectionPanel.add(playerTypeSelectLabel);

		humanPlayerLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerTypeSelectionPanel.add(humanPlayerLabel);

		computerPlayerLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		computerPlayerLabel.setForeground(Color.WHITE);
		playerTypeSelectionPanel.add(computerPlayerLabel);
		playerTypePanel.add(playerModeSelectionPanel);
		playerTypePanel.add(playerTypeSelectionPanel);
		playerTypeSelectionPanel.setVisible(false);

		JPanel playerNamesPanel = new JPanel();
		playerNamesPanel.setLayout(new GridLayout(6, 1, 0, 5));

		playerNamesLabel.setFont(new Font("Arial Black", Font.PLAIN, 14));
		playerNamesLabel.setForeground(Color.WHITE);
		playerNamesPanel.add(playerNamesLabel);

		// set up the playerOneNamePanel
		playerOneNamePanel.setLayout(new BorderLayout(10, 0));
		playerOneNamePanel.setBackground(greenBackground);

		playerOneNameLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerOneNameLabel.setForeground(Color.WHITE);
		playerOneNamePanel.add(playerOneNameLabel, BorderLayout.WEST);
		playerOneNamePanel.add(playerOneName, BorderLayout.CENTER);
		playerNamesPanel.add(playerOneNamePanel);

		// set up the playerTwoNamePanel
		playerTwoNamePanel.setLayout(new BorderLayout(8, 0));
		playerTwoNamePanel.setBackground(greenBackground);

		playerTwoNameLabel.setFont(new Font("Arial Black", Font.PLAIN, 12));
		playerTwoNameLabel.setForeground(Color.WHITE);
		playerTwoNamePanel.add(playerTwoNameLabel, BorderLayout.WEST);
		playerTwoNamePanel.add(playerTwoName, BorderLayout.CENTER);
		playerNamesPanel.add(playerTwoNamePanel);

		// Hide the playerTwoNamePanel
		playerTwoNamePanel.setVisible(false);

		// Add the player name panels to the
		gameModePanel.add(playerTypePanel);
		gameModePanel.add(playerNamesPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);

		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Set the game options
				setPlayer1Name(playerOneName.getText());
				setPlayer2Name(playerTwoName.getText());
				setGameMode((null == getGameMode()) ? GameMode.SINGLEPLAYER
						: getGameMode());

				try {
					if (("Ginuwine").equalsIgnoreCase(getPlayer1Name())) {
						URL gSound = getClass()
								.getResource("/sounds/roll5.wav");

						AudioInputStream audioStream;
						audioStream = AudioSystem.getAudioInputStream(gSound);
						Clip clip = AudioSystem.getClip();
						clip.open(audioStream);
						clip.start();
					}
				} catch (UnsupportedAudioFileException x) {
					x.printStackTrace();
				} catch (LineUnavailableException y) {
					y.printStackTrace();
				} catch (IOException z) {
					z.printStackTrace();
				}
				FarkleOptionsDialog.this.setVisible(false);
			}
		});
		buttonPanel.add(startButton);

		/**********************************************
		 * 1.1.1 If the user selects the �Close� button at any time, the
		 * application closes.
		 **********************************************/
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FarkleOptionsDialog.this.dispose();
				System.exit(0);
			}
		});
		buttonPanel.add(closeButton);

		// Add a title panel
		JPanel gameModeTitlePanel = new JPanel();
		gameModeTitlePanel.setBackground(Color.WHITE);
		gameModeOptionTitle.setFont(new Font("Arial Black", Font.PLAIN, 14));
		gameModeTitlePanel.add(gameModeOptionTitle);

		gameModePanel.setBackground(greenBackground);
		playerTypePanel.setBackground(greenBackground);
		playerNamesPanel.setBackground(greenBackground);

		window.add(gameModeTitlePanel, BorderLayout.NORTH);
		window.add(gameModePanel, BorderLayout.CENTER);
		window.add(buttonPanel, BorderLayout.SOUTH);
		this.add(window, BorderLayout.CENTER);
		
		
		this.pack();
		this.setLocationRelativeTo(frame);
		
	}
	
	public void showWindow() {
		this.setVisible(true);	
	}

	public PlayerType getPlayerType() {
		return playerType;
	}
	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	public String getPlayer1Name() {
		return player1Name;
	}
	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}
	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public GameMode getGameMode() {
		return gameMode;
	}
	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// SinglePlayer
		if (e.getComponent().getName().equals("1")) {
			singlePlayerLabel.setBackground(Color.YELLOW);
			singlePlayerLabel.setForeground(Color.BLACK);
			multiplayerLabel.setBackground(greenBackground);
			multiplayerLabel.setForeground(Color.WHITE);
			playerTypeSelectionPanel.setVisible(false);
			humanPlayerLabel.setBackground(greenBackground);
			computerPlayerLabel.setBackground(greenBackground);
			playerTwoNamePanel.setVisible(false);
			playerTwoName.setText("");
			gameMode = GameMode.SINGLEPLAYER;
		}
		// Multiplayer
		/**************************************************************
		 * 1.1.3 - If the user highlights the �2 Player Mode� option, the �1
		 * Player Mode� option is deselected, and two more options appear
		 * (�Human Opponent� and �Computer Opponent�). The �Human Opponent�
		 * option is highlighted by default.
		 **************************************************************/
		else if (e.getComponent().getName().equals("2")) {
			singlePlayerLabel.setBackground(greenBackground);
			singlePlayerLabel.setForeground(Color.WHITE);
			multiplayerLabel.setBackground(Color.YELLOW);
			multiplayerLabel.setForeground(Color.BLACK);
			humanPlayerLabel.setForeground(Color.BLACK);
			humanPlayerLabel.setBackground(Color.YELLOW);
			computerPlayerLabel.setForeground(Color.WHITE);
			computerPlayerLabel.setBackground(greenBackground);
			playerTypeSelectionPanel.setVisible(true);
			playerTwoNamePanel.setVisible(true);
			playerTwoNamePanel.setVisible(true);
			playerTwoName.setText("");
			playerTwoName.setEnabled(true);
			setGameMode(GameMode.MULTIPLAYER);
			setPlayerType(PlayerType.USER);
		}
		// Human
		/****************************************************************
		 * 1.1.4 - When the �Human Opponent� option is highlighted, two text
		 * fields are displayed, labeled �Player One Name�, and �Player Two
		 * Name�.
		 ****************************************************************/
		else if (e.getComponent().getName().equals("3")) {
			singlePlayerLabel.setBackground(greenBackground);
			singlePlayerLabel.setForeground(Color.WHITE);
			multiplayerLabel.setBackground(Color.YELLOW);
			multiplayerLabel.setForeground(Color.BLACK);
			humanPlayerLabel.setForeground(Color.BLACK);
			humanPlayerLabel.setBackground(Color.YELLOW);
			computerPlayerLabel.setForeground(Color.WHITE);
			computerPlayerLabel.setBackground(greenBackground);
			playerTypeSelectionPanel.setVisible(true);

			playerTwoNameLabel.setEnabled(true);
			playerTwoNamePanel.setVisible(true);
			playerTwoName.setText("");
			playerTwoName.setEnabled(true);
			setGameMode(GameMode.MULTIPLAYER);
			setPlayerType(PlayerType.USER);
		}
		// Computer
		/****************************************************************
		 * 1.1.5 - When the �Computer Opponent� option is highlighted, only one
		 * text field is displayed, labeled �Player One Name�.
		 ****************************************************************/
		else if (e.getComponent().getName().equals("4")) {
			singlePlayerLabel.setBackground(greenBackground);
			singlePlayerLabel.setForeground(Color.WHITE);
			multiplayerLabel.setBackground(Color.YELLOW);
			multiplayerLabel.setForeground(Color.BLACK);
			humanPlayerLabel.setForeground(Color.WHITE);
			humanPlayerLabel.setBackground(greenBackground);
			computerPlayerLabel.setForeground(Color.BLACK);
			computerPlayerLabel.setBackground(Color.YELLOW);
			playerTypeSelectionPanel.setVisible(true);

			playerTwoNameLabel.setEnabled(false);
			playerTwoNamePanel.setEnabled(false);
			playerTwoName.setText("Computer");
			playerTwoName.setEnabled(false);
			setGameMode(GameMode.MULTIPLAYER);
			setPlayerType(PlayerType.COMPUTER);
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
