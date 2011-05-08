package com.krycha.calendar;

import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MainComponent extends CustomComponent {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private VerticalLayout contentVerticalLayout;
	@AutoGenerated
	private CalComponent calendarComponent;
	@AutoGenerated
	private HorizontalLayout navigationBench;
	@AutoGenerated
	private Button nextButton;
	@AutoGenerated
	private Button addEventButton;
	@AutoGenerated
	private Button monthViewButton;
	@AutoGenerated
	private Label monthLabel;
	@AutoGenerated
	private Button prevButton;
	@AutoGenerated
	private GridLayout gridLayout_1;
	@AutoGenerated
	private HorizontalLayout labButtons;
	@AutoGenerated
	private Button removeLabButton;
	@AutoGenerated
	private Button addLabButton;
	@AutoGenerated
	private ComboBox labBox;
	@AutoGenerated
	private Label labLabel;
	@AutoGenerated
	private Button saveButton;
	@AutoGenerated
	private ComboBox localeBox;
	@AutoGenerated
	private Label localeLabel;
	@AutoGenerated
	private ComboBox timezoneBox;
	@AutoGenerated
	private Label timezoneLabel;
	@AutoGenerated
	private ComboBox locationBox;
	@AutoGenerated
	private Label locationLabel;
	private static final long serialVersionUID = -122920050051329357L;

	private enum Mode {
		MONTH, WEEK, DAY;
	}

	private enum Location {
		COLUMBUS("Columbus", "US/Eastern", new Locale("en", "US")), //
		BYDGOSZCZ("Bydgoszcz", "Europe/Warsaw", new Locale("pl", "PL"));

		private String loc;
		private String timezone;
		private Locale locale;

		Location(String loc, String timezone, Locale locale) {
			this.loc = loc;
			this.timezone = timezone;
			this.locale = locale;
		}

		public String getLocation() {
			return loc;
		}

		public String getTimezone() {
			return timezone;
		}

		public Locale getLocale() {
			return locale;
		}
	}

	/**
	 * This Gregorian calendar is used to control dates and time inside of this
	 * test application.
	 */
	private GregorianCalendar calendar;
	private Mode viewMode = Mode.WEEK;
	private static Location DEFAULT_LOC = Location.COLUMBUS;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public MainComponent() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		setLocale(DEFAULT_LOC.getLocale());
		initializeCalendar();
		initializeComboBoxs();
	}

	private void initializeCalendar() {
		calendarComponent.setImmediate(true);
		calendarComponent.setLocale(getLocale());

		Date today = new Date();
		calendar = new GregorianCalendar(getLocale());
		calendar.setTime(today);

		updateCaptionLabel();

		addCalendarEventListeners();

	}

	private void addCalendarEventListeners() {
		// TODO Auto-generated method stub
	}

	private void updateCaptionLabel() {
		DateFormatSymbols s = new DateFormatSymbols(getLocale());
		String month = s.getShortMonths()[calendar.get(GregorianCalendar.MONTH)];
		monthLabel.setValue(month + " " + calendar.get(GregorianCalendar.YEAR));
	}

	private void initializeComboBoxs() {
		initializeLocaleBox();
		initializeTimeZoneBox();
		initializeLocationBox();
	}

	private void initializeLocationBox() {
		locationBox.addContainerProperty("caption", String.class, "");
		locationBox.setItemCaptionPropertyId("caption");
		locationBox.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		for (Location loc : Location.values()) {
			if (!locationBox.containsId(loc)) {
				Item i = locationBox.addItem(loc);
				i.getItemProperty("caption").setValue(loc.getLocation());
			}
		}
		locationBox.select(DEFAULT_LOC);
		locationBox.setImmediate(true);
		locationBox.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				updateLocation(event.getProperty().getValue());
			}
		});

	}

	protected void updateLocation(Object value) {
		Location loc = (Location) value;
		System.out.println("Location: " + loc.getLocation());
		System.out.println("Timezone: " + loc.getTimezone());
		timezoneBox.select(loc.getTimezone());
		localeBox.select(loc.getLocale());
		updateCalendarTimeZone(loc.getTimezone());
		updateCalendarLocale(loc.getLocale());
	}

	private void initializeTimeZoneBox() {
		timezoneBox.addContainerProperty("caption", String.class, "");
		timezoneBox.setItemCaptionPropertyId("caption");
		timezoneBox.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);

		for (String id : TimeZone.getAvailableIDs()) {
			if (!timezoneBox.containsId(id)) {
				Item i = timezoneBox.addItem(id);
				i.getItemProperty("caption").setValue(id);
			}
		}
		timezoneBox.select(DEFAULT_LOC.getTimezone());
		timezoneBox.setImmediate(true);
		timezoneBox.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				updateCalendarTimeZone(event.getProperty().getValue());
			}
		});
	}

	protected void updateCalendarTimeZone(Object timezoneId) {
		TimeZone tz = TimeZone.getTimeZone((String) timezoneId);

		// remember the week that was showing, so we can re-set it later
		Date startDate = calendarComponent.getStartDate();
		calendar.setTime(startDate);
		int weekNumber = calendar.get(java.util.Calendar.WEEK_OF_YEAR);
		calendarComponent.setTimeZone(tz);
		calendar.setTimeZone(calendarComponent.getTimeZone());

		if (viewMode == Mode.WEEK) {
			calendar.set(java.util.Calendar.WEEK_OF_YEAR, weekNumber);
			calendar.set(java.util.Calendar.DAY_OF_WEEK,
					calendar.getFirstDayOfWeek());

			calendarComponent.setStartDate(calendar.getTime());
			calendar.add(java.util.Calendar.DATE, 6);
			calendarComponent.setEndDate(calendar.getTime());
		}
	}

	private void initializeLocaleBox() {
		localeBox.addContainerProperty("caption", String.class, "");
		localeBox.setItemCaptionPropertyId("caption");
		localeBox.setFilteringMode(ComboBox.FILTERINGMODE_CONTAINS);
		for (Locale l : Locale.getAvailableLocales()) {
			if (!localeBox.containsId(l)) {
				Item i = localeBox.addItem(l);
				i.getItemProperty("caption").setValue(getLocaleItemCaption(l));
			}
		}
		localeBox.select(getLocale());
		localeBox.setImmediate(true);
		localeBox.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				updateCalendarLocale((Locale) event.getProperty().getValue());
			}
		});
	}

	protected void updateCalendarLocale(Locale l) {
		int oldFirstDayOfWeek = calendar.getFirstDayOfWeek();
		setLocale(l);
		calendarComponent.setLocale(l);
		calendar = new GregorianCalendar(l);
		int newFirstDayOfWeek = calendar.getFirstDayOfWeek();

		// we are showing 1 week, and the first day of the week has changed
		// update start and end dates so that the same week is showing
		if (viewMode == Mode.WEEK && oldFirstDayOfWeek != newFirstDayOfWeek) {
			calendar.setTime(calendarComponent.getStartDate());
			calendar.add(java.util.Calendar.DAY_OF_WEEK, 2);
			// starting at the beginning of the week
			calendar.set(GregorianCalendar.DAY_OF_WEEK, newFirstDayOfWeek);
			Date start = calendar.getTime();

			// ending at the end of the week
			calendar.add(GregorianCalendar.DATE, 6);
			Date end = calendar.getTime();

			calendarComponent.setStartDate(start);
			calendarComponent.setEndDate(end);
		}
		updateCaptionLabel();
	}

	private String getLocaleItemCaption(Locale l) {
		String country = l.getDisplayCountry(getLocale());
		String language = l.getDisplayLanguage(getLocale());
		StringBuilder caption = new StringBuilder(country);
		if (caption.length() != 0) {
			caption.append(", ");
		}
		caption.append(language);
		return caption.toString();
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");

		// contentVerticalLayout
		contentVerticalLayout = buildContentVerticalLayout();
		mainLayout.addComponent(contentVerticalLayout,
				"top:0.0px;right:0.0px;bottom:0.0px;left:0.0px;");

		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildContentVerticalLayout() {
		// common part: create layout
		contentVerticalLayout = new VerticalLayout();
		contentVerticalLayout.setWidth("100.0%");
		contentVerticalLayout.setHeight("100.0%");
		contentVerticalLayout.setImmediate(false);
		contentVerticalLayout.setMargin(false);

		// gridLayout_1
		gridLayout_1 = buildGridLayout_1();
		contentVerticalLayout.addComponent(gridLayout_1);

		// navigationBench
		navigationBench = buildNavigationBench();
		contentVerticalLayout.addComponent(navigationBench);

		// calendarComponent
		calendarComponent = new CalComponent();
		calendarComponent.setWidth("100.0%");
		calendarComponent.setHeight("100.0%");
		calendarComponent.setImmediate(false);
		contentVerticalLayout.addComponent(calendarComponent);
		contentVerticalLayout.setExpandRatio(calendarComponent, 1.0f);

		return contentVerticalLayout;
	}

	@AutoGenerated
	private GridLayout buildGridLayout_1() {
		// common part: create layout
		gridLayout_1 = new GridLayout();
		gridLayout_1.setWidth("60.0%");
		gridLayout_1.setHeight("-1px");
		gridLayout_1.setImmediate(false);
		gridLayout_1.setMargin(false);
		gridLayout_1.setColumns(7);
		gridLayout_1.setRows(2);

		// locationLabel
		locationLabel = new Label();
		locationLabel.setWidth("-1px");
		locationLabel.setHeight("-1px");
		locationLabel.setValue("Location:");
		locationLabel.setImmediate(false);
		gridLayout_1.addComponent(locationLabel, 0, 0);
		gridLayout_1.setComponentAlignment(locationLabel, new Alignment(34));

		// locationBox
		locationBox = new ComboBox();
		locationBox.setWidth("-1px");
		locationBox.setHeight("-1px");
		locationBox.setImmediate(false);
		gridLayout_1.addComponent(locationBox, 1, 0);
		gridLayout_1.setComponentAlignment(locationBox, new Alignment(33));

		// timezoneLabel
		timezoneLabel = new Label();
		timezoneLabel.setWidth("-1px");
		timezoneLabel.setHeight("-1px");
		timezoneLabel.setValue("  Timezone:");
		timezoneLabel.setImmediate(false);
		gridLayout_1.addComponent(timezoneLabel, 2, 0);
		gridLayout_1.setComponentAlignment(timezoneLabel, new Alignment(34));

		// timezoneBox
		timezoneBox = new ComboBox();
		timezoneBox.setWidth("-1px");
		timezoneBox.setHeight("-1px");
		timezoneBox.setImmediate(false);
		gridLayout_1.addComponent(timezoneBox, 3, 0);
		gridLayout_1.setComponentAlignment(timezoneBox, new Alignment(33));

		// localeLabel
		localeLabel = new Label();
		localeLabel.setWidth("-1px");
		localeLabel.setHeight("-1px");
		localeLabel.setValue("Locale:");
		localeLabel.setImmediate(false);
		gridLayout_1.addComponent(localeLabel, 4, 0);
		gridLayout_1.setComponentAlignment(localeLabel, new Alignment(34));

		// localeBox
		localeBox = new ComboBox();
		localeBox.setWidth("150px");
		localeBox.setHeight("-1px");
		localeBox.setImmediate(false);
		gridLayout_1.addComponent(localeBox, 5, 0);
		gridLayout_1.setComponentAlignment(localeBox, new Alignment(33));

		// saveButton
		saveButton = new Button();
		saveButton.setWidth("-1px");
		saveButton.setHeight("-1px");
		saveButton.setCaption("Save");
		saveButton.setImmediate(true);
		gridLayout_1.addComponent(saveButton, 6, 0);
		gridLayout_1.setComponentAlignment(saveButton, new Alignment(48));

		// labLabel
		labLabel = new Label();
		labLabel.setWidth("-1px");
		labLabel.setHeight("-1px");
		labLabel.setValue("Lab:");
		labLabel.setImmediate(false);
		gridLayout_1.addComponent(labLabel, 0, 1);
		gridLayout_1.setComponentAlignment(labLabel, new Alignment(34));

		// labBox
		labBox = new ComboBox();
		labBox.setWidth("-1px");
		labBox.setHeight("-1px");
		labBox.setImmediate(false);
		gridLayout_1.addComponent(labBox, 1, 1);
		gridLayout_1.setComponentAlignment(labBox, new Alignment(33));

		// labButtons
		labButtons = buildLabButtons();
		gridLayout_1.addComponent(labButtons, 2, 1);
		gridLayout_1.setComponentAlignment(labButtons, new Alignment(33));

		return gridLayout_1;
	}

	@AutoGenerated
	private HorizontalLayout buildLabButtons() {
		// common part: create layout
		labButtons = new HorizontalLayout();
		labButtons.setWidth("-1px");
		labButtons.setHeight("-1px");
		labButtons.setImmediate(false);
		labButtons.setMargin(false);

		// addLabButton
		addLabButton = new Button();
		addLabButton.setWidth("-1px");
		addLabButton.setHeight("-1px");
		addLabButton.setCaption("+");
		addLabButton.setDescription("Add lab to the list");
		addLabButton.setImmediate(true);
		labButtons.addComponent(addLabButton);
		labButtons.setComponentAlignment(addLabButton, new Alignment(33));

		// removeLabButton
		removeLabButton = new Button();
		removeLabButton.setWidth("-1px");
		removeLabButton.setHeight("-1px");
		removeLabButton.setCaption("-");
		removeLabButton.setDescription("Remove lab from the list");
		removeLabButton.setImmediate(true);
		labButtons.addComponent(removeLabButton);
		labButtons.setComponentAlignment(removeLabButton, new Alignment(33));

		return labButtons;
	}

	@AutoGenerated
	private HorizontalLayout buildNavigationBench() {
		// common part: create layout
		navigationBench = new HorizontalLayout();
		navigationBench.setWidth("100.0%");
		navigationBench.setHeight("-1px");
		navigationBench.setImmediate(false);
		navigationBench.setMargin(false);

		// prevButton
		prevButton = new Button();
		prevButton.setWidth("-1px");
		prevButton.setHeight("-1px");
		prevButton.setCaption("Prev");
		prevButton.setImmediate(true);
		navigationBench.addComponent(prevButton);
		navigationBench.setComponentAlignment(prevButton, new Alignment(33));

		// monthLabel
		monthLabel = new Label();
		monthLabel.setWidth("-1px");
		monthLabel.setHeight("-1px");
		monthLabel.setValue("Month");
		monthLabel.setImmediate(false);
		navigationBench.addComponent(monthLabel);
		navigationBench.setComponentAlignment(monthLabel, new Alignment(33));

		// monthViewButton
		monthViewButton = new Button();
		monthViewButton.setWidth("-1px");
		monthViewButton.setHeight("-1px");
		monthViewButton.setCaption("Month view");
		monthViewButton.setImmediate(true);
		navigationBench.addComponent(monthViewButton);
		navigationBench.setComponentAlignment(monthViewButton,
				new Alignment(33));

		// addEventButton
		addEventButton = new Button();
		addEventButton.setWidth("-1px");
		addEventButton.setHeight("-1px");
		addEventButton.setCaption("Add new event");
		addEventButton.setImmediate(true);
		navigationBench.addComponent(addEventButton);
		navigationBench
				.setComponentAlignment(addEventButton, new Alignment(33));

		// nextButton
		nextButton = new Button();
		nextButton.setWidth("-1px");
		nextButton.setHeight("-1px");
		nextButton.setCaption("Next");
		nextButton.setImmediate(true);
		navigationBench.addComponent(nextButton);
		navigationBench.setComponentAlignment(nextButton, new Alignment(34));

		return navigationBench;
	}

}