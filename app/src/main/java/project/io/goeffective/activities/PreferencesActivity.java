package project.io.goeffective.activities;

import android.os.Bundle;

import project.io.goeffective.R;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.PreferencesPresenter;
import project.io.goeffective.views.IPreferencesView;

public class PreferencesActivity extends BaseActivity implements IPreferencesView {

    public PreferencesActivity() {
        super(R.layout.activity_preferences);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new PreferencesPresenter(this);
    }
}
