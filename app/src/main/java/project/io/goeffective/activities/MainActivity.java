package project.io.goeffective.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.MainPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.views.IMainView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;


public class MainActivity extends BaseActivity implements IMainView {

    @InjectView(R.id.button)
    Button openHelloWorldToastButton;

    @InjectView(R.id.main_activity_calendar_button)
    Button openCalendarActivityButton;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new MainPresenter(this, AndroidSchedulers.mainThread(), new Navigator(this));
    }

    @Override
    public Observable showHelloWorldToastClick() {
        return ViewObservable.clicks(openHelloWorldToastButton);
    }

    @Override
    public void showMessage() {
        Toast.makeText(this, R.string.toast_hello_world, Toast.LENGTH_LONG).show();
    }

    @Override
    public Observable openCalendarActivityClick() {
        return ViewObservable.clicks(openCalendarActivityButton);
    }
}
