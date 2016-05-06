package project.io.goeffective.activities;

import android.os.Bundle;

import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.presenters.DayPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.views.IDayView;

public class DayActivity extends BaseActivity implements IDayView {

    public DayActivity() {
        super(R.layout.activity_day);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new DayPresenter(this);
    }
}
