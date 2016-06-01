package project.io.goeffective.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import java.util.Calendar;
import java.util.Random;

import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.notifications.NotificationScheduler;
import project.io.goeffective.notifications.TaskNotificationCreator;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.PreferencesPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.utils.dbobjects.TaskStart;
import project.io.goeffective.views.IPreferencesView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class PreferencesActivity extends BaseActivity implements IPreferencesView {

    @InjectView(R.id.clear_user_data_button)
    Button clearUserDataButton;

    @InjectView(R.id.notification_test_button)
    Button notificationTestButton;

    @InjectView(R.id.notification_cancel_button)
    Button notificationCancelButton;

    private Random random = new Random();

    private Task randomTask;

    public PreferencesActivity() {
        super(R.layout.activity_preferences);
        createRandomTask();
    }

    private void createRandomTask() {
        final int randomId = random.nextInt(100);
        final String taskName = "Przykładowe zadanie " + randomId;
        randomTask = new Task(taskName);

        Calendar date = Calendar.getInstance();
        randomTask.addTaskStart(new TaskStart(date.getTime(), 7));
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new PreferencesPresenter(this, new Navigator(this), AndroidSchedulers.mainThread(), this);
    }

    @Override
    public Observable clearUserDataButtonClick() {
        return ViewObservable.clicks(clearUserDataButton);
    }

    @Override
    protected void onViewReady() {
        super.onViewReady();
        notificationTestButton.setOnClickListener(view -> createNotification());
        notificationCancelButton.setOnClickListener(view -> cancelNotification());
    }

    private void createNotification() {
        final TaskNotificationCreator taskNotificationCreator = new TaskNotificationCreator(this);
        final Notification notification = taskNotificationCreator.createNotification(randomTask);
        final Object systemService = getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.notify(TaskNotificationCreator.NOTIFICATION_ID, notification);
    }

    private void cancelNotification() {
        final TaskNotificationCreator taskNotificationCreator = new TaskNotificationCreator(this);
        final Object systemService = getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.cancel(TaskNotificationCreator.NOTIFICATION_ID);
    }

    private void createScheduledNotification() {
        final TaskNotificationCreator taskNotificationCreator = new TaskNotificationCreator(this);
        final NotificationScheduler notificationScheduler = new NotificationScheduler(this);
        final Notification notification = taskNotificationCreator.createNotification(randomTask);
        notificationScheduler.scheduleNotification(notification, TaskNotificationCreator.NOTIFICATION_ID, Calendar.getInstance().getTime());
    }

    private void cancelScheduledNotification() {
        final TaskNotificationCreator taskNotificationCreator = new TaskNotificationCreator(this);
        final NotificationScheduler notificationScheduler = new NotificationScheduler(this);
        final Notification notification = taskNotificationCreator.createNotification(randomTask);
        notificationScheduler.cancelNotification(notification, TaskNotificationCreator.NOTIFICATION_ID);
    }
}
