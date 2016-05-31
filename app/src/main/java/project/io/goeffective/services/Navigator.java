package project.io.goeffective.services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import project.io.goeffective.activities.PreferencesActivity;
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
        Toast.makeText(context,"openAddTaskActivity",Toast.LENGTH_SHORT).show();
        //context.startActivity(new Intent(context, AddTask));
    }

    @Override
    public void openTaskEditActivity(Task task) {
        Intent intent = new Intent(context, TaskEditActivity.class);
        intent.putExtra("task", task);
        context.startActivity(intent);
    }
}
