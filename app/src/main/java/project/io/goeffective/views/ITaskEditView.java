package project.io.goeffective.views;

import rx.Observable;

public interface ITaskEditView {
    Observable removeTaskButtonClick();
    void removeTask();
}
