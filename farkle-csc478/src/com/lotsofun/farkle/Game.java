package com.lotsofun.farkle;

enum gameModes {SINGLE, MULTIPLAYER}
enum gameStates {NEW_TURN, ROLL_PREVAILDATE, ROLL_POSTVALIDATE, BONUS_TURN}

public class Game {


	public int heldDiceCount, unheldDiceCount, numberOfPlayers = 1, currentPlayer = 0;
	public gameModes gameMode;
	public gameStates gameState;
	public Die[] dice;
	public Player[] players;
	
	public Game ()
	{
		newGame();
	}
	
	void newGame()
	{
		heldDiceCount = 0;
		unheldDiceCount = 6;
		gameMode = gameModes.SINGLE;
		dice = new Die[6];
		for (int i = 0; i < 6; i++)
		{
			dice[i] = new Die();
		}
		players = new Player[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++)
		{
			players[i] = new Player();
			players[i].playerNumber = i;
			players[i].currentPlayer = false;
		}
		players[currentPlayer].currentPlayer = true;
	}
	
	
	
	/*validate()  - score held/unheld dice
	// iterate through held dice and see if the combination
	// is contained in a map of possible scores

	String selection = “”;

	if (game.state == ROLL_PREVALIDATE)
	{ // score unheld dice
		selection = getUnheldDice()
		iterate through map
		{
			if selection.contains(map value) // if any valid score
				game.state = ROLL_POSTVALIDATE // not farkle
				game.unheldDice = 6
				break
		}

			if(game.state != ROLL_POSTVALIDATE)	
			farkle() 
			// else farkle
	}
	else
	{
		selection = getHeldDice
		score = map.get(selection) // get combo score
		if(score > 0)
		{
			rollScore.put(rollNumber, score) // set rollScore to score
			// this allows the score to be reevaluated anytime // any die changes state
			if(unheldDiceCount == 0)
			{
				game.state = BONUS_TURN
			}
			enable roll button
			if(rollScore >= 300)
				enable bank button
			else
			disable bank button
//	}
	else
		disable bank button
}*/

	/*
	farkle()
		turnScore[turnNumber] = 0
		change state to NEW_TURN
		call reset()
	*/
	
	void farkle ()
	{
		players[currentPlayer].endTurn(true);
		currentPlayer = getNextPlayer();
	}
	
	/*
	roll()
	rollNumber++
	disable roll button
	disable bank button
	call roll on all dice
	game.state = ROLL_PREVALIDATE
	validate()
	*/

	/*
	bank()
	Increment player turn
	set turnScore[turnNumber] = sum of all rollScores
	increment activePlayer.turn
	change state to NEW_TURN
	reset()
	*/
	void bank()
	{
		players[currentPlayer].endTurn(false);
	}
	
	/*
	reset()
	enable roll button
	if(game.gameMode = SINGLE_PLAYER && game.activePlayer.getTurnCount >= 10)
		endGame()
	//TODO: Add multiplayer check

		if(game.state != BONUS_TURN)
		reset rollScore to 0
		reset turnScore to 0
		reset rollCount to 0

		String getUnheldDice()
			get value from all dice with state of FREE
			sort ascending
			

		String getHeldDice()
			get value from all dice with state of HELD
			sort ascending
			*/
	void reset ()
	{
		heldDiceCount = 0;
		unheldDiceCount = 6;
		if ((gameMode == gameModes.SINGLE) && (players[currentPlayer].turnNumber >= 10))
		{
			//endGame();
		}
	}
	
	int getNextPlayer ()
	{
		int retVal = 0;
		if (currentPlayer != players.length - 1)
		{
			retVal = currentPlayer+1;
		}
		return retVal;
	}
	
	String getHeldDice()
	{
		String retVal = "";
		for(int i = 0; i < 6; i++)
		{
			if (dice[i].dieState == rollState.HELD)
			{
				retVal += Integer.toString(dice[i].faceValue);
			}
		}
		return retVal;
	}
	
	String getUnheldDice()
	{
		String retVal = "";
		for(int i = 0; i < 6; i++)
		{
			if (dice[i].dieState == rollState.FREE)
			{
				retVal += Integer.toString(dice[i].faceValue);
			}
		}
		return retVal;
	}
}
