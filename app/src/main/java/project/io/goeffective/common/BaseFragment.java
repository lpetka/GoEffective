package project.io.goeffective.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import project.io.goeffective.presenters.IPresenter;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {
    protected IPresenter presenter;
    protected final CompositeSubscription subscriptions = new CompositeSubscription();

    public BaseFragment() {
    }

    protected abstract IPresenter createPresenter(BaseFragment baseFragment, Bundle savedInstanceState);

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
