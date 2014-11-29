package com.lotsofun.farkle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Displays a Farkle message on the screen, and plays the Farkle sound
 * 
 * @author Curtis Brown
 * @version 3.0.0
 *
 */
public class FarkleMessage extends JDialog {

	/** Serialize this to comply with component conventions */
	private static final long serialVersionUID = 1L;

	/** The center Farkle Message JLabel */
	JLabel farkleCenterMsg = null;

	/** The left Farkle Message JLabel */
	JLabel farkleLeftMsg = null;

	/** The right Farkle Message JLabel */
	JLabel farkleRightMsg = null;

	/** The location of the farkle sound */
	URL farkleSound = null;

	/**
	 * Constructor: Build the Farkle Message JDialog
	 */
	public FarkleMessage() {

		// Set the modality to display this in front of all windows
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		// Instantiate the image objects for the Farkle Message
		Image farkleCenterImg = null;
		Image farkleLeftImg = null;
		Image farkleRightImg = null;

		// Set the preferred size, decoration, and background color
		this.setPreferredSize(new Dimension(1024, 768));
		this.setUndecorated(true);
		this.setBackground(new Color(0, 0, 0, 0));

		// Set the Farkle Images and sound
		try {
			farkleCenterImg = ImageIO.read(getClass().getResource(
					"/images/FarkleCenter.png"));
			farkleLeftImg = ImageIO.read(getClass().getResource(
					"/images/FarkleLeft.png"));
			farkleRightImg = ImageIO.read(getClass().getResource(
					"/images/FarkleRight.png"));

			farkleSound = getClass().getResource("/sounds/farkle.wav");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Set the JLabels with the Farkle images
		farkleCenterMsg = new JLabel(new ImageIcon(
				farkleCenterImg
						.getScaledInstance(1000, 200, Image.SCALE_SMOOTH)));
		farkleRightMsg = new JLabel(
				new ImageIcon(farkleRightImg.getScaledInstance(1000, 755,
						Image.SCALE_SMOOTH)));
		farkleLeftMsg = new JLabel(new ImageIcon(
				farkleLeftImg.getScaledInstance(1000, 755, Image.SCALE_SMOOTH)));

		// Add the JLabels to this JDialog
		this.add(farkleCenterMsg);
		this.add(farkleRightMsg);
		this.add(farkleLeftMsg);

		// Size the window to fit the preferred size and layout of its
		// subcomponents
		this.pack();

	}

	/**
	 * Animates the Farkle Message, and plays the farkle sound.
	 * 
	 * @param visible
	 *            Boolean - display the Farkle Message
	 */
	@Override
	public void setVisible(boolean visible) {

		// If visible is true, display the Farkle message
		if (visible) {

			// Timer used to animate the message
			final Timer fadeTimer = new Timer(50, null);

			// ActionListener for the fade timer
			ActionListener listener = new ActionListener() {

				// Set the opacity to 1
				BigDecimal opacity = BigDecimal.valueOf(1);

				// Number of steps in the animation of the Farkle message
				int step = 4;

				/**
				 * Animate the message in the actionPerformed method
				 */
				@Override
				public void actionPerformed(ActionEvent e) {

					// Start playing the Farkle sound when the JDialog is first
					// diaplayed
					if (step == 4) {
						playFarkleSound();
						step--;
					}

					/*
					 * If step = 0, the animation is complete, reset to farkle
					 * left message, set step back to 4, and hide this JDialog
					 */
					if (step == 0) {
						fadeTimer.stop();
						FarkleMessage.this.setVisible(false);
						FarkleMessage.this.getContentPane().add(farkleLeftMsg);
						step = 4;
					}

					// If the opacity != 0, keep fading
					if (opacity.compareTo(BigDecimal.valueOf(0)) > 0) {
						fade();

						// If the opacity <= 0, change the
						// JLabel and decrement step
					} else if (opacity.compareTo(BigDecimal.valueOf(0)) <= 0) {
						switch (step) {
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

				/**
				 * Decrement the opacity, and set the JDialog to the new opacity
				 */
				public void fade() {
					if (step == 1) {
						opacity = opacity.subtract(BigDecimal.valueOf(0.05));
					} else {
						opacity = opacity.subtract(BigDecimal.valueOf(0.1));
					}
					FarkleMessage.this.setOpacity(opacity.floatValue());
				}

				/**
				 * Remove the provided JLabel, add a different JLabel, and set
				 * opacity to 1.
				 * 
				 * @param c
				 *            the JLabel to remove
				 */
				public void remove(Component c) {

					// Remove the provided JLabel
					FarkleMessage.this.remove(c);

					// If the message has not finished playing, set the opacity
					// to 1
					if (step > 0) {
						opacity = BigDecimal.valueOf(1.00);
					}

					// Add the next JLabel in the sequence based in the removed
					// JLabel
					if (c.equals(farkleLeftMsg)) {
						FarkleMessage.this.getContentPane().add(farkleRightMsg);
					} else if (c.equals(farkleRightMsg)) {
						FarkleMessage.this.getContentPane()
								.add(farkleCenterMsg);
					}

					// Size the window to fit the preferred size and layout of
					// its subcomponents
					FarkleMessage.this.pack();

					// Play the farkle sound again
					if (step > 1) {
						playFarkleSound();
					}
				}
			};

			// Add the action listener to the fade timer and start it
			fadeTimer.addActionListener(listener);
			fadeTimer.start();

			// Show this JDialog
			super.setVisible(true);
		} else {

			// Hide this JDialog
			super.setVisible(false);
		}
	}

	/**
	 * Play the Farkle sound
	 */
	public void playFarkleSound() {

		// Use AudioStream and Clip to play the Farkle Sound
		try {
			AudioInputStream audioStream;
			audioStream = AudioSystem.getAudioInputStream(farkleSound);
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
}
