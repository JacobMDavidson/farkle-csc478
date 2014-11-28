package com.lotsofun.farkle;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * The GameCalculateScoreTest class tests the calculateScore(List<Integer>, boolean) and the 
 * calculateHighestScore(List<Integer>) methods and the of the Game class for every
 * possible combination of dice to ensure they comply with the requirement 6.0.0, scoring,  of the 
 * specific requirements in the requirements document. This includes rolls of 1, 2, 3, 4, 5 or 6 
 * dice. 
 * @author Jacob Davidson
 * @version 3.0.0
 */
public class GameCalculateScoreTest {

	/** The game object used for testing */
	private Game game;
	
	/** The FarkleController object used for testing */
	private FarkleController farkleController;
	
	/** The list of lists of permutations for a given roll to be tested */
	List<List<Integer>> myPermutations;
	
	/** List of strings representing rolls for permutations of die that are not 1 or 5 */
	List<String> automatedPermutations;
	
	/** Retrieved score for a given roll */
	int score = 0;
	
	/** Three of kind die value */
	int dieOneValue = 0;
	
	/** The value of the second three of a kind die if two three of a kinds rolled at once */
	int dieTwoValue = 0;
	
	/** The object array used to store the returned array from the calculateHighestScore method */
	Object[] highestScore;
	
	/** List of integers indicating the highest roll */
	List<Integer> highestRoll;
	
	/**
	 * Instantiate the objects before testing begins
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		// Instantiate the FarkleController object
		farkleController = new FarkleController(true);
		
		// Instantiate the game option. The Game Mode used does not change the results of this test
		game = new Game(GameMode.SINGLEPLAYER, farkleController);
		
	}
	
	/**
	 * Test the helper methods that calculate the dice roll permutations used to check
	 * the calculateScore method
	 */
	@Test
	public void testPermutationHelperMethods(){
		
		/* Test the permutations method */
		
		// If null is passed to permutations, null should be returned
		assertEquals(null, permutations(null));
		
		// If letters anything but digits are in the string, null should be returned
		assertEquals(null, permutations("ABC"));
		
		// Check permutations returned from a string of integers
		List<List<Integer>> permutationsToTest = permutations("123");
		assertEquals(6, permutationsToTest.size());
		List<Integer> numbers = new LinkedList<Integer>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		assertTrue(permutationsToTest.contains(numbers));
		numbers.clear();
		numbers.add(1);
		numbers.add(3);
		numbers.add(2);
		assertTrue(permutationsToTest.contains(numbers));
		numbers.clear();
		numbers.add(2);
		numbers.add(1);
		numbers.add(3);
		assertTrue(permutationsToTest.contains(numbers));		
		numbers.clear();
		numbers.add(2);
		numbers.add(3);
		numbers.add(1);
		assertTrue(permutationsToTest.contains(numbers));
		numbers.clear();
		numbers.add(3);
		numbers.add(1);
		numbers.add(2);
		assertTrue(permutationsToTest.contains(numbers));
		numbers.clear();
		numbers.add(3);
		numbers.add(2);
		numbers.add(1);
		assertTrue(permutationsToTest.contains(numbers));
		
		/* Test the stringPermutations method */
		
		// If null is passed to stringPermutations, null should be returned
		assertEquals(null, stringPermutations(null));
		
		// Check permutations returned from a string
		List<String> stringPermutationsToTest = stringPermutations("123");
		assertEquals(6, stringPermutationsToTest.size());
		assertTrue(stringPermutationsToTest.contains("123"));
		assertTrue(stringPermutationsToTest.contains("132"));
		assertTrue(stringPermutationsToTest.contains("213"));
		assertTrue(stringPermutationsToTest.contains("231"));
		assertTrue(stringPermutationsToTest.contains("321"));
		assertTrue(stringPermutationsToTest.contains("312"));
		
		/* Test the integerPermutations method */
		
		// If null is passed to integerPermutations, null should be returned
		assertEquals(null, integerPermutations(null));
		
		// If a string with no variables is passed to integerPermutations, return that 
		// String should be the only string in the returned list
		assertEquals(1, integerPermutations("123").size());
		assertTrue(integerPermutations("123").contains("123"));
		
		// Check the permutations returned from a string with variables included
		List<String> integerPermutationsToTest = integerPermutations("AB");
		assertEquals(12, integerPermutationsToTest.size());
		assertTrue(integerPermutationsToTest.contains("23"));
		assertTrue(integerPermutationsToTest.contains("24"));
		assertTrue(integerPermutationsToTest.contains("26"));
		assertTrue(integerPermutationsToTest.contains("32"));
		assertTrue(integerPermutationsToTest.contains("34"));
		assertTrue(integerPermutationsToTest.contains("36"));
		assertTrue(integerPermutationsToTest.contains("42"));
		assertTrue(integerPermutationsToTest.contains("43"));
		assertTrue(integerPermutationsToTest.contains("46"));
		assertTrue(integerPermutationsToTest.contains("62"));
		assertTrue(integerPermutationsToTest.contains("63"));
		assertTrue(integerPermutationsToTest.contains("64"));
		
	}
	
