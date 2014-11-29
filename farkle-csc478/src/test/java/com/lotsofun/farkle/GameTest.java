package com.lotsofun.farkle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Game Class (except the calculateHighestScore(List<Integer>) and the
 * calculateScore(List<Integer>, boolean) methods
 * 
 * @author Jacob Davidson
 * @version 3.0.0
 */
public class GameTest {

	/** The FarkleController object used for testing */
	FarkleController farkleController;

	/** The Single Player Game object used for testing */
	Game singlePlayerGame;

	/** The Multi Player Game object used for testing */
	Game multiPlayerGame;

	/**
	 * Instantiate all objects before testing begins
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		// Instantiate the farklecontroller
		farkleController = new FarkleController(true);

		// Instantiate the single player game object
		singlePlayerGame = new Game(GameMode.SINGLEPLAYER, farkleController);

		// Instantiate the single player game object
		multiPlayerGame = new Game(GameMode.MULTIPLAYER, farkleController);

	}

	/**
	 * Test the Game constructor
	 */
	@Test
	public void testGameConstructor() {

		// Assert that the farkleController is not null
		assertNotNull(farkleController);

		// Test the single player mode game constructor
		assertNotNull(singlePlayerGame);
		assertTrue(singlePlayerGame.getPlayers()[0].equals(singlePlayerGame
				.getCurrentPlayer()));
		assertEquals(1, singlePlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, singlePlayerGame.getGameScoreForPlayer(1));

		// Test the multi player mode game constructor
		assertNotNull(multiPlayerGame);
		assertTrue(multiPlayerGame.getPlayers()[0].equals(multiPlayerGame
				.getCurrentPlayer()));
		assertEquals(1, multiPlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getGameScoreForPlayer(1));
		assertEquals(1, multiPlayerGame.getPlayers()[1].getTurnNumber());
		assertEquals(0, multiPlayerGame.getGameScoreForPlayer(2));

	}

	/**
	 * Test the bank() method
	 */
	@Test
	public void testBank() {

		// Test bank for singlePlayerGame by setting the game score and ensuring
		// the bank method
		// returns that score.
		singlePlayerGame.getCurrentPlayer().setGameScore(500);
		assertEquals(500, singlePlayerGame.bank());

		// Test bank for multiPlayerGame, set the game score for player 1 and
		// ensure the bank method
		// returns that score.
		multiPlayerGame.getCurrentPlayer().setGameScore(100);
		assertEquals(100, multiPlayerGame.bank());

		// Test to check the current player is player two after the first call
		// to bank()
		assertTrue(multiPlayerGame.getPlayers()[1].equals(multiPlayerGame
				.getCurrentPlayer()));

		// Set the game score for the current player to 1000
		multiPlayerGame.getCurrentPlayer().setGameScore(1000);

		// Test to ensure the bank method returns 1000 for the current player
		// (player 2)
		assertEquals(1000, multiPlayerGame.bank());

		// Test to make sure the next bank method call returns the game score
		// for player 1
		assertEquals(100, multiPlayerGame.bank());

	}

	/**
	 * Test the farkle() method
	 */
	@Test
	public void testFarkle() {

		// Test farkle for singlePlayerGame by adding a roll score, and testing
		// to make sure
		// that the roll scare is not added to the game score after a farkle
		singlePlayerGame.getCurrentPlayer().setGameScore(1000);
		singlePlayerGame.getCurrentPlayer().scoreRoll(500);
		singlePlayerGame.farkle();
		assertEquals(1000, singlePlayerGame.getGameScoreForCurrentPlayer());

		// Test farkle for multiPlayerGame. set the game score to 100 and the
		// roll score to 500
		// for the current player
		multiPlayerGame.getCurrentPlayer().setGameScore(100);
		multiPlayerGame.getCurrentPlayer().scoreRoll(500);

		// Upon a farkle, the current player should be player 2 and the roll
		// score should not
		// have been added to the game score for player 1
		multiPlayerGame.farkle();

		// Test to check the current player is player two after the first call
		// to farkle()
		assertTrue(multiPlayerGame.getPlayers()[1].equals(multiPlayerGame
				.getCurrentPlayer()));

		// Test to make sure the roll score wasn't added to player 1's game
		// score
		assertEquals(100, multiPlayerGame.getPlayers()[0].getGameScore());

		// Set the game score for the current player to 1000, and the roll score
		// to 500
		multiPlayerGame.getCurrentPlayer().setGameScore(1000);
		multiPlayerGame.getCurrentPlayer().scoreRoll(500);

		// Upon a farkle, the current player should be player 1 and the roll
		// score should not
		// have been added to the game score for player 2.
		multiPlayerGame.farkle();

		// Test to check the current player is player one after the second call
		// to farkle()
		assertTrue(multiPlayerGame.getPlayers()[0].equals(multiPlayerGame
				.getCurrentPlayer()));

		// Test to make sure the roll score wasn't added to player 1's game
		// score
		assertEquals(1000, multiPlayerGame.getPlayers()[1].getGameScore());

	}

	/**
	 * Test the setCurrentPlayer(int) and getCurrentPlayer() method
	 */
	@Test
	public void testSetAndGetCurrentPlayer() {

		// Check if player 1 is the current player for singlePlayerGame
		assertTrue(singlePlayerGame.getCurrentPlayer().equals(
				singlePlayerGame.getPlayers()[0]));

		// Check if player 1 is the current player for multiplayergame
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(
				multiPlayerGame.getPlayers()[0]));

		// Change the multiplayergame current player to player 2
		multiPlayerGame.setCurrentPlayer(2);

		// Check if player 2 is the current player for multiplayergame
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(
				multiPlayerGame.getPlayers()[1]));

	}

	/**
	 * Test the getGameMode() method
	 */
	@Test
	public void testGetAndSetGameMode() {

		// Test with the singlePlayerGame
		assertTrue(singlePlayerGame.getGameMode() == GameMode.SINGLEPLAYER);

		// Test with multiPlayerGame
		assertTrue(multiPlayerGame.getGameMode() == GameMode.MULTIPLAYER);

	}

	/**
	 * Test the getGameScoreForCurrentPlayer() method
	 */
	@Test
	public void testGetGameScoreForCurrentPlayer() {

		// Test that the score is 0 at the beginning of the single player game
		assertTrue(singlePlayerGame.getGameScoreForCurrentPlayer() == 0);

		// Change the player score and test again
		singlePlayerGame.getCurrentPlayer().setGameScore(1000);
		assertTrue(singlePlayerGame.getGameScoreForCurrentPlayer() == 1000);

		// Test that the game score is 0 for both players at the beginning of a
		// multiPlayer game
		assertTrue(multiPlayerGame.getGameScoreForCurrentPlayer() == 0
				&& multiPlayerGame.getCurrentPlayer().equals(
						multiPlayerGame.getPlayers()[0]));
		multiPlayerGame.setCurrentPlayer(2);
		assertTrue(multiPlayerGame.getGameScoreForCurrentPlayer() == 0
				&& multiPlayerGame.getCurrentPlayer().equals(
						multiPlayerGame.getPlayers()[1]));
		multiPlayerGame.setCurrentPlayer(1);

		// Change each player score and test again
		multiPlayerGame.getCurrentPlayer().setGameScore(500);
		assertTrue(multiPlayerGame.getGameScoreForCurrentPlayer() == 500
				&& multiPlayerGame.getCurrentPlayer().equals(
						multiPlayerGame.getPlayers()[0]));
		multiPlayerGame.setCurrentPlayer(2);
		multiPlayerGame.getCurrentPlayer().setGameScore(100);
		assertTrue(multiPlayerGame.getGameScoreForCurrentPlayer() == 100
				&& multiPlayerGame.getCurrentPlayer().equals(
						multiPlayerGame.getPlayers()[1]));

	}

	/**
	 * Test the getGameScoreForPlayer(int) method
	 */
	@Test
	public void testGetGameScoreForPlayer() {

		// Test for single player game, make sure the game score for player 1 is
		// 0 at the start
		assertEquals(0, singlePlayerGame.getGameScoreForPlayer(1));

		// Set the game score to an integer, and ensure the
		// getGameScoreForPlayer returns that integer
		singlePlayerGame.getCurrentPlayer().setGameScore(500);
		assertEquals(500, singlePlayerGame.getGameScoreForPlayer(1));

		// Make sure player 2 is null for single player game
		assertNull(singlePlayerGame.getPlayers()[1]);

		// Test for multi player game, make sure the game score is initially 0
		// for both players
		assertEquals(0, multiPlayerGame.getGameScoreForPlayer(1));
		assertEquals(0, multiPlayerGame.getGameScoreForPlayer(2));

		// Set the game score to an integer for each player, and ensure the
		// getGameScoreForPlayer
		// returns that integer for both
		multiPlayerGame.getPlayers()[0].setGameScore(1000);
		multiPlayerGame.getPlayers()[1].setGameScore(700);
		assertEquals(1000, multiPlayerGame.getGameScoreForPlayer(1));
		assertEquals(700, multiPlayerGame.getGameScoreForPlayer(2));

	}

	/**
	 * Test the getHighScore() and setHighScore(int) methods
	 */
	@Test
	public void testGetAndSetHighScore() {

		// Set the high score, and then get it to make sure it matches. This is
		// a Preference, so it will be the same regardless of setting and
		// getting
		// it through the single player game or multi player game
		singlePlayerGame.setHighScore(5000);
		assertEquals(5000, singlePlayerGame.getHighScore());
		assertEquals(5000, multiPlayerGame.getHighScore());

		// Because we can't rely on the JUnits to necessarily fire in
		// the order in which they're written, this preference needs
		// to be reset to prevent it from persisting in the build environment.
		singlePlayerGame.setHighScore(0);
		assertEquals(0, singlePlayerGame.getHighScore());
		assertEquals(0, multiPlayerGame.getHighScore());

	}

	/**
	 * Test the getNextPlayer() method
	 */
	@Test
	public void testGetNextPlayer() {

		// In single player mode, this should always return 1
		assertEquals(1, singlePlayerGame.getNextPlayer());

		// Farkle will end the current players turn, and set the next player
		singlePlayerGame.farkle();

		// In single player mode, getNextPlayer() should still result in 1
		assertEquals(1, singlePlayerGame.getNextPlayer());

		// In multi player mode, this will be one or two. At the start of the
		// game
		// the current player is player 1 so getNextPlayer should return 2
		assertEquals(2, multiPlayerGame.getNextPlayer());

		// Farkle will end the current players turn, and set the next player
		multiPlayerGame.farkle();

		// In multi player mode, getNextPlayer() should now result in 1
		assertEquals(1, multiPlayerGame.getNextPlayer());

		// Bank will also end the current players turn, and set the next player
		multiPlayerGame.bank();

		// In multi player mode, getNextPlayer() should now result in 2 again
		assertEquals(2, multiPlayerGame.getNextPlayer());

	}

	/**
	 * Test the getPlayerName(int) and setPlayerName(int, String) methods
	 */
	@Test
	public void testGetAndSetPlayerName() {

		// Test the single player game. The player's name should initially be
		// null
		assertNull(singlePlayerGame.getPlayerName(1));

		// Assigning null to the player's name should result in a name of null
		singlePlayerGame.setPlayerName(1, null);
		assertNull(singlePlayerGame.getPlayerName(1));

		// Assigning any string to the player's name should result in that
		// string
		singlePlayerGame.setPlayerName(1, "Jake");
		assertTrue(singlePlayerGame.getPlayerName(1).equals("Jake"));

		// Test the multi player game. The player's names should initially be
		// null
		assertNull(multiPlayerGame.getPlayerName(1));
		assertNull(multiPlayerGame.getPlayerName(2));

		// Assigning null to the player's name should result in a name of null
		multiPlayerGame.setPlayerName(1, null);
		multiPlayerGame.setPlayerName(2, null);
		assertNull(multiPlayerGame.getPlayerName(1));
		assertNull(multiPlayerGame.getPlayerName(2));

		// Assigning any string to the player's name should result in that
		// string
		multiPlayerGame.setPlayerName(1, "bill");
		multiPlayerGame.setPlayerName(2, "roger");
		assertTrue(multiPlayerGame.getPlayerName(1).equals("bill"));
		assertTrue(multiPlayerGame.getPlayerName(2).equals("roger"));

	}

	/**
	 * Test getPlayerNumberForCurrentPlayer() method
	 */
	@Test
	public void testGetPlayerNumberForCurrentPlayer() {

		// The current player number for a single player game should always be 1
		assertEquals(1, singlePlayerGame.getPlayerNumberForCurrentPlayer());

		// Same should be true after a farkle or bank
		singlePlayerGame.farkle();
		assertEquals(1, singlePlayerGame.getPlayerNumberForCurrentPlayer());
		singlePlayerGame.bank();
		assertEquals(1, singlePlayerGame.getPlayerNumberForCurrentPlayer());

		// The current player number for a multi player game should start as 1
		assertEquals(1, multiPlayerGame.getPlayerNumberForCurrentPlayer());

		// After the first farkle it should be 2
		multiPlayerGame.farkle();
		assertEquals(2, multiPlayerGame.getPlayerNumberForCurrentPlayer());

		// After the second farkle it should be 1 again
		multiPlayerGame.farkle();
		assertEquals(1, multiPlayerGame.getPlayerNumberForCurrentPlayer());

		// After the first bank it should be 2
		multiPlayerGame.bank();
		assertEquals(2, multiPlayerGame.getPlayerNumberForCurrentPlayer());

		// After the second bank it should be 1 again
		multiPlayerGame.bank();
		assertEquals(1, multiPlayerGame.getPlayerNumberForCurrentPlayer());

	}

	/**
	 * Test the getPlayers() method
	 */
	@Test
	public void testGetPlayers() {

		// Make sure the number of players returned for the single player game
		// is 1
		Player[] singlePlayerGamePlayers = singlePlayerGame.getPlayers();
		int playerCount = 0;
		for (int i = 0; i < singlePlayerGamePlayers.length; i++) {
			if (null != singlePlayerGamePlayers[i]) {
				playerCount++;
			}
		}
		assertEquals(1, playerCount);

		// Make sure that player is the current player
		assertTrue(singlePlayerGamePlayers[0].equals(singlePlayerGame
				.getCurrentPlayer()));

		// Make sure the number of players returned for the multi player game is
		// 2
		Player[] multiPlayerGamePlayers = multiPlayerGame.getPlayers();
		playerCount = 0;
		for (int i = 0; i < multiPlayerGamePlayers.length; i++) {
			if (null != multiPlayerGamePlayers[i]) {
				playerCount++;
			}
		}
		assertEquals(2, playerCount);

		// Make sure the current player is the first player in the
		// multiPlayerGamePlayers array
		assertTrue(multiPlayerGamePlayers[0].equals(multiPlayerGame
				.getCurrentPlayer()));

		// bank or farkle to change the current player, and check that player
		// against the array
		multiPlayerGame.bank();
		assertTrue(multiPlayerGamePlayers[1].equals(multiPlayerGame
				.getCurrentPlayer()));

	}

	/**
	 * Test the getPlayerTypeForCurrentPlayer() and setPlayerType(int,
	 * PlayerType) methods
	 */
	@Test
	public void testGetAndSetPlayerType() {

		// Test for single player mode. It should start as PlayerType.USER
		assertEquals(PlayerType.USER,
				singlePlayerGame.getPlayerTypeForCurrentPlayer());

		// Set the player type to computer and check it
		singlePlayerGame.setPlayerType(1, PlayerType.COMPUTER);
		assertEquals(PlayerType.COMPUTER,
				singlePlayerGame.getPlayerTypeForCurrentPlayer());

		// Test for multi player mode. Both players should start out as
		// PlayerType.USER
		assertEquals(PlayerType.USER,
				multiPlayerGame.getPlayerTypeForCurrentPlayer());
		multiPlayerGame.setPlayerType(1, PlayerType.COMPUTER);
		assertEquals(PlayerType.COMPUTER,
				multiPlayerGame.getPlayerTypeForCurrentPlayer());

		// Change the player via a bank() call, and test player 2
		multiPlayerGame.bank();
		assertEquals(PlayerType.USER,
				multiPlayerGame.getPlayerTypeForCurrentPlayer());
		multiPlayerGame.setPlayerType(2, PlayerType.COMPUTER);
		assertEquals(PlayerType.COMPUTER,
				multiPlayerGame.getPlayerTypeForCurrentPlayer());

	}

	/**
	 * Test the getRollScores() method
	 */
	@Test
	public void testGetRollScores() {

		// Test the single player game. The getRollScores method should return 0
		// at the start
		assertEquals(0, singlePlayerGame.getRollScores());

		// Add a few rolls and test again
		singlePlayerGame.getCurrentPlayer().scoreRoll(100);
		singlePlayerGame.processRoll();
		assertEquals(100, singlePlayerGame.getRollScores());
		singlePlayerGame.getCurrentPlayer().scoreRoll(100);
		singlePlayerGame.processRoll();
		assertEquals(200, singlePlayerGame.getRollScores());

		// End the turn by banking and check to make sure it's back to 0
		singlePlayerGame.bank();
		assertEquals(0, singlePlayerGame.getRollScores());

		// Test the multi player game. The getRollScores method should return 0
		// at the start
		assertEquals(0, multiPlayerGame.getRollScores());

		// add a few rolls and test again
		multiPlayerGame.getCurrentPlayer().scoreRoll(100);
		multiPlayerGame.processRoll();
		assertEquals(100, multiPlayerGame.getRollScores());
		multiPlayerGame.getCurrentPlayer().scoreRoll(100);
		multiPlayerGame.processRoll();
		assertEquals(200, multiPlayerGame.getRollScores());

		// End the turn by farkle and test the second player
		multiPlayerGame.farkle();

		// Test the multi player game. The getRollScores method should return 0
		// at the start
		assertEquals(0, multiPlayerGame.getRollScores());

		// add a few rolls and test again
		multiPlayerGame.getCurrentPlayer().scoreRoll(100);
		multiPlayerGame.processRoll();
		assertEquals(100, multiPlayerGame.getRollScores());
		multiPlayerGame.getCurrentPlayer().scoreRoll(100);
		multiPlayerGame.processRoll();
		assertEquals(200, multiPlayerGame.getRollScores());

	}

	/**
	 * Test the getTurnNumberForCurrentPlayer() method
	 */
	@Test
	public void testGetTurnNumberForCurrentPlayer() {

		// Test for single player game. This should initially be 1
		assertEquals(1, singlePlayerGame.getTurnNumberForCurrentPlayer());

		// End the turn via bank() or farkle() and test again
		singlePlayerGame.bank();
		assertEquals(2, singlePlayerGame.getTurnNumberForCurrentPlayer());
		singlePlayerGame.farkle();
		assertEquals(3, singlePlayerGame.getTurnNumberForCurrentPlayer());

		// Test for multiPlayerGame
		assertEquals(1, multiPlayerGame.getTurnNumberForCurrentPlayer());
		multiPlayerGame.bank();

		// The bank should change the current player to player 2, current turn
		// should still be 1
		assertEquals(1, multiPlayerGame.getTurnNumberForCurrentPlayer());

		// End turn via bank() or farkle() and test again
		multiPlayerGame.bank();
		assertEquals(2, multiPlayerGame.getTurnNumberForCurrentPlayer());
		multiPlayerGame.farkle();
		assertEquals(2, multiPlayerGame.getTurnNumberForCurrentPlayer());
		multiPlayerGame.farkle();
		assertEquals(3, multiPlayerGame.getTurnNumberForCurrentPlayer());

	}

	/**
	 * Test the getWinningPlayerInfo() method
	 */
	@Test
	public void testGetWinningPlayerInfo() {

		// Test for single player game. The name is initially null
		String[] winner = singlePlayerGame.getWinningPlayerInfo();
		assertNull(winner[0]);
		assertTrue(winner[1].equals("0"));

		// Change the name and score for the single player game and test again
		singlePlayerGame.setPlayerName(1, "Brant");
		singlePlayerGame.getCurrentPlayer().setGameScore(4100);
		winner = singlePlayerGame.getWinningPlayerInfo();
		assertTrue(winner[0].equals("Brant"));
		assertTrue(winner[1].equals("4100"));

		// Test for each player of the multiPlayerGame. The name's should
		// initially be null and they should be tied
		winner = multiPlayerGame.getWinningPlayerInfo();
		assertNull(winner[0]);
		assertNull(winner[1]);
		assertTrue(winner[2].equals("0"));

		// Set names and various scores for testing multiplayer
		multiPlayerGame.setPlayerName(1, "Curtis");
		multiPlayerGame.setPlayerName(2, "Jacob");

		// Set scores so player 1 wins
		multiPlayerGame.getPlayers()[0].setGameScore(1000);
		multiPlayerGame.getPlayers()[1].setGameScore(500);
		winner = multiPlayerGame.getWinningPlayerInfo();
		assertTrue(winner[0].equals("Curtis"));
		assertTrue(winner[1].equals("1000"));

		// Set scores so player 2 wins
		multiPlayerGame.getPlayers()[0].setGameScore(500);
		multiPlayerGame.getPlayers()[1].setGameScore(700);
		winner = multiPlayerGame.getWinningPlayerInfo();
		assertTrue(winner[0].equals("Jacob"));
		assertTrue(winner[1].equals("700"));

		// Set scores so players tie
		multiPlayerGame.getPlayers()[0].setGameScore(500);
		multiPlayerGame.getPlayers()[1].setGameScore(500);
		winner = multiPlayerGame.getWinningPlayerInfo();
		assertTrue(winner[0].equals("Curtis"));
		assertTrue(winner[1].equals("Jacob"));
		assertTrue(winner[2].equals("500"));

	}

	/**
	 * Test the isBonusTuen() and setBonusTurn(boolean) methods
	 */
	@Test
	public void testIsAndSetBonusTurn() {

		// test single player game
		assertFalse(singlePlayerGame.isBonusTurn());
		singlePlayerGame.setBonusTurn(true);
		assertTrue(singlePlayerGame.isBonusTurn());
		singlePlayerGame.setBonusTurn(false);
		assertFalse(singlePlayerGame.isBonusTurn());

		// Test multi player game
		assertFalse(multiPlayerGame.isBonusTurn());
		multiPlayerGame.setBonusTurn(true);
		assertTrue(multiPlayerGame.isBonusTurn());
		multiPlayerGame.setBonusTurn(false);
		assertFalse(multiPlayerGame.isBonusTurn());

	}

	/**
	 * Test the processHold(int) method
	 */
	@Test
	public void testProcessHold() {

		// Test for single player game
		assertEquals(0, singlePlayerGame.getRollScores());
		singlePlayerGame.processHold(100);
		assertEquals(100, singlePlayerGame.getRollScores());
		singlePlayerGame.processHold(-100);
		assertEquals(-100, singlePlayerGame.getRollScores());

		// test for multi player game - player 1
		assertEquals(0, multiPlayerGame.getRollScores());
		multiPlayerGame.processHold(100);
		assertEquals(100, multiPlayerGame.getRollScores());
		multiPlayerGame.processHold(-100);
		assertEquals(-100, multiPlayerGame.getRollScores());

		// test for multi player game - player 2
		multiPlayerGame.farkle();
		assertEquals(0, multiPlayerGame.getRollScores());
		multiPlayerGame.processHold(100);
		assertEquals(100, multiPlayerGame.getRollScores());
		multiPlayerGame.processHold(-100);
		assertEquals(-100, multiPlayerGame.getRollScores());

	}

	/**
	 * Test the processRoll() method
	 */
	@Test
	public void testProcessRoll() {

		// Test for single player game

		// The roll number and roll score should be initially 0
		assertEquals(0, singlePlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, singlePlayerGame.getRollScores());

		// Add 100 to the roll and process it
		singlePlayerGame.processHold(100);
		singlePlayerGame.processRoll();

		// The roll number should be 1 and the roll score should be 100
		assertEquals(1, singlePlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(100, singlePlayerGame.getRollScores());

		// Add -50 to the roll and process it
		singlePlayerGame.processHold(-50);
		singlePlayerGame.processRoll();

		// The roll number should be 2 and the roll score should be 50
		assertEquals(2, singlePlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(50, singlePlayerGame.getRollScores());

		// Test for multi player game

		// The roll number and roll score should be initially 0
		assertEquals(0, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, multiPlayerGame.getRollScores());

		// Add 100 to the roll and process it
		multiPlayerGame.processHold(100);
		multiPlayerGame.processRoll();

		// The roll number should be 1 and the roll score should be 100
		assertEquals(1, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(100, multiPlayerGame.getRollScores());

		// Add -50 to the roll and process it
		multiPlayerGame.processHold(-50);
		multiPlayerGame.processRoll();

		// The roll number should be 2 and the roll score should be 50
		assertEquals(2, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(50, multiPlayerGame.getRollScores());

		// call the fakle method to change to player 2 and test again
		multiPlayerGame.farkle();

		// The roll number and roll score should be initially 0
		assertEquals(0, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, multiPlayerGame.getRollScores());

		// Add 100 to the roll and process it
		multiPlayerGame.processHold(100);
		multiPlayerGame.processRoll();

		// The roll number should be 1 and the roll score should be 100
		assertEquals(1, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(100, multiPlayerGame.getRollScores());

		// Add -50 to the roll and process it
		multiPlayerGame.processHold(-50);
		multiPlayerGame.processRoll();

		// The roll number should be 2 and the roll score should be 50
		assertEquals(2, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(50, multiPlayerGame.getRollScores());

	}

	/**
	 * Test the resetGame() method
	 */
	@Test
	public void testResetGame() {

		// Test reset on a single player game

		/*
		 * Test for turn number = 1, game score = 0, roll number = 0, roll score
		 * = 0, turnScores is empty, and rollScore is empty
		 */
		assertEquals(1, singlePlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, singlePlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(0, singlePlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, singlePlayerGame.getRollScores());
		assertTrue(singlePlayerGame.getCurrentPlayer().getTurnScores()
				.isEmpty());
		assertTrue(singlePlayerGame.getCurrentPlayer().getRollScore().isEmpty());

		// simulate a few rolls and turns
		singlePlayerGame.processHold(100);
		singlePlayerGame.processRoll();
		singlePlayerGame.processHold(150);
		singlePlayerGame.processRoll();
		singlePlayerGame.bank();
		singlePlayerGame.farkle();
		singlePlayerGame.processHold(100);
		singlePlayerGame.processRoll();
		assertEquals(3, singlePlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(250, singlePlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(1, singlePlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(100, singlePlayerGame.getRollScores());

		// Call the reset method, and test again
		singlePlayerGame.resetGame();
		assertEquals(1, singlePlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, singlePlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(0, singlePlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, singlePlayerGame.getRollScores());
		assertTrue(singlePlayerGame.getCurrentPlayer().getTurnScores()
				.isEmpty());
		assertTrue(singlePlayerGame.getCurrentPlayer().getRollScore().isEmpty());

		// Test reset on a multi player game

		/*
		 * Test for turn number = 1, game score = 0, roll number = 0, roll score
		 * = 0, turnScores is empty, and rollScore is empty for player 1
		 */
		assertEquals(1, multiPlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, multiPlayerGame.getRollScores());
		assertTrue(multiPlayerGame.getCurrentPlayer().getTurnScores().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().getRollScore().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(
				multiPlayerGame.getPlayers()[0]));

		// Set player 2 and check the same tests
		multiPlayerGame.setCurrentPlayer(2);
		assertEquals(1, multiPlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, multiPlayerGame.getRollScores());
		assertTrue(multiPlayerGame.getCurrentPlayer().getTurnScores().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().getRollScore().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(
				multiPlayerGame.getPlayers()[1]));

		// Change current player back to player 1 and process a few rolls
		multiPlayerGame.setCurrentPlayer(1);
		multiPlayerGame.processHold(100);
		multiPlayerGame.processRoll();
		multiPlayerGame.processHold(150);
		multiPlayerGame.processRoll();
		multiPlayerGame.bank();

		// Player 1 now has a game score of 250, and is on turn 2
		multiPlayerGame.farkle();

		// Player 2 now has a game score of 0, and is on turn 2
		multiPlayerGame.processHold(100);
		multiPlayerGame.processRoll();
		multiPlayerGame.bank();

		// Player 1 now has a game score of 350, and is on turn 3
		multiPlayerGame.processHold(550);
		multiPlayerGame.processRoll();
		multiPlayerGame.bank();

		// Player 2 now has a score of 550, and is on turn 3
		multiPlayerGame.processHold(100);
		multiPlayerGame.processRoll();
		multiPlayerGame.processHold(150);
		multiPlayerGame.processRoll();

		// PLayer 1 now has a roll score of 250, and is on roll 2 (starts from
		// 0)

		// Test player 1 game state
		assertEquals(3, multiPlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(350, multiPlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(2, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(250, multiPlayerGame.getRollScores());
		assertTrue(!multiPlayerGame.getCurrentPlayer().getTurnScores()
				.isEmpty());
		assertTrue(!multiPlayerGame.getCurrentPlayer().getRollScore().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(
				multiPlayerGame.getPlayers()[0]));

		// Test player 2 game state
		multiPlayerGame.farkle();
		assertEquals(3, multiPlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(550, multiPlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, multiPlayerGame.getRollScores());
		assertTrue(!multiPlayerGame.getCurrentPlayer().getTurnScores()
				.isEmpty());
		assertTrue(!multiPlayerGame.getCurrentPlayer().getRollScore().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(
				multiPlayerGame.getPlayers()[1]));

		// Reset the game and test each player
		multiPlayerGame.resetGame();
		assertEquals(1, multiPlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, multiPlayerGame.getRollScores());
		assertTrue(multiPlayerGame.getCurrentPlayer().getTurnScores().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().getRollScore().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(
				multiPlayerGame.getPlayers()[0]));

		// Change the current player and check again
		multiPlayerGame.setCurrentPlayer(2);
		assertEquals(1, multiPlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getGameScoreForCurrentPlayer());
		assertEquals(0, multiPlayerGame.getCurrentPlayer().getRollNumber());
		assertEquals(0, multiPlayerGame.getRollScores());
		assertTrue(multiPlayerGame.getCurrentPlayer().getTurnScores().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().getRollScore().isEmpty());
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(
				multiPlayerGame.getPlayers()[1]));

	}

	/**
	 * Test the resetHighScore() method
	 */
	@Test
	public void testResetHighScore() {

		// Set the high score, and then reset it to make sure it is reset to 0.
		// This is
		// a Preference, so it will be the same regardless of setting and
		// getting
		// it through the single player game or multi player game
		singlePlayerGame.setHighScore(5000);
		assertEquals(5000, singlePlayerGame.getHighScore());
		assertEquals(5000, multiPlayerGame.getHighScore());
		singlePlayerGame.resetHighScore();
		assertEquals(0, singlePlayerGame.getHighScore());
		assertEquals(0, multiPlayerGame.getHighScore());

	}
}
