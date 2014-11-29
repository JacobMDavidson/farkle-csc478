package com.lotsofun.farkle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * The Game class serves as the Model for the Farkle application. This class is responsible for tracking
 * each player, setting high scores, storing the game mode, calculating all scoring, and determining
 * game outcomes.
 * @author Brant Mullinix
 * @version 3.0.0
 */
public class Game {

	/** Stores the high score */
	private Preferences prefs;
	
	/** Tracks the current player during the game */
	private int currentPlayer = 1;
	
	/** Stores the game mode (single player or two player) */
	private GameMode gameMode;
	
	/** References to the players in a game */
	private Player[] players = new Player[2];
	
	/** Reference to the controller for the game */
	private FarkleController controller;
	
	/** Used to determine if a player is given a bonus turn */
	private boolean bonusTurn = false;

	/**
	 * Constructor: Creates a new Game object, passes the GameMode and a
	 * reference to the controller, and retrieves and sets the high score
	 * 
	 * @param gMode The selected game mode for the game
	 * @param controller The controller for the game that links the model to 
	 * the view
	 */
	public Game(GameMode gMode, FarkleController controller) {
		
		// Define the preferences file
		prefs = Preferences.userRoot().node(this.getClass().getName());
		
		// Set the game mode and controller fields
		this.gameMode = gMode;
		this.controller = controller;

		// Player 1 is instantiated regardless of the game mode
		players[0] = new Player(1);

		// If the game mode is set to two player, instantiate player 2.
		if (gameMode == GameMode.MULTIPLAYER) {
			players[1] = new Player(2);
		}
		
		// Set the high score label
		controller.setUIHighScore(getHighScore());

		// Initialize the turns
		resetGame();
	}

