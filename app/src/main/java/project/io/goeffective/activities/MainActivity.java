package project.io.goeffective.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

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
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity implements IMainView {
    @InjectView(R.id.tabs)
    TabLayout tabLayout;

    @InjectView(R.id.viewpager)
    ViewPager viewPager;


    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new MainPresenter(this, AndroidSchedulers.mainThread(), new Navigator(this));
    }

    @Override
    protected void onViewReady() {
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new CalendarFragment(), "Kalendarz");
        viewPagerAdapter.addFragment(new HabitFragment(), "Nawyki");
        viewPager.setAdapter(viewPagerAdapter);
    }
}
