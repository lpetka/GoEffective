package project.io.goeffective.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPublisher extends BroadcastReceiver {

    public static final String NOTIFICATION_ID_KEY = "notification-id";
    public static final String NOTIFICATION_KEY = "notification";

    public void onReceive(Context context, Intent intent) {
        final Object systemService = context.getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationManager notificationManager = (NotificationManager) systemService;
        final Notification notification = intent.getParcelableExtra(NOTIFICATION_KEY);
        final int id = intent.getIntExtra(NOTIFICATION_ID_KEY, 0);
        notificationManager.notify(id, notification);
    }
}