package project.io.goeffective.services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import project.io.goeffective.activities.PreferencesActivity;

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
    public void openAddTaskActivity() {
        Toast.makeText(context,"openAddTaskActivity",Toast.LENGTH_SHORT).show();
        //context.startActivity(new Intent(context, AddTask));
    }
}
