package com.krycha.calendar;

import java.util.Date;

public interface CalEventEditor extends CalEvent {
	void setCaption(String caption);

	void setDescription(String description);

	void setEnd(Date end);

	void setStart(Date start);

	void setStyleName(String styleName);

	void setAllDay(boolean isAllDay);
}
