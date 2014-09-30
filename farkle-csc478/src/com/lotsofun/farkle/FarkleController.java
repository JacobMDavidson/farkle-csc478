package com.lotsofun.farkle;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FarkleController implements ActionListener, MouseListener {
	FarkleUI farkleUI;
	Game farkleGame;
	//TODO: Game object
	
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
	 * @param farkle
	 */
	public void setUI(FarkleUI farkle) {
		this.farkleUI = farkle;
	}
	
	/**
	 * Set a reference to the game object
	 * @param g
	 */
	public void setGame(Game g) {
		this.farkleGame = g;
	}
	
	/**
	 * Handle farkle by sending
	 * calls to both the Model and View
	 */
	public void farkle() {
		farkleUI.resetDice();
		farkleUI.getRunningScore().setText("FARKLE!");
		farkleGame.farkle();
	}
	
	/**
	 * Handle bank by sending calls 
	 * to both the Model and the View
	 */
	public void bank() {
		farkleUI.getRunningScore().setText("0");
		farkleUI.resetDice();
		farkleUI.getGameScore().setText(String.valueOf(farkleGame.bank()));
	}

	/**
	 * UI accessor method to simplify the
	 * process of updating a specific score label
	 * @param int player
	 * @param int turn
	 * @param int score
	 */
	public void setTurnScore(int player, int turn, int score) {
		farkleUI.setTurnScore(player, turn, score);
	}
	
	/**
	 * UI accessor method to simplify the
	 * process of updating the running score label
	 * @param score
	 */
	public void setRunningScore(int score) {
		farkleUI.setRunningScore(score);
	}

	@Override
    public void actionPerformed(ActionEvent arg0) {
		// If Roll button clicked
        if(arg0.getSource() == farkleUI.getRollBtn()) {
        	farkleUI.lockScoredDice();
        	farkleUI.rollDice();
        	
        	// Disable the Roll and Bank buttons
        	farkleUI.getRollBtn().setEnabled(false);
        	farkleUI.getBankBtn().setEnabled(false);
        	
        	// Tell the model this is a new roll
    		farkleGame.processRoll();
    		
    		// Score any UNHELD dice
    		int rollScore = farkleGame.calculateScore(farkleUI.getDieValues(DieState.UNHELD), false);
    		
    		// If it's farkle
    		if(rollScore == 0) {
    			// Tell everyone
    			farkle();
    			farkleUI.getRollBtn().setEnabled(true);
    			farkleUI.getBankBtn().setEnabled(true);
    		}
    		
    	// If Bank button clicked
        } else if (arg0.getSource() == farkleUI.getBankBtn()) {
        	bank();
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
        if(d.getValue() > 0) {
	        if(d.getState() == DieState.HELD) {
	        	d.setState(DieState.UNHELD);
	        } else if(d.getState() == DieState.UNHELD) {
	        	d.setState(DieState.HELD);
	        }
	
	        // If the die's state is not SCORED
	        if(d.isScored() == false) {
	        	
	        	// Get the value of the HELD dice
		        int rollScore = farkleGame.calculateScore(farkleUI.getDieValues(DieState.HELD), true);
		        
		        // Tell the model about it
		        farkleGame.processHold(rollScore);
		        
		        // Get the running score from the model
		        int runningScore = farkleGame.getRollScores();
		        
		        // Update the UI based on the model's response
		        if(runningScore > 0) {
		        	farkleUI.getRollBtn().setEnabled(true);
		        } else {
		        	farkleUI.getRollBtn().setEnabled(false);
		        }
		        
		        if(runningScore >= 300) {
		        	farkleUI.getBankBtn().setEnabled(true);
		        } else {
		        	farkleUI.getBankBtn().setEnabled(false);
		        }
		        
		        farkleUI.getRunningScore().setText(String.valueOf(runningScore));
		        
	        }
        }
    }
    

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

}
