package com.lotsofun.farkle;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class GameTest {

	@Test
	public void testGameConstructor() {
		FarkleController controller = new FarkleController();
		assertNotNull(controller);
		
		// Create a new game object with every
		// possible paramter
		Game game = new Game(GameMode.MULTIPLAYER, controller);
		assertNotNull(game);
		game = null;
		assertNull(game);
		game = new Game(GameMode.SINGLEPLAYER, controller);
		assertNotNull(game);
	}
	
	
	/**
	 * See GameCalculateScoreTest.java for this
	 * method's test
	 */
	@Test
	public void testCalculateScore() {
		/**
		 * Implemented in GameCalculateScoreTest.java
		 */
		assertTrue(true);
	}
	
	
	
	@Test
	public void testFarkle() {
		GameMode gameMode = GameMode.SINGLEPLAYER;
		Map<Integer, PlayerType> playerSet = new HashMap<Integer, PlayerType>();
		playerSet.put(1, PlayerType.USER);		
		
		FarkleTestHelper helper = new FarkleTestHelper(gameMode, playerSet);
		synchronized(helper) {
			helper.initializeFarkle();
		}
		assertNotNull(helper);
		
		FarkleController controller = helper.getController();
		assertNotNull(controller);
		
		FarkleUI uI = helper.getUI();
		assertNotNull(uI);
		
		Game game = helper.getGame();
		assertNotNull(game);
		
		// Get player and make sure it's not null
		Player player = game.getCurrentPlayer();
		assertNotNull(player);
		
		// Make sure that it's a user
		assertEquals(player.getType(), PlayerType.USER);
		
		// Make sure that it's player 1
		assertEquals(player.getPlayerNumber() + 1, 1);
		
		// Check that the player's score is 0 for turn 0
		game.farkle();
		assertEquals(player.getTurnScores()[0], 0);

		// Check that the player's turn number has been incremented
		assertEquals(player.getTurnNumber(), 2);
		
		// Kill the tester
//		helper.dispose();
		
	}

}
