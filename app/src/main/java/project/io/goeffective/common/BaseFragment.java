package project.io.goeffective.common;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.utils.StringConstants;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {
    protected final Bundle args = new Bundle();
    protected IPresenter presenter;
    protected final CompositeSubscription subscriptions = new CompositeSubscription();
    private int layoutResId;

    public BaseFragment() {}

    protected abstract IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutResId = this.getArguments().getInt(StringConstants.FRAGMENT_RESOURCE_ID, 0);
        View view = inflater.inflate(layoutResId, null);
        ButterKnife.inject(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.presenter = this.createPresenter(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.presenter.stop();
        this.subscriptions.clear();
    }
}
