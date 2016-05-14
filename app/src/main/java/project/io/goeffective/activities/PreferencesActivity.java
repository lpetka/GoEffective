package project.io.goeffective.activities;

import android.app.Notification;
import android.os.Bundle;
import android.widget.Button;

import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.notifications.NotificationCreator;
import project.io.goeffective.notifications.NotificationScheduler;
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

    @InjectView(R.id.notification_test_button)
    Button notificationTestButton;

    public PreferencesActivity() {
        super(R.layout.activity_preferences);
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
    }

    private void createTestNotification() {
        final NotificationCreator notificationCreator = new NotificationCreator(this);
        final NotificationScheduler notificationScheduler = new NotificationScheduler(this);
        final Notification notification = notificationCreator.createNotification("Title", "Content", R.drawable.day_task_not_done);
        notificationScheduler.scheduleNotification(notification, 3000);
    }
}
