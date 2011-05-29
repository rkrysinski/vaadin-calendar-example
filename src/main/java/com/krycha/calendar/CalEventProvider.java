package com.krycha.calendar;

import java.util.List;

import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.event.BasicEventProvider;
import com.vaadin.addon.calendar.event.CalendarEvent;

/**
 * Event provider.
 */
public class CalEventProvider extends BasicEventProvider {

	private static final long serialVersionUID = 9062422648092430557L;
	
	@Override
	public void addEvent(BasicEvent event) {
		super.addEvent(event);
	}

	@Override
	public void removeEvent(BasicEvent event) {
		super.removeEvent(event);
	}

	public List<CalendarEvent> getDataSource() {
		return this.eventList;
	}

}
