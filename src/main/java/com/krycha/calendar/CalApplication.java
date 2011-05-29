package com.krycha.calendar;

import com.vaadin.Application;
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
    	window = new Window("Lab usage application");
        setMainWindow(window);
        MainComponent main = new MainComponent();
        main.setMainWindow(getMainWindow());
        window.addComponent(main);
        window.getContent().setSizeFull();
        setTheme("LabCalendar");
        
    }
    
}
