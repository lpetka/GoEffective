package project.io.goeffective.models;

import android.content.Context;
import android.view.View;

import java.util.Calendar;
import java.util.Collection;

import project.io.goeffective.utils.dbobjects.Task;

public interface ICalendarModel {
    Collection<Task> getCalendarTasks();
    TaskStatus getTaskStatus(Calendar calendar);
    View.OnClickListener getOnClickListener(Context context, Calendar date);
}
