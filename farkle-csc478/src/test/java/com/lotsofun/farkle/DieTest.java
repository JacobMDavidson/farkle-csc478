package com.lotsofun.farkle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import org.junit.Test;

/**
 * Test the Die class
 * 
 * @author Curtis Brown
 * @version 3.0.0
 */
public class DieTest {

	/**
	 * Test Die constructor
	 */
	@Test
	public void testCreateDie() {
		FarkleController c = new FarkleController(true);
		assertNotNull(c);

		Die d = new Die(c);
		assertNotNull(d);

		assertEquals(d.getState(), DieState.UNHELD);
		assertNotEquals(d.getContainerListeners(), null);
	}

	/**
	 * Create a new Die and roll it repeatedly to check the range
	 */
	@Test
	public void testRoll() {

		// Instantiate a controller and assert it is not null
		FarkleController c = new FarkleController(true);
		assertNotNull(c);

		// Assert that the value of a newly instantiated die is 0
		Die d = new Die(c);
		assertNotNull(d);
		assertEquals(d.getValue(), 0);

		/****************************************************
		 * 3.2.0 - Each die that is rolled shall be assigned a random value from
		 * 1 to 6 (inclusive) at the conclusion of the roll.
		 ****************************************************/
		// Roll the die 100 times and assert it is in the range of 1 to 6
		// (inclusive).
		List<Integer> possibleValues = Arrays.asList(1, 2, 3, 4, 5, 6);
		int lcv = 100;
		while (lcv >= 0) {
			d.roll();
			assertTrue(possibleValues.contains(d.getValue()));
			lcv--;
		}
	}

	/**
	 * Test the setIcon() and getIcon() methods
	 */
	@Test
	public void testGetAndSetIcon() {

		// Instantiate a controller and assert it is not null
		FarkleController c = new FarkleController(true);
		assertNotNull(c);

		// Create a new BufferedImage
		BufferedImage bi = new BufferedImage(55, 55,
				BufferedImage.TYPE_INT_ARGB);
		assertNotNull(bi);

		// Create a new Die and assert it is not null
		Die d = new Die(c);
		assertNotNull(d);

		// Assert that the die icon is not null
		assertNull(d.getIcon());

		// create a new Image Icon
		ImageIcon newImageIcon = new ImageIcon(bi);

		// Set it to the BufferedImage
		d.setIcon(newImageIcon);

		// Make sure it worked
		assertNotNull(d.getIcon());
		assertTrue(d.getIcon().equals(newImageIcon));
	}

	/**
	 * Test the getValue() and setValue() methods
	 */
	@Test
	public void testGetAndSetValue() {

		// Instantiate a controller and assert it is not null
		FarkleController c = new FarkleController(true);
		assertNotNull(c);

		// Assert that the value of a newly instantiated die is 0
		Die d = new Die(c);
		assertNotNull(d);
		assertEquals(d.getValue(), 0);

		// Set the die value to '3' and assert getValue returns 3
		d.setValue('3');
		assertEquals(d.getValue(), 3);
		assertNotEquals(d.getValue(), 5);
	}

	/**
	 * Test the isHeld() method
	 */
	@Test
	public void testIsHeld() {

		// Instantiate a controller and assert it is not null
		FarkleController c = new FarkleController(true);
		assertNotNull(c);

		// Create a new Die and assert it is not null
		Die d = new Die(c);
		assertNotNull(d);

		// Assert that the newly created die is not in a HELD state
		assertFalse(d.isHeld());

		// Set the die to a HELD state and assert it is in a HELD state
		d.setState(DieState.HELD);
		assertTrue(d.isHeld());
	}

	/**
	 * Test the isScored() method
	 */
	@Test
	public void testIsScored() {

		// Instantiate a controller and assert it is not null
		FarkleController c = new FarkleController(true);
		assertNotNull(c);

		// Create a new Die and assert it is not null
		Die d = new Die(c);
		assertNotNull(d);

		// Assert that the newly created die is not in a SCORED state
		assertFalse(d.isScored());

		// Set the die to a SCORED state and assert it is in a SCORED state
		d.setState(DieState.SCORED);
		assertTrue(d.isScored());
	}

	/**
	 * Test the isUnheld() method
	 */
	@Test
	public void testIsUnheld() {

		// Instantiate a controller and assert it is not null
		FarkleController c = new FarkleController(true);
		assertNotNull(c);

		// Create a new Die and assert it is not null
		Die d = new Die(c);
		assertNotNull(d);

		// Assert that the newly created die is in a HELD state
		assertTrue(d.isUnheld());

		// Set the die to a HELD state and assert it is not in an UNHELD state
		d.setState(DieState.HELD);
		assertFalse(d.isUnheld());
	}

	/**
	 * Test the setState() and getState() methods
	 */
	@Test
	public void testSetAndGetState() {

		// Instantiate a controller and assert it is not null
		FarkleController c = new FarkleController(true);
		assertNotNull(c);

		// Create a new Die and assert it is not null
		Die d = new Die(c);
		assertNotNull(d);

		// Assert that the newly created die is in an UNHELD state
		assertEquals(d.getState(), DieState.UNHELD);

		// Set the die to a SCORED state and assert it is in a SCORED state
		d.setState(DieState.SCORED);
		assertEquals(d.getState(), DieState.SCORED);

		// Set the die to a HELD state and assert it is in a HELD state
		d.setState(DieState.HELD);
		assertEquals(d.getState(), DieState.HELD);

		// Set the die to an UNHELD state and assert it is in an UNHELD state
		d.setState(DieState.UNHELD);
		assertEquals(d.getState(), DieState.UNHELD);
	}
}
