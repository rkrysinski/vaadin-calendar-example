package com.krycha.calendar;

import com.vaadin.Application;
import com.vaadin.addon.calendar.ui.Calendar;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class CalApplication extends Application
{
    private Window window;

    @Override
    public void init()
    {
        window = new Window("My Vaadin Application");
        setMainWindow(window);
//        Button button = new Button("Click Me");
//        button.addListener(new Button.ClickListener() {
//            public void buttonClick(ClickEvent event) {
//                window.addComponent(new Label("Thank you for clicking"));
//            }
//        });
        Calendar cal = new Calendar(new CalEventProvider());
        window.addComponent(cal);
        
    }
    
}
