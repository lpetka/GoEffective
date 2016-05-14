package project.io.goeffective.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import project.io.goeffective.R;
import project.io.goeffective.activities.MainActivity;

public class TaskNotificationCreator {
    private static final int SET_DONE_REQUEST_CODE = 12345;

    private final Context context;
    private final String taskNotificationTitle;
    private final String markTaskAsDoneActionName;

    public TaskNotificationCreator(Context context) {
        this.context = context;
        taskNotificationTitle = context.getResources().getString(R.string.taks_notification_title);
        markTaskAsDoneActionName = context.getResources().getString(R.string.mark_task_as_done_action);
    }

    public Notification createNotification(Task task) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(taskNotificationTitle);
        builder.setContentText(task.getName());
        final PendingIntent pendingMainActivityIntent = getPendingMainActivityIntent();
        builder.setContentIntent(pendingMainActivityIntent);
        final PendingIntent pendingSetDoneIntent = getPendingSetDoneIntent(task);
        builder.addAction(R.drawable.day_task_not_done, markTaskAsDoneActionName, pendingSetDoneIntent);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        return builder.build();
    }

    private PendingIntent getPendingMainActivityIntent() {
        final Intent intentToOpen = new Intent(context, MainActivity.class);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intentToOpen);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getPendingSetDoneIntent(Task task) {
        final Intent setDoneIntent = new Intent();
        setDoneIntent.setAction("setDone");
        final Bundle setDoneBundle = new Bundle();
        setDoneBundle.putInt("taskId", task.getId());
        setDoneIntent.putExtras(setDoneBundle);
        return PendingIntent.getBroadcast(context, SET_DONE_REQUEST_CODE, setDoneIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
