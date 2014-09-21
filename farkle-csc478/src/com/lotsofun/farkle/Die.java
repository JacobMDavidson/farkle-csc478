package com.lotsofun.farkle;
import java.util.Random;

enum rollState {FREE, HELD};

public class Die {
	
	/*The face value of the die, possibilities 1-6*/
	public int faceValue;
	
	public rollState dieState;
	
	public Die ()
	{
		dieState = rollState.FREE;
		roll();
	}
	
	public void roll()
	{
		if (dieState == rollState.FREE)
		{
			Random random = new Random();
			/*Max:6 Min:1*/
			faceValue = random.nextInt(6) + 1;
		}
	}
	
	/* TODO:
	hold()
	die.state = HELD
	game.heldDice++
	game.unheldDice--
	if(game.state = ROLL_POSTVALIDATE)
	validate()
	*/
	
	public void hold()
	{
		dieState = rollState.HELD;
	}
	
	/*
	TODO:
	release()
	die.state = FREE
	game.heldDice--
	game.unheldDice++
	if(game.state = ROLL_POSTVALIDATE)
		validate()
	 */
	
	public void release()
	{
		dieState = rollState.FREE;
	}

}
