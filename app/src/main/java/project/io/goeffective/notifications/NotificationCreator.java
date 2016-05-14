package project.io.goeffective.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.os.Build;

import project.io.goeffective.R;

public class NotificationCreator {
    private final Context context;

    public NotificationCreator(Context context) {
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public Notification createNotification(String title, String content) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.day_task_not_done);
        return builder.build();
    }
}
