package project.io.goeffective.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;
import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.notifications.NotificationScheduler;
import project.io.goeffective.notifications.TaskNotificationCreator;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.PreferencesPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.views.IPreferencesView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class PreferencesActivity extends BaseActivity implements IPreferencesView {

    @Inject
    DatabaseHandler databaseHandler;

    @InjectView(R.id.clear_user_data_button)
    Button clearUserDataButton;

    @InjectView(R.id.notification_test_button)
    Button notificationTestButton;

    @InjectView(R.id.notification_cancel_button)
    Button notificationCancelButton;

    public PreferencesActivity() {
        super(R.layout.activity_preferences);
        App.getComponent().inject(this);
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
        final Notification notification = taskNotificationCreator.createNotification(getTasks());
        final Object systemService = getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.notify(TaskNotificationCreator.NOTIFICATION_ID, notification);
    }

    private void cancelNotification() {
        final Object systemService = getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.cancel(TaskNotificationCreator.NOTIFICATION_ID);
    }

    private void createScheduledNotification() {
        final TaskNotificationCreator taskNotificationCreator = new TaskNotificationCreator(this);
        final NotificationScheduler notificationScheduler = new NotificationScheduler(this);
        final Notification notification = taskNotificationCreator.createNotification(getTasks());
        notificationScheduler.scheduleNotification(notification, Calendar.getInstance().getTime());
    }

    private void cancelScheduledNotification() {
        final NotificationScheduler notificationScheduler = new NotificationScheduler(this);
        notificationScheduler.cancelNotification();
    }

    private List<Task> getTasks() {
        return databaseHandler.getTasksAtDate(Calendar.getInstance().getTime());
    }
}
