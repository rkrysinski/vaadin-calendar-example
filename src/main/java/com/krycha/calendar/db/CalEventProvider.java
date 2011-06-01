package com.krycha.calendar.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.vaadin.addon.calendar.event.CalendarEvent;
import com.vaadin.addon.calendar.event.CalendarEvent.EventChange;
import com.vaadin.addon.calendar.event.CalendarEventProvider;
import com.vaadin.addon.calendar.event.CalendarEventProvider.EventSetChangeNotifier;

/**
 * Event provider.
 */
@Entity
public class CalEventProvider implements CalendarEventProvider, EventSetChangeNotifier,
		CalendarEvent.EventChangeListener {

	@Id
	private String description;
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy = "calendarId")
	protected List<BasicCalendarEvent> eventList = new ArrayList<BasicCalendarEvent>();
	
	@Transient
	private static final long serialVersionUID = 630145351104741918L;
	@Transient
	private List<EventSetChangeListener> listeners = new ArrayList<EventSetChangeListener>();

	public List<CalendarEvent> getEvents(Date startDate, Date endDate) {
		ArrayList<CalendarEvent> activeEvents = new ArrayList<CalendarEvent>();

		for (CalendarEvent ev : eventList) {
			long from = startDate.getTime();
			long to = endDate.getTime();

			long f = ev.getStart().getTime();
			long t = ev.getEnd().getTime();
			// Select only events that overlaps with startDate and
			// endDate.
			if ((f <= to && f >= from) || (t >= from && t <= to) || (f <= from && t >= to)) {
				activeEvents.add(ev);
			}
		}

		return activeEvents;
	}

	public void addEvent(BasicCalendarEvent event) {
		eventList.add(event);

		event.addListener(this);

		fireEventSetChange();
	}

	public void removeEvent(BasicCalendarEvent event) {
		eventList.remove(event);

		event.removeListener(this);

		fireEventSetChange();
	}

	public boolean containsEvent(BasicCalendarEvent event) {
		return eventList.contains(event);
	}

	public void addListener(EventSetChangeListener listener) {
		listeners.add(listener);

	}

	public void removeListener(EventSetChangeListener listener) {
		listeners.remove(listener);
	}

	protected void fireEventSetChange() {
		EventSetChange event = new EventSetChange(this);

		for (EventSetChangeListener listener : listeners) {
			listener.eventSetChange(event);
		}
	}

	public void eventChange(EventChange changeEvent) {
		// naive implementation
		fireEventSetChange();
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
