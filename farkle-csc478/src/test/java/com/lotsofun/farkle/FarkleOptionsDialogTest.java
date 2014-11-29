package com.lotsofun.farkle;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Complete JUnit testing of the FarkleOptionsDialog class
 * 
 * @author Jacob Davidson
 * @version 3.0.0
 */
public class FarkleOptionsDialogTest {

	/** The FarkleOptionsDialog object to test */
	FarkleOptionsDialog farkleOptionsDialog;

	/** The primary background color */
	private Color greenBackground = new Color(35, 119, 34);

	/**
	 * Instantiate the objects before testing begins
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		// Instantiate the farkleOptionsDialog passing null as the parent frame
		farkleOptionsDialog = new FarkleOptionsDialog(null);

	}

	/**
	 * Dispose the window at the completion of testing
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {

		// Dispose of the farkleOptionsDialog box
		farkleOptionsDialog.dispose();

	}

	/**
	 * Test the FarkleOptionsDialog(JFrame) constructor
	 */
	@Test
	public void testConstructor() {

		// Assert that the farkleOptionsDialog is not null
		assertNotNull(farkleOptionsDialog);

		// Assert that the player1Name and player2Name Strings are empty
		assertTrue(farkleOptionsDialog.getPlayer1Name().isEmpty());
		assertTrue(farkleOptionsDialog.getPlayer2Name().isEmpty());

		// Assert that the gameMode is set to GameMode.SINGLEPLAYER
		assertTrue(farkleOptionsDialog.getGameMode() == GameMode.SINGLEPLAYER);

		// Assert that the opponent player type is PlayerType.USER
		assertTrue(farkleOptionsDialog.getPlayerType() == PlayerType.USER);

		// Assert that the preferred size should is set to Dimension(750, 250)
		assertTrue(farkleOptionsDialog.getPreferredSize().equals(
				new Dimension(750, 250)));

		// Assert that the modality is set to ModalityType.APPLICATION_MODAL
		assertTrue(farkleOptionsDialog.getModalityType() == ModalityType.APPLICATION_MODAL);

		// Assert that the window is undecorated
		assertTrue(farkleOptionsDialog.isUndecorated());

		// Assert that the window is not resizeable
		assertTrue(!farkleOptionsDialog.isResizable());

		// Assert that the JDialog box is not visible
		assertTrue(!farkleOptionsDialog.isVisible());

	}

	/**
	 * Test the getPlayerType() and setPlayerType(PlayerType) methods
	 */
	@Test
	public void testGetAndSetPlayerType() {

		// The player type should be initially set to PlayerType.USER
		assertEquals(farkleOptionsDialog.getPlayerType(), PlayerType.USER);

		// Set the player type to PlayerType.COMPUTER and test again
		farkleOptionsDialog.setPlayerType(PlayerType.COMPUTER);
		assertEquals(farkleOptionsDialog.getPlayerType(), PlayerType.COMPUTER);

		// Set the player type to null and test again
		farkleOptionsDialog.setPlayerType(null);
		assertNull(farkleOptionsDialog.getPlayerType());

		// Set the player type to PlayerType.USER and test again
		farkleOptionsDialog.setPlayerType(PlayerType.USER);
		assertEquals(farkleOptionsDialog.getPlayerType(), PlayerType.USER);

	}

	/**
	 * Test the getPlayer1Name() and setPlayer1Name(String) methods
	 */
	@Test
	public void testGetAndSetPlayer1Name() {

		// Player 1 Name should initially be empty
		assertTrue(farkleOptionsDialog.getPlayer1Name().isEmpty());

		// Set the player 1 name to null and test again
		farkleOptionsDialog.setPlayer1Name(null);
		assertNull(farkleOptionsDialog.getPlayer1Name());

		// Set the player 1 name to a string and test again
		farkleOptionsDialog.setPlayer1Name("Jake");
		assertTrue(farkleOptionsDialog.getPlayer1Name().equals("Jake"));

	}

	/**
	 * Test the getPlayer2Name() and setPlayer2Name(String) methods
	 */
	@Test
	public void testGetAndSetPlayer2Name() {

		// Player 2 Name should initially be empty
		assertTrue(farkleOptionsDialog.getPlayer2Name().isEmpty());

		// Set the player 2 name to null and test again
		farkleOptionsDialog.setPlayer2Name(null);
		assertNull(farkleOptionsDialog.getPlayer2Name());

		// Set the player 2 name to a string and test again
		farkleOptionsDialog.setPlayer2Name("Brant");
		assertTrue(farkleOptionsDialog.getPlayer2Name().equals("Brant"));

	}

