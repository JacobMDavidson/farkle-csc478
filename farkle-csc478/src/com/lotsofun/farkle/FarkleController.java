package com.lotsofun.farkle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FarkleController implements ActionListener, MouseListener {
	FarkleUI farkle;
	
	public void setUI(FarkleUI farkle) {
		this.farkle = farkle;
	}


	@Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource() == farkle.getRollBtn()) {
        	farkle.rollDice();
        	
        	for(int i = 0; i < 6; i++) {
        		farkle.getScores()[i].setText("" + farkle.getDice()[i].getValue());
        	}
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        DieComponent d = (DieComponent) arg0.getSource();
        d.roll();
        farkle.getScores()[9].setText("" + ((DieComponent) arg0.getSource()).getValue());
    }
    

    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        
    }

}
