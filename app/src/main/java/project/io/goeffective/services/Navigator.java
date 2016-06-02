package project.io.goeffective.services;

import android.content.Context;
import android.content.Intent;

import java.util.Date;

import project.io.goeffective.activities.DayActivity;
import project.io.goeffective.activities.PreferencesActivity;
import project.io.goeffective.activities.TaskAddActivity;
import project.io.goeffective.activities.TaskEditActivity;
import project.io.goeffective.utils.dbobjects.Task;

public class Navigator implements INavigator {
    private final Context context;

    public Navigator(Context context) {
        this.context = context;
    }

    @Override
    public void openPreferencesActivity() {
        context.startActivity(new Intent(context, PreferencesActivity.class));
    }

    @Override
    public void openTaskAddActivity() {
        context.startActivity(new Intent(context, TaskAddActivity.class));
    }

    @Override
    public void openTaskEditActivity(Task task) {
        Intent intent = new Intent(context, TaskEditActivity.class);
        intent.putExtra("task", task);
        context.startActivity(intent);
    }

    @Override
    public void openDayActivity(Date date) {
        Intent intent = new Intent(context, DayActivity.class);
        intent.putExtra("date", date);
        context.startActivity(intent);
    }

}
