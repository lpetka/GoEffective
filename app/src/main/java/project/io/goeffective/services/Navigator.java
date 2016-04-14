package project.io.goeffective.services;

import android.content.Context;
import android.content.Intent;

import project.io.goeffective.activities.CalendarActivity;
import project.io.goeffective.activities.PreferencesActivity;

public class Navigator implements INavigator {
    private final Context context;

    public Navigator(Context context) {
        this.context = context;
    }

    @Override
    public void openCalendarActivity() {
        context.startActivity(new Intent(context, CalendarActivity.class));
    }

    @Override
    public void openPreferencesActivity() {
        context.startActivity(new Intent(context, PreferencesActivity.class));
    }
}
