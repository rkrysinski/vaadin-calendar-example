package com.krycha.calendar;

import com.vaadin.addon.calendar.event.CalendarEvent;

interface CalEvent extends CalendarEvent {
	public Long getId();
}
