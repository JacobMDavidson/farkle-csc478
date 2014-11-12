package com.lotsofun.farkle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

	private int numberOfPlayers = 1, currentPlayer = 1;
	private GameMode gameMode;
	private GameState gameState;
	private Die[] dice;
	private Player[] players = new Player[2];
	private FarkleController controller;
	private int highScore = 5000;
	private boolean bonusTurn = false;

	/**
	 * Constructor: Creates a new Game object, passes the GameMode and a
	 * reference to the controller
	 * 
	 * @param GameMode
	 *            gMode
	 * @param FarkleController
	 *            controller
	 */
	public Game(GameMode gMode, FarkleController controller) {
		this.gameMode = gMode;
		this.controller = controller;

		// Create player 1 no matter what
		players[0] = new Player(1);

		// Create player 2 if needed
		if (gameMode == GameMode.MULTIPLAYER) {
			players[1] = new Player(2);
		}

		// Set the number of players
		assert (setNumberOfPlayers());

		// Initialize the turns
		resetGame();
	}

	/**
	 * Calculates the score of a list of integers per the rules of this version
	 * of Farkle.
	 * 
	 * @param roll
	 *            a List of Integers representing the roll of dice for which the
	 *            Farkle score will be calculated
	 * @return The calculated score for the list of dice
	 */

	public int calculateScore(List<Integer> roll, boolean checkHeldDice) {
		// Initialize the calculated score and set it to 0
		Integer calculatedScore;
		if (roll != null) {
			calculatedScore = 0;

			if (!roll.isEmpty()) {

				boolean incorrectValuePresent = false;
				for (Integer value : roll) {
					if (!((value == 1) || (value == 2) || (value == 3)
							|| (value == 4) || (value == 5) || (value == 6))) {
						incorrectValuePresent = true;
					}
				}

				if (!incorrectValuePresent) {

					boolean oneOrTwoStrictDie = false;
					int numberOfDieHeld = 0;
					numberOfDieHeld = roll.size();

					// Flag to check for a straight
					boolean isStraight = true;

					// Flags to check for three pair
					boolean isThreePair = true;
					int countOfPairs = 0;

					// This array stores the count of each die in the roll.
					// Index 0 represents a die
					// with value 1, etc.
					int[] countedDie = new int[6];

					// Determine the value of each die in the roll and add to
					// the total count
					// for each die value in countedDie
					for (int value : roll) {
						
						// decrement value to get the proper die index and
						// increment the count for
						// that value of die
						countedDie[--value]++;
					}

					// Calculate the score for the list of dice by looping
					// through the dice count for each value of die
					for (int i = 0; i < countedDie.length; i++) {
						
						// Get the count for the current die value
						Integer currentCount = countedDie[i];

						if (i >= 1 && i != 4) // If it is 2,3,4,6 aka strict
												// dice
						{
							if ((currentCount == 1) || (currentCount == 2)) // Die
																			// appears
																			// 1
																			// or
																			// 2
																			// times
							{
								oneOrTwoStrictDie = true;
							}
						}

						// If the current count does not equal one, it can be
						// deduced that
						// this roll does not include a straight.
						if (currentCount != 1)
							isStraight = false;

						// If the current count does not equal 2 and does not
						// equal 0, it can
						// be deduced that this roll does not include three pair
						if (currentCount != 2 && currentCount != 0)
							isThreePair = false;
						if (currentCount == 2) {
							countOfPairs++;
						}

						/**********************************************
						 * 6.1.0: Each 1 rolled is worth 100 points
						 * ******************************************** 6.2.0:
						 * Each 5 rolled is worth 50 points
						 * ******************************************** 6.3.0:
						 * Three 1�s are worth 1000 points
						 * ******************************************** 6.4.0:
						 * Three of a kind of any value other than 1 is worth
						 * 100 times the value of the die (e.g. three 4�s is
						 * worth 400 points).
						 * ******************************************** 6.4.5:
						 * Four, five, or six of a kind is scored by doubling
						 * the three of a kind value for every additional
						 * matching die (e.g. five 3�s would be scored as 300
						 * X 2 X 2 = 1200.
						 * ******************************************** 6.6.0:
						 * Three distinct doubles (e.g. 1-1-2-2-3-3) are worth
						 * 750 points. This scoring rule does not include the
						 * condition of rolling four of a kind along with a pair
						 * (e.g. 2-2-2-2-3-3 is worth does not satisfy the three
						 * distinct doubles scoring rule).
						 * ******************************************** 6.7.0: A
						 * straight (e.g. 1-2-3-4-5-6), which can only be
						 * achieved when all 6 dice are rolled, is worth 1500
						 * points.
						 * ********************************************/

						Double temp;
						// Add to the score based on the current die value
						switch (i) {
						// If the current die value is 1 (index 0), add 100 to
						// the score
						// for every 1. If there are three or more, add 1000 * 2
						// ^ (count - 3)
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
						// If the current die value is 5 (index 4), add 50 to
						// the score
						// for every 5. If there are three or more, add 500 * 2
						// ^ (count - 3)
						case 4:
							if (currentCount > 0 && currentCount < 3) {
								calculatedScore += currentCount * 50;
							} else if (currentCount >= 3) {
								temp = Math.pow(2, (currentCount - 3));
								calculatedScore = calculatedScore + 500
										* temp.intValue();
							}
							break;
						default:
							// If the count of the current die is greater than
							// 3, add the current
							// die value * 100 * 2 ^ (count - 3)
							if (currentCount >= 3) {
								temp = Math.pow(2, (currentCount - 3));
								calculatedScore += (i + 1) * 100
										* temp.intValue();
							}

							// Else if the is straight flag is true, and the die
							// value is 6 (index 5)
							// and 6 dice were rolled, set the calculated score
							// to 1500
							else if (isStraight == true && i == 5
									&& roll.size() == 6) {
								calculatedScore = 1500;
							}

							// Else if the is three pair flag is true, and the
							// die value is 6 (index 5)
							// and 6 dice were rolled, set the calculated score
							// to 750
							else if (isThreePair == true && i == 5
									&& roll.size() == 6) {
								calculatedScore = 750;
							}

						}

					}

					if (checkHeldDice) {
						if (countOfPairs != 3) // Stops from returning true if
												// only two pairs are held
						{
							isThreePair = false;
						}
						
						// If a 2, 3, 4, or 6 occurs once or twice and the
						// total dice don't contain 3 pair or a straight
						if (oneOrTwoStrictDie && !isStraight && !isThreePair) 
						{
							calculatedScore = 0;
						}
						if (numberOfDieHeld == 0) {
							calculatedScore = 0;
						}

					}
				} else {
					calculatedScore = 0;
				}
			}
		} else {
			calculatedScore = 0;
		}
		return calculatedScore;
	}
	
	public Object[] calculateHighestScore(List<Integer> dice) {
		int highestScorePossible = 0;
		Object[] highestScore = new Object[2];
		
		List<Integer> highestScoringCombination = new ArrayList<Integer>();
		if ((dice != null) && dice.size() > 0) {
			Integer magicNumber = (int) Math.pow(2, dice.size()) - 1;

			// Every combination of dice.
			for (int i = 1; i <= magicNumber; i++) {
				List<Integer> tempList = new ArrayList<Integer>();
				
				char[] binaryNumber = new char[dice.size()];
				Arrays.fill(binaryNumber, '0');
				
				char[] ch = Integer.toBinaryString(i).toCharArray();
				for (int c = 0; c < ch.length; c++) {
					binaryNumber[(ch.length) - (c + 1)] = ch[c];
				}
				
				for (int j = 0; j < binaryNumber.length; j++) {
					if (binaryNumber[j] == '1') {
						tempList.add(dice.get(j));
					}
				}
				
				int tempScore = calculateScore(tempList, false);
				if (tempScore > highestScorePossible) {
					highestScorePossible = tempScore;
					highestScoringCombination.clear();
					highestScoringCombination = tempList;
				}
			}
		}
		highestScore[0] = highestScorePossible;
		highestScore[1] = highestScoringCombination;
		return highestScore;
	}
	

	/**
	 * Set this turn's score to 0 and end the current player's turn
	 */
	public void farkle() {
		controller.setTurnScore(currentPlayer, getTurnNumberForCurrentPlayer(),
				0);
		getCurrentPlayer().endTurn(true);
		currentPlayer = getNextPlayer();
		processHold(0);
	}

	/**
	 * Set this turn's score to the total of all rolls and end the current
	 * player's turn
	 * 
	 * @return
	 */
	public int bank() {
		int retVal = 0;
		controller.setTurnScore(currentPlayer, getTurnNumberForCurrentPlayer(),
				getRollScores());
		getCurrentPlayer().endTurn(false);
		retVal = getCurrentPlayer().getGameScore();
		currentPlayer = getNextPlayer();
		return retVal;
	}

	/**
	 * Get the integer index of the next player
	 * 
	 * @return
	 */
	public int getNextPlayer() {
		// If gameMode is Multiplayer, we need 1 or 0
		if (gameMode == GameMode.MULTIPLAYER) {
			return (currentPlayer == 1) ? 2 : 1;
		} else {
			// gameMode is Singleplayer so it's always 0
			return 1;
		}
	}

	/**
	 * Add the provided score to the map of roll scores for the current turn
	 * 
	 * @param score
	 */
	public void processHold(int score) {
		Player player = getCurrentPlayer();
		player.scoreRoll(score);
	}

	/**
	 * Increment the current roll number of this player
	 */
	public void processRoll() {
		getCurrentPlayer()
				.setRollNumber(getCurrentPlayer().getRollNumber() + 1);
	}

	/**
	 * Increment the current turn value for the current player
	 */
	public void processTurn() {
		getCurrentPlayer().nextTurn();
	}

	/**
	 * Get the total score of all the rolls for this turn
	 * 
	 * @return
	 */
	public int getRollScores() {
		return getCurrentPlayer().getRollScores();
	}

	/**
	 * 
	 * @return the number of players in the game object's players array
	 */
	public int getNumberOfPlayers() {
		return players.length;
	}

	private boolean setNumberOfPlayers() {
		if (null != players && players.length > 0) {
			this.numberOfPlayers = players.length;
			return true;
		} else {
			return false;
		}
	}

	public Player getCurrentPlayer() {
		return players[currentPlayer - 1];
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

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	/**
	 * Set all the turn scores for all players in the Game object to 0 and set
	 * the first player as the current player
	 */
	public void resetGame() {
		for (Player player : players) {
			if (null != player) {
				player.setTurnNumber(1);
				player.resetTurnScores();
				player.setGameScore(0);
			}
		}
		players[0].setCurrentPlayer(true);
	}

	/**
	 * Set the name of the player represented the int passed to the String
	 * passed
	 * 
	 * @param playerNumber
	 *            Player's position - NOT AN INDEX
	 * @param name
	 */
	public void setPlayerName(int playerNumber, String name) {
		if (null != name && playerNumber >= 1 && playerNumber <= 2) {
			this.players[playerNumber - 1].setPlayerName(name);
			if (playerNumber == 2 && name.equalsIgnoreCase("computer")) {
				this.players[1].setIsComputer(true);
			}
		}
	}

	public int getTurnNumberForCurrentPlayer() {
		return this.getCurrentPlayer().getTurnNumber();
	}

	public void setTurnNumberForCurrentPlayer(int turnNumber) {
		getCurrentPlayer().setTurnNumber(turnNumber);
	}

	public int getGameScoreForCurrentPlayer() {
		return this.getCurrentPlayer().getGameScore();
	}

	public int getPlayerNumberForCurrentPlayer() {
		return this.getCurrentPlayer().getPlayerNumber();
	}

	public int getGameScoreForPlayer(int playerNumber) {
		return players[playerNumber - 1].getGameScore();
	}

	public String getPlayerName(int playerNumber) {
		return players[playerNumber - 1].getPlayerName();
	}

	public PlayerType getPlayerTypeForCurrentPlayer() {
		return this.getCurrentPlayer().getType();
	}
	
	public void setPlayerType(int playerNumber, PlayerType playerType) {
		players[playerNumber - 1].setType(playerType);
	}

	/**
	 * Returns true if current turn is a bonus turn
	 * 
	 * @return
	 */
	public boolean isBonusTurn() {
		return bonusTurn;
	}

	public void setBonusTurn(boolean isBonusTurn) {
		this.bonusTurn = isBonusTurn;
	}

	/**
	 * Gets a String array with the winning player's name and score Array will
	 * be of length 3 should there be a tie: player1, player2, score
	 *
	 * @return
	 */
	public String[] getWinningPlayerInfo() {
		if (this.gameMode != GameMode.MULTIPLAYER) {
			return new String[] { getPlayerName(1),
					"" + getGameScoreForPlayer(1) };
		} else {
			int player1Score = getGameScoreForPlayer(1);
			int player2Score = getGameScoreForPlayer(2);
			if (player1Score > player2Score) {
				return new String[] { getPlayerName(1),
						"" + getGameScoreForPlayer(1) };
			} else if (player1Score < player2Score) {
				return new String[] { getPlayerName(2),
						"" + getGameScoreForPlayer(2) };
			} else {
				return new String[] { getPlayerName(1), getPlayerName(2),
						"" + getGameScoreForPlayer(1) };
			}
		}
	}
}
