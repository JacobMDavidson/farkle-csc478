package com.lotsofun.farkle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	// The FarkleController object used for testing
	FarkleController farkleController;
	
	// The Single Player Game object used for testing
	Game singlePlayerGame;
	
	// The Multi Player Game object used for testing
	Game multiPlayerGame;
	
	@Before
	public void setUp() throws Exception {
		// Instantiate the farklecontroller
		farkleController = new FarkleController();
		
		// Instantiate the single player game object
		singlePlayerGame = new Game(GameMode.SINGLEPLAYER, farkleController);

		// Instantiate the single player game object
		multiPlayerGame = new Game(GameMode.MULTIPLAYER, farkleController);
		
	}

	/**
	 * Test Game constructor
	 */
	@Test
	public void testGameConstructor() {
		assertNotNull(farkleController);	
		
		// Test the single player mode game constructor
		assertNotNull(singlePlayerGame);
		assertTrue(singlePlayerGame.getPlayers()[0].equals(singlePlayerGame.getCurrentPlayer()));
		assertEquals(1, singlePlayerGame.getTurnNumberForCurrentPlayer());
		assertEquals(0, singlePlayerGame.getGameScoreForPlayer(1));
		
		// Test the multi player mode game constructor		
		assertNotNull(multiPlayerGame);
		assertTrue(multiPlayerGame.getPlayers()[0].equals(multiPlayerGame.getCurrentPlayer()));
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
		// Test bank for singlePlayerGame by setting the game score and ensuring the bank method
		// returns that score.
		singlePlayerGame.getCurrentPlayer().setGameScore(500);
		assertEquals(500, singlePlayerGame.bank());
		
		// Test bank for multiPlayerGame, set the game score for player 1 and ensure the bank method
		// returns that score.
		multiPlayerGame.getCurrentPlayer().setGameScore(100);
		assertEquals(100, multiPlayerGame.bank());
		
		// Test to check the current player is player two after the first call to bank()
		assertTrue(multiPlayerGame.getPlayers()[1].equals(multiPlayerGame.getCurrentPlayer()));
		
		// Set the game score for the current player to 1000
		multiPlayerGame.getCurrentPlayer().setGameScore(1000);
		
		// Test to ensure the bank method returns 1000 for the current player (player 2)
		assertEquals(1000, multiPlayerGame.bank());
		
		// Test to make sure the next bank method call returns the game score for player 1
		assertEquals(100, multiPlayerGame.bank());
	}

	
	/**
	 * Test the setCurrentPlayer(int) and getCurrentPlayer() method
	 */
	@Test
	public void testSetAndGetCurrentPlayer() {
		
		// Check if player 1 is the current player for singlePlayerGame
		assertTrue(singlePlayerGame.getCurrentPlayer().equals(singlePlayerGame.getPlayers()[0]));
		
		// Check if player 1 is the current player for multiplayergame
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(multiPlayerGame.getPlayers()[0]));
		
		// Change the multiplayergame current player to player 2
		multiPlayerGame.setCurrentPlayer(2);
		
		// Check if player 2 is the current player for multiplayergame
		assertTrue(multiPlayerGame.getCurrentPlayer().equals(multiPlayerGame.getPlayers()[1]));
	}
	
	/**
	 * Test the setDice(Die[]) and getDice() methods
	 */
	@Test
	public void testSetAndGetDice(){
		Die[] dice = new Die[6];
		for(int i = 0; i < dice.length; i++)
		{
			dice[i] = new Die(farkleController);
		}

		// Test singlePlayerGame
		singlePlayerGame.setDice(dice);
		for(int i = 0; i < dice.length; i++)
		{
			assertTrue(dice[i].equals(singlePlayerGame.getDice()[i]));
		}
		
		// Test multiPlayerGame
		multiPlayerGame.setDice(dice);
		for(int i = 0; i < dice.length; i++)
		{
			assertTrue(dice[i].equals(multiPlayerGame.getDice()[i]));
		}
	}
	
	/**
	 * Test the getGameMode() and setGameMode(GameMode) methods
	 */
	@Test
	public void testGetAndSetGameMode()
	{
		// Test with the singlePlayerGame
		assertTrue(singlePlayerGame.getGameMode() == GameMode.SINGLEPLAYER);
		
		// Set the singleplayergame mode to multiplayer and test it
		singlePlayerGame.setGameMode(GameMode.MULTIPLAYER);
		assertTrue(singlePlayerGame.getGameMode() == GameMode.MULTIPLAYER);
	}
	
	/**
	 * Test the getGameScoreForCurrentPlayer() method
	 */
	@Test
	public void testGetGameScoreForCurrentPlayer()
	{
		// Test that the score is 0 at the beginning of the single player game
		assertTrue(singlePlayerGame.getGameScoreForCurrentPlayer() == 0);
		
		// Change the player score and test again
		singlePlayerGame.getCurrentPlayer().setGameScore(1000);
		assertTrue(singlePlayerGame.getGameScoreForCurrentPlayer() == 1000);
		
		// Test that the game score is 0 for both players at the beginning of a multiPlayer game
		assertTrue(multiPlayerGame.getGameScoreForCurrentPlayer() == 0 && 
				multiPlayerGame.getCurrentPlayer().equals(multiPlayerGame.getPlayers()[0]));
		multiPlayerGame.setCurrentPlayer(2);
		assertTrue(multiPlayerGame.getGameScoreForCurrentPlayer() == 0 && 
				multiPlayerGame.getCurrentPlayer().equals(multiPlayerGame.getPlayers()[1]));
		multiPlayerGame.setCurrentPlayer(1);
		
		// Change each player score and test again
		multiPlayerGame.getCurrentPlayer().setGameScore(500);
		assertTrue(multiPlayerGame.getGameScoreForCurrentPlayer() == 500 && 
				multiPlayerGame.getCurrentPlayer().equals(multiPlayerGame.getPlayers()[0]));
		multiPlayerGame.setCurrentPlayer(2);
		multiPlayerGame.getCurrentPlayer().setGameScore(100);
		assertTrue(multiPlayerGame.getGameScoreForCurrentPlayer() == 100 && 
				multiPlayerGame.getCurrentPlayer().equals(multiPlayerGame.getPlayers()[1]));
	}
	
	
	
	
}

