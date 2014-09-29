package com.lotsofun.farkle;

import java.util.HashMap;

public class Player {

	public HashMap<Integer, Integer> rollScore = new HashMap<Integer, Integer>();
	public int playerNumber, turnNumber, rollNumber, gameScore = 0;
	public boolean currentPlayer;
	public int[] turnScores;
	public PlayerType type;
	
	/**
	 * Constructor:
	 * Takes player number and initializes
	 * the turnScores
	 * @param playerNumber
	 */
	public Player(int playerNumber)
	{
		type = PlayerType.USER;
		turnScores = new int[10];
	}
	
	/**
	 * Puts the score in to
	 * the map for the given roll
	 * 
	 * TODO: Idk if we'll actually need this
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
			turnScores[turnNumber - 1] = 0;	
		}
		else {
			turnScores[turnNumber - 1] = getRollScores();
		}
		
		gameScore += turnScores [turnNumber -1];
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

	public int[] getTurnScores() {
		return turnScores;
	}

	public void setTurnScores(int[] turnScores) {
		this.turnScores = turnScores;
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
}
