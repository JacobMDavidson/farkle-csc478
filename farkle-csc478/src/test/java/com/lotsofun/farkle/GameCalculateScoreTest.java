package com.lotsofun.farkle;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameCalculateScoreTest {

	private Game game;
	List<List<Integer>> myPermutations;
	List<String> automatedPermutations;
	int score = 0;
	int dieOneValue = 0;
	int dieTwoValue = 0;
	
	@Before
	public void setUp() throws Exception {
		game = new Game(GameMode.SINGLEPLAYER, new FarkleController());
		myPermutations = new LinkedList<List<Integer>>();	
	}
	
	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 1 die
	 */
	@Test
	public void testCalculateScore1(){
		
		/* Test 1: Check scoring for a die roll of A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of die not including 1 or 5
		automatedPermutations = integerPermutations("A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}	
		
		/* Test 2: Check scoring for a die roll of 5 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("5");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(50, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(50, score);
		}
		
		/* Test 3: Check scoring for a die roll of 1 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("1");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(100, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(100, score);
		}
	}
	
	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 2 dice
	 */
	@Test
	public void testCalculateScore2(){
		
		/* Test 4: Check scoring for a die roll of AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}	
		
		/* Test 5: Check scoring for a die roll of AX (A and X represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 6: Check scoring for a die roll of 5X (X represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5X");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 7: Check scoring for a die roll of 1X (X represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1X");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 8: Check scoring for a die roll of 55 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("55");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(100, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(100, score);
		}
		
		/* Test 9: Check scoring for a die roll of 15 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("15");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(150, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(150, score);
		}
		
		/* Test 10: Check scoring for a die roll of 11 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("11");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(200, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(200, score);
		}
		
	}
	
	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 3 dice
	 */
	@Test
	public void testCalculateScore3(){	
		
		/* Test 11: Check scoring for a die roll of AXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 12: Check scoring for a die roll of AAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 13: Check scoring for a die roll of 5AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 14: Check scoring for a die roll of 5AX (A and X represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 15: Check scoring for a die roll of 1AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 16: Check scoring for a die roll of 1AX (A and X represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 17: Check scoring for a die roll of 55A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 18: Check scoring for a die roll of 15A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 19: Check scoring for a die roll of 11A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 20: Check scoring for a die roll of 155 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("155");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(200, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(200, score);
		}
		
		/* Test 21: Check scoring for a die roll of 115 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("115");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(250, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(250, score);
		}
		
		/* Test 22: Check scoring for a die roll of AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Get the die value
				dieOneValue = currentNumbers.get(0);
				
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue, score);
			}
		}	
		
		/* Test 23: Check scoring for a die roll of 555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(500, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(500, score);
		}
		
		/* Test 24: Check scoring for a die roll of 111 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("111");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(1000, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(1000, score);
		}
	}

	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 4 dice
	 */
	@Test
	public void testCalculateScore4(){
		
		/* Test 25: Check scoring for a die roll of AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 26: Check scoring for a die roll of AAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 27: Check scoring for a die roll of AAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 28: Check scoring for a die roll of 5AXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 29: Check scoring for a die roll of 5AAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 30: Check scoring for a die roll of 1AXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 31: Check scoring for a die roll of 1AAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 32: Check scoring for a die roll of 55AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 33: Check scoring for a die roll of 55AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 34: Check scoring for a die roll of 15AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 35: Check scoring for a die roll of 15AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 36: Check scoring for a die roll of 11AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 37: Check scoring for a die roll of 55AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 38: Check scoring for a die roll of 155A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("155A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
				
		/* Test 39: Check scoring for a die roll of 115A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("115A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(250, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 40: Check scoring for a die roll of AAAX (A and X represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 41: Check scoring for a die roll of 555A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("555A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(500, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 42: Check scoring for a die roll of 111A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("111A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 43: Check scoring for a die roll of 5AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue + 50, score);
			}
		}
		
		/* Test 44: Check scoring for a die roll of 1AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue + 100, score);
			}
		}
		
		/* Test 45: Check scoring for a die roll of 1555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("1555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(600, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(600, score);
		}
		
		/* Test 46: Check scoring for a die roll of 5111 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("5111");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(1050, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(1050, score);
		}	
		
		/* Test 47: Check scoring for a die roll of AAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue * 2, score);
			}
		}
		
		/* Test 48: Check scoring for a die roll of 5555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("5555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(1000, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(1000, score);
		}
		
		/* Test 49: Check scoring for a die roll of 1111 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("1111");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(2000, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(2000, score);
		}
	}

	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 5 dice
	 */
	@Test
	public void testCalculateScore5(){
		/* Test 50: Check scoring for a die roll of AAXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 51: Check scoring for a die roll of AAXXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAXXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 52: Check scoring for a die roll of 5AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 53: Check scoring for a die roll of 5AAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 54: Check scoring for a die roll of 5AAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 55: Check scoring for a die roll of 1AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 56: Check scoring for a die roll of 1AAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 57: Check scoring for a die roll of 1AAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 58: Check scoring for a die roll of 55AXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 59: Check scoring for a die roll of 55AAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 60: Check scoring for a die roll of 15AXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 61: Check scoring for a die roll of 15AAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 62: Check scoring for a die roll of 11AXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 63: Check scoring for a die roll of 11AAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 64: Check scoring for a die roll of 155AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("155AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 65: Check scoring for a die roll of 155AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("155AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 66: Check scoring for a die roll of 115AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("115AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(250, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 67: Check scoring for a die roll of 115AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("115AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(250, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 68: Check scoring for a die roll of AAAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 69: Check scoring for a die roll of AAAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 70: Check scoring for a die roll of 5AAAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50 + 100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 71: Check scoring for a die roll of 1AAAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 + 100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 72: Check scoring for a die roll of 55AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 + 100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 + 100 * dieOneValue, score);
			}
		}
		
		/* Test 73: Check scoring for a die roll of 15AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(150 + 100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(150 + 100 * dieOneValue, score);
			}
		}
		
		/* Test 74: Check scoring for a die roll of 11AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200 + 100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(200 + 100 * dieOneValue, score);
			}
		}
		
		/* Test 75: Check scoring for a die roll of 555AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("555AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(500, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 76: Check scoring for a die roll of 555AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("555AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(500, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 77: Check scoring for a die roll of 1555A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1555A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(600, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 78: Check scoring for a die roll of 11555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("11555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(700, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(700, score);
		}
		
		/* Test 79: Check scoring for a die roll of 111AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("111AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 80: Check scoring for a die roll of 111AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("111AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 81: Check scoring for a die roll of 1115A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1115A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1050, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 82: Check scoring for a die roll of 11155 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("11155");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(1100, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(1100, score);
		}
		
		/* Test 83: Check scoring for a die roll of AAAAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 84: Check scoring for a die roll of 5AAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200 * dieOneValue + 50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(200 * dieOneValue + 50, score);
			}
		}
		
		/* Test 85: Check scoring for a die roll of 1AAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200 * dieOneValue + 100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(200 * dieOneValue + 100, score);
			}
		}
		
		
		/* Test 86: Check scoring for a die roll of 5555A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5555A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 87: Check scoring for a die roll of 15555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("15555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(1100, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(1100, score);
		}
		
		/* Test 88: Check scoring for a die roll of 1111A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1111A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(2000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 89: Check scoring for a die roll of 11115 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("11115");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(2050, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(2050, score);
		}
		
		/* Test 90: Check scoring for a die roll of AAAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(400 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(400 * dieOneValue, score);
			}
		}
		
		/* Test 91: Check scoring for a die roll of 55555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("55555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(2000, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(2000, score);
		}
		
		/* Test 92: Check scoring for a die roll of 11111 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("11111");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(4000, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(4000, score);
		}
	}
	
	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 6 dice
	 */
	@Test
	public void testCalculateScore6(){
		
		/* Test 93: Check scoring for a die roll of AAXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAXXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(0, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 94: Check scoring for a die roll of AAXXYY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAXXYY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(750, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(750, score);
			}
		}
		
		/* Test 95: Check scoring for a die roll of 11AAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(750, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(750, score);
			}
		}
		
		/* Test 96: Check scoring for a die roll of 55AAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(750, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(750, score);
			}
		}
		
		/* Test 97: Check scoring for a die roll of 1155AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1155AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(750, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(750, score);
			}
		}
		
		/* Test 98: Check scoring for a die roll of 1AXY5Z (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AXY5Z");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1500, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(1500, score);
			}
		}
		
		/* Test 99: Check scoring for a die roll of 5AAXXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAXXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 100: Check scoring for a die roll of 5AAXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 101: Check scoring for a die roll of 1AAXXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAXXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 102: Check scoring for a die roll of 1AAXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}		
		
		/* Test 103: Check scoring for a die roll of 55AAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 104: Check scoring for a die roll of 55AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}	
		
		/* Test 105: Check scoring for a die roll of 15AAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 106: Check scoring for a die roll of 15AAXx (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 107: Check scoring for a die roll of 11AAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 108: Check scoring for a die roll of 11AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 109: Check scoring for a die roll of 155AAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("155AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}	
		
		/* Test 110: Check scoring for a die roll of 155AXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("155AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}		

		/* Test 111: Check scoring for a die roll of 115AAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("115AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(250, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}	
		
		/* Test 112: Check scoring for a die roll of 115AXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("115AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(250, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}	
		
		/* Test 113: Check scoring for a die roll of AAAXXY (A, X and Y represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAXXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 114: Check scoring for a die roll of AAAXYZ (A, X, Y and Z represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAXYZ");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
	
		/* Test 115: Check scoring for a die roll of 555AAX (A and X represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("555AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(500, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 116: Check scoring for a die roll of 555AXY (A, X and Y represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("555AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(500, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}		
		
		/* Test 117: Check scoring for a die roll of 111AAX (A and X represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("111AAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 118: Check scoring for a die roll of 111AXY (A, X and Y represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("111AXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}		
		
		/* Test 119: Check scoring for a die roll of 1AAAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 120: Check scoring for a die roll of 1AAAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 121: Check scoring for a die roll of 5AAAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 122: Check scoring for a die roll of 5AAAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 123: Check scoring for a die roll of 55AAAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 124: Check scoring for a die roll of 15AAAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 125: Check scoring for a die roll of 11AAAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
				
		/* Test 126: Check scoring for a die roll of 155AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("155AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(3));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue + 200, score);
			}
		}
		
		/* Test 127: Check scoring for a die roll of 115AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("115AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(3));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 250, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue + 250, score);
			}
		}
		
		/* Test 128: Check scoring for a die roll of AAAXXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAXXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Get the die value for calculating the score
			dieTwoValue = Character.getNumericValue(numbers.charAt(3));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 100 * dieTwoValue, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue + 100 * dieTwoValue, score);
			}
		}
				
		/* Test 129: Check scoring for a die roll of 555AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("555AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(3));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 500, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue + 500, score);
			}
		}
		
		/* Test 130: Check scoring for a die roll of 111AAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("111AAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(3));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue + 1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue + 1000, score);
			}
		}
		
		/* Test 131: Check scoring for a die roll of 111555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("111555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(1500, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(1500, score);
		}		
		
		/* Test 132: Check scoring for a die roll of 5551AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5551AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(600, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 133: Check scoring for a die roll of 5551AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5551AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(600, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 134: Check scoring for a die roll of 55511A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55511A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(700, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 135: Check scoring for a die roll of 1115AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1115AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1050, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 136: Check scoring for a die roll of 1115AX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1115AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1050, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 137: Check scoring for a die roll of 11155A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11155A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		
		/* Test 138: Check scoring for a die roll of AAAAXX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 139: Check scoring for a die roll of AAAAXY (A, X and Y each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAAXY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 140: Check scoring for a die roll of 5555AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5555AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 141: Check scoring for a die roll of 5555AX (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5555AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 142: Check scoring for a die roll of 1111AA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1111AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(2000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 143: Check scoring for a die roll of 1111AX (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1111AX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(2000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 144: Check scoring for a die roll of 1AAAAX (A amd X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 + 100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 145: Check scoring for a die roll of 5AAAAX (A amd X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 + 50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 146: Check scoring for a die roll of 55AAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 + 100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue * 2 + 100, score);
			}
		}
				
		/* Test 147: Check scoring for a die roll of 15AAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15AAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 + 150, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue * 2 + 150, score);
			}
		}
		
		/* Test 148: Check scoring for a die roll of 11AAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 + 200, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue * 2 + 200, score);
			}
		}
		
		/* Test 149: Check scoring for a die roll of 15555A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("15555A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(1100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 150: Check scoring for a die roll of 115555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("115555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(1200, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(1200, score);
		}
		
		/* Test 151: Check scoring for a die roll of 11115A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11115A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(2050, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}
		
		/* Test 152: Check scoring for a die roll of 111155 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("111155");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(2100, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(2100, score);
		}
		
		/* Test 153: Check scoring for a die roll of AAAAAX (A and X each represent all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAAAX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 * 2, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}		
		
		/* Test 154: Check scoring for a die roll of 5AAAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("5AAAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 * 2 + 50, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue * 2 * 2 + 50, score);
			}
		}	
		
		/* Test 155: Check scoring for a die roll of 1AAAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1AAAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 * 2 + 100, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue * 2 * 2 + 100, score);
			}
		}	

		/* Test 156: Check scoring for a die roll of 55555A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55555A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(2000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}

		/* Test 157: Check scoring for a die roll of 155555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("155555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(2100, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(2100, score);
		}
		
		/* Test 158: Check scoring for a die roll of 11111A (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11111A");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(4000, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(0, score);
			}
		}	
		
		/* Test 159: Check scoring for a die roll of 111115 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("111115");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(4050, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(4050, score);
		}
		
		/* Test 160: Check scoring for a die roll of AAAAAA (A represents all die values other than 1 or 5) */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAAAAA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(1));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100 * dieOneValue * 2 * 2 * 2, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(100 * dieOneValue * 2 * 2 * 2, score);
			}
		}
		
		/* Test 161: Check scoring for a die roll of 555555 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("555555");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(4000, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(4000, score);
		}
		
		/* Test 162: Check scoring for a die roll of 111111 */
		
		// Generate all permutations of the roll and store in a list of lists of integers
		myPermutations = permutations("111111");
		
		// Check each list of integers for correct scoring
		for(List<Integer> numbers : myPermutations)
		{
			// Test the total score
			score = game.calculateScore(numbers, false);
			assertEquals(8000, score);
			
			// Test the held score
			score = game.calculateScore(numbers, true);
			assertEquals(8000, score);
		}

	}
	
	// Helper methods for determining permutations
	
	/**
	 * Returns a list of lists of permutations of integers for a given string of integers
	 * @param myString string of integers used to find all distinct permutations
	 * @return List of Lists of integers representing disting permutations 
	 */
	public static List<List<Integer>> permutations(String myString){
		
		// Retrieve a list of all distinct permutations of the string
		List<String> myList = stringPermutations(myString);
		
		// Convert the list of strings to a list of lists of integers
		List<List<Integer>> myIntegers = new LinkedList<List<Integer>>();
		
		for(String number : myList)
		{
			List<Integer> currentPermutation = new LinkedList<Integer>();
			for(int i = 0; i < number.length(); i++)
			{
				currentPermutation.add(Character.getNumericValue(number.charAt(i)));
			}
			myIntegers.add(currentPermutation);
		}
		
		
		return myIntegers;
	}
	
	/**
	 * Find all permutations of a string of characters
	 * @param s string of characters for which all permutations are to be found
	 * @return List of strings representing all distinct permutations of the input string
	 */
	public static List<String> stringPermutations(String s) {
        if (s == null) {
            return null;
        }

        List<String> resultList = new LinkedList<String>();
        int length = s.length();
        if (length < 2) {
            resultList.add(s);
            return resultList;
        }

        
        char currentChar;

        for (int i = 0; i < length; i++) {
            currentChar = s.charAt(i);

            String subString = s.substring(0, i) + s.substring(i + 1);

            List<String> subPermutations = stringPermutations(subString);

            for (String item : subPermutations) {
                if(!resultList.contains(currentChar + item))
                	resultList.add(currentChar + item);
            }
        }

        return resultList;
    }
	/**
	 * 
	 * @param myString input string to be converted, with variables as placeholders for dice that could be 2,3,4 or 6
	 * @return List of strings with all permutations of 2, 3, 4, or 6 valued die in place of variables in the input string
	 */
	public static List<String> integerPermutations(String myString) {
		int numberOfVariables;
		List<Character> presentVariables = new LinkedList<Character>();
		List<String> rollPermutations = new LinkedList<String>();
		char currentCharacter;
		int length = myString.length();
		String currentPermutation = "";
		
		// Retrieve the different variables in myString
		for(int i = 0; i < length; i++)
		{	
			currentCharacter = myString.charAt(i);
			
			// If the current character is a letter, and the variables list is empty or the variables list does not contain the letter
			// Add it to the variables list
			if(Character.isLetter(currentCharacter) && 
					(presentVariables.isEmpty() || !presentVariables.contains(currentCharacter)))
			{
				presentVariables.add(currentCharacter);
			}
		}
		
		// Determine the number of distinct variables present in myString
		numberOfVariables = presentVariables.size();
		
		if(numberOfVariables > 0 && numberOfVariables <= 4)
		{
			for(int i = 2; i <= 6; i++)
			{
				if(i == 5)
					continue;
				currentPermutation += i;
				if(numberOfVariables > 1)
				{
					for(int j = 2; j <= 6; j++)
					{
						if(j == i || j == 5)
							continue;
						currentPermutation += j;
						if(numberOfVariables > 2)
						{
							for(int k = 2; k <= 6; k++)
							{
								if(k == i || k == j || k == 5)
									continue;
								currentPermutation += k;
								if(numberOfVariables > 3)
								{
									for(int l = 2; l <= 6; l++)
									{
										if(l == k || l == j || l == i || l == 5)
											continue;	
										// Clear the currentPermutation String
										currentPermutation = "";
										
										for(int x = 0; x < length; x++)
										{	
											
											currentCharacter = myString.charAt(x);
											
											// If the current character is a letter, and the variables list is empty or the variables list does not contain the letter
											// Add it to the variables list
											if(Character.isLetter(currentCharacter))
											{
												switch(presentVariables.indexOf(currentCharacter))
												{
													case 0:
														currentPermutation += i;
														break;
													case 1:
														currentPermutation += j;
														break;
													case 2:
														currentPermutation += k;
														break;
													case 3:
														currentPermutation += l;
														break;
												}
											}
											else
											{
												currentPermutation += currentCharacter;
											}
										}
										rollPermutations.add(currentPermutation);
									}
								}
								else
								{
									// Clear the currentPermutation String
									currentPermutation = "";
									
									for(int x = 0; x < length; x++)
									{
										
										currentCharacter = myString.charAt(x);
										
										// If the current character is a letter, and the variables list is empty or the variables list does not contain the letter
										// Add it to the variables list
										if(Character.isLetter(currentCharacter))
										{
											switch(presentVariables.indexOf(currentCharacter))
											{
												case 0:
													currentPermutation += i;
													break;
												case 1:
													currentPermutation += j;
													break;
												case 2:
													currentPermutation += k;
													break;
											}
										}
										else
										{
											currentPermutation += currentCharacter;
										}
									}
									rollPermutations.add(currentPermutation);
								}
							}
						}
						else
						{
							// Clear the currentPermutation String
							currentPermutation = "";
							
							for(int x = 0; x < length; x++)
							{	
								
								currentCharacter = myString.charAt(x);
								
								// If the current character is a letter, and the variables list is empty or the variables list does not contain the letter
								// Add it to the variables list
								if(Character.isLetter(currentCharacter))
								{
									switch(presentVariables.indexOf(currentCharacter))
									{
										case 0:
											currentPermutation += i;
											break;
										case 1:
											currentPermutation += j;
											break;
									}
								}
								else
								{
									currentPermutation += currentCharacter;
								}
							}	
							rollPermutations.add(currentPermutation);
						}
					}
				}
				else
				{
					// Clear the currentPermutation String
					currentPermutation = "";
					
					for(int x = 0; x < length; x++)
					{	

						
						currentCharacter = myString.charAt(x);
						
						// If the current character is a letter, and the variables list is empty or the variables list does not contain the letter
						// Add it to the variables list
						if(Character.isLetter(currentCharacter))
						{
							currentPermutation += i;
									
						}
						else
						{
							currentPermutation += currentCharacter;
						}
					}
					rollPermutations.add(currentPermutation);
				}
			}
		}
		
		// If more than 4 variables were in the string, return null
		else if(numberOfVariables > 4)
		{
			return null;
		}
		else
		{
			// There are no variables present, simply add myString to the rollPermutations
			rollPermutations.add(myString);
		}
		
		return rollPermutations;
	}
}
