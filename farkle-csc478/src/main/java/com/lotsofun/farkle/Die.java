package com.lotsofun.farkle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * Displays a die and its state in the graphic user interface with the a value 
 * from 1 to 6 (inclusive). The die value is determined via the roll method. Tracks 
 * the state of the die, boxing a held die in a yellow border, and shading a scored 
 * die in gray.
 * @author Curtis Brown
 * @version 3.0.0
 */
public class Die extends JLabel {

	/** Serialize this to comply with component conventions */
	private static final long serialVersionUID = 1L;
	
	/** The standard border gap used */
	private static final int BORDER_GAP = 3;
	
	/** The border used for an unheld die */
    private static final Border UNHELD_BORDER = 
          BorderFactory.createEmptyBorder(BORDER_GAP, BORDER_GAP, BORDER_GAP, BORDER_GAP);   
    
    /** The border used for a held die */
    private static final Border HELD_BORDER = 
    		BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(BORDER_GAP, 12, BORDER_GAP, 12),
    				BorderFactory.createLineBorder(Color.YELLOW, BORDER_GAP));
    
    /** Standard die image width and height */
	private static final int OUT_FRAME = 55;
	
	/** Die corner radius */
    private static final int ARC = 8;
    
    /** Standard line thickness */
    private static final float STROKE_WIDTH = 2f;
    
    /** Small gap used around die graphic */
    private static final int SML_GAP = 1;
    
    /** Radius of the die value dots */
    private static final int OVAL_RADIUS = 12;
    
    /** Random number used to generate die values for a given roll */
    private Random random = new Random();
    
    /** The created die element */
    private Icon icon;
    
    /** The value of the die */
    private char value;
    
    /** The state of the die */
    private DieState state;
    
    /** The possible die values */
    private final char[] values = {'1', '2', '3', '4', '5', '6'};
    

    /**
     * Constructor takes a reference to the controller to add as a listener, and
     * sets the initial die state to unheld.
     * @param controller The farkle controller this will reference
     */
	public Die(FarkleController controller) {
		
		// Call the default JLabel constructor
    	super();
    	
    	// Set the die state to unheld
    	this.state = DieState.UNHELD;
    	
    	// Add a mouse listener to this die from the controller
    	this.addMouseListener(controller);
    }
	
	/**
	 * Sets the die value to a
	 * random number and repaints it
	 */
	public void roll() {
		
		/***********************************************
		 * 3.2.0: Each die that is rolled shall be 
		 * assigned a random value from 1 to 6 
		 * (inclusive) at the conclusion of the roll.
		 ***********************************************/
		/***********************************************
		 * 4.6.0: If the player elects to roll
		 * again, the remaining dice are rolled 
		 * and the process returns to requirement 4.2.0.
		 ***********************************************/
		// Roll die only if die is unheld
		if(this.state == DieState.UNHELD) {
			
			// Set the die value to a random char from 1 to 6
			value = (char) values[random.nextInt(6)];
			
			// Repaint the die
			this.repaint();
		}
	}
    
    
    /**
     * Draw the Die object, set the face value to the current
     * value of the die, and decorate it according to the die 
     * state
     */
	@Override
	public void paintComponent(Graphics g) {
		
		// Cast the Graphics object as a Graphics2D
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		
		// Set the image size
        BufferedImage img = new BufferedImage(OUT_FRAME, OUT_FRAME, 
              BufferedImage.TYPE_INT_ARGB);
        g2 = img.createGraphics();
        
        // Set the stroke width
        Stroke stroke = new BasicStroke(STROKE_WIDTH);
        
        // The die is white if it is not in a scored state
        if(this.state != DieState.SCORED) {
        	g2.setColor(Color.white);
        	
        // Else, the die is in a scored state, set its color to gray
        } else {
        	g2.setColor(Color.gray);
        }
        
        // Generate the die
        g2.fillRoundRect(0, 0, OUT_FRAME, OUT_FRAME, ARC, ARC);
        g2.setColor(Color.black);
        g2.setStroke(stroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
              RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(SML_GAP, SML_GAP, OUT_FRAME - SML_GAP * 2, 
              OUT_FRAME - SML_GAP * 2, ARC, ARC);
        g2.setColor(Color.black);
        
        // Add the die dots based on the current die value
        switch (value) {
        case '1':
           fillOval(g2, 1, 1);
           break;
        case '2':
           fillOval(g2, 0, 0);
           fillOval(g2, 2, 2);
           break;
        case '3':
           fillOval(g2, 0, 0);
           fillOval(g2, 1, 1);
           fillOval(g2, 2, 2);
           break;
        case '4':
           fillOval(g2, 0, 0);
           fillOval(g2, 0, 2);
           fillOval(g2, 2, 0);
           fillOval(g2, 2, 2);
           break;
        case '5':
           fillOval(g2, 0, 0);
           fillOval(g2, 0, 2);
           fillOval(g2, 1, 1);
           fillOval(g2, 2, 0);
           fillOval(g2, 2, 2);
           break;
        case '6':
           fillOval(g2, 0, 0);
           fillOval(g2, 0, 1);
           fillOval(g2, 0, 2);
           fillOval(g2, 2, 0);
           fillOval(g2, 2, 1);
           fillOval(g2, 2, 2);
           break;
           
        /* The following die values are used to display "Farkle"
         * On the dice when starting a new game.
         */
        case 'f':
        	fillOval(g2, 0, 0);
        	fillOval(g2, 1, 0);
        	fillOval(g2, 2, 0);
        	fillOval(g2, 0, 1);
        	fillOval(g2, 0, 2);
        	fillOval(g2, 1, 1);
        	break;
        case 'a':
        	fillOval(g2, 2, 0);
        	fillOval(g2, 2, 2);
        	fillOval(g2, 0, 1);
        	fillOval(g2, 1, 1);
        	break;
        case 'r':
        	fillOval(g2, 0, 0);
        	fillOval(g2, 1, 0);
        	fillOval(g2, 2, 0);
        	fillOval(g2, 0, 1);
        	fillOval(g2, 0, 2);
        	break;
        case 'k':
        	fillOval(g2, 0, 0);
        	fillOval(g2, 1, 0);
        	fillOval(g2, 2, 0);
        	fillOval(g2, 0, 2);
        	fillOval(g2, 2, 2);
        	fillOval(g2, 1, 1);
        	break;
        case 'l':
        	fillOval(g2, 0, 0);
        	fillOval(g2, 1, 0);
        	fillOval(g2, 2, 0);
        	fillOval(g2, 2, 2);
        	fillOval(g2, 2, 1);
        	break;
        case 'e':
        	fillOval(g2, 0, 0);
        	fillOval(g2, 1, 0);
        	fillOval(g2, 2, 0);
        	fillOval(g2, 0, 1);
        	fillOval(g2, 0, 2);
        	fillOval(g2, 1, 1);
        	fillOval(g2, 2, 0);
        	fillOval(g2, 2, 2);
        	fillOval(g2, 2, 1);
        	break;
        default:
           break;
        }

        // Done creating the die graphic, dispose of the Graphics2D object
        g2.dispose();
        
        // Set the Image Icon for this JLabel to the created image
        this.setIcon(new ImageIcon(img));
        
        // Repaint the JLabel
        this.repaint();
    }

	/**
	 * Adds a black dot to the die graphic at the specified location
	 * @param g2 The graphics2D object
	 * @param row The die row to to which the dot is added
	 * @param col The die column to which the dot is added
	 */
    private void fillOval(Graphics2D g2, int row, int col) {
    	
    	// Calculate the width of the die (minus the stroke width)
        double rectWidth = OUT_FRAME - 4 * STROKE_WIDTH;
        
        // Convert the specified column to an absolute x location for the graphic
        int x = (int) (2 * STROKE_WIDTH - OVAL_RADIUS / 2 + (col + 0.5) * rectWidth / 3);
        
        // Convert the specified row to an absolute y location for the graphic
        int y = (int) (2 * STROKE_WIDTH - OVAL_RADIUS / 2 + (row + 0.5) * rectWidth / 3);
        
        // Draw the black dot at the specified location.
        g2.fillOval(x, y, OVAL_RADIUS, OVAL_RADIUS);
    }

    /**
     * Get the Icon for this JLabel
     */
	@Override
	public Icon getIcon() {
		return icon;
	}

	/**
	 * Set the Icon got this JLabel
	 */
	@Override
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	/**
	 * Get the value of this die If the value isn't a number then 0 
	 * is returned else the value is returned as an integer
	 * @return The int die value
	 */
	public int getValue() {
		
		// Return the numeric value of the char representing the die value
    	int retVal = 0;
		retVal = Character.digit(value, 10);
		return (retVal == -1) ? 0 : retVal;
	}
    
	/**
	 * Set the die to a specified value
	 * @param value int specified value to set
	 */
	public void setValue(int value) {
		
		// Cast the value to a char and set it
		this.value = (char) value;
		
		// Repaint the die
		this.repaint();
	}
	
	/**
	 * Determine if the die is in a held state
	 * @return True if the die is held
	 */
	public boolean isHeld() {
		return (this.state == DieState.HELD);
	}
	
	/**
	 * Determine if the die is in a scored state
	 * @return True if the die is scored
	 */
	public boolean isScored() {
		return (this.state == DieState.SCORED);
	}
	
	/**
	 * Determine if the die is in an unheld state
	 * @return True if the die is unheld
	 */
	public boolean isUnheld() {
		return (this.state == DieState.UNHELD);
	}
	
	/**
	 * Set the state of this die
	 * @param dieState The state to which the die is set
	 */
	public void setState(DieState dieState) {
		
		// Set the die state
		this.state = dieState;
		
		// If the new die state is held, set the border to held
		if(dieState == DieState.HELD) {
			this.setBorder(HELD_BORDER);
			
		// Else, set the border to unheld
		} else {
			this.setBorder(UNHELD_BORDER);
		} 
	}
	
	/**
	 * Get the state of this die
	 * @return The DieState of this die
	 */
	public DieState getState() {
		return this.state;
	}
}
