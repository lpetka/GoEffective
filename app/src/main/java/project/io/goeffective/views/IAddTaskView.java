package project.io.goeffective.views;

import android.widget.EditText;

import project.io.goeffective.widgets.WeekDayView;
import rx.Observable;

public interface IAddTaskView {
    Observable createTaskButtonClick();
    EditText getNoteInput();
    EditText getTaskName();
    WeekDayView getWeekDayListSelector();
    void close();
}
