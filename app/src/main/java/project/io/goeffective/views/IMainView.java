package project.io.goeffective.views;


import rx.Observable;

public interface IMainView {
    Observable showHelloWorldToastClick();
    Observable openCalendarActivityClick();

    void showMessage();
}
