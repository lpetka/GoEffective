package project.io.goeffective.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseFragment;
import project.io.goeffective.presenters.AddTaskPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.views.IAddTaskView;

public class HabitFragment extends BaseFragment implements IAddTaskView {

    public HabitFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_task, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState) {
        return new AddTaskPresenter(this);
    }
}
