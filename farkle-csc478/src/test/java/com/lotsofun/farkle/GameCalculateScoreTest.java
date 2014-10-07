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
