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
	public FarkleUI fU;
	
	@Test
	public void testSetUI() {
		
		Runnable r = new Runnable() {
			public void run() {
				f = new FarkleController();
				fU = new FarkleUI(f);
			}
		};
		
		Thread t = new Thread(r);
		t.start();
				
		Window.getOwnerlessWindows();
		
		boolean finished = false;
		while(!fU.isValid()) {
			int i = 1;
		}
	    	for (Window w : JDialog.getWindows()) {
	    		if(w instanceof JDialog && ((JDialog)w).getTitle().equals("Player Mode")) {
	    			JButton btn = getButton(((JDialog)w), "1 Player");
	    			if(null != btn) {
	    				btn.doClick();
	    			}
	    		}
	    	}
    	
    	for(Window w : JDialog.getWindows()) {
    		if(w instanceof JDialog && ((JDialog) w).getTitle().equals("Player 1")) {
				JTextField j = getTextField(((JDialog) w));
				j.setText("Tester");
			}
    	}

		fU.getComponents();
		assertEquals(f.farkleUI, fU);
		fU.dispose();
	}
	
	
	public static JButton getButton(Container container, String text) {
        JButton btn = null;
        List<Container> children = new ArrayList<Container>(25);
        for (Component child : container.getComponents()) {
            System.out.println(child);
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
	
	public static JTextField getTextField(Container container) {
		JTextField textField = null;
        List<Container> children = new ArrayList<Container>(25);
        for (Component child : container.getComponents()) {
            System.out.println(child);
            if (child instanceof JTextField) {
                JTextField field = (JTextField) child;
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
}