	/**
	 * Calculate the score of a list of integers representing a roll of dice per the 
	 * rules of this version of Farkle. This method calculates the scores in accordance 
	 * with the following requirements: <br /><br />
	 * 6.0.0 - Scoring <br />
	 * 6.1.0 - Each 1 rolled is worth 100 points<br />
	 * 6.2.0 - Each 5 rolled is worth 50 points<br />
	 * 6.3.0 - Three 1's are worth 1000 points<br />
	 * 6.4.0 - Three of a kind of any value other than 1 is worth 100 times the value 
	 * of the die (e.g. three 4's is worth 400 points).<br />
	 * 6.5.0 - Four, five, or six of a kind is scored by doubling the three of a kind 
	 * value for every additional matching die (e.g. five 3's would be scored as 
	 * 300 X 2 X 2 = 1200.<br />
	 * 6.6.0 - Three distinct doubles (e.g. 1-1-2-2-3-3) are worth 750 points. This 
	 * scoring rule does not include the condition of rolling four of a kind along with 
	 * a pair (e.g. 2-2-2-2-3-3 does not satisfy the three distinct doubles scoring rule).
	 * <br />
	 * 6.7.0 - A straight (e.g. 1-2-3-4-5-6), which can only be achieved when all 6 dice 
	 * are rolled, is worth 1500 points.
	 * 
	 * @param roll A List of Integers representing the roll of dice for which the
	 * Farkle score will be calculated.
	 * @param checkHeldDice A flag that is set to true when the calculated score 
	 * should be zero if any of the dice do not contribute to the score
	 * @return int - The calculated score for the list of dice
	 */
	public int calculateScore(List<Integer> roll, boolean checkHeldDice) {
		
		// Initialize the calculated score and set it to 0
		Integer calculatedScore;
		
		// If roll is not null, continue calculating the score
		if (roll != null) {
			
			// Set calculatedScore to 0
			calculatedScore = 0;

			// Calculate the roll score if roll is not empty
			if (!roll.isEmpty()) {
				
				// Test to ensure all values are within the range of 1 to 6 (inclusive)
				boolean incorrectValuePresent = false;
				for (Integer value : roll) {
					if (!((value == 1) || (value == 2) || (value == 3)
							|| (value == 4) || (value == 5) || (value == 6))) {
						incorrectValuePresent = true;
					}
				}

				/* If all values are within the accepted range of 1 to 6 (inclusive) 
				 * calculate the score
				 */
				if (!incorrectValuePresent) {
					
					// Flag to check for one or two dice
					boolean oneOrTwoStrictDie = false;
					
					// Determine the number of dice held
					int numberOfDieHeld = 0;
					numberOfDieHeld = roll.size();

					// Flag to check for a straight
					boolean isStraight = true;

					// Flags to check for three pair
					boolean isThreePair = true;
					int countOfPairs = 0;

					/* This array stores the count of each die in the roll.
					 * Index 0 represents a die with value 1, etc.
					 */
					int[] countedDie = new int[6];

					/* Determine the value of each die in the roll and add to
					 * the total count for each die value in countedDie
					 */
					for (int value : roll) {
						
						/* decrement value to get the proper die index and
						 * increment the count for that value of die
						 */
						countedDie[--value]++;
					}

					/* Calculate the score for the list of dice by looping
					 * through the dice count for each value of die
					 */
					for (int i = 0; i < countedDie.length; i++) {
						
						// Get the count for the current die value
						Integer currentCount = countedDie[i];
						
						// If the die is 2, 3, 4, or 6 (aka strict dice)
						if (i >= 1 && i != 4)
						{
							// If the die appears 1 or 2 times set the flag to true
							if ((currentCount == 1) || (currentCount == 2)) 
							{
								oneOrTwoStrictDie = true;
							}
						}

						/* If the current count does not equal one, it can be
						 * deduced that this roll does not include a straight.
						 */ 
						if (currentCount != 1)
							isStraight = false;

						/* If the current count does not equal 2 and does not equal 0,
						 * it can be deduced that this roll does not include three pair
						 */
						if (currentCount != 2 && currentCount != 0)
							isThreePair = false;
						
						// Keep track of the total number of pairs
						if (currentCount == 2) {
							countOfPairs++;
						}

						// Temporary variable used for power calculations
						Double temp;
						
						// Add to the score based on the current die value
						switch (i) {
						
						/* If the current die value is 1 (index 0), add 100 to the score
						 * for every 1. If there are three or more, add 1000 * 2 ^ (count - 3)
						 */
						case 0:
							if (currentCount > 0 && currentCount < 3) {
								calculatedScore = calculatedScore
										+ currentCount * 100;
							} else if (currentCount >= 3) {
								temp = (Math.pow(2, (currentCount - 3)));
								calculatedScore = calculatedScore + 1000
										* temp.intValue();
							}
							break;
							
						/* If the current die value is 5 (index 4), add 50 to the score
						 * for every 5. If there are three or more, add 500 * 2 ^ (count - 3)
						 */
						case 4:
							if (currentCount > 0 && currentCount < 3) {
								calculatedScore += currentCount * 50;
							} else if (currentCount >= 3) {
								temp = Math.pow(2, (currentCount - 3));
								calculatedScore = calculatedScore + 500
										* temp.intValue();
							}
							break;
							
						// The die value is 2, 3, 4, or 6. 	
						default:
							/* If the count of the current die is greater than  3, 
							 * add the current die value * 100 * 2 ^ (count - 3)
							 */
							if (currentCount >= 3) {
								temp = Math.pow(2, (currentCount - 3));
								calculatedScore += (i + 1) * 100
										* temp.intValue();
							}

							/* Else if the is straight flag is true, and the die
							 * value is 6 (index 5) and 6 dice were rolled, it is 
							 * confirmed that a straight was rolled, set the 
							 * calculated score to 1500
							 */
							else if (isStraight == true && i == 5
									&& roll.size() == 6) {
								calculatedScore = 1500;
							}

							/* Else if the is three pair flag is true, and the die
							 * value is 6 (index 5) and 6 dice were rolled, it is
							 * confirmed that 3 pairs was rolled, set the calculated 
							 * score to 750
							 */ 
							else if (isThreePair == true && i == 5
									&& roll.size() == 6) {
								calculatedScore = 750;
							}

						}

					}
					
					/* If the checkHeldDice flag is true, we need to return 0 if any
					 * of the dice do not contribute to the score.
					 */
					if (checkHeldDice) {
						
						/* if the number of counted pairs is less than 3, set the 
						 * isThreePair flag to false.
						 */
						if (countOfPairs != 3) 
						{
							isThreePair = false;
						}
						
						/* If a 2, 3, 4, or 6 occurs once or twice and the roll does
						 * not contain 3 pair or a straight, set calculatedScore to 0
						 */
						if (oneOrTwoStrictDie && !isStraight && !isThreePair) 
						{
							calculatedScore = 0;
						}
						
						// If no dice are held, set calculatedScpre to 0.
						if (numberOfDieHeld == 0) {
							calculatedScore = 0;
						}

					}
					
				/* Else, at least one die value was outside of the accepted range, set
				 * calculatedScore to 0
				 */
				} else {
					calculatedScore = 0;
				}
			}
		
		// The list of integers representing the roll was null. set calculatedScore to 0
		} else {
			calculatedScore = 0;
		}
		
		// Return the calculated score
		return calculatedScore;
	}
	
