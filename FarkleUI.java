package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FarkleUI extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static DieComponent[] dice = new DieComponent[6];
	public static JButton rollBtn = new JButton("Roll Dice");
	public static JButton bankBtn = new JButton("Bank Score");
	public static FarkleController controller = new FarkleController();
	public static JLabel[] turnScores = new JLabel[10];
	public static JLabel gameScore = new JLabel("Fake Score For Now");
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new FarkleUI();
            }
        });
	}
	
	public FarkleUI() {
		initUI();
	}
	
	public void initUI() {
		
		// Pass a reference to the controller
		controller.setUI(this);
		
		// Create and set up the main Window
        JFrame frame = new JFrame("Farkle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1024, 768));
        frame.setLayout(new GridLayout(1, 3, 10, 10));
        
        // Build the UI
		frame.add(createPlayerPanel("BeeBops Mcgee", 1));
		frame.add(createDicePanel());
//		frame.add(createButtonPanel());
		
		// Center and display the window
        frame.setLocationRelativeTo(null);
        frame.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
		frame.setVisible(true);
	}
	
	public static JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		rollBtn.addActionListener(controller);
		buttonPanel.add(rollBtn);
		bankBtn.addActionListener(controller);
		buttonPanel.add(bankBtn);
		return buttonPanel;		
	}
	
	public static JPanel createDicePanel() {
		// Create the panel
		JPanel dicePanel = new JPanel(new GridLayout(0, 1, 0, 0));
		
		// Initialize the dice and add to panel
		for(int i = 0; i < dice.length; i++) {
			dice[i] = new DieComponent(controller);
			dicePanel.add(dice[i]);
		}
		
		dicePanel.add(createButtonPanel());
		return dicePanel;
	}
	
    private static JPanel createPlayerPanel(String playerName, int playerNumber) {
        JPanel playerPanel = new JPanel(new GridLayout(0, 2, 2, 2));
        JLabel nameLbl = new JLabel("Player " + playerNumber);
        playerPanel.add(nameLbl);
        JLabel name = new JLabel(playerName);
        playerPanel.add(name);
        
        JLabel filler0 = new JLabel(" ");
        playerPanel.add(filler0);
        JLabel filler1 = new JLabel(" ");
        playerPanel.add(filler1);
        
        
        JLabel[] scoreLabels = new JLabel[10];
        for(int i = 0; i < scoreLabels.length; i++) {
            scoreLabels[i] = new JLabel("Roll: " + (i + 1));
            playerPanel.add(scoreLabels[i]);
            
            turnScores[i] = new JLabel("score" + (i + 1));
            playerPanel.add(turnScores[i]);
        }
        
        playerPanel.add(new JLabel("Total Score: "));
        playerPanel.add(gameScore);
        
        playerPanel.setPreferredSize(new Dimension(500, 300));
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        return playerPanel;
    }
    

	public DieComponent[] getDice() {
		return dice;
	}

	public JButton getRollBtn() {
		return rollBtn;
	}
	
	public JLabel[] getScores() {
		return turnScores;
	}

	public static void setScores(JLabel[] scores) {
		FarkleUI.turnScores = scores;
	}

	public static JLabel getGameScore() {
		return gameScore;
	}

	public static void setGameScore(JLabel gameScore) {
		FarkleUI.gameScore = gameScore;
	}

	public void rollDice() {
		for(DieComponent d : dice) {
			d.roll();
		}
	}
	
	public FarkleUI getUI() {
		return this;
	}
}
