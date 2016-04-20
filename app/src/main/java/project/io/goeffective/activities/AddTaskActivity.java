package project.io.goeffective.activities;

import android.os.Bundle;

import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.presenters.AddTaskPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.views.IAddTaskView;

public class AddTaskActivity extends BaseActivity implements IAddTaskView {

    public AddTaskActivity() {
        super(R.layout.activity_edit_task);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new AddTaskPresenter(this);
    }
}
