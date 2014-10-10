package com.lotsofun.farkle;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.junit.Test;

public class DieTest {

	/**
	 * Test Die constructor
	 */
	@Test
	public void testCreateDie() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		
		assertEquals(d.getState(), DieState.UNHELD);
		assertNotEquals(d.getContainerListeners(), null);
	}
	
	/**
	 * Create a new Die and roll
	 * it repeatedly to check
	 * the range
	 */
	@Test
	public void testRoll() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		assertEquals(d.getValue(), 0);
		
		List<Integer> possibleValues = Arrays.asList(1, 2, 3, 4, 5, 6);
		int lcv = 100;		
		while(lcv >= 0) {
			d.roll();		
			assertTrue(possibleValues.contains(d.getValue()));
			lcv--;
		}
	}
	
	@Test
	public void testGetIcon() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		// Create a new BufferedImage
		BufferedImage bi = new BufferedImage(55, 55, BufferedImage.TYPE_INT_ARGB);
		assertNotNull(bi);
		
		// Create a new Die
		Die d = new Die(c);
		assertNotNull(d);
		assertNull(d.getIcon()); // Check that it has no icon
		
		// Set it to the BufferedImage
		d.setIcon(new ImageIcon(bi));
		assertNotNull(d.getIcon()); // Make sure it worked
	}
	
	@Test
	public void testPaintComponent() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);

		// TODO: CuBr - Figure out how to test this
	}
	

	
	@Test
	public void testSetIcon() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		// Create a new BufferedImage
		BufferedImage bi = new BufferedImage(55, 55, BufferedImage.TYPE_INT_ARGB);
		assertNotNull(bi);
		
		// Create a new Die
		Die d = new Die(c);
		assertNotNull(d);
		assertNull(d.getIcon()); // Check that it has no icon
		
		// Set it to the BufferedImage
		d.setIcon(new ImageIcon(bi));
		assertNotNull(d.getIcon()); // Make sure it worked
	}
	
	
	@Test
	public void testGetValue() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		assertEquals(d.getValue(), 0);

		d.setValue(3);
		assertEquals(d.getValue(), 3);
		assertNotEquals(d.getValue(), 5);
	}
	
	@Test
	public void testSetValue() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		assertEquals(d.getValue(), 0);

		d.setValue(3);
		assertEquals(d.getValue(), 3);
		assertNotEquals(d.getValue(), 5);
	}
	
	@Test
	public void testIsHeld() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		
		assertFalse(d.isHeld());		
		d.setState(DieState.HELD);
		assertTrue(d.isHeld());
	}
	
	@Test
	public void testIsScored() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		
		assertFalse(d.isScored());		
		d.setState(DieState.SCORED);
		assertTrue(d.isScored());
	}
	
	@Test
	public void testIsUnheld() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		
		assertTrue(d.isUnheld());		
		d.setState(DieState.HELD);
		assertFalse(d.isUnheld());
	}
	
	@Test
	public void testSetState() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		
		assertEquals(d.getState(), DieState.UNHELD);
		d.setState(DieState.SCORED);
		assertEquals(d.getState(), DieState.SCORED);
		d.setState(DieState.HELD);
		assertEquals(d.getState(), DieState.HELD);
		d.setState(DieState.UNHELD);
		assertEquals(d.getState(), DieState.UNHELD);
	}
	
	@Test
	public void testGetState() {
		FarkleController c = new FarkleController();
		assertNotNull(c);
		
		Die d = new Die(c);
		assertNotNull(d);
		
		assertEquals(d.getState(), DieState.UNHELD);
		d.setState(DieState.SCORED);
		assertEquals(d.getState(), DieState.SCORED);
		d.setState(DieState.HELD);
		assertEquals(d.getState(), DieState.HELD);
		d.setState(DieState.UNHELD);
		assertEquals(d.getState(), DieState.UNHELD);
	}
	

	/**
	 * Helper for testPaintComponent
	 * @param pixel
	 */
	public void printPixelARGB(int pixel) {
	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
	  }
	
	/**
	 * Helpers for testPaintComponent
	 * @param image
	 */
	private void walkImage(BufferedImage image) {
	    int w = image.getWidth();
	    int h = image.getHeight();
	    System.out.println("width, height: " + w + ", " + h);
	 
	    for (int i = 0; i < h; i++) {
	      for (int j = 0; j < w; j++) {
	        System.out.println("x,y: " + j + ", " + i);
	        int pixel = image.getRGB(j, i);
	        printPixelARGB(pixel);
	        System.out.println("");
	      }
	    }
	  }
}
