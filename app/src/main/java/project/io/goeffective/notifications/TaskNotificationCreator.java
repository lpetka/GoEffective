package project.io.goeffective.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.List;

import project.io.goeffective.R;
import project.io.goeffective.activities.MainActivity;
import project.io.goeffective.utils.dbobjects.Task;

public class TaskNotificationCreator {
    public static final int NOTIFICATION_ID = 1;

    private final Context context;
    private final String taskNotificationTitle;

    public TaskNotificationCreator(Context context) {
        this.context = context;
        taskNotificationTitle = context.getResources().getString(R.string.taks_notification_title);
    }

    public Notification createNotification(List<Task> tasks) {
        StringBuilder contentText = new StringBuilder("");
        for (Task task : tasks) {
            contentText.append(task.getName());
            contentText.append(", ");
        }
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(taskNotificationTitle);
        builder.setContentText(contentText.toString());
        final PendingIntent pendingMainActivityIntent = getPendingMainActivityIntent();
        builder.setContentIntent(pendingMainActivityIntent);
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
        builder.setOngoing(true);
        return builder.build();
    }

    private PendingIntent getPendingMainActivityIntent() {
        final Intent intentToOpen = new Intent(context, MainActivity.class);
        final TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intentToOpen);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
