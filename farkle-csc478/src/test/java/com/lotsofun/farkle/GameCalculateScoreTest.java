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
		
		myPermutations.clear();
		automatedPermutations = integerPermutations("1AA");
		 int currentNumber = 1;
		for(String numbers : automatedPermutations)
		{
			myPermutations.clear();
			myPermutations = permutations(numbers);
			for(List<Integer> currentNumbers : myPermutations)
			{
				 System.out.println((currentNumber++) + ")" + currentNumbers.toString());
				score = game.calculateScore(currentNumbers, false);
				assertEquals(100, score);
			}
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
