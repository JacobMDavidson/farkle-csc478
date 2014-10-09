package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FarkleController implements ActionListener, MouseListener {
	FarkleUI farkleUI;
	Game farkleGame;

	
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
		
		/********************************************************
		 * 1.3.7: If any roll results in 0 points, the word 
		 * “Farkle!!!” is displayed above the dice until the next 
		 * player rolls (in 2 player mode) or the first roll of 
		 * the next turn is taken (in 1 player mode).
		 ********************************************************/
		
		/*********************************************************
		 * 4.9.0: If the player farkles on any roll during the 
		 * current turn, that player loses all points accumulated 
		 * during the current turn and the turn is over.
		 *********************************************************/
		
		farkleUI.resetDice();
		farkleUI.getRunningScore().setText("FARKLE!!!");
		farkleGame.farkle();

		if (farkleGame.players[0].turnNumber > 10) {
			endGame();
		}
	}

	/**
	 * Handle bank by sending calls to both the Model and the View
	 */
	public void bank() {
		
		/***************************************************
		 * 4.8.0: If the player selects the bank button, 
		 * the current turn point total is added to the 
		 * player’s game point total, and the turn is over.
		 ***************************************************/
		
		farkleUI.getRunningScore().setText("0");
		farkleUI.resetDice();
		farkleUI.getGameScore().setText(String.valueOf(farkleGame.bank()));
		farkleUI.getBankBtn().setEnabled(false);
		if (farkleGame.players[0].turnNumber > 10) {
			endGame();
		}
	}

	public void endGame() {
		farkleUI.runningScore.setText("End Game");
		farkleUI.rollBtn.setText("New Game");
		farkleUI.getBankBtn().setEnabled(false);
		farkleUI.setTurnHighlighting(0);
		farkleUI.gameScoreTitle.setBackground(Color.WHITE);
		farkleUI.gameScore.setBackground(Color.WHITE);
		
		/**
		 * TODO: FINISH IMPLEMENTING 1.6.0
		 * 
		 *************************************************
		 * 1.6.0: At the conclusion of the game, the user
		 * shall be greeted with three options:
		 * “Play again?”, “Main Menu”, and “Quit”.
		 *************************************************/
		
		/**
		 * TODO: IMPLEMENT 2.1.4 (1.6.0) AND 2.1.5
		 * 
		 ************************************************
		 * 2.1.5: If “Play again?” is selected, the
		 *  game starts over in 1 player mode.
		 ************************************************/
		
		
		/**
		 * TODO: IMPLEMENT 2.1.6
		 * 
		 ************************************************
		 * 2.1.6: If “Main Menu” is selected, the user
		 *  is taken to the mode selection screen.
		 ************************************************/
		
		/**
		 * TODO: IMPLEMENT 2.1.7
		 * 
		 *************************************************
		 * 2.1.7: If “Quit” is selected, the application
		 *  immediately closes.
		 *************************************************/
		
		
		/**************************************************
		 * 2.1.2: The game ends at the conclusion of the
		 * tenth turn, and the player’s score is 
		 * compared to the current high score. 
		 ****************************************************
		 * 2.1.3:  If the player’s score is greater than the
		 * current high score, a congratulatory message is
		 * displayed, and the player’s score replaces the
		 *  previous high score.
		 ****************************************************/
		if (farkleGame.players[0].getGameScore() > farkleGame.highScore)
		{
			farkleUI.displayMessage("Congrats! You got a high score!", "OMFG! NEW HIGH SCORE!");
			farkleGame.highScore = farkleGame.players[0].getGameScore();
			farkleUI.highScore.setText(Integer.toString(farkleGame.highScore));
			farkleUI.setTurnHighlighting(0);
			farkleUI.highScoreTitle.setBackground(Color.YELLOW);
			farkleUI.highScore.setBackground(Color.YELLOW);
		}
	}
	
	public void newGame()
	{
		//farkleUI.initUI(); //TODO: CuBr/BrMu - make this not start a new game in the same process
		farkleUI.rollBtn.setText("Roll Dice");
		farkleUI.runningScore.setText("0");
		farkleGame = new Game(GameMode.SINGLEPLAYER, this);
		//set scores for each turn
		farkleUI.resetScores();
		farkleUI.gameScore.setText("0");
		farkleUI.setTurnHighlighting(0);
		
		
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		/**********************************************
		 * 1.4.4: The current turn shall be indicated
		 *  by highlighting that turn on the left
		 *  side of the screen.
		 *********************************************/
		
		/*********************************************
		 * 2.1.1: Each turn is taken according to the 
		 * requirements of section 4.0.0.
		 *********************************************/
		
		/*********************************************
		 * 4.1.0: At the beginning of the turn the 
		 * turn point total is set to 0, the player 
		 * selects the roll button, and all 6 dice 
		 * are rolled in accordance with the 
		 * requirement 3.2.0.
		 *********************************************/
		
		/**********************************************
		 * 4.10.0: When the turn is over all dice are 
		 * deselected, the roll button is enabled, 
		 * the bank button is disabled, the current 
		 * turn point total is set to 0, the current 
		 * roll point total is set to 0, and play 
		 * passes to the next player (two player mode) 
		 * or the next turn (single player mode).
		 *********************************************/
		
		// If Roll button clicked
		if (arg0.getSource() == farkleUI.getRollBtn()) {
			if (farkleUI.rollBtn.getText().equals("Roll Dice")) {
				farkleUI.lockScoredDice();
				farkleUI.rollDice();

				// Play roll sound
				farkleUI.playRollSound();
				
				// Disable the Roll and Bank buttons
				farkleUI.getRollBtn().setEnabled(false);
				farkleUI.getBankBtn().setEnabled(false);
				
				//Turn Highlighting
				farkleUI.setTurnHighlighting(farkleGame.players[0].turnNumber);

				// Tell the model this is a new roll
				farkleGame.processRoll();

				
				/***************************************************
				 * 4.2.0: The resulting roll is analyzed according
				 *  to requirement 6.0.0 to determine if the 
				 *  player farkled. A farkle occurs if the roll
				 *   results in 0 points.
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
			}
			else if (farkleUI.rollBtn.getText().equals("New Game"))
			{
				newGame();
			}

			// If Bank button clicked
		} else if (arg0.getSource() == farkleUI.getBankBtn()) {
			bank();
			farkleUI.playBankSound();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO: Exception handling for die cast
		Die d = (Die) arg0.getSource();

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

				// Get the running score from the model
				int runningScore = farkleGame.getRollScores();

				// Update the UI based on the model's response
				if (runningScore > 0) {
					farkleUI.getRollBtn().setEnabled(true);
				} else {
					farkleUI.getRollBtn().setEnabled(false);
				}

				
				/*********************************************
				 * 4.5.0: If the current turn point total is 
				 * greater than or equal to 300, the bank 
				 * button is enabled.
				 *********************************************/
				
				// Enable the bank button if the score is >= 300
				if (runningScore >= 300) {
					farkleUI.getBankBtn().setEnabled(true);
				} else {
					farkleUI.getBankBtn().setEnabled(false);
				}

				
				/*********************************************************
				 * 4.4.0: When all of the selected dice contribute 
				 * to the point total for the roll, the roll 
				 * button is enabled and the roll point total is 
				 * added to the running point total for the current turn.
				 *********************************************************/
				
				// Don't allow a user to roll with no scoring dice held
				if (rollScore > 0) {
					farkleUI.getRollBtn().setEnabled(true);
				} else {
					farkleUI.getRollBtn().setEnabled(false);
					farkleUI.getBankBtn().setEnabled(false);
				}

				
				/***********************************************************
				 * 4.7.0: If all six dice have been selected, and 
				 * they all contribute to the turns point total, 
				 * the player is issued a bonus roll. All selected 
				 * dice are deselected, and the process returns 
				 * to requirement 4.1.0.
				 ***********************************************************/
				if ((farkleUI.getDice(DieState.HELD).size()
						+ farkleUI.getDice(DieState.SCORED).size() == 6)
						&& (rollScore > 0)) {
					farkleUI.resetDice();
					farkleUI.playBonusSound();
					// TODO: BrMu / CuBr - We need to update this to say BONUS!
					// somewhere so people know when to celebrate.
				}

				farkleUI.getRunningScore()
						.setText(String.valueOf(runningScore));
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
