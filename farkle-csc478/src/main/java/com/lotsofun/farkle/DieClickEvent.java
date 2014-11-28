package com.lotsofun.farkle;

import java.awt.Component;
import java.awt.event.MouseEvent;

public class DieClickEvent extends MouseEvent {

	/**
	 * Custom click event for the automated player
	 */
	private static final long serialVersionUID = 1L;
	
	public DieClickEvent(Component source, int id, long when, int modifiers,
			int x, int y, int clickCount, boolean popupTrigger) {
		super(source, id, when, modifiers, x, y, clickCount, popupTrigger);
	}
}
