package com.krycha.calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.vaadin.addon.calendar.event.BasicEvent;
import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.event.CalendarEventProvider;

/**
 * Event provider.
 */
public class CalEventProvider implements CalendarEventProvider {

	private static final long serialVersionUID = 9062422648092430557L;

	/**
	 * ${@inheritDoc}.
	 */
	@Override
	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		Date start = cal.getTime();
		cal.add(GregorianCalendar.HOUR, 5);
		Date end = cal.getTime();
		BasicEvent event = new BasicEvent();
		event.setCaption("My Event");
		event.setDescription("My Event Description");
		event.setStart(start);
		event.setEnd(end);
		events.add(event);
		return events;
	}

}
