package project.io.goeffective.common;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import project.io.goeffective.presenters.IPresenter;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {
    protected IPresenter presenter;
    private final int layoutResID;

    protected final CompositeSubscription subscriptions = new CompositeSubscription();

    public BaseActivity(int layoutResID) {
        this.layoutResID = layoutResID;
    }

    public IPresenter getPresenter() {
        return presenter;
    }

    protected abstract IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        ButterKnife.inject(this);
        this.onViewReady();
        this.presenter = this.createPresenter(this, savedInstanceState);
    }

    protected void onViewReady()
    {

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.presenter.stop();
        this.subscriptions.clear();
    }
}
