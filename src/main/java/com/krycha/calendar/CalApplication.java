package com.krycha.calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class 
 * 2) layout - correct spacing between buttons 
 * 3) error handling 
 * 5) axax push of newly added items to the calendar
 * calendar?lab=delta
 */
@SuppressWarnings("serial")
public class CalApplication extends Application implements HttpServletRequestListener {
	private Window window;
	private MainComponent main = null;

	@Override
	public void init() {
		window = new Window("Lab usage application");
		setMainWindow(window);
		main.setMainWindow(getMainWindow());
		window.addComponent(main);
		window.getContent().setSizeFull();
		setTheme("LabCalendar");
	}

	@Override
	public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
		if (main != null) {
			main.onRequestStart(request, response);
		} else {
			main = new MainComponent(request);
		}
	}

	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
	}

}
