package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FarkleUI extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Die[] dice = new Die[6];
	public JButton rollBtn = new JButton("Roll Dice");
	public JButton bankBtn = new JButton("Bank Score");
	public FarkleController controller;
	public JLabel[] player1Scores = new JLabel[10];
	public JLabel[] player2Scores = new JLabel[10];
	public JLabel gameScore = new JLabel("0");
	public JLabel runningScore = new JLabel();
	public ArrayList<URL> rollSounds = new ArrayList<URL>();
	public ArrayList<URL> bankSounds = new ArrayList<URL>();
	public URL bonusSound;
	public AudioInputStream audioStream = null;

	
	/**
	 * Constructor
	 * Get a reference to the controller object
	 * and fire up the UI
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
		
		// TODO: Possibly move lines 54 - 80 in to their own method
		// TODO: Reuse this method to start a new game
		
		rollSounds.add(getClass().getResource("/sounds/roll1.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll2.wav")); 
		rollSounds.add(getClass().getResource("/sounds/roll3.wav"));
		rollSounds.add(getClass().getResource("/sounds/roll4.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank1.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank2.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank3.wav"));
		bankSounds.add(getClass().getResource("/sounds/bank4.wav"));
		bonusSound = getClass().getResource("/sounds/bonus.wav");
		
		// Prompts user for game modes
		Object countOps[] = {"1 Player", "2 Players", "Cancel"};
		Object modeOps[] = {"Human Opponent", "Computer Opponent", "Cancel"};
		String player1Name = "", player2Name = "";
		int playerCount = JOptionPane.showOptionDialog(this, "Choose the number of players.", "Player Mode", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, countOps, countOps[0]);
		int playerMode = -1;
		
		if(playerCount != JOptionPane.CANCEL_OPTION) {
			player1Name = JOptionPane.showInputDialog(this, "Please enter your name.", "Player 1", JOptionPane.OK_CANCEL_OPTION);
			
			// Player chooses 2 player game mode
			if(playerCount == JOptionPane.NO_OPTION) {
				
				// Prompt player 1 for type of player they wish player 2 to be
				playerMode = JOptionPane.showOptionDialog(this, "What type of player is player 2?.", "Player Type", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, modeOps, modeOps[0]);
				
				// If they choose for player 2 to be human
				if(playerMode == JOptionPane.YES_OPTION) {
					player2Name = JOptionPane.showInputDialog(this, "Please enter your name.", "Player 2");
				}
			// Player chooses 1 player mode
			} 
			else {
				// Create the game object and pass it to the controller
				controller.setGame(new Game(GameMode.SINGLEPLAYER, controller));
			}
		}
		else
		{
			System.exit(0);
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
			frame.add(createPlayerPanel(player1Name, 1));
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
	
	/**
	 * Create a JPanel which contains
	 * two buttons and attach a Listener
	 * to each
	 * @return
	 */
	public  JPanel[] createButtonPanel() {
		JPanel buttonPanels[] = {new JPanel(), new JPanel()};
		rollBtn.addActionListener(controller);
		buttonPanels[0].add(rollBtn);
		bankBtn.addActionListener(controller);
		buttonPanels[1].add(bankBtn);
		return buttonPanels;		
	}
	
	
	/**
	 * Create a JPanel with six Dice,
	 * the running score JLabels
	 * and the Roll and Bank buttons
	 * @return
	 */
	public JPanel createDicePanel() {
		// Create the panel
		JPanel dicePanel = new JPanel(new GridLayout(0, 3, 0, 0));
		dicePanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		dicePanel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
		
		dicePanel.add(new JLabel("Turn Score:"));
		dicePanel.add(runningScore);
		dicePanel.add(new JLabel());
		// Initialize the dice and add to panel
		for(int i = 0; i < dice.length; i++) {
			dice[i] = new Die(controller);
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
	
	
	/**
	 * Create a new JPanel that contains all the JLabels
	 * necessary to represent a player with 10 turns
	 * TODO: Possibly update to accept a third parameter for the
	 * 		 number of scores
	 * @param playerName
	 * @param playerNumber
	 * @return
	 */
    private  JPanel createPlayerPanel(String playerName, int playerNumber) {
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
            
            player1Scores[i] = new JLabel();
            //"score" + (i + 1)
            playerPanel.add(player1Scores[i]);
        }
        
        playerPanel.add(new JLabel("Total Score: "));
        playerPanel.add(gameScore);
        
        playerPanel.setPreferredSize(new Dimension(500, 300));
        playerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        
        return playerPanel;
    }
    
    /**
     * Create a new JPanel that displays
     * the score guide image
     * @return JLabel
     */
    private JPanel createScorePanel() {
    	JPanel scorePanel = new JPanel();
    	try {
    		
			Image scoreGuide = ImageIO.read(getClass().getResource("/images/FarkleScores.jpg"));
			scoreGuide = scoreGuide.getScaledInstance(200, 680, Image.SCALE_SMOOTH);
			JLabel scoreLabel = new JLabel(new ImageIcon(scoreGuide));
			scorePanel.add(scoreLabel);			
			
			scorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
    	
    	return scorePanel;
    }

	public JButton getRollBtn() {
		return rollBtn;
	}
	
	public JButton getBankBtn() {
		return bankBtn;
	}

	public JLabel[] getScores() {
		return player1Scores;
	}

	public void setScores(JLabel[] scores) {
		this.player1Scores = scores;
	}

	public JLabel getGameScore() {
		return gameScore;
	}

	public void setGameScore(JLabel gameScore) {
		this.gameScore = gameScore;
	}

	public JLabel getRunningScore() {
		return runningScore;
	}


	public void rollDice() {
		// Test farkle roll
		/*dice[0].setValue(1);
		dice[1].setValue(1);
		dice[2].setValue(1);
		dice[3].setValue(4);
		dice[4].setValue(4);
		dice[5].setValue(6);*/
		
		// Roll all the dice
		for(Die d : dice) {
			d.roll();
		}
	}
	
	/**
	 * TODO: If this isn't called anywhere
	 * then take it out
	 * @return
	 */
	@Deprecated
	public FarkleUI getUI() {
		return this;
	}
	
	
	/**
	 * Get the dice with the specified DieStates
	 * @param dieState One or more DieState types
	 * @return the dice with the specified DieStates
	 */
	public ArrayList<Die> getDice(DieState...dieState) {
		ArrayList<DieState> dieStates = new ArrayList<DieState>();
		for(DieState state : dieState) {
			dieStates.add(state);
		}
		ArrayList<Die> retDice = new ArrayList<Die>();
		for(Die d : dice) {
			if(dieStates.contains(d.getState())) {
				retDice.add(d);
			}
		}
		return retDice;
	}
	
	
	/**
	 * Get the values from the dice with the
	 * specified DieStates
	 * @param dieState One or more DieState types
	 * @return List<Integer> values from the dice with the
	 * specified DieStates
	 */
	public List<Integer> getDieValues(DieState...dieState) {
		List<Integer> retVals = new ArrayList<Integer>();
		ArrayList<Die> diceToCheck = getDice(dieState);
		for(Die d : diceToCheck) {
			retVals.add(d.getValue());
		}
		return retVals;
	}
	
	/**
	 * Set all six dice back to 0
	 * and their states back to UNHELD
	 */
	public void resetDice() {
		for(Die d : dice) {
			d.setValue(0);
			d.setState(DieState.UNHELD);
		}
	}
	
	public void resetScores() {
		for(JLabel score : player1Scores) {
			score.setText("0");
		}
	}
	
	/**
	 * Update all dice with a HELD state
	 * to a SCORED state
	 */
	public void lockScoredDice() {
		for(Die d : dice) {
			if(d.getState() == DieState.HELD) {
				d.setState(DieState.SCORED);
			}
		}
	}
	
	/**
	 * Update the turn label for the specified
	 * player with the specified score
	 * @param int player
	 * @param int turn
	 * @param int score
	 */
	public void setTurnScore(int player, int turn, int score) {
		JLabel scoreLbls[] = (player == 0) ? player1Scores : player2Scores;
		scoreLbls[turn-1].setText(String.valueOf(score));
	}
	
	/**
	 * Update the running score label
	 * with the specified score
	 * @param int score
	 */
	public void setRunningScore(int score) {
		runningScore.setText(String.valueOf(score));
	}
	
	
	/**
	 * Randomly return one of the 
	 * four dice roll sounds
	 * @return
	 */
	public void playRollSound() {
		try {
			audioStream = AudioSystem.getAudioInputStream(rollSounds.get(new Random().nextInt(3)));
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void playBonusSound() {
		try {
			audioStream = AudioSystem.getAudioInputStream(bonusSound);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playBankSound() {
		try {
			audioStream = AudioSystem.getAudioInputStream(bankSounds.get(new Random().nextInt(3)));
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
