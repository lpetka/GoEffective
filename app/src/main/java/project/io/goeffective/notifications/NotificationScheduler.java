package project.io.goeffective.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;
import java.util.Date;

public class NotificationScheduler {
    private static AlarmManager alarmManager = null;

    private final Context context;

    public NotificationScheduler(Context context) {
        this.context = context;
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
    }

    public void cancelNotification(Notification notification, int notificationId) {
        final PendingIntent pendingIntent = createNotificationIntent(notification, notificationId);
        alarmManager.cancel(pendingIntent);
    }

    public void scheduleNotification(Notification notification, int notificationId, long delay) {
        final PendingIntent pendingIntent = createNotificationIntent(notification, notificationId);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public void scheduleNotification(Notification notification, int notificationId, Date futureDate) {
        final long futureTimeInMillis = getFutureTimeInMillis(futureDate);
        final PendingIntent pendingIntent = createNotificationIntent(notification, notificationId);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureTimeInMillis, pendingIntent);
    }

    private long getFutureTimeInMillis(Date futureDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(futureDate);
        return calendar.getTimeInMillis();
    }

    private PendingIntent createNotificationIntent(Notification notification, int notificationId) {
        final Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        return PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
