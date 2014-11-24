package com.lotsofun.farkle;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FarkleOptionsDialogTest {
	// The FarkleOptionsDialog object to test
	FarkleOptionsDialog farkleOptionsDialog;
	@Before
	public void setUp() throws Exception {
		farkleOptionsDialog = new FarkleOptionsDialog(null);
	}

	@Test
	public void testConstructor() {
		assertNotNull(farkleOptionsDialog);
	}

}
