package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class FarkleController implements ActionListener, MouseListener {
	FarkleUI farkleUI;
	Game farkleGame;
	//Gamemode, playertype, Player1Name, Player2Name
	String[] tempGameInformation = new String[4];

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
		 * 1.3.7: If any roll results in 0 points, the word �Farkle!!!� is
		 * displayed above the dice until the next player rolls (in 2 player
		 * mode) or the first roll of the next turn is taken (in 1 player mode).
		 ********************************************************/

		/*********************************************************
		 * 4.9.0: If the player farkles on any roll during the current turn,
		 * that player loses all points accumulated during the current turn and
		 * the turn is over.
		 *********************************************************/

		farkleUI.resetDice();
		farkleUI.getRunningScore().setText("FARKLE!!!");
		farkleUI.setRollScore(0);
		farkleGame.farkle();

		if (farkleGame.getCurrentPlayer().turnNumber > 10) {
			if (checkHighScore())
			{
				farkleUI.displayMessage("Congrats! You achieved a new high score.", "High Score");
			}
			endGame(false, false);
			
		}
		else
		{
			farkleUI.updateGUI(farkleGame.getCurrentPlayer().getTurnScores(), farkleGame.getCurrentPlayer().playerName);
		}
	}

	/**
	 * Handle bank by sending calls to both the Model and the View
	 */
	public void bank() {

		/***************************************************
		 * 4.8.0: If the player selects the bank button, the current turn point
		 * total is added to the player�s game point total, and the turn is
		 * over.
		 ***************************************************/
		
		farkleUI.getRunningScore().setText("0");
		farkleUI.setRollScore(0);
		farkleUI.resetDice();
		farkleUI.getGameScore().setText(String.valueOf(farkleGame.bank()));
		farkleUI.getBankBtn().setEnabled(false);
		farkleUI.getSelectAllBtn().setEnabled(false);
		checkHighScore();
		if (farkleGame.getCurrentPlayer().turnNumber > 10) {
			if (checkHighScore())
			{
				farkleUI.displayMessage("Congrats! You achieved a new high score.", "High Score");
			}
			endGame(false, false);
			
		}
		else
		{
			farkleUI.updateGUI(farkleGame.getCurrentPlayer().getTurnScores(), farkleGame.getCurrentPlayer().playerName);
		}
		farkleUI.setTurnHighlighting(farkleGame.getCurrentPlayer().turnNumber, false);
	}
	
	public boolean checkHighScore()
	{
		if (farkleGame.getCurrentPlayer().getGameScore() >= farkleGame.highScore) {
			farkleGame.highScore = farkleGame.getCurrentPlayer().getGameScore();
			farkleUI.highScore.setText(Integer.toString(farkleGame.highScore));
			return true;
		}
		else
		{
			return false;
		}
	}

	public void endGame(boolean resetOnly, boolean mainMenu) {

		/**************************************************
		 * 2.1.2: The game ends at the conclusion of the tenth turn, and the
		 * player�s score is compared to the current high score.
		 **************************************************** 
		 * 2.1.3: If the player�s score is greater than the current high score,
		 * a congratulatory message is displayed, and the player�s score
		 * replaces the previous high score.
		 ****************************************************/
		boolean replayGame = farkleUI.gameEnded(resetOnly, mainMenu);
		if (replayGame)
		{
			replayGame();
		}
		else
		{
			setupGame();
		}
	}
	
	public void setupGame()
	{
		farkleUI.getGameInformation();
	}

	public void newGame() {
	
		//If it is a single player game.
		if (tempGameInformation[0].equals("S"))
		{
			farkleGame = new Game(GameMode.SINGLEPLAYER, this);
			farkleUI.frame.setTitle("Farkle - Single Player Mode");
			farkleGame.players[0].playerName = tempGameInformation[2];
		}
		//If it is a multiplayer game.
		else
		{
			farkleGame = new Game(GameMode.MULTIPLAYER, this);
			farkleUI.frame.setTitle("Farkle - Two Player Mode");
			farkleGame.players[0].playerName = tempGameInformation[2];
			//If it is another human player.
			if (tempGameInformation[1].equals("H"))
			{
				farkleGame.players[1].playerName = tempGameInformation[3];
			}
			//It is a computer player.
			else
			{
				farkleGame.players[1].playerName = "Computer";
				farkleGame.players[1].isComputer = true;
			}			
		}
		farkleUI.updateGUI(farkleGame.getCurrentPlayer().getTurnScores(), farkleGame.getCurrentPlayer().playerName);
		farkleUI.setTurnHighlighting(farkleGame.getCurrentPlayer().turnNumber, false);	
	}
	
	public void replayGame()
	{
		farkleGame.replayGame();
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
		 * 4.1.0: At the beginning of the turn the turn point total is set to 0,
		 * the player selects the roll button, and all 6 dice are rolled in
		 * accordance with the requirement 3.2.0.
		 *********************************************/

		/**********************************************
		 * 4.10.0: When the turn is over all dice are deselected, the roll
		 * button is enabled, the bank button is disabled, the current turn
		 * point total is set to 0, the current roll point total is set to 0,
		 * and play passes to the next player (two player mode) or the next turn
		 * (single player mode).
		 *********************************************/

		// If Roll button clicked
		if (arg0.getSource() == farkleUI.getRollBtn()) {
			
			if (farkleUI.rollBtn.getText().equals("Roll Dice")) {
				if (farkleGame.isBonusTurn)
				{
					farkleGame.isBonusTurn = false;
				}
				
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
				farkleUI.getRollBtn().setEnabled(false);
				farkleUI.getBankBtn().setEnabled(false);
				
				//Enable Select All Button
				farkleUI.getSelectAllBtn().setEnabled(true);

				// Turn Highlighting
				farkleUI.setTurnHighlighting(farkleGame.getCurrentPlayer().turnNumber, false);
				farkleUI.setRollScore(0);
				
			}
		}
		// If Bank button clicked
		else if (arg0.getSource() == farkleUI.getBankBtn()) {
			if (farkleGame.isBonusTurn)
			{
				if (farkleUI.displayYesNoMessage("You used all your dice so you earned a bonus turn.\nIf you bank your score now, you'll lose your bonus turn.\nAre you sure you want to bank now?", "Warning!"))
				{
					bank();
					farkleUI.playBankSound();
					farkleGame.isBonusTurn = false;
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
					farkleUI.setTurnHighlighting(farkleGame.getCurrentPlayer().turnNumber, true);
					farkleGame.isBonusTurn = true;
				}
				

				farkleUI.getRunningScore()
						.setText(String.valueOf(runningScore));
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// Auto-generated method stub

	}

}
