package com.lotsofun.farkle;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

/**
 * Controls the communication between the FarkleUI (the view) and Game (the model) classes. 
 * Also serves as the entry point for the application.
 * @author Curtis Brown
 * @version 3.0.0
 */
public class FarkleController implements ActionListener, MouseListener {
	
	/** The view of the application */
	FarkleUI farkleUI;
	
	/** The model of the application */
	Game farkleGame;
	
	/** The point threshold for two player mode */
	final int POINT_THRESHOLD = 10000;
	
	/** Flag to check if the threshold has been reached making this the last turn */
	boolean isLastTurn = false;
	
	/** The farkle options dialog box */
	FarkleOptionsDialog farkleOptions = null;
	
	/** Used for testing the controller */
	boolean isTest = false;
	
	/** Random number generator for computer decisions */
	Random rand = new Random();

	/**
	 * The entry point for the application
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Instantiate the Farkle Controller
		final FarkleController controller = new FarkleController(false);
		
		// Pass the controller to a new FarkleUI
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FarkleUI(controller);
			}
		});
	}
	
	/**
	 * Constructor: Instantiate the Farkle Controller
	 * @param isTest Set to true if this is used for testing
	 */
	public FarkleController(boolean isTest) {
		this.isTest = isTest;
	}

	/**
	 * Set a reference to the UI object
	 * @param farkle the FarkleUI object to set
	 */
	public void setUI(FarkleUI farkle) {
		this.farkleUI = farkle;
	}

	/**
	 * Handle farkle by sending calls to both the Model and View
	 */
	public void farkle() {

		/******************************************************************
		 * 1.2.7: If any roll results in 0 points, the word �Farkle!!!� is
		 * prominently displayeddisplayed, and 0 points is displayed in the
		 * accumulated turn score above the dice. The dice retain their current
		 * values that resulted in the Farkle. After the Farkle message is
		 * displayed, the dice still retain the values that resulted in the
		 * Farkle, but all dice are unlocked and play passes to the next player
		 * (in two player mode), or to the next turn (in one player mode). until
		 * the next player rolls (in 2 player mode) or the first roll of the
		 * next turn is taken (in 1 player mode).
		 ********************************************************************/

		/*********************************************************
		 * 4.9.0: If the player farkles on any roll during the current turn,
		 * that player loses all points accumulated during the current turn, the
		 * farkle message is displayed per requirement 1.2.7, and the turn is
		 * over.
		 *********************************************************/

		/* farkleUI: Disable the dice, set the running score and roll score to 0,
		 * disable the "Select All" button, disable the "Roll Dice" Button, show the
		 * FarkleMessage, and unhighlight all turn scores for the current player.
		 */
		farkleUI.disableDice();
		farkleUI.setRunningScore(0);
		farkleUI.setRollScore(0);
		farkleUI.getSelectAllBtn().setEnabled(false);
		farkleUI.getRollBtn().setEnabled(false);
		if(isTest == false) {
			farkleUI.getFarkleMessage().setLocationRelativeTo(farkleUI);
			farkleUI.getFarkleMessage().setVisible(true);
		}
		farkleUI.unHighlightAllTurnScores(farkleGame
				.getPlayerNumberForCurrentPlayer());
		
		/* farkleGame: set the turn score for the current player and call the
		 * farkleGame's farkle() method.
		 */
		setTurnScore(farkleGame.getPlayerNumberForCurrentPlayer(),
				farkleGame.getTurnNumberForCurrentPlayer(), 0);
		farkleGame.farkle();

		// Determine if the game has ended
		tryToEndGame();

		/****************************
		 * 1.3.4 - Turn Highlighting
		 ****************************/
		
		// Highlight the current turn for the new current player after the farkle
		farkleUI.highlightTurnScore(
				farkleGame.getPlayerNumberForCurrentPlayer(),
				farkleGame.getTurnNumberForCurrentPlayer(), false);

		/* If the new current player is a computer player, set the running score 
		 * to 0, and have the computer take its turn
		 */
		if (farkleGame.getPlayerTypeForCurrentPlayer() == PlayerType.COMPUTER) {
			farkleUI.setRunningScore(0);
			asynchronousRoll();
			
		// Else, enable the roll button for the new current player
		} else {
			farkleUI.getRollBtn().setEnabled(true);
		}
	}

