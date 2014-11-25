package com.lotsofun.farkle;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Player Class
 * and the calculateScore(List<Integer>, boolean) methods
 * @author Jacob Davidson
 * @version 3.0.0
 */
public class PlayerTest {
	/** The Player object used for testing */
	Player player;
	
	/** rollScore Hash Map used for testing */
	HashMap<Integer, Integer> rollScore;
	
	/** An array list of turn scores used for testing */
	ArrayList<Integer> turnScores;

	/**
	 * Instantiate all objects before testing begins
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Instantiate the player object used for testing
		player = new Player(1);
		
		// Instantiate a Hash Map for the roll scores
		rollScore = new HashMap<Integer, Integer>();
		
		// Instantiate the turnScore array list
		turnScores = new ArrayList<Integer>();
	}


	/**
	 * Test the Player constructor
	 */
	@Test
	public void test() {
		assertNotNull(player);
		assertNotNull(player.getTurnScores());
		assertTrue(player.getTurnScores().isEmpty());
		assertEquals(1, player.getPlayerNumber());
		assertEquals(PlayerType.USER, player.getType());
	}
	
	/**
	 * Test the endTurn(boolean) method
	 */
	@Test
	public void testEndTurn() {
		
		// Add a few rolls to the rollScore hash map
		rollScore.put(0, 50);
		rollScore.put(1, 100);
		rollScore.put(2, 150);
		
		// Set the roll score for the player
		player.setRollScore(rollScore);
		
		// Set the roll number for the player
		player.setRollNumber(3);
		
		// Add a few turns to the turnScores array list
		turnScores.add(400);
		turnScores.add(300);
		
		// Set the turnScores for the player
		player.setTurnScores(turnScores);
		
		// Set the turn number for the player
		player.setTurnNumber(3);
		
		// Set the game score for the player
		player.setGameScore(700);
		
		// End turn via a farkle, and test
		player.endTurn(true);
		
		assertTrue(0 == player.getTurnScores().get(2));
		assertEquals(700, player.getGameScore());
		assertEquals(4, player.getTurnNumber());		
		assertEquals(0, player.getRollNumber());
		assertTrue(player.getRollScore().isEmpty());
		assertEquals(player.getRollScores(), 0);
		
		// Reset the player information, and test the endTurn(false)
		
		// Clear and Add a few rolls to the rollScore hash map
		rollScore.clear();
		rollScore.put(0, 50);
		rollScore.put(1, 100);
		rollScore.put(2, 150);
		
		// reset the roll score for the player
		player.setRollScore(rollScore);
		
		// reet the roll number for the player
		player.setRollNumber(3);
		
		// Clear and add a few turns to the turnScores array list
		turnScores.clear();
		turnScores.add(400);
		turnScores.add(300);
		
		// reet the turnScores for the player
		player.setTurnScores(turnScores);
		
		// reet the turn number for the player
		player.setTurnNumber(3);
		
		// reet the game score for the player
		player.setGameScore(700);
		
		// End turn via a bank, and test
		player.endTurn(false);
		
		assertTrue(300 == player.getTurnScores().get(2));
		assertEquals(1000, player.getGameScore());
		assertEquals(4, player.getTurnNumber());		
		assertEquals(0, player.getRollNumber());
		assertTrue(player.getRollScore().isEmpty());
		assertEquals(player.getRollScores(), 0);
	}
	
	/**
	 * Test the getGameScore() and setGameScore(int) methods
	 */
	@Test
	public void testGetAndSetGameScore() {
		
		// The player game score should initially be 0
		assertEquals(0, player.getGameScore());
		
		// Set the game score and test again
		player.setGameScore(1000);
		assertEquals(1000, player.getGameScore());
	}
	
	/**
	 * Test the getPlayerName() and setPlayerName(String) methods
	 */
	@Test
	public void testGetAndSetPlayerName() {
		// The player name should initially be null
		assertNull(player.getPlayerName());
		
		// Set the player name to something else and test it again
		player.setPlayerName("Jake");
		assertTrue(player.getPlayerName().equals("Jake"));
	}
	
	/**
	 * Test the getPlayerNumber() method
	 */
	@Test
	public void testGetPlayerNumber(){
		// The player number is set with the constructor 
		assertEquals(1, player.getPlayerNumber());
		
		// Initialize another player with a different number and test it
		Player playerTwo = new Player(2);
		assertEquals(2, playerTwo.getPlayerNumber());
	}
	
	/**
	 * Test the getRollNumber() and setRollNumber(int) methods
	 */
	@Test
	public void testGetAndSetRollNumber() {
		// The roll number should be initially set to 0
		assertEquals(0, player.getRollNumber());
		
		// Set the roll number and test again
		player.setRollNumber(5);
		assertEquals(5, player.getRollNumber());
		
		// Test with a negative number
		player.setRollNumber(-5);
		assertEquals(-5, player.getRollNumber());
	}
	
