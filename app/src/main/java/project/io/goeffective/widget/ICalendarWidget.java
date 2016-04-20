package project.io.goeffective.widget;

import project.io.goeffective.models.ICalendarModel;
import project.io.goeffective.widget.events.OnMonthChangeListener;

public interface ICalendarWidget {
    void setModel(ICalendarModel model);
    void setOnActionListener(OnMonthChangeListener actionListener);
}
