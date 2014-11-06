package com.lotsofun.farkle;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

	private HashMap<Integer, Integer> rollScore = new HashMap<Integer, Integer>();
	private int playerNumber, turnNumber = 1, rollNumber, gameScore = 0;
	private boolean currentPlayer, isComputer;
	private ArrayList<Integer> turnScores;
	private PlayerType type;
	private String playerName;
	
	/**
	 * Constructor:
	 * Takes player number and initializes
	 * the turnScores
	 * @param playerNumber
	 */
	public Player(int playerNumber)
	{
		this.playerNumber = playerNumber;
		type = PlayerType.USER;
		turnScores = new ArrayList<Integer>();
	}
	
	/**
	 * Puts the score in to
	 * the map for the given roll
	 * @param score
	 */
	public void scoreRoll(int score) {
		rollScore.put(rollNumber, score);
	}
	
	/**
	 * Get the sum of all the roll
	 * scores 
	 * @return
	 */
	public int getRollScores() {
		int currentTurnScore = 0;
		for(int i : this.getRollScore().values()) {
			currentTurnScore += i;
		}
		return currentTurnScore;
	}

	/**
	 * Sets the appropriate turn's score,
	 * increments turnNumber, resets
	 * rollNumber, currentPlayer, and rollSCore
	 * @param didFarkle
	 */
	public void endTurn(boolean didFarkle)
	{
		if (didFarkle) {
			turnScores.add(0);	
		}
		else {
			turnScores.add(getRollScores());
		}
		
		gameScore += turnScores.get(turnNumber - 1);
		turnNumber++;
		rollNumber = 0;
		currentPlayer = false;
		rollScore.clear();
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}
	
	public void nextTurn() {
		this.turnNumber++;
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public int getGameScore() {
		return gameScore;
	}

	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	public boolean isCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(boolean currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public ArrayList<Integer> getTurnScores() {
		return turnScores;
	}

	public void setTurnScores(ArrayList<Integer> turnScores) {
		this.turnScores = turnScores;
	}
	
	public void resetTurnScores() {
		for(int score : turnScores) {
			score = 0;
		}
	}

	public PlayerType getType() {
		return type;
	}

	public void setType(PlayerType type) {
		this.type = type;
	}

	public HashMap<Integer, Integer> getRollScore() {
		return rollScore;
	}

	public void setRollScore(HashMap<Integer, Integer> rollScore) {
		this.rollScore = rollScore;
	}
	
	public boolean getIsComputer ()
	{
		return isComputer;
	}
	
	public void setIsComputer (boolean isComputer)
	{
		this.isComputer = isComputer;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}	
}
