package com.krycha.calendar;

import com.vaadin.addon.calendar.ui.Calendar;

public class CalComponent extends Calendar {

	private static final long serialVersionUID = 2310975067647618615L;

	public CalComponent() {
		super(new CalEventProvider());
	}
}