	/**
	 * Calculate the highest possible score for a given roll of the dice, and details the
	 * dice that must be selected to achieve that score.
	 * @param dice The list of integers representing the roll of dice
	 * @return Object[] array - the first index holds the calculated highest possible
	 * score for a given roll, and the second index is a list of dice that must be selected
	 * to achieve that score.
	 */
	public Object[] calculateHighestScore(List<Integer> dice) {
		
		// The calculated highest score
		int highestScorePossible = 0;
		
		// The Object[] array that will be returned
		Object[] highestScore = new Object[2];
		
		// The list of dice that must be selected to produce the highest score
		List<Integer> highestScoringCombination = new ArrayList<Integer>();
		
		/* If the dice list is not empty or null, calculate the score for all
		 * permutations of selected dice, and determine that which results in
		 * the highest score
		 */
		if ((dice != null) && dice.size() > 0) {
			
			// Determine the number of permutations of selected dice
			Integer magicNumber = (int) Math.pow(2, dice.size()) - 1;

			// Calculate the score for each permutation
			for (int i = 1; i <= magicNumber; i++) {
				
				// The list of dice in this permutation
				List<Integer> tempList = new ArrayList<Integer>();
				
				// Compute the binary number for this permutation
				char[] binaryNumber = new char[dice.size()];
				Arrays.fill(binaryNumber, '0');
				char[] ch = Integer.toBinaryString(i).toCharArray();
				for (int c = 0; c < ch.length; c++) {
					binaryNumber[(ch.length) - (c + 1)] = ch[c];
				}
				
				// Add the appropriate die from the roll for each 1 in binaryNumber
				for (int j = 0; j < binaryNumber.length; j++) {
					if (binaryNumber[j] == '1') {
						tempList.add(dice.get(j));
					}
				}
				
				// Calculate the score for this permutation
				int tempScore = calculateScore(tempList, false);
				
				// Compare tempScore to the previously set highest calculated score
				if (tempScore > highestScorePossible) {
					highestScorePossible = tempScore;
					highestScoringCombination.clear();
					highestScoringCombination = tempList;
				}
			}
		}
		
		/* Add the highest calculated score and that scores combination to the
		 * highest score array, and return it
		 */ 
		highestScore[0] = highestScorePossible;
		highestScore[1] = highestScoringCombination;
		return highestScore;
	}
	