	/**
	 * Handle bank by sending calls to both the Model and the View
	 */
	public void bank() {
		
		// Retrieve the current player number from the farkleGame
		int player = farkleGame.getPlayerNumberForCurrentPlayer();

		/***************************************************
		 * 4.8.0: If the player selects the bank button, the current turn point
		 * total is added to the player's game point total, and the turn is
		 * over.
		 ***************************************************/

		/* farkleUI: Disable the dice, set the running score and roll score to 0,
		 * disable the "Select All" button, disable the "Bank" Button, set the 
		 * updated game score, unhighlight all turn scores for the current player, 
		 * and check the game score against the high score.
		 */
		farkleUI.setRunningScore(0);
		farkleUI.setRollScore(0);
		farkleUI.disableDice();
		farkleUI.unHighlightAllTurnScores(player);
		farkleUI.setGameScore(player, farkleGame.bank());
		farkleUI.getBankBtn().setEnabled(false);
		farkleUI.getSelectAllBtn().setEnabled(false);
		
		// If this is a single player game, check the high score
		if(farkleGame.getGameMode() == GameMode.SINGLEPLAYER)
			checkHighScore(player);
		
		// Determine if the game has ended
		tryToEndGame();

		/****************************
		 * 1.3.4 - Turn Highlighting
		 ****************************/
		
		// Highlight the current turn for the new current player after the bank
		farkleUI.highlightTurnScore(
				farkleGame.getPlayerNumberForCurrentPlayer(),
				farkleGame.getTurnNumberForCurrentPlayer(), false);
		
		// If this isn't the last turn, set the turn score for the new current player to 0
		if(!isLastTurn) {
			setTurnScore(farkleGame.getPlayerNumberForCurrentPlayer(), farkleGame.getTurnNumberForCurrentPlayer(), 0);
		}
		
		/* If the new current player is a computer player, set the running score 
		 * to 0, disable the "Roll Dice" button, and have the computer take its turn
		 */		
		if (farkleGame.getPlayerTypeForCurrentPlayer() == PlayerType.COMPUTER) {
			farkleUI.setRunningScore(0);
			farkleUI.getRollBtn().setEnabled(false);
			asynchronousRoll();
		}
		
		// Enable the "Roll Dice" button for the new current player
		farkleUI.getRollBtn().setEnabled(true);
	}

	/**
	 * Checks given player's game score to see if it is greater than or equal to the
	 * saved high score. If it is, it saves the new high score and updates the 
	 * FarkleUI with the new high score.
	 * @param playerNumber int representing the player to check
	 * @return true if player's score is the new high score
	 */
	public boolean checkHighScore(int playerNumber) {
		
		// Check the player's game score against the saved high score
		if (farkleGame.getGameScoreForPlayer(playerNumber) >= farkleGame
				.getHighScore()) {
			
			// Save the new high score
			farkleGame.setHighScore(farkleGame.getGameScoreForPlayer(playerNumber));
			
			// Set the farkle UI to display the new high score
			farkleUI.setHighScore(farkleGame.getHighScore());
			
			return true;
		}
		
		// This score is not greater than or equal to the saved high score
		return false;
	}

	/**
	 * Used to set the high score in the UI on init
	 * 
	 * @param highScore integer representing the high score
	 */
	public void setUIHighScore(int highScore) {
		
		// Set the high score displayed in the Farkle UI
		if(null != farkleUI) {
			farkleUI.setHighScore(highScore);
		}
	}

	/**
	 * Resets the high score stored in the preferences to the default value (0)
	 */
	public void resetHighScore() {
		
		// Reset the high score in the model
		farkleGame.resetHighScore();
		
		// Set the high score in the FarkleUI to the reset score
		farkleUI.setHighScore(farkleGame.getHighScore());
	}

	/******************************************************
	 * 2.1.2 - The game ends at the conclusion of the tenth turn, and the
	 * player�s score is compared to the current high score.
	 ******************************************************/
	/**
	 * Determine if this is the last turn in single player mode or two player
	 * mode, or if the game has ended for single player mode or two player mode
	 */
	public void tryToEndGame() {
		
		// Check if the game has ended in single player mode
		if (farkleGame.getGameMode() == GameMode.SINGLEPLAYER) {
			
			// If the current turn is 10, this is the last turn
			if(farkleGame.getTurnNumberForCurrentPlayer() == 10) {
				isLastTurn = true;
			}
			
			// If the current turn is greater than 10, end the game
			if(farkleGame.getTurnNumberForCurrentPlayer() > 10) {
				/*****************************************************
				 * 2.1.3 - If the player�s score is greater than the current high
				 * score, a congratulatory message is displayed, and the player�s
				 * score replaces the previous high score.
				 *****************************************************/
				
				// If the player acheived a high score, display a message
				if (checkHighScore(1)) {
					farkleUI.displayMessage(
							"Congrats! You achieved a new high score.",
							"High Score");
				}
				
				// End the game.
				endGame(false, false, false);
			}
			
		// Check if the game has ended in two player mode
		} else if (farkleGame.getGameMode() == GameMode.MULTIPLAYER) {
			
			// Determine if either player has surpassed the threshold
			int player1Score = farkleGame.getGameScoreForPlayer(1);
			int player2Score = farkleGame.getGameScoreForPlayer(2);
			if (player1Score >= POINT_THRESHOLD
					|| player2Score >= POINT_THRESHOLD) {

				/***************************************************
				 * 2.2.2 - The first player to surpass 10,000 total points
				 * at the end of a given turn is highlighted in a different
				 * color.
				 ***************************************************/

				/***************************************************
				 * 2.2.11 - The first player to surpass 10,000 total points at
				 * the end of a given turn is highlighted in a different color.
				 ***************************************************/

				/***************************************************
				 * 2.2.12 - The other player has one more turn to try and
				 * surpass the point total of the first player to surpass 10,000
				 * points.
				 ***************************************************/
				
				/* If it is not the last turn, display a message that the other player
				 * has one more turn. an set the isLastTurn flag
				 */ 
				if (!isLastTurn) {
					int player = (player1Score >= POINT_THRESHOLD) ? 1 : 2;
					if(isTest == false) {
						farkleUI.displayMessage(
								farkleGame.getPlayerName(player)
										+ " has scored "
										+ ""
										+ farkleGame.getGameScoreForPlayer(player)
										+ " points\n"
										+ "This is your last turn to try to beat them.\n"
										+ "Good Luck!", "Last Turn");
					}
					isLastTurn = true;
				
				// Else, reset the last turn flag and end the game
				} else {
					isLastTurn = false;
					endGame(false, false, false);
				}
			}
		}
	}

