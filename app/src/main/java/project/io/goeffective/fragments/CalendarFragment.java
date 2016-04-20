package project.io.goeffective.fragments;

import android.os.Bundle;

import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.common.BaseFragment;
import project.io.goeffective.presenters.CalendarPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.utils.StringConstants;
import project.io.goeffective.views.ICalendarView;

public class CalendarFragment extends BaseFragment implements ICalendarView {

    public CalendarFragment() {
        args.putInt(StringConstants.FRAGMENT_RESOURCE_ID, R.layout.activity_calendar);
        super.setArguments(args);
    }

    @Override
    protected IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState) {
        return new CalendarPresenter(this);
    }
}