	/**
	 * This method tests the fringe conditions of the calculateScore method of the 
	 * Game class
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCalculateScoreFringe(){
		
		// The list used for testing in this method
		List<Integer> roll = new LinkedList<Integer>();
		
		// Returned score
		int score = 0;
		
		// Test for a roll with an empty list
		score = game.calculateScore(roll, true);
		assertEquals(0, score);
		score = game.calculateScore(roll, false);
		assertEquals(0, score);
		
		// Test calculateHighestScore with an empty list
		highestScore = game.calculateHighestScore(roll);
		assertEquals(0, highestScore[0]);
		highestRoll = (List<Integer>) highestScore[1];
		assertTrue(highestRoll.isEmpty());
		
		// Test that a roll with negative number integers returns 0
		roll.add(-1);
		roll.add(-5);
		score = game.calculateScore(roll, true);
		assertEquals(0, score);
		score = game.calculateScore(roll, false);
		assertEquals(0, score);
		
		// Test calculateHighestScore with the negative number
		highestScore = game.calculateHighestScore(roll);
		assertEquals(0, highestScore[0]);	
		highestRoll = (List<Integer>) highestScore[1];
		assertTrue(highestRoll.isEmpty());
		
		// Test that passing null to the calculateScore method returns 0
		assertEquals(0, game.calculateScore(null, true));
		assertEquals(0, game.calculateScore(null, false));
		
		// Test calculateHighestScore with null
		highestScore = game.calculateHighestScore(null);
		assertEquals(0, highestScore[0]);	
		highestRoll = (List<Integer>) highestScore[1];
		assertTrue(highestRoll.isEmpty());
		
	}
	
	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 1 die,
	 * requirements 6.1.0 and 6.2.0: <br /><br />
	 * 6.1.0 Each 1 rolled is worth 100 points<br />
	 * 6.2.0 Each 5 rolled is worth 50 points
	 */
	@SuppressWarnings("unchecked")
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
				