	/**
	 * Unhighlights all player's turns, and resets the current game or starts a new game 
	 * based on the passed parameters. If resetOnly and mainMenu are both false, displays
	 * the end of game dialog and gets the user selection to replay the game or go to the
	 * main menu. Otherwise, the game is replayed.
	 * @param resetOnly set to true if the game is to be reset
	 * @param mainMenu set to true if the main menu is to be displayed
	 * @param testReplayGame set to true if this is a test of this method
	 */
	public void endGame(boolean resetOnly, boolean mainMenu, boolean testReplayGame) {

		// Flag to determine if the current game is to be replayed
		boolean replayGame = (isTest) ? testReplayGame : gameEnded(resetOnly, mainMenu);
		
		// Unhighlight all player turns
		farkleUI.unHighlightAllTurnScores(1);
		if(farkleGame.getGameMode() == GameMode.MULTIPLAYER) {
			farkleUI.unHighlightAllTurnScores(2);
		}
		
		// If replayGame is true, reset the current game
		if (replayGame) {
			replayGame();
			farkleUI.diceFirstRun();
			
		// Else show the main menu
		} else {
			replayGame();
			farkleUI.initUI();
		}
	}

	/**
	 * Displays the farkle options dialog, and creates a new game based on the
	 * chosen settings.
	 * @param options used for testing, set to null if not testing
	 */
	public void newGame(FarkleOptionsDialog options) {
		
		// If option is null, display the farkle options dialog
		if(null == options) {
			// Get the game options
			farkleOptions = new FarkleOptionsDialog(farkleUI);
			farkleOptions.showWindow();
			
		// Else this is used for testing, set farkleOptions to the passed options
		} else {
			farkleOptions = options;
		}

		/****************************************************************
		 * 2.2.0 - When 2 two player mode against a live person is selected, the
		 * 2 two player mode graphic user interface (section 1.4.0) is displayed
		 * with the name �Farkle� displayed on the dice is displayed with blank
		 * dice, the �Bank Score� button disabled, the �Select All� button
		 * disabled, the bank button disabled, and player one highlighted
		 * indicating it is his or her turn.
		 ****************************************************************/

		/****************************************************************
		 * 2.2.8 - .When 2 two player mode against the computer is selected, the
		 * 2 two player mode graphic user interface (section 1.4.0) is displayed
		 * with the name �Farkle�is displayed with blankon the dice, the �Bank
		 * Score� button disabled, the �Select All� button disabled, the bank
		 * button disabled, and player one highlighted indicating it is his
		 * turn.
		 ****************************************************************/
		
		// Build the dice panel
		farkleUI.buildDicePanel();
		
		// Build the scoring guide panel
		farkleUI.createScoreGuidePanel();
		
		// Build the player panel for the selected game mode
		farkleUI.buildPlayerPanel(farkleOptions.getGameMode());

		// Create the game object no matter what the mode is
		if (null != farkleOptions.getGameMode()) {
			farkleGame = new Game(farkleOptions.getGameMode(), this);
		}

		/************************************************************************
		 * 1.1.2a - If the user selects the �Start� button with �1 Player
		 * Mode� highlighted and the �Player One Name� field empty, the
		 * one player GUI (section 1.3.0) opens with a random name assigned to
		 * player one.
		 ************************************************************************/
		// Set the first player's name to "Jacob" if it was not provided
		if (farkleOptions.getPlayer1Name().length() == 0) {
			farkleUI.setPlayerName(1, "Jacob");
			farkleGame.setPlayerName(1, "Jacob");
		
		// Else, set the first player's name to the provided name
		} else {

			/*************************************************************************
			 * 1.1.2b - If the user selects the �Start� button with �1
			 * Player Mode� highlighted and a name supplied in the �Player
			 * One Name� text field, the one player GUI (section 1.3.0) opens
			 * with the provided name assigned to player one.
			 *************************************************************************/
			farkleUI.setPlayerName(1, farkleOptions.getPlayer1Name());
			farkleGame.setPlayerName(1, farkleOptions.getPlayer1Name());
		}

		// If it is a multiplayer game.
		if (farkleOptions.getGameMode() == GameMode.MULTIPLAYER) {
			/************************************************
			 * 1.4.1 - The title of the window shall display, "Farkle - Two
			 * Player Mode".
			 ************************************************/
			farkleUI.setTitle("Farkle - Two Player Mode");

			// If the opponent is a human player
			if (farkleOptions.getPlayerType() == PlayerType.USER) {
				
				// Set the player's name to "Brant" if it was not provided
				if (farkleOptions.getPlayer2Name().length() == 0) {
					farkleUI.setPlayerName(2, "Brant");
					farkleGame.setPlayerName(2, "Brant");
					
				// Else set the player's name to the provided name
				} else {
					farkleUI.setPlayerName(2, farkleOptions.getPlayer2Name());
					farkleGame.setPlayerName(2, farkleOptions.getPlayer2Name());
				}
			}
			
			/* Else, the opponent is a computer player. Set the name and player type
			 * to Computer
			 */
			else {
				farkleUI.setPlayerName(2, "Computer");
				farkleGame.setPlayerName(2, "Computer");
				farkleGame.setPlayerType(2, PlayerType.COMPUTER);
			}

		// Else, it is a single player game.
		} else {
			/******************************************
			 * 1.3.1 - The title of the window shall display: "Farkle -
			 * Single Player Mode".
			 ******************************************/
			farkleUI.setTitle("Farkle - Single Player Mode");
		}

		// Reset the dice to display "Farkle"
		farkleUI.diceFirstRun();


		/****************************
		 * 1.3.4 - Turn Highlighting
		 ****************************/
		farkleUI.highlightTurnScore(
				farkleGame.getPlayerNumberForCurrentPlayer(),
				farkleGame.getTurnNumberForCurrentPlayer(), false);

		// Pack the frame
		farkleUI.pack();

		// Discard the no longer needed options dialog
		farkleOptions.dispose();
	}

