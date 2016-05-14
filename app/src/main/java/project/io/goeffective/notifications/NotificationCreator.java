package project.io.goeffective.notifications;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import project.io.goeffective.R;

public class NotificationCreator {
    private final Context context;

    public NotificationCreator(Context context) {
        this.context = context;
    }

    public Notification createNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.day_task_not_done);
        return builder.build();
    }
}
