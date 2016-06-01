package project.io.goeffective.presenters;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import project.io.goeffective.App;
import project.io.goeffective.R;
import project.io.goeffective.activities.TaskAddActivity;
import project.io.goeffective.services.INavigator;
import project.io.goeffective.utils.DatabaseHandler;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.utils.dbobjects.TaskStart;
import project.io.goeffective.views.IAddTaskView;
import project.io.goeffective.widgets.WeekDayView;
import project.io.goeffective.widgets.adapters.WeekDayListAdapter;
import rx.Scheduler;

public class AddTaskPresenter extends Presenter<IAddTaskView> {
    private final Scheduler uiThread;
    private final INavigator navigator;
    private WeekDayView weekDayListSelector;
    private final Context context;

    @Inject
    DatabaseHandler databaseHandler;

    public AddTaskPresenter(IAddTaskView iAddTaskView, INavigator navigator, Scheduler uiThread, TaskAddActivity taskAddActivity) {
        super(iAddTaskView);
        this.navigator = navigator;
        this.uiThread = uiThread;
        context = taskAddActivity;
        App.getComponent().inject(this);
    }

    @Override
    public void start() {
        super.start();

        this.subscriptions.add(view.createTaskButtonClick().observeOn(uiThread).subscribe(o -> {
            if(weekDayListSelector == null) {
                weekDayListSelector = view.getWeekDayListSelector();
            }
            createTask(view.getTaskName(), weekDayListSelector, view.getNoteInput());
        }));
    }

    private void createTask(EditText taskName, WeekDayView weekDayListSelector, EditText noteInput) {
        switch(validateData(taskName, weekDayListSelector)) {
            case 0:     //valid
                showDialog(taskName, weekDayListSelector, noteInput);
                break;
            case 1:     //empty task name
                Toast.makeText(context, "Podaj nazwę zadania.", Toast.LENGTH_SHORT).show();
                break;
            case 2:     //no checked days
                Toast.makeText(context, "Wybierz dni tygodnia.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void showDialog(EditText taskName, WeekDayView weekDayListSelector, EditText noteInput) {
        new AlertDialog.Builder(context)
                .setTitle("Dodawanie zadanie")
                .setMessage("Czy chcesz utworzyć to zadanie?")
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    addTask(taskName, weekDayListSelector, noteInput);
                    view.close();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void addTask(EditText taskName, WeekDayView weekDayListSelector, EditText noteInput) {
        Task task = new Task(taskName.getText().toString());
        WeekDayListAdapter weekDayListAdapter = (WeekDayListAdapter) weekDayListSelector.getAdapter();
        boolean[] checked = weekDayListAdapter.getChecked();
        GregorianCalendar date;
        for(int i = 0; i < checked.length; i++) {
            if(checked[i]) {
                //in Gregorian Calendar day number 1 is Sunday
                int myDay = (i + 2) % 7 + 1;
                date = new GregorianCalendar(
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                date.add(Calendar.DATE, -70);
                while (date.get(Calendar.DAY_OF_WEEK) != myDay)
                    date.add(Calendar.DATE, 1);
                task.addTaskStart(new TaskStart(date.getTime(), 7));
            }
        }
        task.setNote(noteInput.getText().toString());
        databaseHandler.addTask(task);
    }

    private int validateData(EditText taskName, WeekDayView weekDayListSelector) {
        if(taskName.getText().toString().matches(""))
                return 1;

        WeekDayListAdapter weekDayListAdapter = (WeekDayListAdapter) weekDayListSelector.getAdapter();
        boolean[] checked = weekDayListAdapter.getChecked();
        int i = 0;
        while(i < checked.length) {
            if (checked[i])
                return 0;
            i++;
        }

        return 2;
    }
}