	/**
	 * Reset the game with the current configuration
	 */
	public void replayGame() {
		
		// Reset the farkleGame
		farkleGame.resetGame();
		
		// Set the UI turn score for player 1 to 0
		setTurnScore(1, 1, 0);
		
		// Set the UI roll score to 0
		farkleUI.setRollScore(0);
		
		// Reset the UI Dice Panel
		farkleUI.resetDicePanel();
		
		// Highlight the current turn for the current player
		farkleUI.highlightTurnScore(
				farkleGame.getPlayerNumberForCurrentPlayer(),
				farkleGame.getTurnNumberForCurrentPlayer(), false);
	}

	/**
	 * UI accessor method to simplify the process of updating a specific score
	 * label
	 * 
	 * @param player player who's turn score is to be set
	 * @param turn the turn for which to set the score
	 * @param score the score to set the turn to
	 */
	public void setTurnScore(int player, int turn, int score) {
		if(null != farkleUI) {
			farkleUI.setTurnScore(player, turn, score);
		}
	}

	/**
	 * Call the appropriate method based on the button selected
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		/**********************************************
		 * 1.4.4: The current turn shall be indicated by highlighting that turn
		 * on the left side of the screen.
		 *********************************************/

		/*********************************************
		 * 2.1.1: Each turn is taken according to the requirements of section
		 * 4.0.0.
		 *********************************************/

		/*********************************************
		 * 2.2.1 - Each turn is taken according to the requirements of section
		 * 4.0.0. The current player for each turn is highlighted during that
		 * player's turn.
		 *********************************************/

		/*********************************************
		 * 2.2.9 - Each turn is taken according to the requirements of section
		 * 4.0.0. The current player for each turn is highlighted during that
		 * players turn.
		 *********************************************/

		/*********************************************
		 * 4.1.0: At the beginning of the turn the turn point total is set to 0,
		 * the player selects the roll button, and all 6 dice are rolled in
		 * accordance with the requirement 3.2.0.
		 *********************************************/

		/**********************************************
		 * 4.10.0:When the turn is over all dice are unlocked while continuing
		 * to display their last value , the "Roll Dice" button is enabled,
		 * the "Bank Score" button is disabled, the "Select All" button
		 * is disabled, the current turn point total is set to 0, the current
		 * roll point total is set to 0, and play passes to the next player (two
		 * player mode) or the next turn (single player mode) by highlighting
		 * the appropriate player or turn on the left hand side of the screen.
		 *********************************************/

