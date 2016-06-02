package project.io.goeffective.activities;

import android.os.Bundle;
import android.widget.Button;

import butterknife.InjectView;
import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
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
}
