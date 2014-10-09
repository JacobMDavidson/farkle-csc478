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

public class Die extends JLabel {

	// Serialize this to comply with component conventions
	private static final long serialVersionUID = 1L;
	
	// Create constants for drawing the die
	private static final int BORDER_GAP = 3;
    private static final Border HELD_BORDER = 
          BorderFactory.createLineBorder(Color.red, BORDER_GAP);
    private static final Border UNHELD_BORDER = 
          BorderFactory.createEmptyBorder(BORDER_GAP, BORDER_GAP, BORDER_GAP, BORDER_GAP);        
	private static final int OUT_FRAME = 55;
    private static final int ARC = 8;
    private static final float STROKE_WIDTH = 2f;
    private static final int SML_GAP = 1;
    private static final int OVAL_RADIUS = 12;
    
    private Random random = new Random();
    private Icon icon;
    private int value;
    private DieState state;
    

    /**
     * Constructor takes a reference to the
     * controller as a listener
     * @param controller
     */
	public Die(FarkleController controller) {
    	super();
    	this.state = DieState.UNHELD;
    	this.addMouseListener(controller);
    }
	
	/**
	 * Sets the die value to a
	 * random number and repaints it
	 */
	public void roll() {
		
		/************************************
		 * 3.2.0: Each die that is rolled
		 *  shall be assigned a random value
		 *   from 1 to 6 (inclusive) at the
		 *    conclusion of the roll.
		 ************************************/
		
		/***********************************************
		 * 4.6.0: If the player elects to roll
		 * again, the remaining dice are rolled 
		 * and the process returns to requirement 4.2.0.
		 ***********************************************/
		 // Roll only if die is unheld
		if(this.state == DieState.UNHELD) {
			value = random.nextInt(6) + 1;
			this.repaint();
		}
	}
    
    
    /**
     * Draw the Die object and sets the
     * face value to the current
     * value of the this.value
     */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		
        BufferedImage img = new BufferedImage(OUT_FRAME, OUT_FRAME, 
              BufferedImage.TYPE_INT_ARGB);
        g2 = img.createGraphics();
        Stroke stroke = new BasicStroke(STROKE_WIDTH);
        
        // Paints differently for a SCORED die
        if(this.state != DieState.SCORED) {
        	g2.setColor(Color.white);
        } else {
        	g2.setColor(Color.gray);
        }
        
        g2.fillRoundRect(0, 0, OUT_FRAME, OUT_FRAME, ARC, ARC);
        g2.setColor(Color.black);
        g2.setStroke(stroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
              RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(SML_GAP, SML_GAP, OUT_FRAME - SML_GAP * 2, 
              OUT_FRAME - SML_GAP * 2, ARC, ARC);
        g2.setColor(Color.black);
        
        
        switch (value) {
        case 1:
           fillOval(g2, 1, 1);
           break;
        case 2:
           fillOval(g2, 0, 0);
           fillOval(g2, 2, 2);
           break;
        case 3:
           fillOval(g2, 0, 0);
           fillOval(g2, 1, 1);
           fillOval(g2, 2, 2);
           break;
        case 4:
           fillOval(g2, 0, 0);
           fillOval(g2, 0, 2);
           fillOval(g2, 2, 0);
           fillOval(g2, 2, 2);
           break;
        case 5:
           fillOval(g2, 0, 0);
           fillOval(g2, 0, 2);
           fillOval(g2, 1, 1);
           fillOval(g2, 2, 0);
           fillOval(g2, 2, 2);
           break;
        case 6:
           fillOval(g2, 0, 0);
           fillOval(g2, 0, 1);
           fillOval(g2, 0, 2);
           fillOval(g2, 2, 0);
           fillOval(g2, 2, 1);
           fillOval(g2, 2, 2);
           break;

        default:
           break;
        }

        g2.dispose();
        this.setIcon(new ImageIcon(img));
        this.repaint();
     }

     private void fillOval(Graphics2D g2, int row, int col) {
        double rectWidth = OUT_FRAME - 4 * STROKE_WIDTH;
        int x = (int) (2 * STROKE_WIDTH - OVAL_RADIUS / 2 + (col + 0.5) * rectWidth / 3);
        int y = (int) (2 * STROKE_WIDTH - OVAL_RADIUS / 2 + (row + 0.5) * rectWidth / 3);

        g2.fillOval(x, y, OVAL_RADIUS, OVAL_RADIUS);
     }

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
    public int getValue() {
		return value;
	}
    
	public void setValue(int value) {
		this.value = value;
		this.repaint();
	}
	
	public boolean isHeld() {
		return (this.state == DieState.HELD);
	}
	
	public boolean isScored() {
		return (this.state == DieState.SCORED);
	}
	
	public boolean isUnheld() {
		return (this.state == DieState.UNHELD);
	}
	
	public void setState(DieState dieState) {
		if(dieState == DieState.HELD) {
			this.state = DieState.HELD;
			this.setBorder(HELD_BORDER);
		} else if(dieState == DieState.SCORED) {
			this.state = DieState.SCORED;
			this.setBorder(UNHELD_BORDER);
		} else {
			this.state = DieState.UNHELD;
			this.setBorder(UNHELD_BORDER);
		}
	}
	
	public DieState getState() {
		return this.state;
	}
}
