package com.krycha.calendar.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class CalendarId extends DbPojo implements Serializable {
	@Transient
	private static final long serialVersionUID = -4613303344120359911L;

	private String description;

	@OneToMany(mappedBy = "calendarId")
	private final List<CalendarEvent> events = new ArrayList<CalendarEvent>();

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

	/**
	 * @return the events
	 */
	public List<CalendarEvent> getEvents() {
		return events;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendarId: Id:" + this.getId() + " description:" + this.getDescription();
	}

}
