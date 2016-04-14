package project.io.goeffective.views;


import rx.Observable;

public interface IMainView {
    Observable showHelloWorldToastClick();

    Observable openCalendarActivityClick();

    Observable openPreferencesActivityClick();

    void showMessage();
}
