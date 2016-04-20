package project.io.goeffective.fragments;

import android.os.Bundle;

import project.io.goeffective.R;
import project.io.goeffective.common.BaseFragment;
import project.io.goeffective.presenters.AddTaskPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.utils.StringConstants;
import project.io.goeffective.views.IAddTaskView;

public class HabitFragment extends BaseFragment implements IAddTaskView {

    public HabitFragment() {
        args.putInt(StringConstants.FRAGMENT_RESOURCE_ID, R.layout.activity_edit_task);
    }

    @Override
    protected IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState) {
        return new AddTaskPresenter(this);
    }
}