	/**
	 * Set this turn's score to 0 and end the current player's turn
	 */
	public void farkle() {
		
		// Set the turn score to 0 for the current player
		controller.setTurnScore(currentPlayer, getTurnNumberForCurrentPlayer(),
				0);
		
		// End the current player's turn
		getCurrentPlayer().endTurn(true);
		
		// Get the next player
		currentPlayer = getNextPlayer();
		
		// Set the next player's roll score to 0
		processHold(0);
	}

	/**
	 * Set this turn's score to the total of all rolls and end the current
	 * player's turn.
	 * @return int - Game score for the current player when this method was called
	 */
	public int bank() {
		
		// The game score for the current player
		int retVal = 0;
		
		// Set the turn score for the current player in the controller
		controller.setTurnScore(currentPlayer, getTurnNumberForCurrentPlayer(),
				getRollScores());
		
		// End the current player's turn
		getCurrentPlayer().endTurn(false);
		
		// Retrieve the game score for the current player
		retVal = getCurrentPlayer().getGameScore();
		
		// Set the new current player
		currentPlayer = getNextPlayer();
		
		// Return the game score
		return retVal;
	}

	/**
	 * Get the integer index of the next player in the players array
	 * 
	 * @return int - index of the next player
	 */
	public int getNextPlayer() {
		
		/* If gameMode is Multiplayer, return the index of the player that is 
		 * not current
		 */
		if (gameMode == GameMode.MULTIPLAYER) {
			return (currentPlayer == 1) ? 2 : 1;
		} else {
			
			// gameMode is Singleplayer return the index for player 1
			return 1;
		}
	}

	/**
	 * Add the provided score to the map of roll scores for the current turn
	 * 
	 * @param score - The roll score to add
	 */
	public void processHold(int score) {
		
		Player player = getCurrentPlayer();
		player.scoreRoll(score);
	}

	/**
	 * Increment the current roll number of the current player
	 */
	public void processRoll() {
		
		getCurrentPlayer()
				.setRollNumber(getCurrentPlayer().getRollNumber() + 1);
	}

	/**
	 * Get the total score of all the rolls for this turn
	 * 
	 * @return int the total score of all rolls for this turn
	 */
	public int getRollScores() {
		return getCurrentPlayer().getRollScores();
	}

	/**
	 * Get the current player
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return players[currentPlayer - 1];
	}

	/**
	 * Set the current player
	 * @param currentPlayer The index of the player to set as current (1 or 2)
	 */
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Get the game mode of the current game
	 * @return GameMode.SINGLEPLAYER or GameMode.MULTIPLAYER
	 */
	public GameMode getGameMode() {
		return gameMode;
	}

	/**
	 * Get the array of players for the current game
	 * @return Player[] array of players for the current game
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * Get the high score
	 * @return int high score saved in Preferences
	 */
	public int getHighScore() {
		return prefs.getInt("HighScore", 0);
	}

	/**
	 * Set the high score and save it in preferences
	 * @param highScore integer to save as the high score in preferences
	 */
	public void setHighScore(int highScore) {
		prefs.putInt("HighScore", highScore);
	}
	
	/**
	 * Reset the stored high score to 0.
	 */
	public void resetHighScore() {
		prefs.putInt("HighScore", 0);
	}

	/**
	 * Set all the turn scores, roll scores, and game scores for all players in the 
	 * Game object to 0, and set the first player as the current player
	 */
	public void resetGame() {
		
		// For each player, reset the roll scores, turn scores, and game scores
		for (Player player : players) {
			if (null != player) {
				player.setTurnNumber(1);
				player.resetTurnScores();
				player.resetRollScores();
				player.setGameScore(0);
				player.setRollNumber(0);
			}
		}
		
		// Set player 1 as the current player
		this.setCurrentPlayer(1);
	}

