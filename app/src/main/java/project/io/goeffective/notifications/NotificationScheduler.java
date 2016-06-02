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

    public void cancelNotification() {
        final Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public void scheduleNotification(Notification notification, long delay) {
        final PendingIntent pendingIntent = createNotificationIntent(notification);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public void scheduleNotification(Notification notification, Date futureDate) {
        final long futureTimeInMillis = getFutureTimeInMillis(futureDate);
        final PendingIntent pendingIntent = createNotificationIntent(notification);
        alarmManager.set(AlarmManager.RTC_WAKEUP, futureTimeInMillis, pendingIntent);
    }

    private long getFutureTimeInMillis(Date futureDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(futureDate);
        return calendar.getTimeInMillis();
    }

    private PendingIntent createNotificationIntent(Notification notification) {
        final Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_KEY, notification);
        return PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
