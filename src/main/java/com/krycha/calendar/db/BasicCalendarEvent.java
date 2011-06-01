package com.krycha.calendar.db;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.vaadin.addon.calendar.event.CalendarEvent.EventChangeNotifier;
import com.vaadin.addon.calendar.event.CalendarEventEditor;

@Entity
public class BasicCalendarEvent extends DbPojo implements CalendarEventEditor, EventChangeNotifier {

	private String caption;
	private String description;
	@Column(name = "END_DATE")
	@Temporal(TemporalType.TIME)
	private Calendar end = new GregorianCalendar();
	@Column(name = "START_DATE")
	@Temporal(TemporalType.TIME)
	private Calendar start = new GregorianCalendar();
	private String styleName;
	private boolean isAllDay;
	//@ManyToOne
	private CalEventProvider calendarId;

	@Transient
	private static final long serialVersionUID = 2207135337270065699L;
	@Transient
	private List<EventChangeListener> listeners = new ArrayList<EventChangeListener>();

	public BasicCalendarEvent() {

	}

	public String getCaption() {
		return caption;
	}

	public String getDescription() {
		return description;
	}

	public Date getEnd() {
		return end.getTime();
	}

	public Date getStart() {
		return start.getTime();
	}

	public String getStyleName() {
		return styleName;
	}

	public boolean isAllDay() {
		return isAllDay;
	}

	public void setCaption(String caption) {
		this.caption = caption;
		fireEventChange();
	}

	public void setDescription(String description) {
		this.description = description;
		fireEventChange();
	}

	public void setEnd(Date end) {
		this.end.setTime(end);
		fireEventChange();
	}

	public void setStart(Date start) {
		this.start.setTime(start);
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

	public void addListener(EventChangeListener listener) {
		listeners.add(listener);

	}

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
	public void setCalendarId(CalEventProvider calendarId) {
		this.calendarId = calendarId;
	}

	/**
	 * @return the calendarId
	 */
	public CalEventProvider getCalendarId() {
		return calendarId;
	}
}