	/**
	 * Set the name of a chosen player
	 * 
	 * @param playerNumber Player's position - NOT AN INDEX (1 or 2)
	 * @param name The string used to set the player's name
	 */
	public void setPlayerName(int playerNumber, String name) {
		if (null != name && playerNumber >= 1 && playerNumber <= 2) {
			this.players[playerNumber - 1].setPlayerName(name);
		}
	}

	/**
	 * Get the turn number for the current player
	 * @return int - Turn number for the current player
	 */
	public int getTurnNumberForCurrentPlayer() {
		return this.getCurrentPlayer().getTurnNumber();
	}

	/**
	 * Get the game score for the current player
	 * @return int - Total game score for the current player
	 */
	public int getGameScoreForCurrentPlayer() {
		return this.getCurrentPlayer().getGameScore();
	}

	/**
	 * Get the player number for the current player
	 * @return int - Player number for the current player
	 */
	public int getPlayerNumberForCurrentPlayer() {
		return this.getCurrentPlayer().getPlayerNumber();
	}

	/**
	 * Get the game score for a chosen player
	 * @param playerNumber The player for which the game score is sought
	 * @return int - Total game score for the chosen player
	 */
	public int getGameScoreForPlayer(int playerNumber) {
		return players[playerNumber - 1].getGameScore();
	}

	/**
	 * Get the name of a chosen player
	 * @param playerNumber The player for which the name is sought
	 * @return String - The name of the chosen player
	 */
	public String getPlayerName(int playerNumber) {
		return players[playerNumber - 1].getPlayerName();
	}

	/**
	 * Get the player type for the current player
	 * @return The player type for the current player (PlayerType.USER or PlayerType.COMPUTER)
	 */
	public PlayerType getPlayerTypeForCurrentPlayer() {
		return this.getCurrentPlayer().getType();
	}
	
	public PlayerType getPlayerTypeForPlayer(int playerNumber) {
		return players[playerNumber - 1].getType();
	}
	
	/**
	 * Set the player type for a chosen player
	 * @param playerNumber The player for which the type is to be set
	 * @param playerType The type to set the chose player to (PlayerType.USER or PlayerType.COMPUTER)
	 */
	public void setPlayerType(int playerNumber, PlayerType playerType) {
		players[playerNumber - 1].setType(playerType);
	}

	/**
	 * Get the bonusTurn flag
	 * 
	 * @return boolean - True if the current turn is a bonus turn, false otherwise
	 */
	public boolean isBonusTurn() {
		return bonusTurn;
	}

	/**
	 * Set the bonustTurn flag
	 * @param isBonusTurn - True if the current turn is a bonus turn, false otherwise
	 */
	public void setBonusTurn(boolean isBonusTurn) {
		this.bonusTurn = isBonusTurn;
	}

	/**
	 * Gets a String array with the winning player's name and score. Array will
	 * be of length 3 should there be a tie: player1, player2, score
	 *
	 * @return String[] array with the winning player's information
	 */
	public String[] getWinningPlayerInfo() {
		
		// If this is sinle player mode, return the player's name and game score
		if (this.gameMode != GameMode.MULTIPLAYER) {
			return new String[] { getPlayerName(1),
					"" + getGameScoreForPlayer(1) };
			
		// Else determine the winner and return that player's name and score
		} else {
			
			// Get the score for each player
			int player1Score = getGameScoreForPlayer(1);
			int player2Score = getGameScoreForPlayer(2);
			
			// If player 1 wins, return player 1's name and score
			if (player1Score > player2Score) {
				return new String[] { getPlayerName(1),
						"" + getGameScoreForPlayer(1) };
				
			// Else if player 2 wins, return player 2's name and score
			} else if (player1Score < player2Score) {
				return new String[] { getPlayerName(2),
						"" + getGameScoreForPlayer(2) };
			
			// Else there was a tie, return both players' names and the score
			} else {
				return new String[] { getPlayerName(1), getPlayerName(2),
						"" + getGameScoreForPlayer(1) };
			}
		}
	}
}
