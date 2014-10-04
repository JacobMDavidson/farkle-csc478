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
	@Before
	public void setUp() throws Exception {
		game = new Game(GameMode.SINGLEPLAYER, new FarkleController());
		myPermutations = new LinkedList<List<Integer>>();
	}

	@Test
	public void testCalculateScore() {
		int score = 0;
		myPermutations = permutations("115324");
		for(List<Integer> numbers : myPermutations)
		{
			//System.out.println(number.toString());
			score = game.calculateScore(numbers, false);
			assertEquals(250, score);
		}
		myPermutations.clear();
		myPermutations = permutations("1155");
		for(List<Integer> numbers : myPermutations)
		{
			//System.out.println(number.toString());
			score = game.calculateScore(numbers, false);
			assertEquals(300, score);
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
	public static LinkedList<String> stringPermutations(String s) {
        if (s == null) {
            return null;
        }

        LinkedList<String> resultList = new LinkedList<String>();
        int length = s.length();
        if (length < 2) {
            resultList.add(s);
            return resultList;
        }

        
        char currentChar;

        for (int i = 0; i < length; i++) {
            currentChar = s.charAt(i);

            String subString = s.substring(0, i) + s.substring(i + 1);

            LinkedList<String> subPermutations = stringPermutations(subString);

            for (String item : subPermutations) {
                if(!resultList.contains(currentChar + item))
                	resultList.add(currentChar + item);
            }
        }

        return resultList;
    } 
}
