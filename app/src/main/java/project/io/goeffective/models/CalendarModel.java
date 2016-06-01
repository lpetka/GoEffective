package project.io.goeffective.models;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import project.io.goeffective.App;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;

public class CalendarModel implements ICalendarModel {
    @Inject
    DatabaseHandler databaseHandler;

    private final Collection<Task> calendarTasks;

    public CalendarModel() {
        App.getComponent().inject(this);
        this.calendarTasks = databaseHandler.getTasksList();
    }

    public CalendarModel(Task calendarTask) {
        App.getComponent().inject(this);
        this.calendarTasks = new HashSet<>();
        this.calendarTasks.add(calendarTask);
    }

    public TaskStatus getTaskStatus(Calendar calendar) {
        final Date date = calendar.getTime();
        final List<Pair<Task, Boolean>> tasksStatusAtDate = databaseHandler.getTasksStatusAtDate(date);

        if (tasksStatusAtDate.isEmpty()) {
            return TaskStatus.DOES_NOT_MATTER;
        }

        boolean isEveryTaskDone = true;
        boolean isEveryTaskNotDone = true;
        for (Pair<Task, Boolean> taskIsDonePair : tasksStatusAtDate) {
            Task task = taskIsDonePair.first;
            if (calendarTasks.contains(task)) {
                Boolean isTaskDone = taskIsDonePair.second;
                if (isTaskDone) {
                    isEveryTaskNotDone = false;
                } else {
                    isEveryTaskDone = false;
                }
            }
        }

        if (isEveryTaskDone) {
            return TaskStatus.DONE;
        }

        if (isEveryTaskNotDone) {
            return TaskStatus.NOT_DONE;
        }

        return TaskStatus.PARTLY_DONE;
    }

    @Override
    public View.OnClickListener getOnClickListener(Context context, Calendar date) {
        return view -> {
            StringBuffer dataString = new StringBuffer();
            dataString.append(date.get(Calendar.DAY_OF_MONTH));
            dataString.append(".");
            dataString.append(date.get(Calendar.MONTH));
            dataString.append(".");
            dataString.append(date.get(Calendar.YEAR));
            Toast.makeText(context, dataString, Toast.LENGTH_SHORT).show();
        };
    }
}
