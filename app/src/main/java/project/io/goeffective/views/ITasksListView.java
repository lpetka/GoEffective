package project.io.goeffective.views;

import rx.Observable;

public interface ITasksListView {
    Observable addTaskClick();
    Observable addDummyTaskClick();
    void addDummyTask();
}
