package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

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
		
		Object countOps[] = {"1 Player", "2 Players", "Cancel"};
		Object modeOps[] = {"Human Opponent", "Computer Opponent", "Cancel"};
		int playerCount = JOptionPane.showOptionDialog(this, "Choose the number of players.", "Player Mode", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, countOps, countOps[0]);
		int playerMode = -1;
		
		if(playerCount == JOptionPane.NO_OPTION) {
			playerMode = JOptionPane.showOptionDialog(this, "What type of player is player 2?.", "Player Type", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, modeOps, modeOps[0]);
		}
		
		
		if(playerCount != JOptionPane.CANCEL_OPTION && playerMode != JOptionPane.CANCEL_OPTION) {
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
			frame.add(createScorePanel());
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
	}
	
	public static JPanel[] createButtonPanel() {
		JPanel buttonPanels[] = {new JPanel(), new JPanel()};
		rollBtn.addActionListener(controller);
		buttonPanels[0].add(rollBtn);
		bankBtn.addActionListener(controller);
		buttonPanels[1].add(bankBtn);
		return buttonPanels;		
	}
	
	public static JPanel createDicePanel() {
		// Create the panel
		JPanel dicePanel = new JPanel(new GridLayout(0, 3, 0, 0));
		
		// Initialize the dice and add to panel
		for(int i = 0; i < dice.length; i++) {
			dice[i] = new DieComponent(controller);
			dicePanel.add(new JLabel(" "));
			dicePanel.add(dice[i]);
			dicePanel.add(new JLabel(" "));
		}
		dicePanel.add(new JLabel(" "));
		dicePanel.add(new JLabel(" "));
		dicePanel.add(new JLabel(" "));
		
		JPanel btns[] = createButtonPanel();		
		dicePanel.add(btns[0]);
		dicePanel.add(new JLabel(" "));
		dicePanel.add(btns[1]);
		
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
            
            turnScores[i] = new JLabel();
            //"score" + (i + 1)
            playerPanel.add(turnScores[i]);
        }
        
        playerPanel.add(new JLabel("Total Score: "));
        playerPanel.add(gameScore);
        
        playerPanel.setPreferredSize(new Dimension(500, 300));
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        return playerPanel;
    }
    
    private static JPanel createScorePanel() {
    	JPanel scorePanel = new JPanel();
    	try {
			Image scoreGuide = ImageIO.read(new File("lib//FarkleScores.jpg"));
			scoreGuide = scoreGuide.getScaledInstance(200, 680, Image.SCALE_SMOOTH);
			JLabel scoreLabel = new JLabel(new ImageIcon(scoreGuide));
			scorePanel.add(scoreLabel);			
			
			scorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return scorePanel;
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
