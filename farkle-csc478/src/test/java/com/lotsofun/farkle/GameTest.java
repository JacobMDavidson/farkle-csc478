package com.lotsofun.farkle;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {

	/**
	 * Test Game constructor
	 */
	@Test
	public void testCreateGame() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Game g = new Game(GameMode.SINGLEPLAYER, c);
		assertNotNull(c);
	}
	
}

