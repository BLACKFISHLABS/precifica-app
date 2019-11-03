package io.github.blackfishlabs.precificaapp.ui;

import io.github.blackfishlabs.precificaapp.R;
import io.github.blackfishlabs.precificaapp.ui.common.BaseFragment;

public class DashboardFragment extends BaseFragment {

    public static final String TAG = DashboardFragment.class.getName();

    @Override
    protected int provideContentViewResource() {
        return R.layout.fragment_dashboard;
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }
}
