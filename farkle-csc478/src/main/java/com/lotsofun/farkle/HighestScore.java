package com.lotsofun.farkle;

import java.util.ArrayList;
import java.util.List;

public class HighestScore {
	
	public List<Integer> calculateHighestScore (List<Integer> dice, Game gameClass)
	{			
		int highestScore = 0; 
		List<Integer> highestScoringCombination = new ArrayList<Integer>();
		if ((dice != null) && dice.size() > 0)
		{
			Integer numberOfDice = dice.size();				

			//Every combination of dice.
			for (int i = 1; i <= (numberOfDice ^ 2); i++)
			{
				List<Integer> tempList = new ArrayList<Integer>();
				char[] binaryNumber = Integer.toBinaryString(i).toCharArray();
				for (int j = 0; j < binaryNumber.length; j++ )
				{
					if (binaryNumber[j] == '1')
					{
						tempList.add((int) binaryNumber[j]);
					}
				}
				int tempScore = gameClass.calculateScore(tempList, false);
				if (tempScore > highestScore)
				{
					highestScore = tempScore;
					highestScoringCombination.clear();
					highestScoringCombination = tempList;					
				}
				tempList.clear();
			}
		}
		return highestScoringCombination;
	}

}