				// Test calculateHighestScore
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
			}
		}	
		
		/* Test 2: Check scoring for a die roll of 5 (requirement 6.2.0) */
		
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
			
			// Test calculateHighestScore
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(50, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 1);
			assertTrue(highestRoll.get(0).equals(5));
		}
		
		/* Test 3: Check scoring for a die roll of 1 (requirement 6.1.0) */
		
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
			
			// Test calculateHighestScore
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(100, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 1);
			assertTrue(highestRoll.get(0).equals(1));
		}
	}
	
	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 2 dice,
	 * requirement 6.1.0 and 6.2.0. <br /><br />
	 * 6.1.0 Each 1 rolled is worth 100 points<br />
	 * 6.2.0 Each 5 rolled is worth 50 points
	 */
	@SuppressWarnings("unchecked")
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
				
				// Test calculateHighestScore
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
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
				
				// Test calculateHighestScore
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
			}
		}
		
		/* Test 6: Check scoring for a die roll of 5X (X represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test calculateHighestScore
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(highestRoll.get(0).equals(5));
			}
		}
		
		/* Test 7: Check scoring for a die roll of 1X (X represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 
		 */
		
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
				
				// Test calculateHighestScore
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(highestRoll.get(0).equals(1));
			}
		}
		
		/* Test 8: Check scoring for a die roll of 55 
		 * Requirement 6.2.0 
		 */
		
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
			
			// Test calculateHighestScore
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(100, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 2);
			assertTrue(highestRoll.get(0).equals(5));
			assertTrue(highestRoll.get(1).equals(5));
		}
		
		/* Test 9: Check scoring for a die roll of 15 
		 * Requirement 6.1.0 and 6.2.0 
		 */
		
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
			
			// Test calculateHighestScore
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(150, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 2);
			assertTrue(highestRoll.contains(1));
			assertTrue(highestRoll.contains(5));
		}
		
		/* Test 10: Check scoring for a die roll of 11 
		 * Requirement 6.1.0 
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(200, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 2);
			assertTrue(highestRoll.contains(1));
			assertTrue(!highestRoll.contains(2));
			assertTrue(!highestRoll.contains(3));
			assertTrue(!highestRoll.contains(4));
			assertTrue(!highestRoll.contains(5));
			assertTrue(!highestRoll.contains(6));
		}
		
	}
	
	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 3 dice,
	 * requirements 6.1.0, 6.2.0, 6.3.0, and 6.4.0. <br /><br />
	 * 6.1.0 Each 1 rolled is worth 100 points<br />
	 * 6.2.0 Each 5 rolled is worth 50 points<br />
	 * 6.3.0 Three 1’s are worth 1000 points<br />
	 * 6.4.0 Three of a kind of any value other than 1 is worth 100 times the value of the die (e.g. 
	 * three 4’s is worth 400 points).
	 */
	@SuppressWarnings("unchecked")
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
			}
		}
		
		/* Test 13: Check scoring for a die roll of 5AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(!highestRoll.contains(1));
				assertTrue(!highestRoll.contains(2));
				assertTrue(!highestRoll.contains(3));
				assertTrue(!highestRoll.contains(4));
				assertTrue(highestRoll.contains(5));
				assertTrue(!highestRoll.contains(6));
			}
		}
		
		/* Test 14: Check scoring for a die roll of 5AX (A and X represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(!highestRoll.contains(1));
				assertTrue(!highestRoll.contains(2));
				assertTrue(!highestRoll.contains(3));
				assertTrue(!highestRoll.contains(4));
				assertTrue(highestRoll.contains(5));
				assertTrue(!highestRoll.contains(6));
			}
		}
		
		/* Test 15: Check scoring for a die roll of 1AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(highestRoll.contains(1));
				assertTrue(!highestRoll.contains(2));
				assertTrue(!highestRoll.contains(3));
				assertTrue(!highestRoll.contains(4));
				assertTrue(!highestRoll.contains(5));
				assertTrue(!highestRoll.contains(6));
			}
		}
		
		/* Test 16: Check scoring for a die roll of 1AX (A and X represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(highestRoll.contains(1));
				assertTrue(!highestRoll.contains(2));
				assertTrue(!highestRoll.contains(3));
				assertTrue(!highestRoll.contains(4));
				assertTrue(!highestRoll.contains(5));
				assertTrue(!highestRoll.contains(6));
			}
		}
		
		/* Test 17: Check scoring for a die roll of 55A (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(!highestRoll.contains(1));
				assertTrue(!highestRoll.contains(2));
				assertTrue(!highestRoll.contains(3));
				assertTrue(!highestRoll.contains(4));
				assertTrue(highestRoll.contains(5));
				assertTrue(!highestRoll.contains(6));
			}
		}
		
		/* Test 18: Check scoring for a die roll of 15A (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(highestRoll.contains(1));
				assertTrue(!highestRoll.contains(2));
				assertTrue(!highestRoll.contains(3));
				assertTrue(!highestRoll.contains(4));
				assertTrue(highestRoll.contains(5));
				assertTrue(!highestRoll.contains(6));
			}
		}
		
		/* Test 19: Check scoring for a die roll of 11A (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(highestRoll.contains(1));
				assertTrue(!highestRoll.contains(2));
				assertTrue(!highestRoll.contains(3));
				assertTrue(!highestRoll.contains(4));
				assertTrue(!highestRoll.contains(5));
				assertTrue(!highestRoll.contains(6));
			}
		}
		
		/* Test 20: Check scoring for a die roll of 155 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(200, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 3);
			assertTrue(highestRoll.contains(1));
			assertTrue(!highestRoll.contains(2));
			assertTrue(!highestRoll.contains(3));
			assertTrue(!highestRoll.contains(4));
			assertTrue(highestRoll.contains(5));
			assertTrue(!highestRoll.contains(6));
		}
		
		/* Test 21: Check scoring for a die roll of 115 
		 * Requirement 6.1.0 and 6.2.0 
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(250, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 3);
			assertTrue(highestRoll.contains(1));
			assertTrue(!highestRoll.contains(2));
			assertTrue(!highestRoll.contains(3));
			assertTrue(!highestRoll.contains(4));
			assertTrue(highestRoll.contains(5));
			assertTrue(!highestRoll.contains(6));
		}
		
		/* Test 22: Check scoring for a die roll of AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.4.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}	
		
		/* Test 23: Check scoring for a die roll of 555 
		 * Requirement 6.4.0 
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(500, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 3);
			assertTrue(Collections.frequency(highestRoll, 5) == 3);
		}
		
		/* Test 24: Check scoring for a die roll of 111 
		 * Requirement 6.3.0 
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(1000, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 3);
			assertTrue(Collections.frequency(highestRoll, 1) == 3);
		}
	}

	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 4 dice,
	 * requirements 6.1.0, 6.2.0, 6.3.0, 6.4.0, and 6.5.0: <br /><br />
	 * 6.1.0 Each 1 rolled is worth 100 points<br />
	 * 6.2.0 Each 5 rolled is worth 50 points<br />
	 * 6.3.0 Three 1’s are worth 1000 points<br />
	 * 6.4.0 Three of a kind of any value other than 1 is worth 100 times the value of the die (e.g. 
	 * three 4’s is worth 400 points).<br />
	 * 6.5.0 Four, five, or six of a kind is scored by doubling the three of a kind value for every 
	 * additional matching die (e.g. five 3’s would be scored as 300 X 2 X 2 = 1200.
	 */
	@SuppressWarnings("unchecked")
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
			}
		}
		
		/* Test 28: Check scoring for a die roll of 5AXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 29: Check scoring for a die roll of 5AAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 30: Check scoring for a die roll of 1AXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 31: Check scoring for a die roll of 1AAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 32: Check scoring for a die roll of 55AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 33: Check scoring for a die roll of 55AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 34: Check scoring for a die roll of 15AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 35: Check scoring for a die roll of 15AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		
		/* Test 36: Check scoring for a die roll of 11AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 37: Check scoring for a die roll of 55AA (A represents all die values other than 1 or 5)
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 38: Check scoring for a die roll of 155A (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
				
		/* Test 39: Check scoring for a die roll of 115A (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(250, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 40: Check scoring for a die roll of AAAX (A and X represent all die values other than 1 or 5) 
		 * Requirement 6.4.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 41: Check scoring for a die roll of 555A (A represents all die values other than 1 or 5) 
		 * Requirement 6.4.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(500, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
			}
		}
		
		/* Test 42: Check scoring for a die roll of 111A (A represents all die values other than 1 or 5) 
		 * Requirement 6.3.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}
		
		/* Test 43: Check scoring for a die roll of 5AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 44: Check scoring for a die roll of 1AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 45: Check scoring for a die roll of 1555 
		 * Requirement 6.1.0 and 6.4.0 
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(600, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 4);
			assertTrue(Collections.frequency(highestRoll, 5) == 3);
			assertTrue(Collections.frequency(highestRoll, 1) == 1);
		}
		
		/* Test 46: Check scoring for a die roll of 5111 
		 * Requirement 6.2.0 and 6.3.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(1050, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 4);
			assertTrue(Collections.frequency(highestRoll, 5) == 1);
			assertTrue(Collections.frequency(highestRoll, 1) == 3);
		}	
		
		/* Test 47: Check scoring for a die roll of AAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
		
		/* Test 48: Check scoring for a die roll of 5555 
		 * Requirement 6.5.0 
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(1000, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 4);
			assertTrue(Collections.frequency(highestRoll, 5) == 4);
		}
		
		/* Test 49: Check scoring for a die roll of 1111 
		 * Requirement 6.5.0 
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(2000, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 4);
			assertTrue(Collections.frequency(highestRoll, 1) == 4);
		}
	}

	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 5 dice,
	 * requirements 6.1.0, 6.2.0, 6.3.0, 6.4.0, and 6.5.0:<br /><br />
	 * 6.1.0 Each 1 rolled is worth 100 points<br />
	 * 6.2.0 Each 5 rolled is worth 50 points<br />
	 * 6.3.0 Three 1’s are worth 1000 points<br />
	 * 6.4.0 Three of a kind of any value other than 1 is worth 100 times the value of the die (e.g. 
	 * three 4’s is worth 400 points).<br />
	 * 6.5.0 Four, five, or six of a kind is scored by doubling the three of a kind value for every 
	 * additional matching die (e.g. five 3’s would be scored as 300 X 2 X 2 = 1200.
	 */
	@SuppressWarnings("unchecked")
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
			}
		}
		
		/* Test 52: Check scoring for a die roll of 5AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 53: Check scoring for a die roll of 5AAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 54: Check scoring for a die roll of 5AAXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 55: Check scoring for a die roll of 1AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 56: Check scoring for a die roll of 1AAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 57: Check scoring for a die roll of 1AAXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 58: Check scoring for a die roll of 55AXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 59: Check scoring for a die roll of 55AAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 60: Check scoring for a die roll of 15AXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 61: Check scoring for a die roll of 15AAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 62: Check scoring for a die roll of 11AXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 63: Check scoring for a die roll of 11AAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 64: Check scoring for a die roll of 155AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		
		/* Test 65: Check scoring for a die roll of 155AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 66: Check scoring for a die roll of 115AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(250, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		
		/* Test 67: Check scoring for a die roll of 115AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(250, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 68: Check scoring for a die roll of AAAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 69: Check scoring for a die roll of AAAXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		
		/* Test 70: Check scoring for a die roll of 5AAAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 71: Check scoring for a die roll of 1AAAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 72: Check scoring for a die roll of 55AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 73: Check scoring for a die roll of 15AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0, 6.2.0, and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 74: Check scoring for a die roll of 11AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 75: Check scoring for a die roll of 555AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(500, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
			}
		}
		
		/* Test 76: Check scoring for a die roll of 555AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(500, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
			}
		}
		
		/* Test 77: Check scoring for a die roll of 1555A (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(600, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 78: Check scoring for a die roll of 11555 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(700, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 5);
			assertTrue(Collections.frequency(highestRoll, 5) == 3);
			assertTrue(Collections.frequency(highestRoll, 1) == 2);
			
		}
		
		/* Test 79: Check scoring for a die roll of 111AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.3.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}
		
		/* Test 80: Check scoring for a die roll of 111AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.3.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}
		
		/* Test 81: Check scoring for a die roll of 1115A (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.3.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1050, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}
		
		/* Test 82: Check scoring for a die roll of 11155 
		 * Requirement 6.2.0 and 6.3.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(1100, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 5);
			assertTrue(Collections.frequency(highestRoll, 5) == 2);
			assertTrue(Collections.frequency(highestRoll, 1) == 3);
		}
		
		/* Test 83: Check scoring for a die roll of AAAAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200 * dieOneValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
		
		/* Test 84: Check scoring for a die roll of 5AAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200 * dieOneValue + 50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 85: Check scoring for a die roll of 1AAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200 * dieOneValue + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		
		/* Test 86: Check scoring for a die roll of 5555A (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 4);
			}
		}
		
		/* Test 87: Check scoring for a die roll of 15555 
		 * Requirement 6.1.0 and 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(1100, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 5);
			assertTrue(Collections.frequency(highestRoll, 5) == 4);
			assertTrue(Collections.frequency(highestRoll, 1) == 1);
		}
		
		/* Test 88: Check scoring for a die roll of 1111A (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(2000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 1) == 4);
			}
		}
		
		/* Test 89: Check scoring for a die roll of 11115 
		 * Requirement 6.2.0 and 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(2050, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 5);
			assertTrue(Collections.frequency(highestRoll, 5) == 1);
			assertTrue(Collections.frequency(highestRoll, 1) == 4);
		}
		
		/* Test 90: Check scoring for a die roll of AAAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(400 * dieOneValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 5);
			}
		}
		
		/* Test 91: Check scoring for a die roll of 55555 
		 * Requirement 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(2000, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 5);
			assertTrue(Collections.frequency(highestRoll, 5) == 5);
		}
		
		/* Test 92: Check scoring for a die roll of 11111 
		 * Requirement 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(4000, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 5);
			assertTrue(Collections.frequency(highestRoll, 1) == 5);
		}
	}
	
	/**
	 * This method tests the calculateScore method of the Game class for all permutations of 6 dice,
	 * requirements 6.1.0, 6.2.0, 6.3.0, 6.4.0, 6.5.0, 6.6.0, and 6.7.0:<br /><br />
	 * 6.1.0 Each 1 rolled is worth 100 points<br />
	 * 6.2.0 Each 5 rolled is worth 50 points<br />
	 * 6.3.0 Three 1’s are worth 1000 points<br />
	 * 6.4.0 Three of a kind of any value other than 1 is worth 100 times the value of the die (e.g. 
	 * three 4’s is worth 400 points).<br />
	 * 6.5.0 Four, five, or six of a kind is scored by doubling the three of a kind value for every 
	 * additional matching die (e.g. five 3’s would be scored as 300 X 2 X 2 = 1200.<br />
	 * 6.6.0 Three doubles (e.g. 1-1-2-2-3-3) is worth 750 points.<br />
	 * 6.7.0 A straight (e.g. 1-2-3-4-5-6), which can only be achieved when all 6 dice are rolled, 
	 * is worth 1500 points.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCalculateScore6(){
		
		/* Test 93: Check scoring for a die roll of AAXXYZ (A, X, Y and Z each represent all die values other than 1 or 5) */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(0, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertTrue(highestRoll.isEmpty());
			}
		}
		
		/* Test 94: Check scoring for a die roll of AAXXYY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.6.0
		 */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("AAXXYY");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			// Get the die value for calculating the score
			dieOneValue = Character.getNumericValue(numbers.charAt(0));
			dieTwoValue = Character.getNumericValue(numbers.charAt(2));
			int dieThreeValue = Character.getNumericValue(numbers.charAt(4));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(750, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(750, score);
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(750, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 2);
				assertTrue(Collections.frequency(highestRoll, dieTwoValue) == 2);
				assertTrue(Collections.frequency(highestRoll, dieThreeValue) == 2);
			}
		}
		
		/* Test 95: Check scoring for a die roll of 11AAXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.6.0
		 */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("11AAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			dieTwoValue = Character.getNumericValue(numbers.charAt(4));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(750, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(750, score);
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(750, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 2);
				assertTrue(Collections.frequency(highestRoll, dieTwoValue) == 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 96: Check scoring for a die roll of 55AAXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.6.0
		 */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("55AAXX");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			dieOneValue = Character.getNumericValue(numbers.charAt(2));
			dieTwoValue = Character.getNumericValue(numbers.charAt(4));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(750, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(750, score);
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(750, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 2);
				assertTrue(Collections.frequency(highestRoll, dieTwoValue) == 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 97: Check scoring for a die roll of 1155AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.6.0
		 */
		
		// Generate all permutations of the roll
		automatedPermutations = integerPermutations("1155AA");
		
		// Convert the list of Strings to a list of lists of integers
		for(String numbers : automatedPermutations)
		{
			myPermutations = permutations(numbers);
			
			dieOneValue = Character.getNumericValue(numbers.charAt(4));
			
			// Check each list of integers for proper scoring
			for(List<Integer> currentNumbers : myPermutations)
			{
				// Test the total score
				score = game.calculateScore(currentNumbers, false);
				assertEquals(750, score);
				
				// Test the held score
				score = game.calculateScore(currentNumbers, true);
				assertEquals(750, score);
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(750, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 98: Check scoring for a die roll of 1AXY5Z (A, X, Y and Z each represent all die values other than 1 or 5) 
		 * Requirement 6.7.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1500, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(highestRoll.contains(1));
				assertTrue(highestRoll.contains(2));
				assertTrue(highestRoll.contains(3));
				assertTrue(highestRoll.contains(4));
				assertTrue(highestRoll.contains(5));
				assertTrue(highestRoll.contains(6));
			}
		}
		
		/* Test 99: Check scoring for a die roll of 5AAXXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 100: Check scoring for a die roll of 5AAXYZ (A, X, Y and Z each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		
		/* Test 101: Check scoring for a die roll of 1AAXXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 102: Check scoring for a die roll of 1AAXYZ (A, X, Y and Z each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}		
		
		/* Test 103: Check scoring for a die roll of 55AAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}
		
		/* Test 104: Check scoring for a die roll of 55AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}	
		
		/* Test 105: Check scoring for a die roll of 15AAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 106: Check scoring for a die roll of 15AAXx (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 107: Check scoring for a die roll of 11AAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 108: Check scoring for a die roll of 11AXYZ (A, X, Y and Z each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		/* Test 109: Check scoring for a die roll of 155AAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}	
		
		/* Test 110: Check scoring for a die roll of 155AXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
			}
		}		

		/* Test 111: Check scoring for a die roll of 115AAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(250, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}	
		
		/* Test 112: Check scoring for a die roll of 115AXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.2.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(250, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}	
		
		/* Test 113: Check scoring for a die roll of AAAXXY (A, X and Y represent all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 114: Check scoring for a die roll of AAAXYZ (A, X, Y and Z represent all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
	
		/* Test 115: Check scoring for a die roll of 555AAX (A and X represent all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(500, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
			}
		}
		
		
		/* Test 116: Check scoring for a die roll of 555AXY (A, X and Y represent all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(500, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
			}
		}		
		
		/* Test 117: Check scoring for a die roll of 111AAX (A and X represent all die values other than 1 or 5) 
		 * Requirement 6.3.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}
		
		
		/* Test 118: Check scoring for a die roll of 111AXY (A, X and Y represent all die values other than 1 or 5) 
		 * Requirement 6.3.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}		
		
		/* Test 119: Check scoring for a die roll of 1AAAXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 120: Check scoring for a die roll of 1AAAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 121: Check scoring for a die roll of 5AAAXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 122: Check scoring for a die roll of 5AAAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 123: Check scoring for a die roll of 55AAAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 124: Check scoring for a die roll of 15AAAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0, 6.2.0, and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 125: Check scoring for a die roll of 11AAAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
				
		/* Test 126: Check scoring for a die roll of 155AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0, 6.2.0, and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 127: Check scoring for a die roll of 115AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0, 6.2.0, and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 250, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 128: Check scoring for a die roll of AAAXXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 100 * dieTwoValue, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, dieTwoValue) == 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
				
		/* Test 129: Check scoring for a die roll of 555AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 500, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 130: Check scoring for a die roll of 111AAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.3.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue + 1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 3);
			}
		}
		
		/* Test 131: Check scoring for a die roll of 111555 
		 * Requirement 6.3.0 and 6.4.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(1500, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 6);
			assertTrue(Collections.frequency(highestRoll, 5) == 3);
			assertTrue(Collections.frequency(highestRoll, 1) == 3);
		}		
		
		/* Test 132: Check scoring for a die roll of 5551AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(600, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 133: Check scoring for a die roll of 5551AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(600, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}
		
		/* Test 134: Check scoring for a die roll of 55511A (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(700, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 5) == 3);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
			}
		}
		
		
		/* Test 135: Check scoring for a die roll of 1115AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.3.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1050, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}
		
		/* Test 136: Check scoring for a die roll of 1115AX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.4.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1050, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}
		
		/* Test 137: Check scoring for a die roll of 11155A (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.3.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
				assertTrue(Collections.frequency(highestRoll, 1) == 3);
			}
		}
		
		
		/* Test 138: Check scoring for a die roll of AAAAXX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
		
		/* Test 139: Check scoring for a die roll of AAAAXY (A, X and Y each represent all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
		
		/* Test 140: Check scoring for a die roll of 5555AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 4);
			}
		}
		
		/* Test 141: Check scoring for a die roll of 5555AX (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 4);
			}
		}
		
		/* Test 142: Check scoring for a die roll of 1111AA (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(2000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 1) == 4);
			}
		}
		
		/* Test 143: Check scoring for a die roll of 1111AX (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(2000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 4);
				assertTrue(Collections.frequency(highestRoll, 1) == 4);
			}
		}
		
		/* Test 144: Check scoring for a die roll of 1AAAAX (A amd X each represent all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
		
		/* Test 145: Check scoring for a die roll of 5AAAAX (A amd X each represent all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 + 50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
		
		/* Test 146: Check scoring for a die roll of 55AAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, 5) == 2);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
				
		/* Test 147: Check scoring for a die roll of 15AAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0, 6.2.0, and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 + 150, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
		
		/* Test 148: Check scoring for a die roll of 11AAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 + 200, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, 1) == 2);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 4);
			}
		}
		
		/* Test 149: Check scoring for a die roll of 15555A (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(1100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
				assertTrue(Collections.frequency(highestRoll, 5) == 4);
			}
		}
		
		/* Test 150: Check scoring for a die roll of 115555 
		 * Requirement 6.1.0 and 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(1200, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 6);
			assertTrue(Collections.frequency(highestRoll, 1) == 2);
			assertTrue(Collections.frequency(highestRoll, 5) == 4);
		}
		
		/* Test 151: Check scoring for a die roll of 11115A (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(2050, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 1) == 4);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}
		
		/* Test 152: Check scoring for a die roll of 111155 
		 * Requirement 6.2.0 and 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(2100, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 6);
			assertTrue(Collections.frequency(highestRoll, 1) == 4);
			assertTrue(Collections.frequency(highestRoll, 5) == 2);
		}
		
		/* Test 153: Check scoring for a die roll of AAAAAX (A and X each represent all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 * 2, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 5);
			}
		}		
		
		/* Test 154: Check scoring for a die roll of 5AAAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.2.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 * 2 + 50, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 5);
				assertTrue(Collections.frequency(highestRoll, 5) == 1);
			}
		}	
		
		/* Test 155: Check scoring for a die roll of 1AAAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.1.0 and 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 * 2 + 100, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 5);
				assertTrue(Collections.frequency(highestRoll, 1) == 1);
			}
		}	

		/* Test 156: Check scoring for a die roll of 55555A (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(2000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 5) == 5);
			}
		}

		/* Test 157: Check scoring for a die roll of 155555 
		 * Requirement 6.1.0 and 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(2100, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 6);
			assertTrue(Collections.frequency(highestRoll, 5) == 5);
			assertTrue(Collections.frequency(highestRoll, 1) == 1);
		}
		
		/* Test 158: Check scoring for a die roll of 11111A (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(4000, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 5);
				assertTrue(Collections.frequency(highestRoll, 1) == 5);
			}
		}	
		
		/* Test 159: Check scoring for a die roll of 111115 
		 * Requirement 6.2.0 and 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(4050, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 6);
			assertTrue(Collections.frequency(highestRoll, 5) == 1);
			assertTrue(Collections.frequency(highestRoll, 1) == 5);
		}
		
		/* Test 160: Check scoring for a die roll of AAAAAA (A represents all die values other than 1 or 5) 
		 * Requirement 6.5.0
		 */
		
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
				
				// Test the calculateHighestScore method
				highestScore = game.calculateHighestScore(currentNumbers);
				assertEquals(100 * dieOneValue * 2 * 2 * 2, highestScore[0]);	
				highestRoll = (List<Integer>) highestScore[1];
				assertEquals(highestRoll.size(), 6);
				assertTrue(Collections.frequency(highestRoll, dieOneValue) == 6);
			}
		}
		
		/* Test 161: Check scoring for a die roll of 555555 
		 * Requirement 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(4000, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 6);
			assertTrue(Collections.frequency(highestRoll, 5) == 6);
		}
		
		/* Test 162: Check scoring for a die roll of 111111 
		 * Requirement 6.5.0
		 */
		
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
			
			// Test the calculateHighestScore method
			highestScore = game.calculateHighestScore(numbers);
			assertEquals(8000, highestScore[0]);	
			highestRoll = (List<Integer>) highestScore[1];
			assertEquals(highestRoll.size(), 6);
			assertTrue(Collections.frequency(highestRoll, 1) == 6);
		}

	}
	
	/* The following are helper methods used for retrieving roll permutations */
	
	/**
	 * The permutations method is a wrapper class to the stringPermutations method.
	 * This method passes a string of integers for which the permutations are sought to
	 * the stringPermutations method that then returns a list of all the string permutations
	 * of the input string. The permutations method than converts the list of strings to
	 * a list of lists of integers.
	 * @param myString string of integers for which all permutations are sought
	 * @return List of Lists of integers representing distinct permutations of the input string 
	 */
	public static List<List<Integer>> permutations(String myString){
		
		// If myString is null, return null
		if (myString == null) {
            return null;
        }
		
		// If any character in myString is not a digit, return null
		int length = myString.length();

        // For each character in myString
        for (int i = 0; i < length; i++) {
            if(!Character.isDigit(myString.charAt(i))) {
            	return null;
            }
        }
		
		// Retrieve a list of all distinct permutations of the string
		List<String> myList = stringPermutations(myString);
		
		// Convert the list of strings to a list of lists of integers
		List<List<Integer>> myIntegers = new LinkedList<List<Integer>>();
		
		// For each string in myList, convert that string to a list of integers and add
		// the resulting list to the myIntegers list
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
	 * The stringPermutation method uses recursion to find all permutations of a string of characters,
	 * returning a list of those strings
	 * @param myString string of characters for which all permutations are to be found
	 * @return List of strings representing all distinct permutations of the input string
	 */
	public static List<String> stringPermutations(String myString) {
        
		// If myString is null, return null
		if (myString == null) {
            return null;
        }
		
		// Instantiate the permutationsList as a Linked List of strings
        List<String> permutationsList = new LinkedList<String>();
        
        int length = myString.length();
        
        // If the length of myString is less than 2, there are no more permutations to perform
        // so add myString to the permutationsList
        if (length < 2) {
            permutationsList.add(myString);
            return permutationsList;
        }
        
        char currentChar;

        // For each character in myString
        for (int i = 0; i < length; i++) {
            currentChar = myString.charAt(i);
            
            // Retrieve the substring excluding the current character
            String subString = myString.substring(0, i) + myString.substring(i + 1);
            
            // Retrieve all of the permutations for a substring excluding the current character
            List<String> subPermutations = stringPermutations(subString);
            
            // For each permutation of the substring, add the string that includes the current
            // character plus the substring to the permutations list.
            for (String item : subPermutations) {
                if(!permutationsList.contains(currentChar + item))
                	permutationsList.add(currentChar + item);
            }
        }

        return permutationsList;
    }
	
	/**
	 * The integerPermutations method converts a string, representing a Farkle dice roll, into a 
	 * list of strings including all roll permutations for the input string.
	 * @param myString input string to be converted, with variables as placeholders for dice that 
	 * could be 2,3,4 or 6. E.g. the string "1AABC" represents a roll including a die with the value
	 * 1, and 4 dice with the value 2 through 6. For each permuation, 2 of the 4 dice, represented by
	 * A, are of the same value. On a given roll, the value for die B never equals that for A or C, and 
	 * the value of die C never equals that for A or B.
	 * @return List of strings with all permutations of 2, 3, 4, or 6 valued die in place of variables in 
	 * the input string. E.g. an input string of "1A" would return the list {"12", "13", "14", "16"}
	 */
	public static List<String> integerPermutations(String myString) {
		
		if(myString == null)
		{
			return null;
		}
		
		// The number of distinct variables in the input string
		int numberOfVariables;
		
		// List of present variables in the input string
		List<Character> presentVariables = new LinkedList<Character>();
		
		// List of strings representing the roll permutations calculated from the input string
		List<String> rollPermutations = new LinkedList<String>();
		
		// The current character of the input string 
		char currentCharacter;
		
		// The length of the input string
		int length = myString.length();
		
		// The current permutation that will be added to the list
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
		
		// There can be a maximum of 4 variables (representing 2, 3, 4, 6),
		// Depending on the number of variables present in the input string, add
		// the string representing each possible permutation to the rollPermutations
		// list
		if(numberOfVariables > 0 && numberOfVariables <= 4)
		{
			
			// Outer loop for 1 die
			for(int i = 2; i <= 6; i++)
			{
				if(i == 5)
					continue;
				//currentPermutation += i;
				
				// Embedded loop for a variable representing a second die
				if(numberOfVariables > 1)
				{
					for(int j = 2; j <= 6; j++)
					{
						if(j == i || j == 5)
							continue;
						//currentPermutation += j;
						
						// Embedded loop for a variable representing a third die
						if(numberOfVariables > 2)
						{
							for(int k = 2; k <= 6; k++)
							{
								if(k == i || k == j || k == 5)
									continue;
								//currentPermutation += k;
								
								// Embedded loop for a variable representing a fourth die
								if(numberOfVariables > 3)
								{
									for(int l = 2; l <= 6; l++)
									{
										if(l == k || l == j || l == i || l == 5)
											continue;	
										// Clear the currentPermutation String
										currentPermutation = "";
										
										// Loop through the input string adding this permutation of the roll. 
										for(int x = 0; x < length; x++)
										{					
											// Clear the currentPermutation String
											currentCharacter = myString.charAt(x);
										
											// If the current character is a letter, and the variables list is empty 
											// or the variables list does not contain the letter, add it to the variables 
											// list
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
									
									// Loop through the input string adding this permutation of the roll.
									for(int x = 0; x < length; x++)
									{
										currentCharacter = myString.charAt(x);
										
										// If the current character is a letter, and the variables list is empty 
										// or the variables list does not contain the letter, add it to the variables 
										// list
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
							
							// Loop through the input string adding this permutation of the roll.
							for(int x = 0; x < length; x++)
							{	
								
								currentCharacter = myString.charAt(x);
								
								// If the current character is a letter, and the variables list is empty 
								// or the variables list does not contain the letter, add it to the variables 
								// list
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
					
					// Loop through the input string adding this permutation of the roll.
					for(int x = 0; x < length; x++)
					{			
						currentCharacter = myString.charAt(x);
						
						// If the current character is a letter, and the variables list is empty 
						// or the variables list does not contain the letter, add it to the variables 
						// list
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
		
		// If more than 4 variables were in the string it is incorrect input, return null
		else if(numberOfVariables > 4)
		{
			return null;
		}
		else
		{
			// There are no variables present to perform the permutation on, simply add myString 
			// to the rollPermutations
			rollPermutations.add(myString);
		}
		
		return rollPermutations;
	}
}
