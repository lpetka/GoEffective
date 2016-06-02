package project.io.goeffective.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Pair;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import project.io.goeffective.App;
import project.io.goeffective.activities.PreferencesActivity;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;

public class NotificationsUpdater {
    @Inject
    DatabaseHandler databaseHandler;

    private final NotificationManager notificationManager;
    private final NotificationScheduler notificationScheduler;
    private final TaskNotificationCreator taskNotificationCreator;

    public NotificationsUpdater(Context context) {
        App.getComponent().inject(this);
        final Object systemService = context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager = (NotificationManager) systemService;
        taskNotificationCreator = new TaskNotificationCreator(context);
        notificationScheduler = new NotificationScheduler(context);
    }

    public void updateNotification() {
        if(PreferencesActivity.isNotificationOn) {
            final Date currentDate = Calendar.getInstance().getTime();
            final Pair<Date, List<Task>> nextDateTasksPair = databaseHandler.getNextNonemptyDayTasks(currentDate);
            if (!nextDateTasksPair.second.isEmpty()) {
                final Notification notification = taskNotificationCreator.createNotification(nextDateTasksPair.second);
                notificationScheduler.cancelNotification();
                if (nextDateTasksPair.first.getDate() == currentDate.getDate()) {
                    notificationManager.notify(TaskNotificationCreator.NOTIFICATION_ID, notification);
                } else {
                    notificationManager.cancel(TaskNotificationCreator.NOTIFICATION_ID);
                    notificationScheduler.scheduleNotification(notification, nextDateTasksPair.first);
                }
            }
        } else {
            notificationScheduler.cancelNotification();
            notificationManager.cancel(TaskNotificationCreator.NOTIFICATION_ID);
        }
    }
}
