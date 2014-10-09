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
				for (int j = 0; j <= 9; j++)
				{
					players[i].turnScores[j] = 0;
				}
				players[i].gameScore = 0;
			}
		}
		this.gameState = GameState.NEW_TURN;
	}
	
		
	/**
	 * Calculates the score of a list of integers per the rules of this
	 * version of Farkle. 
	 * 
	 * @param roll a List of Integers representing the roll of dice for which
	 * the Farkle score will be calculated
	 * @return The calculated score for the list of dice
	 */
	public int calculateScore(List<Integer> roll, boolean checkingHeldCards)
	{
		boolean oneOrTwoStrictDie = false;
		int numberOfDieHeld = 0;
		numberOfDieHeld = roll.size();
		
		// Initialize  the calculated score and set it to 0
		int calculatedScore = 0;
		
		// Flag to check for a straight
		boolean isStraight = true;
		
		// Flags to check for three pair
		boolean isThreePair = true;
		int countOfPairs = 0;
		
		// This array stores the count of each die in the roll. Index 0 represents a die
		// with value 1, etc.
		int[] countedDie = new int[6];
		
		// Determine the value of each die in the roll and add to the total count
		// for each die value in countedDie
		for (int value : roll)
		{
			// decrement value to get the proper die index and increment the count for
			// that value of die
			countedDie[--value]++;
		}
		
		// Calculate the score for the list of dice by looping through the dice count
		// for each value of die
		for(int i = 0; i < countedDie.length; i++)
		{
			// Get the count for the current die value
			int currentCount = countedDie[i];
			
			if (i >= 1 && i != 4) //If it is 2,3,4,6 aka strict dice
			{
				if ((currentCount == 1) || (currentCount == 2)) //Die appears 1 or 2 times
				{
					oneOrTwoStrictDie = true;
				}
			}
			
			// If the current count does not equal one, it can be deduced that
			// this roll does not include a straight.
			if(currentCount != 1)
				isStraight = false;
			
			// If the current count does not equal 2 and does not equal 0, it can
			// be deduced that this roll does not include three pair
			if(currentCount != 2 && currentCount != 0)
				isThreePair = false;
			if (currentCount == 2)
			{
				countOfPairs++;
			}
			
			
			
			/**********************************************
			 * 6.1.0: Each 1 rolled is worth 100 points
			 * ********************************************
			 * 6.2.0: Each 5 rolled is worth 50 points
			 * ********************************************
			 * 6.3.0: Three 1’s are worth 1000 points
			 * ********************************************
			 * 6.4.0: Three of a kind of any value other 
			 * than 1 is worth 100 times the value of the 
			 * die (e.g. three 4’s is worth 400 points).
			 * ********************************************
			 * 6.4.5: Four, five, or six of a kind is scored
			 * by doubling the three of a kind value for 
			 * every additional matching die (e.g. five 3’s 
			 * would be scored as 300 X 2 X 2 = 1200.
			 * ********************************************
			 * 6.6.0: Three doubles (e.g. 1-1-2-2-3-3) are
			 *  worth 750 points.
			 * ********************************************
			 * 6.7.0: A straight (e.g. 1-2-3-4-5-6), which 
			 * can only be achieved when all 6 dice are 
			 * rolled, is worth 1500 points.
			 * ********************************************/
			
			
			// Add to the score based on the current die value
			switch(i)
			{
				// If the current die value is 1 (index 0), add 100 to the score 
				// for every 1. If there are three or more, add 1000 * 2 ^ (count - 3)
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
				// If the current die value is 5 (index 4), add 50 to the score 
				// for every 5. If there are three or more, add 500 * 2 ^ (count - 3)
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
					// If the count of the current die is greater than 3, add the current
					// die value * 100 * 2 ^ (count - 3)
					if(currentCount >= 3)
					{
						calculatedScore += (i + 1) * 100 * Math.pow(2 , (currentCount - 3));
					}
					
					// Else if the is straight flag is true, and the die value is 6 (index 5)
					// and 6 dice were rolled, set the calculated score to 1500
					else if(isStraight == true && i == 5 && roll.size() == 6)
					{
						calculatedScore = 1500;
					}
					
					// Else if the is three pair flag is true, and the die value is 6 (index 5)
					// and 6 dice were rolled, set the calculated score to 750
					else if(isThreePair == true && i == 5 && roll.size() == 6)
					{
						calculatedScore = 750;
					}
					
			}
		}
		
		if (checkingHeldCards)
		{
			if (countOfPairs != 3) //Stops from returning true if only two pairs are held
			{
				isThreePair=false;
			}
			if (oneOrTwoStrictDie && !isStraight && !isThreePair) //If a 2,3,4, or 6 occurs once or twice and it is not three pair or a straight
			{
				calculatedScore = 0;
			}
			if (numberOfDieHeld == 0)
			{
				calculatedScore = 0;
			}			
		}
		
		// Return the calculated score
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
