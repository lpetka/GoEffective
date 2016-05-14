package project.io.goeffective.activities;

import android.app.Notification;
import android.os.Bundle;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.notifications.NotificationScheduler;
import project.io.goeffective.notifications.Task;
import project.io.goeffective.notifications.TaskNotificationCreator;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.PreferencesPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.views.IPreferencesView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class PreferencesActivity extends BaseActivity implements IPreferencesView {

    @InjectView(R.id.clear_user_data_button)
    Button clearUserDataButton;

    private Random random = new Random();

    @InjectView(R.id.notification_test_button)
    Button notificationTestButton;

    @InjectView(R.id.notification_cancel_button)
    Button notificationCancelButton;

    private Task randomTask;

    public PreferencesActivity() {
        super(R.layout.activity_preferences);
        createRandomTask();
    }

    private void createRandomTask() {
        final int randomId = random.nextInt(100);
        Calendar calendar = Calendar.getInstance();
        final Date taskDate = new Date(calendar.getTimeInMillis() + 3000);
        final String taskName = "Przykładowe zadanie " + randomId;
        randomTask = new Task(randomId, taskName, taskDate);
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
        notificationTestButton.setOnClickListener(view -> createTestNotification());
        notificationCancelButton.setOnClickListener(view -> cancelTestNotification());
    }

    private void createTestNotification() {
        final TaskNotificationCreator taskNotificationCreator = new TaskNotificationCreator(this);
        final NotificationScheduler notificationScheduler = new NotificationScheduler(this);
        final Notification notification = taskNotificationCreator.createNotification(randomTask);
        notificationScheduler.scheduleNotification(notification, 0);
    }

    private void cancelTestNotification() {
        final TaskNotificationCreator taskNotificationCreator = new TaskNotificationCreator(this);
        final NotificationScheduler notificationScheduler = new NotificationScheduler(this);
        final Notification notification = taskNotificationCreator.createNotification(randomTask);
        notificationScheduler.cancelNotification(notification);
    }
}
