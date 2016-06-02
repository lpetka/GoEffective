package project.io.goeffective.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.fragments.CalendarFragment;
import project.io.goeffective.fragments.TasksListFragment;
import project.io.goeffective.models.CalendarModel;
import project.io.goeffective.models.ICalendarModel;
import project.io.goeffective.models.TaskListModel;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.MainPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.views.IMainView;
import project.io.goeffective.widgets.CalendarView;
import project.io.goeffective.widgets.adapters.ViewPagerAdapter;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity implements IMainView {
    @InjectView(R.id.tabs)
    TabLayout tabLayout;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private final Navigator navigator;

    public MainActivity() {
        super(R.layout.activity_main);
        navigator = new Navigator(this);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new MainPresenter(this, AndroidSchedulers.mainThread(), navigator);
    }

    @Override
    protected void onViewReady() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_task:
                showTaskSelector();
                break;
            case R.id.action_preferences:
                navigator.openPreferencesActivity();
                break;
        }
        return true;
    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new CalendarFragment(), "Kalendarz");
        viewPagerAdapter.addFragment(new TasksListFragment(), "Nawyki");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void showTaskSelector() {
        final TaskListModel taskListModel = new TaskListModel();
        final List<Task> taskList = taskListModel.getDetailedTaskList();
        final String[] taskNames = getTaskNames(taskList);
        TextView calendarName = (TextView) findViewById(R.id.calendar_name);
        assert calendarName != null;
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendar_view);
        assert calendarView != null;
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Wybierz zadanie")
                .setItems(taskNames, (dialog, which) -> {
                    calendarName.setText("Kalendarz dla zadania: " + taskNames[which]);
                    ICalendarModel calendarModel;
                    if (which == 0) {
                        // user selected main calendar
                        calendarModel = new CalendarModel();
                    } else {
                        // user selected task calendar
                        final Task selectedTask = taskList.get(which - 1);
                        calendarModel = new CalendarModel(selectedTask);
                    }
                    calendarView.setModel(calendarModel);
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @NonNull
    private String[] getTaskNames(List<Task> taskList) {
        final String[] taskNames = new String[taskList.size() + 1];
        taskNames[0] = "Wszystkie";
        int i = 1;
        for (Task task : taskList) {
            taskNames[i] = task.getName();
            i++;
        }
        return taskNames;
    }

}
