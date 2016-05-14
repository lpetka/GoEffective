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

    public void cancelNotification(Notification notification) {
        final PendingIntent pendingIntent = createNotificationIntent(notification);
        alarmManager.cancel(pendingIntent);
    }

    public void scheduleNotification(Notification notification, long delay) {
        final PendingIntent pendingIntent = createNotificationIntent(notification);
        setAlarmManager(delay, pendingIntent);
    }

    public void scheduleNotification(Notification notification, Date futureDate) {
        long delay = dateToDelay(futureDate);
        scheduleNotification(notification, delay);
    }

    private long dateToDelay(Date futureDate) {
        final Calendar calendar = Calendar.getInstance();
        final long currentMillis = calendar.getTimeInMillis();
        calendar.setTime(futureDate);
        final long futureMillis = calendar.getTimeInMillis();
        return futureMillis - currentMillis;
    }

    private void setAlarmManager(long delay, PendingIntent pendingIntent) {
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private PendingIntent createNotificationIntent(Notification notification) {
        final Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        return PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
