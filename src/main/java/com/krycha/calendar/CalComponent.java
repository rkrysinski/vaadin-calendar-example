package com.krycha.calendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.krycha.calendar.db.BasicCalendarEvent;
import com.krycha.calendar.db.CalEventProvider;
import com.krycha.calendar.db.EMF;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.DateClickEvent;
import com.vaadin.addon.calendar.ui.CalendarComponentEvents.WeekClick;
import com.vaadin.addon.calendar.ui.handler.BasicDateClickHandler;
import com.vaadin.addon.calendar.ui.handler.BasicWeekClickHandler;

public class CalComponent extends Calendar {

	private static final long serialVersionUID = 2310975067647618615L;
	private static CalEventProvider eventProvider = new CalEventProvider();
	private Date currentMonthsFirstDate;
	private GregorianCalendar calendar;
	private WeekClick currentWeekEvent;
	private Date currentWeekDate;

	public CalComponent() {
		super(eventProvider);

	}

	public void addEvent(BasicCalendarEvent event) {
		if (!eventProvider.containsEvent(event)) {
			//add
			System.out.println("adding new event");
			event.setCalendarId(eventProvider);
			eventProvider.addEvent(event);
		}
		EMF.store(eventProvider);
		System.out.println("addEvent: " + ((BasicCalendarEvent) event).toString());
		swithToLab(eventProvider.getDescription());
	}

	public void removeEvent(BasicCalendarEvent event) {
		event.setCalendarId(eventProvider);
		eventProvider.removeEvent(event);
		EMF.store(eventProvider);
		System.out.println("removeEvent: " + ((BasicCalendarEvent) event).toString());
		swithToLab(eventProvider.getDescription());
	}

	public boolean containsEvent(BasicCalendarEvent event) {
		boolean retVal = eventProvider.containsEvent(event);
		System.out.println("containsEvent: " + retVal);
		return retVal;
	}

	public void init(Locale locale) {
		GregorianCalendar calendarTmp = new GregorianCalendar(locale);
		int rollAmount = calendarTmp.get(GregorianCalendar.DAY_OF_MONTH) - 1;
		calendarTmp.add(GregorianCalendar.DAY_OF_MONTH, -rollAmount);
		currentMonthsFirstDate = calendarTmp.getTime();

		initializeCalendar(locale);

	}

	private void initializeCalendar(Locale locale) {
		Date today = new Date();
		calendar = new GregorianCalendar(locale);
		calendar.setTime(today);

		currentWeekDate = today;
		currentWeekEvent = new WeekClick(this, calendar.get(GregorianCalendar.WEEK_OF_YEAR),
				calendar.get(GregorianCalendar.YEAR));
	}

	@Override
	public void setLocale(Locale newLocale) {
		initializeCalendar(newLocale);
		super.setLocale(newLocale);
	}

	public void switchToMonthView() {
		calendar.setTime(currentMonthsFirstDate);

		doSetStartDate();
		calendar.add(GregorianCalendar.MONTH, 1);
		calendar.add(GregorianCalendar.DATE, -1);
		doSetEndDate();

	}

	public void switchToCurrentWeekView() {
		BasicWeekClickHandler weekHandler = (BasicWeekClickHandler) this
				.getHandler(WeekClick.EVENT_ID);
		weekHandler.weekClick(currentWeekEvent);
		calendar.setTime(currentWeekDate);
	}

	public void rollMonth(int direction) {

		if (direction > 0) {
			calendar.add(GregorianCalendar.DATE, 1);
			doSetStartDate();
			calendar.add(GregorianCalendar.MONTH, direction);
			calendar.add(GregorianCalendar.DATE, -1);
			doSetEndDate();
		} else {
			calendar.add(GregorianCalendar.MONTH, direction * 2);
			calendar.add(GregorianCalendar.DATE, 1);
			doSetStartDate();
			calendar.add(GregorianCalendar.MONTH, 1);
			calendar.add(GregorianCalendar.DATE, -1);
			doSetEndDate();
		}

	}

	public void rollWeek(int direction) {
		calendar.add(GregorianCalendar.WEEK_OF_YEAR, direction);

		WeekClick weekEvent = new WeekClick(this, calendar.get(GregorianCalendar.WEEK_OF_YEAR),
				calendar.get(GregorianCalendar.YEAR));

		BasicWeekClickHandler weekHandler = (BasicWeekClickHandler) this
				.getHandler(WeekClick.EVENT_ID);
		weekHandler.weekClick(weekEvent);
	}

	public void rollDate(int direction) {
		calendar.add(GregorianCalendar.DATE, direction);

		DateClickEvent dayEvent = new DateClickEvent(this, calendar.getTime());
		BasicDateClickHandler dayHandler = (BasicDateClickHandler) this
				.getHandler(DateClickEvent.EVENT_ID);
		dayHandler.dateClick(dayEvent);

	}

	private void doSetStartDate() {
		skaleToMin();
		setStartDate(calendar.getTime());
		System.out.println("setStartDate: " + calendar.getTime());
	}

	private void doSetEndDate() {
		skaleToMax();
		setEndDate(calendar.getTime());
		System.out.println("doSetEndDate: " + calendar.getTime());
	}

	/*
	 * Utilities methods goes here.
	 */
	private void skaleToMax() {
		calendar.set(GregorianCalendar.HOUR_OF_DAY,
				calendar.getMaximum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getMaximum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getMaximum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND,
				calendar.getMaximum(GregorianCalendar.MILLISECOND));
	}

	private void skaleToMin() {
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendar.set(GregorianCalendar.MINUTE, 0);
		calendar.set(GregorianCalendar.SECOND, 1);
		calendar.set(GregorianCalendar.MILLISECOND, 0);
	}

	public GregorianCalendar getCal() {
		return calendar;
	}

	public void setTime(Date date) {
		calendar.setTime(date);
	}

	public void setWeek(int week, int year) {
		calendar.set(GregorianCalendar.WEEK_OF_YEAR, week);
		calendar.set(GregorianCalendar.YEAR, year);
	}

	public void swithToLab(String value) {
		eventProvider = EMF.find(CalEventProvider.class, value);
		System.out.println("eventProvider size: " + eventProvider.getEventList().size());
		for (BasicCalendarEvent event : eventProvider.getEventList()) {
			System.out.println("event: " + event.getId() + " -> " + event.getCaption());
		}
		setEventProvider(eventProvider);
		eventProvider.eventChange(null);
	}
}
