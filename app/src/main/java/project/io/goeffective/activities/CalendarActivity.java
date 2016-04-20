package project.io.goeffective.activities;

import android.os.Bundle;

import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.presenters.CalendarPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.views.ICalendarView;

public class CalendarActivity extends BaseActivity implements ICalendarView {

    public CalendarActivity() {
        super(R.layout.activity_calendar);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new CalendarPresenter(this);
    }
}