	/**
	 * Test the getGameMode() and setGameMode(GameMode) methods
	 */
	@Test
	public void testGetAndSetGameMode() {

		// The game mode should initially be set to GameMode.SINGLEPLAYER
		assertEquals(farkleOptionsDialog.getGameMode(), GameMode.SINGLEPLAYER);

		// Set the game mode to GameMode.MULTIPLAYER and test again
		farkleOptionsDialog.setGameMode(GameMode.MULTIPLAYER);
		assertEquals(farkleOptionsDialog.getGameMode(), GameMode.MULTIPLAYER);

		// Set the game mode to null and test again
		farkleOptionsDialog.setGameMode(null);
		assertNull(farkleOptionsDialog.getGameMode());

		// Set the game mode to GameMode.SINGLEPLAYER and test again
		farkleOptionsDialog.setGameMode(GameMode.SINGLEPLAYER);
		assertEquals(farkleOptionsDialog.getGameMode(), GameMode.SINGLEPLAYER);
	}

	/**
	 * Test the mouseClicked(MouseEvent e) method
	 */
	@Test
	public void testMouseClicked() {

		// Get a reference to each of the JLabel objects used for selection
		JLabel singlePlayerLabel = farkleOptionsDialog.getJLabel(1);
		JLabel multiPlayerLabel = farkleOptionsDialog.getJLabel(2);
		JLabel humanPlayerLabel = farkleOptionsDialog.getJLabel(3);
		JLabel computerPlayerLabel = farkleOptionsDialog.getJLabel(4);

		// Get the playerTypeSelectionPanel
		JPanel playerTypeSelectionPanel = farkleOptionsDialog.getJPanel(1);

		// Get the player two panel information
		JPanel playerTwoNamePanel = farkleOptionsDialog.getJPanel(2);
		JLabel playerTwoNameLabel = farkleOptionsDialog.getJLabel(5);
		JTextField playerTwoName = farkleOptionsDialog.getJTextField(1);

		// Instantiate MouseEvent objects that simulate the user clicking on the
		// above
		// JLabels
		MouseEvent singlePlayerLabelClicked = new MouseEvent(singlePlayerLabel,
				1, 1, 0, 0, 0, 1, false);
		MouseEvent multiPlayerLabelClicked = new MouseEvent(multiPlayerLabel,
				1, 1, 0, 0, 0, 1, false);
		MouseEvent humanPlayerLabelClicked = new MouseEvent(humanPlayerLabel,
				1, 1, 0, 0, 0, 1, false);
		MouseEvent computerPlayerLabelClicked = new MouseEvent(
				computerPlayerLabel, 1, 1, 0, 0, 0, 1, false);

		/*
		 * When the dialog box opens, the singlePlayerLabel is highlighted, test
		 * the click on the multiPlayerLabel.
		 */
		farkleOptionsDialog.mouseClicked(multiPlayerLabelClicked);

		// Assert that singlePlayerLabel is deselected
		assertEquals(singlePlayerLabel.getBackground(), greenBackground);
		assertEquals(singlePlayerLabel.getForeground(), Color.WHITE);

		// Assert that multiPlayer is selected
		assertEquals(multiPlayerLabel.getBackground(), Color.YELLOW);
		assertEquals(multiPlayerLabel.getForeground(), Color.BLACK);

		// Assert that "Human Opponent" is selected
		assertEquals(humanPlayerLabel.getBackground(), Color.YELLOW);
		assertEquals(humanPlayerLabel.getForeground(), Color.BLACK);

		// Assert that "Computer Opponent" is deselected
		assertEquals(computerPlayerLabel.getBackground(), greenBackground);
		assertEquals(computerPlayerLabel.getForeground(), Color.WHITE);

		// Assert that the playerTypeSelectionPanel is visible
		assertTrue(playerTypeSelectionPanel.isVisible());

		// Assert that the playerTwoNamePanel is visible
		assertTrue(playerTwoNamePanel.isVisible());

		// Assert that the playerTwoNameLabel and playerTwoName are enabled
		assertTrue(playerTwoNameLabel.isEnabled());
		assertTrue(playerTwoName.isEnabled());

		// Assert that the playerTwoName is empty
		assertTrue(playerTwoName.getText().isEmpty());

		/*
		 * Two Player Mode is highlighted, simulate a click on
		 * "Computer Opponent"
		 */
		farkleOptionsDialog.mouseClicked(computerPlayerLabelClicked);

		// Assert that singlePlayerLabel is deselected
		assertEquals(singlePlayerLabel.getBackground(), greenBackground);
		assertEquals(singlePlayerLabel.getForeground(), Color.WHITE);

		// Assert that multiPlayer is selected
		assertEquals(multiPlayerLabel.getBackground(), Color.YELLOW);
		assertEquals(multiPlayerLabel.getForeground(), Color.BLACK);

		// Assert that "Human Opponent" is deselected
		assertEquals(humanPlayerLabel.getBackground(), greenBackground);
		assertEquals(humanPlayerLabel.getForeground(), Color.WHITE);

		// Assert that "Computer Opponent" is selected
		assertEquals(computerPlayerLabel.getBackground(), Color.YELLOW);
		assertEquals(computerPlayerLabel.getForeground(), Color.BLACK);

		// Assert that the playerTypeSelectionPanel is visible
		assertTrue(playerTypeSelectionPanel.isVisible());

		// Assert that the playerTwoNamePanel is visible
		assertTrue(playerTwoNamePanel.isVisible());

		// Assert that the playerTwoNameLabel and playerTwoName are disabled
		assertTrue(!playerTwoNameLabel.isEnabled());
		assertTrue(!playerTwoName.isEnabled());

		// Assert that the playerTwoName is set to "Computer"
		assertEquals(playerTwoName.getText(), "Computer");

		/*
		 * Two Player Mode is highlighted, simulate a click on "One Player Mode"
		 */
		farkleOptionsDialog.mouseClicked(singlePlayerLabelClicked);

		// Assert that singlePlayerLabel is selected
		assertEquals(singlePlayerLabel.getBackground(), Color.YELLOW);
		assertEquals(singlePlayerLabel.getForeground(), Color.BLACK);

		// Assert that multiPlayer is deselected
		assertEquals(multiPlayerLabel.getBackground(), greenBackground);
		assertEquals(multiPlayerLabel.getForeground(), Color.WHITE);

		// Assert that "Human Opponent" is deselected
		assertEquals(humanPlayerLabel.getBackground(), greenBackground);

		// Assert that "Computer Opponent" is deselected
		assertEquals(computerPlayerLabel.getBackground(), greenBackground);

		// Assert that the playerTypeSelectionPanel is not visible
		assertTrue(!playerTypeSelectionPanel.isVisible());

		// Assert that the playerTwoNamePanel is not visible
		assertTrue(!playerTwoNamePanel.isVisible());

		// Assert that the playerTwoName is empty
		assertTrue(playerTwoName.getText().isEmpty());

		/*
		 * One player mode is highlighted. The user clicks on multiPlayerMode,
		 * computer, then human opponent
		 */
		farkleOptionsDialog.mouseClicked(multiPlayerLabelClicked);
		farkleOptionsDialog.mouseClicked(computerPlayerLabelClicked);
		farkleOptionsDialog.mouseClicked(humanPlayerLabelClicked);

		// Assert that singlePlayerLabel is deselected
		assertEquals(singlePlayerLabel.getBackground(), greenBackground);
		assertEquals(singlePlayerLabel.getForeground(), Color.WHITE);

		// Assert that multiPlayer is selected
		assertEquals(multiPlayerLabel.getBackground(), Color.YELLOW);
		assertEquals(multiPlayerLabel.getForeground(), Color.BLACK);

		// Assert that "Human Opponent" is selected
		assertEquals(humanPlayerLabel.getBackground(), Color.YELLOW);
		assertEquals(humanPlayerLabel.getForeground(), Color.BLACK);

		// Assert that "Computer Opponent" is deselected
		assertEquals(computerPlayerLabel.getBackground(), greenBackground);
		assertEquals(computerPlayerLabel.getForeground(), Color.WHITE);

		// Assert that the playerTypeSelectionPanel is visible
		assertTrue(playerTypeSelectionPanel.isVisible());

		// Assert that the playerTwoNamePanel is visible
		assertTrue(playerTwoNamePanel.isVisible());

		// Assert that the playerTwoNameLabel and playerTwoName are enabled
		assertTrue(playerTwoNameLabel.isEnabled());
		assertTrue(playerTwoName.isEnabled());

		// Assert that the playerTwoName is empty
		assertTrue(playerTwoName.getText().isEmpty());

	}

}