		// If Roll button clicked call the rollHandler()
		if (arg0.getSource() == farkleUI.getRollBtn()) {
			rollHandler();
		}
		// If Bank button clicked call the bankHandler()
		else if (arg0.getSource() == farkleUI.getBankBtn()) {
			bankHandler();
		}
		// If Select All button clicked, call the fakleUI selectAllDice()
		else if (arg0.getSource() == farkleUI.getSelectAllBtn()) {
			farkleUI.selectAllDice();
		}
	}

	/**
	 * Not used
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// Auto-generated method stub

	}

	/**
	 * Not used
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Auto-generated method stub

	}

	/**
	 * Not used
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// Auto-generated method stub

	}

	/**
	 * Determine the action to take when the player selects a die
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		
		// Flag to determine if the user can select a die
		boolean isClickIgnored;
		
		/* If the current player is PlayerType.COMPUTER, ignore all user clicks so
		 * the player cannot select dice during the computer's turn
		 */
		if(farkleGame.getPlayerTypeForCurrentPlayer() == PlayerType.COMPUTER &&
				(arg0 instanceof DieClickEvent) == false) {
			isClickIgnored = true;
			
		// Else, this is a human player, do not ignore the click
		} else {
			isClickIgnored = false;
		}
		
		// If the click is not ignored
		if(isClickIgnored == false) {
			
			// Determine which die was clicked
			Die d = (Die) arg0.getSource();
	
			/****************************************************
			 * 1.2.9 - During a current roll, current dice selected by the user
			 * shall be indicated with a yellow border around each selected die, and
			 * the score for the currently selected dice shall be updated above the
			 * dice.
			 *****************************************************/
			/* If the value of the die > 0, and it is not disabled,
			 * toggle its state
			 */ 
			if (d.getValue() > 0 && d.getState() != DieState.DISABLED) {
				if (d.getState() == DieState.HELD) {
					d.setState(DieState.UNHELD);
				} else if (d.getState() == DieState.UNHELD) {
					d.setState(DieState.HELD);
				}
	
				// If the die's state is not SCORED
				if (d.isScored() == false) {
	
					// Get the value of the HELD dice
					int rollScore = farkleGame.calculateScore(
							farkleUI.getDieValues(DieState.HELD), true);
	
					// Tell the model about it
					farkleGame.processHold(rollScore);
					farkleUI.setRollScore(rollScore);
	
					// Get the running score from the model
					int runningScore = farkleGame.getRollScores();
	
					// Update the UI based on the model's response
					if (runningScore > 0
							&& farkleGame.getPlayerTypeForCurrentPlayer() != PlayerType.COMPUTER) {
						farkleUI.getRollBtn().setEnabled(true);
					} else {
						farkleUI.getRollBtn().setEnabled(false);
					}
	
					/*********************************************
					 * 4.5.0: If the current turn point total is greater than or
					 * equal to 300, the bank button is enabled.
					 *********************************************/
	
					// Enable the bank button if the score is >= 300
					if (runningScore >= 300
							&& farkleGame.getPlayerTypeForCurrentPlayer() != PlayerType.COMPUTER) {
						farkleUI.getBankBtn().setEnabled(true);
					} else {
						farkleUI.getBankBtn().setEnabled(false);
					}
	
					/*********************************************************
					 * 4.4.0: When all of the selected dice contribute to the point
					 * total for the roll, the roll button is enabled and the roll
					 * point total is added to the running point total for the
					 * current turn.
					 *********************************************************/
	
					// Don't allow a user to roll with no scoring dice held
					if (rollScore > 0
							&& farkleGame.getPlayerTypeForCurrentPlayer() != PlayerType.COMPUTER) {
						farkleUI.getRollBtn().setEnabled(true);
					} else {
						farkleUI.getRollBtn().setEnabled(false);
						farkleUI.getBankBtn().setEnabled(false);
					}
	
					/***********************************************************
					 * 4.7.0: If all six dice have been selected, and they all
					 * contribute to the turns point total, the player is issued a
					 * bonus roll. All selected dice are deselected, and the process
					 * returns to requirement 4.1.0.
					 ***********************************************************/
					if ((farkleUI.getDice(DieState.HELD).size()
							+ farkleUI.getDice(DieState.SCORED).size() == 6)
							&& (rollScore > 0)) {
						farkleUI.disableDice();
						farkleUI.playBonusSound();
						/****************************
						 * 1.3.4 - Turn Highlighting
						 ****************************/
						farkleUI.highlightTurnScore(
								farkleGame.getPlayerNumberForCurrentPlayer(),
								farkleGame.getTurnNumberForCurrentPlayer(), true);
						farkleGame.setBonusTurn(true);
					}
					
					// Updated the farkle UI running score label
					farkleUI.setRunningScore(runningScore);
				}
			}
		}
	}

	/**
	 * Not used
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Auto-generated method stub

	}

	/**
	 * If mainMenu is true, returns false indicating a new game is to be started and
	 * the main menu is to be displayed. If resetOnly is true and main menu is false,
	 * returns true indicating the game is to be reset with the current game configuration.
	 * Displays the end of game option box if both resetOnly and MainMenu are false, and
	 * returns the user's selection (false for main menu or true for reset game). If the
	 * user selects "Quit" from the end of game option box, the application closes.
	 * @param resetOnly true if the game is to be reset
	 * @param mainMenu true if a new game is to be started
	 * @return false indicating a new game should be started, or true indicating the 
	 * current game is to be reset.
	 */
	public boolean gameEnded(boolean resetOnly, boolean mainMenu) {
		boolean retVal = true;
		
		/* If resetOnly and mainMenu are both set to false, display the end
		 * of game dialog box, and return the user's selection.
		 */
		if ((!resetOnly) && (!mainMenu)) {
			/**************************************************
			 * 1.5.1 - At the conclusion of the game, an option box shall be
			 * displayed with the player's overall score for the completed
			 * game (in one player mode), or the winner of the current game (in
			 * two player mode). the user shall be greetedThis option box shall
			 * include with three options: "Play Aagain?", "Main Menu",
			 * and "Quit".
			 *************************************************/

			/**************************************************
			 * 2.1.4 - The player is given three options: "Play again?",
			 * "Main Menu", and "Quit".
			 **************************************************/

			/**************************************************
			 * 2.2.4 - A congratulatory message to the winner is displayed, and
			 * three options are given: "Play again?", "Main Menu", and
			 * "Quit".
			 **************************************************/

			/**************************************************
			 * 2.2.13 - A "You Win!!" or "You Lose" message is
			 * displayed depending on if the live player won or lost the game,
			 * and three options are given: "Play again?", "Main Menu",
			 * and "Quit".
			 **************************************************/
			String message = "";
			
			// Get the winner's information
			String[] winnerStats = farkleGame.getWinningPlayerInfo();
			
			// If the game mode is single player, construct the appropriate message
			if (farkleGame.getGameMode() == GameMode.SINGLEPLAYER) {
				message = "Total Score: " + farkleGame.getGameScoreForPlayer(1);
			
			/* Else if the game mode is two player, and there is a distinct winner
			 * construct the appropriate message.
			 */
			} else if (winnerStats.length > 0 && winnerStats.length < 3) {
				message = winnerStats[0] + " wins with a total " + "score of "
						+ winnerStats[1] + "!";
			
			/* Else if the game mode is two player, and there was a tie, construct
			 * the appropriate message.
			 */
			} else if (winnerStats.length == 3) {
				message = winnerStats[0] + " and " + winnerStats[1] + " "
						+ "tied with a total score of " + winnerStats[2];
			
			// Else, something went wrong. Construct the appropriate message
			} else {
				message = "A winner couldn't be determined.\nSomething is broken.";
			}

			// Display the JOptionPane, and get the user's response
			Object[] options = { "Play Again", "Main Menu", "Exit" };
			int userResponse = JOptionPane.showOptionDialog(farkleUI, message
					+ "\nWhat would you like to do?", "Game Over",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

			/*************************************************
			 * 2.1.5: If "Play Again" is selected, the game starts over in 1
			 * player mode.
			 ************************************************/

			/************************************************
			 * 2.2.5 - If "Play again?" is selected, the game starts over in
			 * 2 player mode against a live person.
			 ************************************************/

			/************************************************
			 * 2.2.14 - If "Play again?" is selected, the game starts over
			 * in 2 player mode against the computer.
			 ************************************************/
			
			// If the user selects "Play Again?" return true
			if (userResponse == 0) {
				retVal = true;
			}

			/*************************************************
			 * 2.1.6: If "Main Menu" is selected, the user is taken to the mode
			 * selection screen.
			 ************************************************/

			/************************************************
			 * 2.2.6 - If "Main Menu" is selected, the user is taken to the
			 * mode selection screen.
			 ************************************************/

			/************************************************
			 * 2.2.15 - If "Main Menu" is selected, the user is taken to the
			 * mode selection screen.
			 ************************************************/
			// Else if the user selects "Main Menu", return false
			else if (userResponse == 1) {
				retVal = false;
			}

			/**************************************************
			 * 2.1.7: If "Quit" is selected, the application immediately closes.
			 *************************************************/

			/**************************************************
			 * 2.2.7 - If "Quit" is selected, the application immediately
			 * closes.The conclusion of game option box (section 1.5.0) is
			 * displayed after a player wins
			 **************************************************/

			/**************************************************
			 * 2.2.16 - If "Quit" is selected, the application immediately
			 * closes.The conclusion of game option box (section 1.5.0) is
			 * displayed after a player wins.
			 **************************************************/
			// Else, the user selected "Quit", close the application
			else {
				farkleUI.dispose();
			}
		}

		// If main menu is true, return false indicating a new game is to be started
		if (mainMenu) {
			retVal = false;
		}
		
		// Disable the bank button
		farkleUI.getBankBtn().setEnabled(false);
		
		// Reset the scores, running score, and game scores for all players
		farkleUI.setRunningScore(0);
		farkleUI.setGameScore(1, 0);
		farkleUI.resetScores(1);
		if (farkleGame.getGameMode() == GameMode.MULTIPLAYER) {
			farkleUI.resetScores(2);
			farkleUI.setGameScore(2, 0);
		}
		
		// Return the selected option
		return retVal;
	}

	/**
	 * Checks if the player selected bank after receiving a bonus roll, and displays
	 * a warning message if that is the case. If not, calls the bank method and plays
	 * the bank sound. If the next player is a computer player, disables the roll button
	 * so the user cannot select it while the computer is taking its turn.
	 */
	public void bankHandler() {
		
		/* If the player banked after receiving a bonus turn, ask the user
		 * if they're sure they don't want to roll again
		 */
		if (farkleGame.isBonusTurn()
				&& farkleGame.getPlayerTypeForCurrentPlayer() == PlayerType.USER) {
			boolean useBonusRoll = farkleUI
					.displayYesNoMessage(
							"You used all your dice so you earned a bonus turn.\n"
									+ "If you bank your score now, you'll lose your bonus turn.\n"
									+ "Are you sure you want to bank now?",
							"Warning!");
			if (useBonusRoll) {
				bank();
				farkleUI.playBankSound();
				farkleGame.setBonusTurn(false);
			}
		
		// Else call the bank() method and play the bank sound
		} else {
			bank();
			farkleUI.playBankSound();
		}
		
		// If the new current player is a compuer player, disable the roll button
		if(farkleGame.getPlayerTypeForCurrentPlayer() == PlayerType.COMPUTER) {
			farkleUI.getRollBtn().setEnabled(false);
		}
	}

	/**
	 * Resets the farkleGame bonus turn flag, resets disabled dice, animates a roll action,
	 * and checks for a Farkle. The action buttons are set accordingly based on the roll
	 * outcome and player type.
	 */
	public void rollHandler() {
		
		if (farkleUI.getRollBtnText().equals("Roll Dice")) {
			
			// If the previos roll was a bonus turn, set BonusTurn to false
			if (farkleGame.isBonusTurn()) {
				farkleGame.setBonusTurn(false);
			}
			
			// If the dice are disabled (which occurs during a bank or farkle), reset them
			if(farkleUI.getDieValues(DieState.DISABLED).size() > 0) {
				farkleUI.resetDice();
			}

			/********************************************************************
			 * 1.2.6 - After each roll, dice that are have previously been
			 * selected, scored, and locked shall be highlightedshaded to
			 * indicate they will not be available on the next roll, and the
			 * selected turn accumulated point totalscore shall be displayed
			 * above the dice updated.
			 ********************************************************************/

			/********************************************************************
			 * 4.6.0 - If the player elects to roll again the selected dice are
			 * locked, the remaining dice are rolled, and the process returns to
			 * requirement 4.2.0.
			 ********************************************************************/
			
			// Lock all scored dice
			farkleUI.lockScoredDice();

			// Create a timer to animate the roll
			Timer rollAnimationTimer = new Timer();
			rollAnimationTimer.scheduleAtFixedRate(new TimerTask() {
				
				// The variable used to count the number of times the dice have rolled
				int count = 0;

				@Override
				public void run() {
					
					// Roll the dice ten times
					farkleUI.rollDice();
					count++;
					
					// After the tenth roll, process it
					if (count >= 10) {
						
						// Tell the model this is a completed roll
						farkleGame.processRoll();

						/***************************************************
						 * 4.2.0: The resulting roll is analyzed according to
						 * requirement 6.0.0 to determine if the player farkled.
						 * A farkle occurs if the roll results in 0 points.
						 ***************************************************/
						// Score any UNHELD dice
						int rollScore = farkleGame.calculateScore(
								farkleUI.getDieValues(DieState.UNHELD), false);

						// If it's farkle
						if (rollScore == 0) {
							
							// Call the farkle method to update the model and view
							farkle();

							// Disable the bank button
							farkleUI.getBankBtn().setEnabled(false);
							
							// If the next player is not a computer player, enable the roll button
							if (farkleGame.getPlayerTypeForCurrentPlayer() != PlayerType.COMPUTER) {
								farkleUI.getRollBtn().setEnabled(true);
							}

						// Else, this roll did not result in a farkle
						} else {
							
							// If the player is a computer player, determine the computers decision
							if (farkleGame.getPlayerTypeForCurrentPlayer() == PlayerType.COMPUTER) {
								compDecision();
							}
						}
						
						// Cancel this timer task
						this.cancel();
					}
				}

			}, 0, 50);

			// Play roll sound
			farkleUI.playRollSound();

			/*************************************************
			 * 4.3.0 - If the player did not farkle, he or she must select at
			 * least one scoring die. The score for the selected dice is
			 * calculated according to requirement 5.0.0, and is updated after
			 * each die selection. Only scoring die can be selected.The score
			 * for the selected dice is displayed in accordance with section
			 * 1.2.9. If any of the selected dice does not contribute to the
			 * score, a selected dice score of 0 is displayed and the "Roll
			 * Dice" and "Bank Score" buttons are disabled.
			 *************************************************/
			// Disable the Roll and Bank buttons
			farkleUI.getRollBtn().setEnabled(false);
			farkleUI.getBankBtn().setEnabled(false);

			// Enable Select All Button if this is not the computer player
			if (farkleGame.getPlayerTypeForCurrentPlayer() != PlayerType.COMPUTER) {
				farkleUI.getSelectAllBtn().setEnabled(true);
			}

			/****************************
			 * 1.3.4 - Turn Highlighting
			 ****************************/
			farkleUI.highlightTurnScore(
					farkleGame.getPlayerNumberForCurrentPlayer(),
					farkleGame.getTurnNumberForCurrentPlayer(), false);
			
			// Set the roll score to 0 
			farkleUI.setRollScore(0);
		}
	}

	/**
	 * Causes the thread to sleep for a split second giving the illusion of playing
	 * against a live player. Calculates and selects the highest scoring combination
	 * of dice for a given roll. Calculates a pseudo random target for the computer
	 * to achieve during a given roll, and banks the turn if that target is achieved
	 * on a roll that was not awarded a bonus turn. Otherwise, the computer rolls
	 * again and calculates a new target for the turn.
	 */
	public void compDecision() {

		// Delay the thread to give the illusion of playing against a live player
		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// The goal the computer is trying to achieve
		int goal = 0;
		
		// Determine the highest scoring dice that can be selected
		List<Integer> highestScoringDieValues = getHighestScoringDieValues();

		// Select the highest scoring combination of dice
		try {
			farkleUI.selectDice(highestScoringDieValues);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/* Randomly choose a goal score for this turn. There is a 30% chance
		 * The computer tries for 600, 20% chance it tries for 1000, and a 50%
		 * chance it tries for 300. This is recalculated every roll leading to 
		 * variable score results. E.g. after a roll, it has a score of 500, but the
		 * target for that turn was 1000 for that roll so it would not bank. If the 
		 * subsequent roll results in a score of 550, and the target is changed to 300,
		 * it would then bank.
		 */
		int n = rand.nextInt(10) + 1;
		if(n <= 5)
		{
			goal = 300;
		} else if (n <= 8) {
			goal = 600;
		} else {
			goal = 1000;
		}

		System.out.println(goal);
		/* If the current turn score is less than the goal, or the computer acheived a bonus turn, 
		 * the computer should always roll again.
		 */ 
		if (farkleUI.getRunningScore() < goal || farkleGame.isBonusTurn()) {
			rollHandler();
			
		// Else, the computer banks the current turn.
		} else {
			bankHandler();
		}
	}

	/**
	 * Returns true if no dice are currently held and the dice have been rolled. Used
	 * to enable/disable the hint option in the file menu.
	 * 
	 * @return boolean true if a hint is available
	 */
	public boolean isHintAvailable() {
		
		// If no dice is held determine if the dice have been rolled
		if (farkleUI.getDice(DieState.HELD).size() == 0) {
			
			// If the dice have been rolled, return true
			for (Die d : farkleUI.getDice(DieState.UNHELD)) {
				if (d.getValue() == 0) {
					
					// The dice have not been rolled, return false
					return false;
				}
			}
			return true;
		}
		
		// At least one die is held, return false
		return false;
	}
	
	
	/**
	 * Since Swing and, consequently, our design isn't thread-safe, 
	 * don't allow the game to be reset or a new game
	 * to be created while the automated player is 
	 * taking its turn.
	 * 
	 * @return boolean true if the computer is not taking its turn
	 */
	public boolean isResetOrNewGameAvailable() {
		
		// If the current player is the computer, return false
		if(farkleGame.getGameMode() == GameMode.MULTIPLAYER && 
				farkleGame.getPlayerTypeForCurrentPlayer() == PlayerType.COMPUTER) {
			return false;
			
		// Else, return true
		} else {
			return true;
		}
	}

	/**
	 * Get the highest possible score of the rolled dice
	 * @return highest possible score of the rolled dice
	 */
	public int getHighestPossibleScore() {
		
		// Determine the highest possible scoring set of dice
		Object[] results = farkleGame.calculateHighestScore(farkleUI
				.getDieValues(DieState.UNHELD));
		try {
			
			// Return the score for that set of dice
			return (Integer) results[0];
		} catch (ClassCastException e) {
			throw e;
		}
	}

	/**
	 * Get a list of the dice to select to achieve the highest
	 * possible score for a given roll of the dice
	 * @return list of dice that result in the highest possible score
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getHighestScoringDieValues() {
		
		// Determine the highest possible scoring set of dice
		Object[] results = farkleGame.calculateHighestScore(farkleUI
				.getDieValues(DieState.UNHELD));
		try {
			// Return the list of dice to select for highest possible score
			return (List<Integer>) results[1];
		} catch (ClassCastException e) {
			throw e;
		}
	}

	/**
	 * Roll the dice in their own thread so the call can sleep without blocking
	 * Swing's Event Dispatch Thread. Used for animating the computer's turn.
	 */
	public void asynchronousRoll() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					rollHandler();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t.start();
	}
}
