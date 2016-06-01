package project.io.goeffective.activities;

import android.os.Bundle;

import java.util.Date;

import javax.inject.Inject;

import butterknife.InjectView;
import project.io.goeffective.R;
import project.io.goeffective.common.BaseActivity;
import project.io.goeffective.presenters.DayPresenter;
import project.io.goeffective.presenters.IPresenter;
import project.io.goeffective.utils.dbobjects.Task;
import project.io.goeffective.views.IDayView;
import project.io.goeffective.widgets.DayView;

public class DayActivity extends BaseActivity implements IDayView {
    @InjectView(R.id.day_view)
    DayView dayView;

    private Date date;

    public DayActivity() {
        super(R.layout.activity_day);
    }

    @Override
    protected IPresenter createPresenter(BaseActivity baseActivity, Bundle savedInstanceState) {
        return new DayPresenter(this);
    }

    @Override
    protected void onViewReady() {
        this.date = (Date) getIntent().getSerializableExtra("date");

    }
}
