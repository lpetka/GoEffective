package project.io.goeffective.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.fragments.CalendarFragment;
import project.io.goeffective.fragments.HabitFragment;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.presenters.MainPresenter;
import project.io.goeffective.services.Navigator;
import project.io.goeffective.views.IMainView;
import project.io.goeffective.widgets.adapters.ViewPagerAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;

public class MainActivity extends BaseActivity implements IMainView {
    @InjectView(R.id.tabs)
    TabLayout tabLayout;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private final Navigator navigator;

    public MainActivity() {
        super(R.layout.activity_main);
        navigator = new Navigator(this);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new MainPresenter(this, AndroidSchedulers.mainThread(), navigator);
    }

    @Override
    protected void onViewReady() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preferences:
                navigator.openPreferencesActivity();
                break;
        }
        return true;
    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new CalendarFragment(), "Kalendarz");
        viewPagerAdapter.addFragment(new HabitFragment(), "Nawyki");
        viewPager.setAdapter(viewPagerAdapter);
    }

}
