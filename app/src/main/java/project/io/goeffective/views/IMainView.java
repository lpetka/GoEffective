package project.io.goeffective.views;


import rx.Observable;

public interface IMainView {
    Observable showHelloWorldToastClick();

    Observable openCalendarActivityClick();
    Observable openAddTaskActivityClick();

    Observable openPreferencesActivityClick();

    void showMessage();
}
