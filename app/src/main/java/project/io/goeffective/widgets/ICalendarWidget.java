package project.io.goeffective.widgets;

import project.io.goeffective.models.ICalendarModel;
import project.io.goeffective.widgets.events.OnMonthChangeListener;

public interface ICalendarWidget {
    void setModel(ICalendarModel model);
    void setOnActionListener(OnMonthChangeListener actionListener);
}
