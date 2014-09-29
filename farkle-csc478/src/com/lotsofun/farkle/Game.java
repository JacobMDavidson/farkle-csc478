package com.lotsofun.farkle;

import java.util.List;

public class Game {


	public int numberOfPlayers = 1, currentPlayer = 0;
	public GameMode gameMode;
	public GameState gameState;
	public Die[] dice;
	public Player[] players;
	public FarkleController controller;

	
	/**
	 * Constructor:
	 * Creates a new Game object, passes
	 * the GameMode and a reference
	 * to the controller
	 * @param GameMode gMode
	 * @param FarkleController controller
	 */
	public Game(GameMode gMode, FarkleController controller) {
		this.gameMode = gMode;
		this.controller = controller;
		if(gameMode == GameMode.SINGLEPLAYER) {
			numberOfPlayers = 1;
			players = new Player[numberOfPlayers];
			for (int i = 0; i < numberOfPlayers; i++)
			{
				players[i] = new Player(i);
				players[i].currentPlayer = true;
				players[i].turnNumber = 1;
			}
		}
		this.gameState = GameState.NEW_TURN;
	}
	
		
	/**
	 * Jake's shiny new scoring method with a few
	 * modifications to accommodate a Straight
	 * and a Full House 
	 * 
	 * TODO: Jake, would you mind commenting this?
	 * 
	 * @param roll
	 * @return
	 */
	public int calculateScore(List<Integer> roll)
	{
		int calculatedScore = 0;
		
		// Flag to check for a straight
		boolean isStraight = true;
		
		// Flag to check for a full house
		boolean isFullHouse = true;
		
		// This array stores the count of each die in the roll. Index 0 represents a die
		// with value 1, etc.
		int[] countedDie = new int[6];
		
		// Determine the value of each die in the roll and add to the total count
		// for each die value in countedDie
		for (int value : roll)
		{
			// decrement value to get the proper die index
			countedDie[--value]++;
		}
		
		// Calculate the score for the list of die
		for(int i = 0; i < countedDie.length; i++)
		{
			int currentCount = countedDie[i];
			
			if(currentCount != 1)
				isStraight = false;
			
			if(currentCount != 2 && currentCount != 0)
				isFullHouse = false;
			
			
			
			switch(i)
			{
				case 0:
					if(currentCount > 0 && currentCount < 3)
					{
						calculatedScore += currentCount * 100;
					}
					else if(currentCount >= 3)
					{
						calculatedScore += 1000 * Math.pow(2 , (currentCount - 3));
					}
					break;
				case 4:
					if(currentCount > 0 && currentCount < 3)
					{
						calculatedScore += currentCount * 50;
					}
					else if(currentCount >= 3)
					{
						calculatedScore += 500 * Math.pow(2 , (currentCount - 3));
					}
					break;
				default:
					if(currentCount >= 3)
					{
						calculatedScore += (i + 1) * 100 * Math.pow(2 , (currentCount - 3));
					}
					else if(isStraight == true && i == 5 && roll.size() == 6)
					{
						calculatedScore = 1500;
					}
					else if(isFullHouse == true && i == 5 && roll.size() == 6)
					{
						calculatedScore = 750;
					}
					
			}
		}
		return calculatedScore;
	}
	
	
	/**
	 * Set this turn's score to 0
	 * and end the current player's turn
	 */
	public void farkle ()
	{
		controller.setTurnScore(currentPlayer, players[currentPlayer].getTurnNumber(), 0);
		players[currentPlayer].endTurn(true);
		currentPlayer = getNextPlayer();
		processHold(0);
	}
	
	/**
	 * Set this turn's score to the
	 * total of all rolls and
	 * end the current player's turn
	 * @return
	 */
	public int bank()
	{
		controller.setTurnScore(currentPlayer, getCurrentPlayer().getTurnNumber(), getRollScores());
		getCurrentPlayer().endTurn(false);
		return getCurrentPlayer().getGameScore();
	}

	
	/**
	 * Get the integer index of the next player
	 * @return
	 */
	public int getNextPlayer()
	{
		// If gameMode is Multiplayer, we need 1 or 0
		if(gameMode == GameMode.MULTIPLAYER) {
			return (currentPlayer == 0) ? 1 : 0;
		} else {
			//gameMode is Singleplayer so it's always 0
			return 0;
		}
	}
	
	/**
	 * Add the provided score to the 
	 * map of roll scores for the current turn
	 * @param score
	 */
	public void processHold(int score) {
		Player player = getCurrentPlayer();
		player.scoreRoll(score);
	}
	
	/**
	 * Increment the current roll
	 * number of this player
	 * 
	 * TODO: Could probably be more elegant
	 */
	public void processRoll() {
		Player player = getCurrentPlayer();
		player.setRollNumber(player.getRollNumber() + 1);
	}
	
	/**
	 * Increment the current turn 
	 * value for the current player
	 */
	public void processTurn() {
		Player player = getCurrentPlayer();
		player.nextTurn();
	}
	
	/**
	 * Get the total score of all
	 * the rolls for this turn
	 * @return
	 */
	public int getRollScores() {
		Player player = getCurrentPlayer();
		return player.getRollScores();
	}

	/*A Crap-ton of get/sets
	 * TODO: Do we need all these variables?
	 * */
	
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Die[] getDice() {
		return dice;
	}

	public void setDice(Die[] dice) {
		this.dice = dice;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}
}
