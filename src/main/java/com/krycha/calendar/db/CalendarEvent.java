package com.krycha.calendar.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.vaadin.addon.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.addon.calendar.event.CalendarEventEditor;

@Entity
public class CalendarEvent extends DbPojo implements CalendarEventEditor, EventChangeNotifier {
	@Transient
	private static final long serialVersionUID = 2207135337270065699L;

	private String caption;
	private String description;
	@Temporal(TemporalType.DATE)
	private Date end;
	@Temporal(TemporalType.DATE)
	private Date start;
	private String styleName;
	private boolean isAllDay;

	private CalendarId calendarId;

	@Transient
	private List<EventChangeListener> listeners = new ArrayList<EventChangeListener>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.addon.calendar.event.CalendarEvent#getCaption()
	 */
	public String getCaption() {
		return caption;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.addon.calendar.event.CalendarEvent#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.addon.calendar.event.CalendarEvent#getEnd()
	 */
	public Date getEnd() {
		return end;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.addon.calendar.event.CalendarEvent#getStart()
	 */
	public Date getStart() {
		return start;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.addon.calendar.event.CalendarEvent#getStyleName()
	 */
	public String getStyleName() {
		return styleName;
	}

	public boolean isAllDay() {
		return isAllDay;
	}

	// setters for properties

	public void setCaption(String caption) {
		this.caption = caption;
		fireEventChange();
	}

	public void setDescription(String description) {
		this.description = description;
		fireEventChange();
	}

	public void setEnd(Date end) {
		this.end = end;
		fireEventChange();
	}

	public void setStart(Date start) {
		this.start = start;
		fireEventChange();
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
		fireEventChange();
	}

	public void setAllDay(boolean isAllDay) {
		this.isAllDay = isAllDay;
		fireEventChange();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventChangeNotifier
	 * #addListener
	 * (com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventChangeListener
	 * )
	 */
	public void addListener(EventChangeListener listener) {
		listeners.add(listener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventChangeNotifier
	 * #removeListener
	 * (com.vaadin.addon.calendar.ui.CalendarComponentEvents.EventChangeListener
	 * )
	 */
	public void removeListener(EventChangeListener listener) {
		listeners.remove(listener);
	}

	protected void fireEventChange() {
		EventChange event = new EventChange(this);

		for (EventChangeListener listener : listeners) {
			listener.eventChange(event);
		}
	}

	/**
	 * @param calendarId
	 *            the calendarId to set
	 */
	public void setCalendarId(CalendarId calendarId) {
		this.calendarId = calendarId;
	}

	/**
	 * @return the calendarId
	 */
	public CalendarId getCalendarId() {
		return calendarId;
	}
}
