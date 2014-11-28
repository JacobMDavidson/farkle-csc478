package com.lotsofun.farkle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Player class creates a player object that tracks the roll scores, game score, 
 * player number, player type, player name, turn number, roll number, turn scores, and
 * game score for this player.
 * @author Brant Mullinix
 * @version 3.0.0
 */
public class Player {

	/** The hash map of roll scores for the player */
	private HashMap<Integer, Integer> rollScore = new HashMap<Integer, Integer>();
	
	/** This player's number */
	private int playerNumber;
	
	/** The turn number for this player */
	private int turnNumber = 1;
	
	/** The roll number for this player */
	private int rollNumber;
	
	/** The game score for this player */
	private int gameScore = 0;
	
	/** The list of turn scores for this player */
	private ArrayList<Integer> turnScores;
	
	/** The PlayerType for this player */
	private PlayerType type;
	
	/** The name for this player */
	private String playerName;
	
	/**
	 * Constructor: Takes player number and initializes
	 * the turnScores
	 * @param playerNumber The player number for this player.
	 */
	public Player(int playerNumber)
	{
		// Set this player's number
		this.playerNumber = playerNumber;
		
		// Initialize the type to PlayerType.USER
		type = PlayerType.USER;
		
		// Initialize the turn scores
		turnScores = new ArrayList<Integer>();
	}
	
	/**
	 * Puts the score in to the map for the given roll
	 * @param score The integer score to enter into the rollScore map
	 */
	public void scoreRoll(int score) {
		rollScore.put(rollNumber, score);
	}
	
	/**
	 * Get the sum of all the roll
	 * scores 
	 * @return integer sum of all the roll scores
	 */
	public int getRollScores() {
		
		// Variable used to sum the roll scores
		int currentTurnScore = 0;
		
		// Sum the roll scores, and return the sum
		for(int i : this.getRollScore().values()) {
			currentTurnScore += i;
		}
		return currentTurnScore;
	}

	/**
	 * Sets the appropriate turn's score, increments turnNumber, resets
	 * rollNumber, currentPlayer, and rollSCore
	 * @param didFarkle Set to true if the end of the turn was caused 
	 * by a Farkle
	 */
	public void endTurn(boolean didFarkle)
	{
		// If the turn ended on a Farkle, set add 0 to the turn scores list
		if (didFarkle) {
			turnScores.add(0);	
		}
		
		// Else, add the sum of the roll scores to the list
		else {
			turnScores.add(getRollScores());
		}
		
		// Add the turn score to this player's game score
		gameScore += turnScores.get(turnNumber - 1);
		
		// Increment the turn number
		turnNumber++;
		
		// Reset the roll number to 0
		rollNumber = 0;
		
		// Clear the map of roll scores
		rollScore.clear();
	}

	/**
	 * Get this player's player number
	 * @return integer representing the player number
	 */
	public int getPlayerNumber() {
		return playerNumber;
	}

	/**
	 * Get this player's current turn number
	 * @return integer representing the current turn number
	 */
	public int getTurnNumber() {
		return turnNumber;
	}

	/**
	 * Set the turn number for this player
	 * @param turnNumber integer turn number to set
	 */
	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}
	
	/**
	 * Get this player's current roll number
	 * @return int representing the current roll number
	 */
	public int getRollNumber() {
		return rollNumber;
	}

	/**
	 * Set this player's roll number
	 * @param rollNumber integer roll number to set
	 */
	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	/**
	 * Get this player's total game score for the current game
	 * @return integer representing the current total game score
	 */
	public int getGameScore() {
		return gameScore;
	}

	/**
	 * Set the game score for the current player
	 * @param gameScore integer game score to set
	 */
	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	/**
	 * Get the list of turn scores for this player
	 * @return ArrayList<Integer> of the turn scores
	 */
	public ArrayList<Integer> getTurnScores() {
		return turnScores;
	}

	/**
	 * Set the list of turn scores for this player
	 * @param turnScores ArrayList<Integer> of turn scores to set
	 */
	public void setTurnScores(ArrayList<Integer> turnScores) {
		this.turnScores = turnScores;
	}
	
	/**
	 * Clear the list of turn scores for this player.
	 */
	public void resetTurnScores() {
		this.turnScores.clear();
	}

	/**
	 * Get the player type of this player
	 * @return PlayerType for this player
	 */
	public PlayerType getType() {
		return type;
	}

	/**
	 * Set the player type for this player
	 * @param type PlayerType.USER or PlayerType.COMPUTER
	 */
	public void setType(PlayerType type) {
		this.type = type;
	}

	/**
	 * Get the map of roll scores for this player
	 * @return HashMap<Integer, Integer> of roll scores for this player
	 */
	public HashMap<Integer, Integer> getRollScore() {
		return rollScore;
	}

	/**
	 * Set the map of roll scores for this player
	 * @param rollScore roll scores to set
	 */
	public void setRollScore(HashMap<Integer, Integer> rollScore) {
		this.rollScore = rollScore;
	}
	
	/**
	 * Clear the roll scores for this player
	 */
	public void resetRollScores() {
		this.rollScore.clear();
	}
	
	/**
	 * Get the name for this player
	 * @return String player name
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Set the name for this player
	 * @param playerName name to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}	
}
