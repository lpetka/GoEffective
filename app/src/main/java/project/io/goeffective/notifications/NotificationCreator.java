package project.io.goeffective.notifications;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

public class NotificationCreator {
    private final Context context;

    public NotificationCreator(Context context) {
        this.context = context;
    }

    public Notification createNotification(String title, String content, int icon) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(icon);
        return builder.build();
    }
}