	/**
	 * Test the getRollScore() and setRollScore(HashMap<integer, integer>) methods
	 */
	@Test
	public void testGetAndSetRollScore() {
		// rollScore should initially be empty
		assertTrue(player.getRollScore().isEmpty());
		
		// Create a rollScore and add it to the player
		rollScore.put(0, 100);
		rollScore.put(1, 200);
		rollScore.put(2,  50);
		
		// Assign this rollScore to the player
		player.setRollScore(rollScore);
		
		// Test the getRollScore() method
		assertTrue(player.getRollScore().equals(rollScore));
		
		// Test with null
		player.setRollScore(null);
		assertNull(player.getRollScore());
	}
	
	/**
	 * Test getRollScores()
	 */
	@Test
	public void testGetRollScores() {
		// The rollScore should initially be null
		assertEquals(0, player.getRollScores());
		
		// Create a rollScore and add it to the player
		rollScore.put(0, 100);
		rollScore.put(1, 200);
		rollScore.put(2,  50);
		
		// Assign this rollScore to the player
		player.setRollScore(rollScore);
		
		// Test the rollScore
		assertEquals(350, player.getRollScores());
		
		// Check for negative numbers
		rollScore.clear();
		rollScore.put(0, -100);
		rollScore.put(1, -200);
		
		// Assign this rollScore to the player
		player.setRollScore(rollScore);
		
		// Test the rollScore
		assertEquals(-300, player.getRollScores());
	}
	
	/**
	 * Test the getTurnNumber() and setTurnNumnber(int) methods
	 */
	@Test
	public void testGetAndSetTurnNumber() {
		// turnNumber should initially be 1
		assertEquals(1, player.getTurnNumber());
		
		// Set the turn number to something else and retest
		player.setTurnNumber(5);
		assertEquals(5, player.getTurnNumber());
	}
	
	/**
	 * Test the getTurnScores() and setTurnScores(ArrayList<Integers>)
	 */
	@Test
	public void testGetAndSetTurnScorees() {
		// turnScores should initially be empty
		assertTrue(player.getTurnScores().isEmpty());
		
		// Set the turnScores and test it
		turnScores.add(400);
		turnScores.add(300);
		player.setTurnScores(turnScores);
		assertTrue(player.getTurnScores().equals(turnScores));
	}
	
	/**
	 * Test the getType() and setType(PlayerType) methods
	 */
	@Test
	public void testGetAndSetType() {
		// Initially, player should be set to PlayerType.USER
		assertEquals(PlayerType.USER, player.getType());
		
		// Change the type and test again
		player.setType(PlayerType.COMPUTER);
		assertEquals(PlayerType.COMPUTER, player.getType());
	}
	
	/**
	 * Test the resetRollScores() method
	 */
	@Test
	public void testResetRollScores() {
		
		// Assign several rolls to roll score
		rollScore.put(0, 100);
		rollScore.put(1,  150);
		rollScore.put(2,  50);
		
		// Assign rollScore to player
		player.setRollScore(rollScore);
		
		// Test to make sure the roll score for player is not empty
		assertTrue(!player.getRollScore().isEmpty());
		
		// Reset the roll score and test to make sure it's empty
		player.resetRollScores();
		assertTrue(player.getRollScore().isEmpty());
	}
	
	/**
	 * Test the resetTurnScores() method
	 */
	@Test
	public void testResetTurnScores() {
		
		// Assign several turns to turnScores
		turnScores.add(400);
		turnScores.add(300);
		turnScores.add(500);
		
		// Assign turnScores to player
		player.setTurnScores(turnScores);
		
		// Test to make sure the turn scores for player are not empty
		assertTrue(!player.getTurnScores().isEmpty());
		
		// Reset the turn scores and test to make sure it's empty
		player.resetTurnScores();
		assertTrue(player.getTurnScores().isEmpty());
	}
	
	/**
	 * Test the scoreRoll(int) method
	 */
	@Test
	public void testScoreRoll() {
		// The rollScore should initially be empty and rollNumber should be 0
		assertTrue(player.getRollScore().isEmpty());
		assertEquals(0, player.getRollNumber());
		
		// score a roll and test it
		player.scoreRoll(550);
		assertTrue(player.getRollScore().get(0).equals(550));
		
		// Change the roll number, add a rollScore and test again
		player.setRollNumber(1);
		player.scoreRoll(300);
		assertTrue(player.getRollScore().get(1).equals(300));
		assertEquals(850, player.getRollScores());
	}

}
