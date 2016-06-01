package project.io.goeffective.views;

import android.widget.EditText;

import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.widgets.WeekDayView;
import rx.Observable;

public interface ITaskEditView {
    Observable removeTaskButtonClick();
    Observable saveTaskButtonClick();
    void close();
    Task getTask();
    WeekDayView getWeekDayListSelector();
    EditText getTaskName();
    EditText getNoteInput();
}
