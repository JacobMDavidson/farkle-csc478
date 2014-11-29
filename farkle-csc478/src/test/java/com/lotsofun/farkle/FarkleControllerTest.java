package com.lotsofun.farkle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

/**
 * Test the Farkle Controller class
 * @author Curtis Brown
 * @version 3.0.0
 */
public class FarkleControllerTest {

	/**
	 * Test the setUI method
	 */
	@Test
	public void testSetUI() {
		// Create a new controller
		FarkleController controller = new FarkleController(true);
		
		// It's ui should be null
		assertEquals(controller.farkleUI, null);
		
		// setUI is called in by the FarklUI constructor
		FarkleUI ui = new FarkleUI(controller);
		FarkleUI currentUI = controller.farkleUI;
		
		// The ui instance ids should match
		assertEquals(ui, currentUI);
	}
	
	/**
	 * Test the farkle() method
	 */
	@Test
	public void testFarkle() {
		
		// Single player test
		FarkleOptionsDialog options = new FarkleOptionsDialog(null);
		options.setGameMode(GameMode.SINGLEPLAYER);
		options.setPlayer1Name("player1");
		
		FarkleController controller = new FarkleController(true);
		FarkleUI ui = new FarkleUI(controller);
		controller.newGame(options);
		Game game = controller.farkleGame;
		
		// Set up random game conditions
		ui.setRunningScore(50);
		assertEquals(ui.getRunningScore(), 50);
		ui.setRollScore(50);
		assertEquals(ui.getRollScore(), 50);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 0);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 1);
		
		// Call farkle
		controller.farkle();
		
		// Check for farkle conditions
		assertEquals(ui.getRunningScore(), 0);
		assertEquals(ui.getRollScore(), 0);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 6);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 1);
		
		
		// Multiplayer test
		options = new FarkleOptionsDialog(null);
		options.setGameMode(GameMode.MULTIPLAYER);
		options.setPlayer1Name("player1");
		options.setPlayer2Name("player2");
		
		controller = new FarkleController(true);
		ui = new FarkleUI(controller);
		controller.newGame(options);
		game = controller.farkleGame;
		
		// Set up random game conditions
		ui.setRunningScore(50);
		assertEquals(ui.getRunningScore(), 50);
		ui.setRollScore(50);
		assertEquals(ui.getRollScore(), 50);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 0);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 1);
		
		// Call farkle
		controller.farkle();
		
		// Check for farkle conditions
		assertEquals(ui.getRunningScore(), 0);
		assertEquals(ui.getRollScore(), 0);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 6);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 2);
	}
	
	/**
	 * Test the bank() method
	 */
	@Test
	public void testBank() {
		
		// Single player test
		FarkleOptionsDialog options = new FarkleOptionsDialog(null);
		options.setGameMode(GameMode.SINGLEPLAYER);
		options.setPlayer1Name("player1");
		
		FarkleController controller = new FarkleController(true);
		FarkleUI ui = new FarkleUI(controller);
		controller.newGame(options);
		Game game = controller.farkleGame;
		
		// Set up random game conditions
		ui.setRunningScore(50);
		assertEquals(ui.getRunningScore(), 50);
		ui.setRollScore(50);
		assertEquals(ui.getRollScore(), 50);
		game.processHold(50);
		assertEquals(ui.getGameScore(1), 0);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 0);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 1);
		
		// Call bank
		controller.bank();
		
		// Check for bank conditions
		assertEquals(ui.getRunningScore(), 0);
		assertEquals(ui.getRollScore(), 0);
		assertEquals(ui.getGameScore(1), 50);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 6);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 1);
		
		
		// Multiplayer test
		options = new FarkleOptionsDialog(null);
		options.setGameMode(GameMode.MULTIPLAYER);
		options.setPlayer1Name("player2");
		
		controller = new FarkleController(true);
		ui = new FarkleUI(controller);
		controller.newGame(options);
		game = controller.farkleGame;
		
		// Set up random game conditions
		ui.setRunningScore(50);
		assertEquals(ui.getRunningScore(), 50);
		ui.setRollScore(50);
		assertEquals(ui.getRollScore(), 50);
		game.processHold(50);
		assertEquals(ui.getGameScore(1), 0);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 0);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 1);
		
		// Call bank
		controller.bank();
		
		// Check for bank conditions
		assertEquals(ui.getRunningScore(), 0);
		assertEquals(ui.getRollScore(), 0);
		assertEquals(ui.getGameScore(1), 50);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 6);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 2);
	}
	
	/**
	 * Test the checkHighScore() method
	 */
	@Test
	public void testCheckHighScore() {
		
		// Single player test
		FarkleOptionsDialog options = new FarkleOptionsDialog(null);
		options.setGameMode(GameMode.SINGLEPLAYER);
		options.setPlayer1Name("player1");
		
		FarkleController controller = new FarkleController(true);
		FarkleUI ui = new FarkleUI(controller);
		controller.newGame(options);
		Game game = controller.farkleGame;
		
		// Set up random game conditions
		ui.setRunningScore(5000);
		assertEquals(ui.getRunningScore(), 5000);
		ui.setRollScore(5000);
		assertEquals(ui.getRollScore(), 5000);
		game.processHold(5000);
		assertEquals(ui.getGameScore(1), 0);
		game.setHighScore(5000);
		assertEquals(game.getHighScore(), 5000);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 0);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 1);
		assertFalse(controller.checkHighScore(game.getPlayerNumberForCurrentPlayer()));
				
		// Call Bank
		controller.bank();
		assertEquals(ui.getRunningScore(), 0);
		assertEquals(ui.getRollScore(), 0);
		assertEquals(ui.getGameScore(1), 5000);
		assertEquals(ui.getDice(DieState.DISABLED).size(), 6);
		assertEquals(game.getPlayerNumberForCurrentPlayer(), 1);
		
		// Set up another roll
		game.processHold(50);
		assertEquals(ui.getGameScore(1), 5000);
		assertEquals(game.getHighScore(), 5000);
		assertTrue(controller.checkHighScore(game.getPlayerNumberForCurrentPlayer()));
	}
	
	/**
	 * Test the resetHighScore() method
	 */
	@Test
	public void testResetHighScore() {
		
		// Single player test
		FarkleOptionsDialog options = new FarkleOptionsDialog(null);
		options.setGameMode(GameMode.SINGLEPLAYER);
		options.setPlayer1Name("player1");
		
		FarkleController controller = new FarkleController(true);
		FarkleUI ui = new FarkleUI(controller);
		controller.newGame(options);
		Game game = controller.farkleGame;
		
		// Set the high score to 5000
		controller.setUIHighScore(5000);
		game.setHighScore(5000);
		assertEquals(game.getHighScore(), 5000);
		
		// Call resetHighScore
		controller.resetHighScore();
		
		// High score should be 0
		assertEquals(ui.getHighScore(), 0);
		assertEquals(game.getHighScore(), 0);
	}
	
	/**
	 * Test the tryToEndGame() method
	 */
	@Test
	public void testTryToEndGame() {
		
		// Single player test
		FarkleOptionsDialog options = new FarkleOptionsDialog(null);
		options.setGameMode(GameMode.SINGLEPLAYER);
		options.setPlayer1Name("player1");
		
		FarkleController controller = new FarkleController(true);
		@SuppressWarnings("unused")
		FarkleUI ui = new FarkleUI(controller);
		controller.newGame(options);
		Game game = controller.farkleGame;
		
		// Test the isLastTurn flag for all turns less than 10
		for(int i = 1; i < 10; i++) {
			game.getPlayers()[0].setTurnNumber(i);
			assertEquals(game.getTurnNumberForCurrentPlayer(), i);
			controller.tryToEndGame();
			assertEquals(controller.isLastTurn, false);
		}
		
		// Test isLastTurn flag on the tenth turn
		game.getPlayers()[0].setTurnNumber(10);
		controller.tryToEndGame();
		assertEquals(controller.isLastTurn, true);
	
	
		// Multiplayer test
		options = new FarkleOptionsDialog(null);
		options.setGameMode(GameMode.MULTIPLAYER);
		options.setPlayer1Name("player1");
		options.setPlayer2Name("player2");
		
		controller = new FarkleController(true);
		ui = new FarkleUI(controller);
		controller.newGame(options);
		game = controller.farkleGame;
		
		// Test that the point threshold is 10000
		assertEquals(controller.POINT_THRESHOLD, 10000);
		
		// Test the isLastTurn flag for all scores less than point threshold
		for(int i = 0; i < 10000; i++) {
			game.getPlayers()[0].setGameScore(i);
			assertEquals(game.getGameScoreForPlayer(1), i);
			controller.tryToEndGame();
			assertFalse(controller.isLastTurn);
		}
		
		// Check isLastTurn flag for a score equal to point threshold
		game.getPlayers()[0].setGameScore(10000);
		assertEquals(game.getGameScoreForPlayer(1), 10000);
		controller.tryToEndGame();
		assertTrue(controller.isLastTurn);
	}
	
	/**
	 * Test the endGame() method
	 */
	@Test
	public void testEndGame() {
		
		int testNumber = 0;
		FarkleOptionsDialog options = null;
		FarkleController controller = null;
		FarkleUI ui = null;
		Game game = null;
		
		do {
			
			// Single player test
			options = new FarkleOptionsDialog(null);
			options.setGameMode(GameMode.SINGLEPLAYER);
			options.setPlayer1Name("player1");
			
			controller = new FarkleController(true);
			ui = new FarkleUI(controller);
			controller.newGame(options);
			game = controller.farkleGame;
			
			// Set preconditions for game.resetGame test
			game.processHold(50);
			controller.bank();
			assertEquals(game.getGameScoreForPlayer(1), 50);
			game.processHold(100);
			controller.bank();
			assertEquals(game.getGameScoreForPlayer(1), 150);
			game.processHold(150);
			controller.bank();
			assertEquals(game.getGameScoreForPlayer(1), 300);
			assertEquals(game.getTurnNumberForCurrentPlayer(), 4);
			assertEquals(game.getPlayers()[0].getTurnScores().size(), 3);
			
			HashMap<Integer, Integer> rollScores = new HashMap<Integer, Integer>();
			rollScores.put(1,  50);
			rollScores.put(2,  100);
			game.getPlayers()[0].setRollScore(rollScores);
			assertEquals(game.getPlayers()[0].getRollScores(), 150);
			assertEquals(ui.getTurnScore(1, 1), 50);
			
			ArrayList<Die> dice = ui.getDice(DieState.UNHELD);
			assertEquals(dice.size(), 0);
			dice = ui.getDice(DieState.DISABLED);
			assertEquals(dice.size(), 6);
			for(Die d : dice) {
				d.setValue('3');
				assertEquals(d.getValue(), 3);
			}
		
			if(testNumber == 0) {
				
				// Call EndGame with replay = true
				controller.endGame(false, false, true);
				
				assertEquals(game.getGameScoreForPlayer(1), 0);
				assertEquals(game.getTurnNumberForCurrentPlayer(), 1);
				assertEquals(game.getPlayers()[0].getTurnScores().size(), 0);
				assertEquals(game.getPlayers()[0].getRollScores(), 0);
				assertEquals(ui.getTurnScore(1, 1), 0);
				
				dice = ui.getDice(DieState.UNHELD);
				assertEquals(dice.size(), 6);
				dice = ui.getDice(DieState.DISABLED);
				assertEquals(dice.size(), 0);
				for(Die d : dice) {
					assertEquals(d.getValue(), 0);
				}
			}
		
			if(testNumber == 1) {
				
				// Call EndGame with replay = false
				controller.endGame(false, false, false);
				controller.newGame(options);
				assertFalse(null == options);
				
				assertEquals(game.getGameScoreForPlayer(1), 0);
				assertEquals(game.getTurnNumberForCurrentPlayer(), 1);
				assertEquals(game.getPlayers()[0].getTurnScores().size(), 0);
				assertEquals(game.getPlayers()[0].getRollScores(), 0);
				assertEquals(ui.getTurnScore(1, 1), 0);
				
				
				dice = ui.getDice(DieState.UNHELD);
				assertEquals(dice.size(), 6);
				dice = ui.getDice(DieState.DISABLED);
				assertEquals(dice.size(), 0);
				for(Die d : dice) {
					assertEquals(d.getValue(), 0);
				}
			}
			
			testNumber++;
		} while(testNumber < 2);
	}
}
