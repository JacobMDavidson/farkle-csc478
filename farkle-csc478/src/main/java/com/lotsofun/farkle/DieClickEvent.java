package com.lotsofun.farkle;

import java.awt.Component;
import java.awt.event.MouseEvent;

/**
 * Custom click event for the automated player
 * 
 * @author Curtis Brown
 * @version 3.0.0
 */
public class DieClickEvent extends MouseEvent {

	/** Serialize this to comply with component conventions */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor: builds a simulated mouse click
	 * 
	 * @param source
	 *            The clicked component
	 * @param id
	 *            Integer indicating the type of event
	 * @param when
	 *            Long that gives the time the event occurred
	 * @param modifiers
	 *            Keys down during the event
	 * @param x
	 *            Horizontal x mouse position
	 * @param y
	 *            Vertical y mouse position
	 * @param clickCount
	 *            The number of mouse clicks associated with the event
	 * @param popupTrigger
	 *            True if this event is a trigger for a popup menu
	 */
	public DieClickEvent(Component source, int id, long when, int modifiers,
			int x, int y, int clickCount, boolean popupTrigger) {
		super(source, id, when, modifiers, x, y, clickCount, popupTrigger);
	}
}
