package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;








import java.math.BigDecimal;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class FarkleMessage extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	JLabel farkleCenterMsg = null;
	JLabel farkleLeftMsg = null;
	JLabel farkleRightMsg = null;
	URL farkleSound1 = null;
	URL farkleSound2 = null;
	URL farkleSound3 = null;
	URL currentFarkleSound = null;
	
	
	public FarkleMessage() {
		
		Image farkleCenterImg = null;
		Image farkleLeftImg = null;
		Image farkleRightImg = null;
	
		this.setPreferredSize(new Dimension(1024, 768));
		this.setResizable(false);
		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 0));
		try {
			farkleCenterImg = ImageIO.read(getClass().getResource(
					"/images/FarkleCenter.png"));
			farkleLeftImg = ImageIO.read(getClass().getResource(
					"/images/FarkleLeft.png"));
			farkleRightImg = ImageIO.read(getClass().getResource(
					"/images/FarkleRight.png"));
			
			farkleSound1 = getClass().getResource("/sounds/farkle1.wav");
			farkleSound2 = getClass().getResource("/sounds/farkle2.wav");
			farkleSound3 = getClass().getResource("/sounds/farkle3.wav");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		farkleCenterMsg = new JLabel(new ImageIcon(farkleCenterImg.getScaledInstance(1000, 200, Image.SCALE_SMOOTH)));
		this.add(farkleCenterMsg);
		farkleRightMsg = new JLabel(new ImageIcon(farkleRightImg.getScaledInstance(1000, 755, Image.SCALE_SMOOTH)));
		this.add(farkleRightMsg);
		farkleLeftMsg = new JLabel(new ImageIcon(farkleLeftImg.getScaledInstance(1000, 755, Image.SCALE_SMOOTH)));
		this.add(farkleLeftMsg);
		
		
		
		// Build the window
		this.setLocationRelativeTo(null);
		this.pack();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		
	}
	
	
	
	
	@Override
	public void setVisible(boolean visible) {
		
		if(visible) {
			currentFarkleSound = chooseFarkleSound();
			
			final Timer fadeTimer = new Timer(50, null);
			ActionListener listener = new ActionListener(){
				BigDecimal opacity = BigDecimal.valueOf(1);
				int step = 4;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(step == 4) {
						playFarkleSound();
						step--;
					}
					
					if(step == 0) {
						fadeTimer.stop();
						FarkleMessage.this.setVisible(false);
						FarkleMessage.this.getContentPane().add(farkleLeftMsg);
						step = 4;
					}
					
					if(opacity.compareTo(BigDecimal.valueOf(0)) > 0) {
						fade();
					} else if(opacity.compareTo(BigDecimal.valueOf(0)) <= 0) {
						switch(step) {
						case 3:
							remove(farkleLeftMsg);
							break;
						case 2:
							remove(farkleRightMsg);
							break;
						case 1:
							remove(farkleCenterMsg);
							break;
						}
						step--;
					}
				}
				
				
				public void fade() {
					if(step == 1) {
						opacity = opacity.subtract(BigDecimal.valueOf(0.05));
					} else {
						opacity = opacity.subtract(BigDecimal.valueOf(0.1));
					}
					FarkleMessage.this.setOpacity(opacity.floatValue());
				}
				
				public void remove(Component c) {
					FarkleMessage.this.remove(c);
					if(step > 0) {
						opacity = BigDecimal.valueOf(1.00);
					}
					
					if(c.equals(farkleLeftMsg)) {
						FarkleMessage.this.getContentPane().add(farkleRightMsg);
					} else if (c.equals(farkleRightMsg)) {
						FarkleMessage.this.getContentPane().add(farkleCenterMsg);
					}
					
					FarkleMessage.this.pack();
					
					if(step > 1) {
						playFarkleSound();
					}
				}
			};
				
			fadeTimer.addActionListener(listener);
			fadeTimer.start();
			super.setVisible(true);
		} else {
			super.setVisible(false);
		}
	}
	
	public void playFarkleSound() {
		try {
			AudioInputStream audioStream;
			audioStream = AudioSystem.getAudioInputStream(farkleSound3);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (UnsupportedAudioFileException x) {
			x.printStackTrace();
		} catch (LineUnavailableException y) {
			y.printStackTrace();
		} catch (IOException z) {
			z.printStackTrace();
		}
	}
	
	public URL chooseFarkleSound() {
		Random r = new Random();
		
		if(r.nextInt(2) == 0) {
			return farkleSound1;
		} else {
			return farkleSound2;
		}
	}
}
