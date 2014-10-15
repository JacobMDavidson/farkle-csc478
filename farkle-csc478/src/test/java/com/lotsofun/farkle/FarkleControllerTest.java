package com.lotsofun.farkle;

import static org.junit.Assert.*;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class FarkleControllerTest {
	public FarkleController f;
	public Game g;
	public FarkleUI fU;
	public Task task;
	
	
	@Test
	public void testSetUI() {
		task = new Task();
		Thread t = new Thread(task);
		t.start();
		
		boolean finished = false;
		while(!finished) {
			// Choose 1 player
	    	for (Window w : JDialog.getWindows()) {
	    		if(w instanceof JDialog && "Player Mode".equals(((JDialog)w).getTitle())) {
	    			JButton btn = getButton(((JDialog)w), "1 Player");
	    			if(null != btn) {
	    				btn.doClick();
	    			}
	    			// Set player 1 to 'Tester'
	    		} else if(w instanceof JDialog && "Player 1".equals(((JDialog)w).getTitle())) {
	    			JTextField j = getTextField(((JDialog) w));
	    			if(j != null) {
						j.setText("Tester");
												
							// Click ok
							for (Window w1 : JDialog.getWindows()) {
								if(w1 instanceof JDialog && "Player Mode".equals(((JDialog) w1).getTitle())) {
					    			JButton btn = getButton(((JDialog)w), "OK");
					    			if(null != btn) {
					    				btn.doClick();
					    				finished = true;
					    				break;
					    			}
					    		}
							}
						}
	    			}
	    		}
	    	}
		f.setUI(fU);
		assertEquals(f.farkleUI, fU); // Check that the created reference matches the one in the controller
		while(t.isAlive()) {
			int i = 0;
		}
	}
	
	
	@Test
	public void testSetGame() {
		task = new Task();
		
		Thread t = new Thread(task);
		t.start();
		
		boolean finished = false;
		while(!finished) {
			// Choose 1 player
	    	for (Window w : JDialog.getWindows()) {
	    		if(w instanceof JDialog && "Player Mode".equals(((JDialog)w).getTitle())) {
	    			JButton btn = getButton(((JDialog)w), "1 Player");
	    			if(null != btn) {
	    				btn.doClick();
	    			}
	    			// Set player 1 to 'Tester'
	    		} else if(w instanceof JDialog && "Player 1".equals(((JDialog)w).getTitle())) {
	    			JTextField j = getTextField(((JDialog) w));
	    			if(j != null) {
						j.setText("Tester");
												
							// Click ok
							for (Window w1 : JDialog.getWindows()) {
								if(w1 instanceof JDialog && "Player Mode".equals(((JDialog) w1).getTitle())) {
					    			JButton btn = getButton(((JDialog)w), "OK");
					    			if(null != btn) {
					    				btn.doClick();
					    				finished = true;
					    			}
					    		}
							}
						}
	    			}
	    		}
	    	}
		
		// Create a new game object
		Game game = new Game(GameMode.SINGLEPLAYER, f);
		assertNotEquals(f.farkleGame, game); // Confirm the controller's current game reference is different
		f.setGame(game); // Set it to the new game reference
		assertEquals(f.farkleGame, game); // Confirm that the references are the same
		while(t.isAlive()) {
			int i = 0;
		}
	}
	
	public void testFarkle() {
		task = new Task();
		
		Thread t = new Thread(task);
		t.start();
		
		boolean finished = false;
		while(!finished) {
			// Choose 1 player
	    	for (Window w : JDialog.getWindows()) {
	    		if(w instanceof JDialog && "Player Mode".equals(((JDialog)w).getTitle())) {
	    			JButton btn = getButton(((JDialog)w), "1 Player");
	    			if(null != btn) {
	    				btn.doClick();
	    			}
	    			// Set player 1 to 'Tester'
	    		} else if(w instanceof JDialog && "Player 1".equals(((JDialog)w).getTitle())) {
	    			JTextField j = getTextField(((JDialog) w));
	    			if(j != null) {
						j.setText("Tester");
												
							// Click ok
							for (Window w1 : JDialog.getWindows()) {
								if(w1 instanceof JDialog && "Player Mode".equals(((JDialog) w1).getTitle())) {
					    			JButton btn = getButton(((JDialog)w), "OK");
					    			if(null != btn) {
					    				btn.doClick();
					    				finished = true;
					    			}
					    		}
							}
						}
	    			}
	    		}
	    	}
		f.farkle();
		assertTrue("FARKLE!".equals(fU.getRunningScore().getText()));
		assertTrue(fU.getDice(DieState.UNHELD).size() == 6);
		for(Die d : fU.getDice(DieState.UNHELD)) {
			assertTrue(d.getValue() == 0);
		}
		Player p = g.getCurrentPlayer();
		assertTrue(g.getCurrentPlayer().getTurnScores()[p.getTurnNumber() - 1] == 0);
		while(t.isAlive()) {
			int i = 0;
		}
	}
	
	
	/**
	 * Helper method for testSetUI
	 * @param container
	 * @param text
	 * @return
	 */
	public static JButton getButton(Container container, String text) {
        JButton btn = null;
        List<Container> children = new ArrayList<Container>(25);
        for (Component child : container.getComponents()) {
            if (child instanceof JButton) {
                JButton button = (JButton) child;
                if (text.equals(button.getText())) {
                    btn = button;
                    break;
                }
            } else if (child instanceof Container) {
                children.add((Container) child);
            }
        }
        if (btn == null) {
            for (Container cont : children) {
                JButton button = getButton(cont, text);
                if (button != null) {
                    btn = button;
                    break;
                }
            }
        }
        return btn;
    }
	
	/**
	 * Helper method for testSetUI
	 * @param container
	 * @return
	 */
	public static JTextField getTextField(Container container) {
		JTextField textField = null;
        List<Container> children = new ArrayList<Container>(25);
        for (Component child : container.getComponents()) {
            if (child instanceof JTextField) {
                JTextField field = (JTextField) child;
                textField = field;
                break;
            } else if (child instanceof Container) {
                children.add((Container) child);
            }
        }
        if (textField == null) {
            for (Container cont : children) {
                JTextField field = getTextField(cont);
                if (field != null) {
                    textField = field;
                    break;
                }
            }
        }
        return textField;
	}
	
	/**
	 * Nested class to create
	 * an instance of the Farkle
	 * components in a separate
	 * thread for testing purposes
	 *
	 */
	public class Task implements Runnable {
		@Override
		public void run() {
			f = new FarkleController();
			fU = new FarkleUI(f);
			g = f.farkleGame;
		}
		
		public void close() {
			fU.dispose();
		}
	}
}
