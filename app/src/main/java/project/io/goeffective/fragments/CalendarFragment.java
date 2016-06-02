package project.io.goeffective.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseFragment;
import project.io.goeffective.models.CalendarModel;
import project.io.goeffective.presenters.CalendarPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.utils.StringConstants;
import project.io.goeffective.views.ICalendarView;
import project.io.goeffective.widgets.CalendarView;

public class CalendarFragment extends BaseFragment implements ICalendarView {
    @InjectView(R.id.calendar_view)
    CalendarView calendarView;

    @InjectView(R.id.calendar_name)
    TextView calendarNameView;

    public CalendarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (calendarView != null && isVisibleToUser) {
            calendarView.setModel(new CalendarModel());
            calendarNameView.setText("Kalendarz dla zadania: Wszystkie");
        }
    }

    @Override
    protected IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState) {
        return new CalendarPresenter(this);
    }
}
