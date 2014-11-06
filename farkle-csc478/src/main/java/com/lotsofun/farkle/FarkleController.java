package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class FarkleController implements ActionListener, MouseListener {
	FarkleUI farkleUI;
	Game farkleGame;
	//Gamemode, playertype, Player1Name, Player2Name
	String[] tempGameInformation = new String[4];
	final int POINT_THRESHOLD = 1000;
	boolean isLastTurn = false;
	
	
	public static void main(String[] args) {
		final FarkleController controller = new FarkleController();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new FarkleUI(controller);
			}
		});
	}

	/**
	 * Set a reference to the UI object
	 * 
	 * @param farkle
	 */
	public void setUI(FarkleUI farkle) {
		this.farkleUI = farkle;
	}

	/**
	 * Set a reference to the game object
	 * 
	 * @param g
	 */
	public void setGame(Game g) {
		this.farkleGame = g;

	}

	/**
	 * Handle farkle by sending calls to both the Model and View
	 */
	public void farkle() {

		/******************************************************************
		 * 1.2.7: If any roll results in 0 points, the word “Farkle!!!”
		 *  is prominently displayeddisplayed, and 0 points is displayed
		 *   in the accumulated turn score above the dice. The dice retain
		 *    their current values that resulted in the Farkle.
		 *     After the Farkle message is displayed, the dice
		 *      still retain the values that resulted in the Farkle,
		 *       but all dice are unlocked and play passes to the next player
		 *        (in two player mode), or to the next turn (in one player mode).
		 *        until the next player rolls (in 2 player mode) or the first
		 *         roll of the next turn is taken (in 1 player mode).
		 ********************************************************************/

		/*********************************************************
		 * 4.9.0: If the player farkles on any roll during the 
		 * current turn, that player loses all points accumulated 
		 * during the current turn, the farkle message is displayed 
		 * per requirement 1.2.7, and the turn is over.
		 *********************************************************/

		farkleUI.resetDice();
		farkleUI.getRunningScore().setText("FARKLE!!!");
		farkleUI.setRollScore(0);
		farkleUI.setEnabled(false);
		farkleUI.getFarkleMessage().setVisible(true);
		farkleUI.setEnabled(true);
		farkleUI.unHighlightAllTurnScores(farkleGame.getPlayerNumberForCurrentPlayer());
		farkleGame.farkle();

		tryToEndGame();
		
		/****************************
		 * 1.3.4 - Turn Highlighting
		 ****************************/
		farkleUI.highlightTurnScore(farkleGame.getPlayerNumberForCurrentPlayer(), farkleGame.getTurnNumberForCurrentPlayer(), false);
	}

	/**
	 * Handle bank by sending calls to both the Model and the View
	 */
	public void bank() {
		int player = farkleGame.getPlayerNumberForCurrentPlayer();

		/***************************************************
		 * 4.8.0: If the player selects the bank button, the current turn point
		 * total is added to the player's game point total, and the turn is
		 * over.
		 ***************************************************/
		
		farkleUI.setRunningScore(0);
		farkleUI.setRollScore(0);
		farkleUI.resetDice();
		farkleUI.unHighlightAllTurnScores(player);
		farkleUI.setGameScore(player, farkleGame.bank());
		farkleUI.getBankBtn().setEnabled(false);
		farkleUI.getSelectAllBtn().setEnabled(false);
		checkHighScore();

		tryToEndGame();
		
		/****************************
		 * 1.3.4 - Turn Highlighting
		 ****************************/
		farkleUI.highlightTurnScore(farkleGame.getPlayerNumberForCurrentPlayer(), farkleGame.getTurnNumberForCurrentPlayer(), false);
	}
	
	public boolean checkHighScore() {
		if (farkleGame.getGameScoreForCurrentPlayer() >= farkleGame.getHighScore()) {
			farkleGame.setHighScore(farkleGame.getGameScoreForCurrentPlayer());
			farkleUI.setHighScore(farkleGame.getHighScore());
			return true;
		}
		else {
			return false;
		}
	}
	
	
	// TODO: Use actual player names instead of numbers
	/******************************************************
	 * 2.1.2 - The game ends at the conclusion of the 
	 * tenth turn, and the player’s score is compared 
	 * to the current high score.
	 ******************************************************/
	public void tryToEndGame() {
		if (farkleGame.getGameMode() == GameMode.SINGLEPLAYER && farkleGame.getTurnNumberForCurrentPlayer() > 10) {
			/*****************************************************
			 * 2.1.3 - If the player’s score is greater than the 
			 * current high score, a congratulatory message is 
			 * displayed, and the player’s score replaces the 
			 * previous high score.
			 *****************************************************/
			if (checkHighScore()) {
				farkleUI.displayMessage("Congrats! You achieved a new high score.", "High Score");
			}
			endGame(false, false, "");
		} else if(farkleGame.getGameMode() == GameMode.MULTIPLAYER) {
			int player1Score = farkleGame.getGameScoreForPlayer(1);
			int player2Score = farkleGame.getGameScoreForPlayer(2);
			if(player1Score >= POINT_THRESHOLD || player2Score >= POINT_THRESHOLD) {
				
				/***************************************************
				 * TODO
				 * 2.2.2 - The first player to surpass 10,000 total 
				 * points at the end of a given turn is highlighted 
				 * in a different color.
				 ***************************************************/
				
				/***************************************************
				 * 2.2.11 - The first player to surpass 10,000 total 
				 * points at the end of a given turn is highlighted 
				 * in a different color.
				 ***************************************************/
				
				/***************************************************
				 * 2.2.12 - The other player has one more turn to 
				 * try and surpass the point total of the first 
				 * player to surpass 10,000 points.
				 ***************************************************/
				if(!isLastTurn) {
					int player = (player1Score >= POINT_THRESHOLD) ? 1 : 2;
					farkleUI.displayMessage("Player " + player + " has scored "
							+ "" + farkleGame.getGameScoreForPlayer(player) + " points\n"
							+ "This is your last turn to try to beat them.\n"
							+ "Good Luck!", "Last Turn");
					isLastTurn = true;
				} else {
					isLastTurn = false;
					String message = "";
					if(player1Score > player2Score) {
						message = "Player 1 wins with a total score of ";
					} else if (player1Score < player2Score) {
						message = "Player 2 wins with a total score of ";
					} else {
						message = "Player 1 and Player 2 tied with a total score of ";
					}

					endGame(false, false, message);
				}
			}
		}
	}

	
	public void endGame(boolean resetOnly, boolean mainMenu, String message) {

		boolean replayGame = gameEnded(resetOnly, mainMenu, message);
		if (replayGame) {
			replayGame();
		}
		else {
			farkleUI.initUI();
		}
	}


	public void newGame() {
		FarkleOptionsDialog farkleOptions = farkleUI.getFarkleOptions();
		
		// Create the game object no matter what the mode is
		if(null != farkleOptions.getGameMode()) {
			farkleGame = new Game(farkleOptions.getGameMode(), this);
		}
		
		
		/************************************************************************
		 * 1.1.2a - If the user selects the “Start” button with “1 Player Mode”
		 * highlighted and the “Player One Name” field empty, the one player
		 * GUI (section 1.3.0) opens with a random name assigned to player one.
		 ************************************************************************/
		// Set the first player's name no matter what
		if(farkleOptions.getPlayer1Name().length() == 0) {
			farkleUI.setPlayerName(1, "Jacob");
			farkleGame.setPlayerName(1, "Jacob");
		} else {
			
			/*************************************************************************
			 * 1.1.2b - If the user selects the “Start” button with “1 Player Mode”
			 *  highlighted and a name supplied in the “Player One Name” text field,
			 *  the one player GUI (section 1.3.0) opens with the provided name
			 *   assigned to player one.
			 *************************************************************************/
			farkleUI.setPlayerName(1, farkleOptions.getPlayer1Name());
			farkleGame.setPlayerName(1, farkleOptions.getPlayer1Name());
		}
	
		//If it is a multiplayer game.
		/************************************************
		 * 1.4.1 - The title of the window shall 
		 * display, “Farkle – Two Player Mode”.
		 ************************************************/
		if(farkleOptions.getGameMode() == GameMode.MULTIPLAYER){
			farkleUI.frame.setTitle("Farkle - Two Player Mode");	
			
			//If it is another human player.
			if (farkleOptions.getPlayerType() == PlayerType.USER) {
				if (farkleOptions.getPlayer2Name().length() == 0) {
					farkleUI.setPlayerName(2, "Curtis");
					farkleGame.setPlayerName(2, "Curtis");
				} else {
					farkleUI.setPlayerName(2, farkleOptions.getPlayer2Name());
					farkleGame.setPlayerName(2, farkleOptions.getPlayer2Name());
				}
			}
			//It is a computer player.
			else {
				farkleUI.setPlayerName(2, "Computer");
				farkleGame.setPlayerName(2, "Computer");
			}		
			
		/******************************************
		 * 1.3.1 - The title of the window shall
		 *  display: “Farkle – Single Player Mode”.
		 ******************************************/
	    //If it is a single player game.
		} else {
			farkleUI.frame.setTitle("Farkle - Single Player Mode");			
		}
		
		/****************************
		 * 1.3.4 - Turn Highlighting
		 ****************************/
		farkleUI.highlightTurnScore(farkleGame.getPlayerNumberForCurrentPlayer(), farkleGame.getTurnNumberForCurrentPlayer(), false);
		
		farkleUI.pack();
	}
	
	public void replayGame()
	{
		farkleGame.resetGame();
	}

	/**
	 * UI accessor method to simplify the process of updating a specific score
	 * label
	 * 
	 * @param int player
	 * @param int turn
	 * @param int score
	 */
	public void setTurnScore(int player, int turn, int score) {
		farkleUI.setTurnScore(player, turn, score);
	}

	/**
	 * UI accessor method to simplify the process of updating the running score
	 * label
	 * 
	 * @param score
	 */
	public void setRunningScore(int score) {
		farkleUI.setRunningScore(score);
	}
	
	public void setRollScore(int score)
	{
		farkleUI.setRollScore(score);
	}

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
		 * 2.2.1 - Each turn is taken according to the 
		 * requirements of section 4.0.0. The current 
		 * player for each turn is highlighted during 
		 * that player’s turn.
		 *********************************************/
		
		/*********************************************
		 * 2.2.9 - Each turn is taken according to the 
		 * requirements of section 4.0.0. The current 
		 * player for each turn is highlighted during 
		 * that players turn.
		 *********************************************/

		/*********************************************
		 * 4.1.0: At the beginning of the turn the turn point total is set to 0,
		 * the player selects the roll button, and all 6 dice are rolled in
		 * accordance with the requirement 3.2.0.
		 *********************************************/

		/**********************************************
		 * 4.10.0:When the turn is over all dice are 
		 * unlocked while continuing to display their 
		 * last value , the “Roll Dice” button is 
		 * enabled, the “Bank Score” button is 
		 * disabled, the “Select All” button is 
		 * disabled, the current turn point total 
		 * is set to 0, the current roll point total 
		 * is set to 0, and play passes to the next 
		 * player (two player mode) or the next turn 
		 * (single player mode) by highlighting the 
		 * appropriate player or turn on the left 
		 * hand side of the screen. 
		 *********************************************/

		// If Roll button clicked
		if (arg0.getSource() == farkleUI.getRollBtn()) {
			
			if (farkleUI.getRollBtnText().equals("Roll Dice")) {
				if (farkleGame.isBonusTurn())
				{
					farkleGame.setBonusTurn(false);
				}
				
				
				/********************************************************************
				 * 1.2.6 -
				 * After each roll, dice that are have previously been selected,
				 *  scored, and locked shall be highlightedshaded to indicate
				 *   they will not be available on the next roll, and the selected
				 *    turn accumulated point totalscore shall be displayed
				 *     above the dice updated.
				 ********************************************************************/
				
				/********************************************************************
				 * 4.6.0 - If the player elects to roll again the selected dice are 
				 * locked, the remaining dice are rolled, and the process returns to 
				 * requirement 4.2.0.
				 ********************************************************************/
				farkleUI.lockScoredDice();
				
				Timer rollAnimationTimer = new Timer();
				rollAnimationTimer.scheduleAtFixedRate(new TimerTask(){
					int count = 0;
					@Override
					public void run() {
						farkleUI.rollDice();
						count ++;
						if(count >= 10)
						{
							// Tell the model this is a new roll
							farkleGame.processRoll();

							/***************************************************
							 * 4.2.0: The resulting roll is analyzed according to
							 * requirement 6.0.0 to determine if the player farkled. A
							 * farkle occurs if the roll results in 0 points.
							 ***************************************************/
							// Score any UNHELD dice
							int rollScore = farkleGame.calculateScore(
									farkleUI.getDieValues(DieState.UNHELD), false);

							// If it's farkle
							if (rollScore == 0) {
								// Tell everyone
								farkle();
								farkleUI.getRollBtn().setEnabled(true);
								farkleUI.getBankBtn().setEnabled(false);
							}
							this.cancel();
						}
						
					}
					
				}, 0, 50);
				farkleUI.rollDice();

				// Play roll sound
				farkleUI.playRollSound();

				// Disable the Roll and Bank buttons
				/*************************************************
				 * 4.3.0 - If the player did not farkle, he or she 
				 * must select at least one scoring die. The score 
				 * for the selected dice is calculated according to 
				 * requirement 5.0.0, and is updated after each die 
				 * selection. Only scoring die can be selected.The 
				 * score for the selected dice is displayed in 
				 * accordance with section 1.2.9. If any of the 
				 * selected dice does not contribute to the score, 
				 * a selected dice score of 0 is displayed and the 
				 * “Roll Dice” and “Bank Score” buttons are disabled. 
				 *************************************************/
				farkleUI.getRollBtn().setEnabled(false);
				farkleUI.getBankBtn().setEnabled(false);
				
				//Enable Select All Button
				farkleUI.getSelectAllBtn().setEnabled(true);
				
				/****************************
				 * 1.3.4 - Turn Highlighting
				 ****************************/
				farkleUI.highlightTurnScore(farkleGame.getPlayerNumberForCurrentPlayer(), farkleGame.getTurnNumberForCurrentPlayer(), false);
				farkleUI.setRollScore(0);
				
			}
		}
		// If Bank button clicked
		else if (arg0.getSource() == farkleUI.getBankBtn()) {
			if (farkleGame.isBonusTurn()) {
				boolean useBonusRoll = farkleUI.displayYesNoMessage("You used all your dice so you earned a bonus turn.\nIf you bank your score now, you'll lose your bonus turn.\nAre you sure you want to bank now?", "Warning!");
				if (useBonusRoll) {
					bank();
					farkleUI.playBankSound();
					farkleGame.setBonusTurn(false);
				}
			}
			else
			{
				bank();
				farkleUI.playBankSound();
			}
		} 
		// If Select All button clicked
		else if(arg0.getSource() == farkleUI.getSelectAllBtn()) {
			farkleUI.selectAllDice();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO: CuBr / BrMu - Exception handling for die cast
		Die d = (Die) arg0.getSource();

		
		/****************************************************
		 * 1.2.9 - During a current roll, current
		 *  dice selected by the user shall be
		 *   indicated with a yellow border
		 *    around each selected die, and 
		 *    the score for the currently 
		 *    selected dice shall be updated 
		 *    above the dice.
		 *****************************************************/
		// If the value of the die >
		// toggle its state
		if (d.getValue() > 0) {
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
				if (runningScore > 0) {
					farkleUI.getRollBtn().setEnabled(true);
				} else {
					farkleUI.getRollBtn().setEnabled(false);
				}

				/*********************************************
				 * 4.5.0: If the current turn point total is greater than or
				 * equal to 300, the bank button is enabled.
				 *********************************************/

				// Enable the bank button if the score is >= 300
				if (runningScore >= 300) {
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
				if (rollScore > 0) {
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
					farkleUI.resetDice();
					farkleUI.playBonusSound();
					/****************************
					 * 1.3.4 - Turn Highlighting
					 ****************************/
					farkleUI.highlightTurnScore(farkleGame.getPlayerNumberForCurrentPlayer(), farkleGame.getTurnNumberForCurrentPlayer(), true);
					farkleGame.setBonusTurn(true);
				}
				farkleUI.setRunningScore(runningScore);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Auto-generated method stub

	}
	
	
	public boolean gameEnded(boolean resetOnly, boolean mainMenu, String message) {
		boolean retVal = true;
		if ((!resetOnly) && (!mainMenu)){
			/************************************************** 
			 * 1.5.1 - At the conclusion of the game, an option 
			 * box shall be displayed with the player’s overall 
			 * score for the completed game (in one player mode), 
			 * or the winner of the current game (in two player 
			 * mode). the user shall be greetedThis option box 
			 * shall include with three options: “Play Aagain?”, 
			 * “Main Menu”, and “Quit”.
			 *************************************************/
			
			/**************************************************
			 * 2.1.4 - The player is given three options: 
			 * “Play again?”, “Main Menu”, and “Quit”.
			 **************************************************/
			
			/**************************************************
			 * 2.2.4 - A congratulatory message to the winner 
			 * is displayed, and three options are given: 
			 * “Play again?”, “Main Menu”, and “Quit”.
			 **************************************************/
			
			/**************************************************
			 * 2.2.13 - A “You Win!!” or “You Lose…” message is 
			 * displayed depending on if the live player won or 
			 * lost the game, and three options are given: 
			 * “Play again?”, “Main Menu”, and “Quit”.
			 **************************************************/
			if(farkleGame.getGameMode() == GameMode.SINGLEPLAYER) {
				message = "Total Score: ";
			}
			
			Object[] options = { "Play Again", "Main Menu", "Exit"};
			int n = JOptionPane.showOptionDialog(farkleUI,	 
					message + farkleGame.getGameScoreForPlayer(1) + "\nWhat would you like to do?",
					"Game Over",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[2]);
			

			/************************************************* 
			 * 2.1.5: If "Play Again" is selected, the game starts over in 1 player
			 * mode.
			 ************************************************/
			
			/************************************************
			 * 2.2.5 - If “Play again?” is selected, the game 
			 * starts over in 2 player mode against a live person.
			 ************************************************/
			
			/************************************************
			 * 2.2.14 - If “Play again?” is selected, the 
			 * game starts over in 2 player mode against 
			 * the computer.
			 ************************************************/
			if (n == 0) {
				retVal = true;
			}
			
			/************************************************* 
			 * 2.1.6: If "Main Menu" is selected, the user is 
			 * taken to the mode selection screen.
			 ************************************************/
			
			/************************************************
			 * 2.2.6 - If “Main Menu” is selected, the user is 
			 * taken to the mode selection screen.
			 ************************************************/
			
			/************************************************
			 * 2.2.15 - If “Main Menu” is selected, the user is
			 *  taken to the mode selection screen.
			 ************************************************/
			else if (n == 1) {
				retVal = false;
			}
			
			/************************************************** 
			 * 2.1.7: If "Quit" is selected, the application immediately closes.
			 *************************************************/
			
			/**************************************************
			 * 2.2.7 - If “Quit” is selected, the application 
			 * immediately closes.The conclusion of game option 
			 * box (section 1.5.0) is displayed after a player wins
			 **************************************************/
			
			/**************************************************
			 * 2.2.16 - If “Quit” is selected, the application 
			 * immediately closes.The conclusion of game option 
			 * box (section 1.5.0) is displayed after a player wins.
			 **************************************************/
			else {
				farkleUI.dispose();
			}
		}

		if (mainMenu) {
			retVal = false;
		}
		farkleUI.setRunningScore(0);
		farkleUI.setGameScore(1, 0);
		farkleUI.getBankBtn().setEnabled(false);
		farkleUI.resetScores(1);
		if(farkleGame.getGameMode() == GameMode.MULTIPLAYER) {
			farkleUI.resetScores(2);
			farkleUI.setGameScore(2, 0);
		}
		return retVal;
	}
}
