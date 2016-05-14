package project.io.goeffective.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SetDoneNotification extends BroadcastReceiver {

    public static final String TASK_ID = "task-id";
    public static final String TASK = "task";

    public void onReceive(Context context, Intent intent) {
        final Task task = (Task) intent.getSerializableExtra(TASK);
        final int id = intent.getIntExtra(TASK_ID, 0);
        String taskName = task.getName();
        Log.d(taskName, "" + id);

        task.setDone();
        final TaskNotificationCreator taskNotificationCreator = new TaskNotificationCreator(context);
        final Notification notification = taskNotificationCreator.createNotification(task);
        final Object systemService = context.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.notify(1, notification);
    }
}