package com.lotsofun.farkle;
enum playerType {USER, COMPUTER}
public class Player {
	
	/*Properties TODO:
	HashMap[int rollNumber, int score] rollScore - Roll score for each player - temp score variable for each roll
	 */

	public int playerNumber, turnNumber, rollNumber, gameScore = 0;
	public boolean currentPlayer;
	public int[] turnScores;
	public playerType type;
	
	public Player ()
	{
		type = playerType.USER;
		turnScores = new int[10];
	}

	public void endTurn(boolean didFarkle)
	{
		if (didFarkle)
		{
			turnScores[turnNumber - 1] = 0;	
		}
		gameScore += turnScores [turnNumber -1];
		turnNumber++;
		rollNumber = 0;
		currentPlayer = false;
	}	
}
