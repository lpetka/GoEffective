package project.io.goeffective.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseFragment;
import project.io.goeffective.models.CalendarModel;
import project.io.goeffective.notifications.NotificationsUpdater;
import project.io.goeffective.presenters.CalendarPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.views.ICalendarView;
import project.io.goeffective.widgets.CalendarView;

public class CalendarFragment extends BaseFragment implements ICalendarView {
    @Inject
    DatabaseHandler databaseHandler;

    @InjectView(R.id.calendar_view)
    CalendarView calendarView;

    @InjectView(R.id.calendar_name)
    TextView calendarNameView;
    private Navigator navigator;


    public CalendarFragment() {
        App.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.inject(this, view);
        navigator = new Navigator(getContext());
        calendarView.getGridView().setOnItemClickListener((parent, view1, position, id) -> {
            Date date = (Date) parent.getAdapter().getItem(position);
            final Collection<Task> calendarTasks = calendarView.getModel().getCalendarTasks();
            if (calendarTasks.size() == 1) {
                Task task = (Task) calendarTasks.toArray()[0];
                boolean flag = databaseHandler.checkTaskStatusAtDate(task, date);
                databaseHandler.setTaskStatusAtDate(date, task, !flag);
                new NotificationsUpdater(getContext()).updateNotification();
                calendarView.update();
            } else {
                navigator.openDayActivity(date);
            }
        });


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
    public void onResume() {
        super.onResume();
        calendarView.update();
    }

    @Override
    protected IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState) {
        return new CalendarPresenter(this);
    }

}
