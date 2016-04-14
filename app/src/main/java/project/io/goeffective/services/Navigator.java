package project.io.goeffective.services;

import android.content.Context;
import android.content.Intent;

import project.io.goeffective.activities.CalendarActivity;

public class Navigator implements INavigator {
    private final Context context;

    public Navigator(Context context) {
        this.context = context;
    }

    @Override
    public void openCalendarActivity() {
        context.startActivity(new Intent(context, CalendarActivity.class));
    }
}
