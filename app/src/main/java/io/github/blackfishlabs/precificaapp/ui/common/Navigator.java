package io.github.blackfishlabs.precificaapp.ui.common;

import android.content.Intent;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

import io.github.blackfishlabs.precificaapp.R;
import io.github.blackfishlabs.precificaapp.ui.AboutFragment;
import io.github.blackfishlabs.precificaapp.ui.DashboardFragment;
import io.github.blackfishlabs.precificaapp.ui.calculate.CalculateActivity;

public class Navigator {

    private final BaseActivity mActivity;

    Navigator(final BaseActivity activity) {
        mActivity = activity;
    }

    public void toDashboard() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        DashboardFragment.newInstance(), DashboardFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.title_dashboard);
    }

    public void toAbout() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
        mActivity.setTitle(R.string.title_about_developer);
    }

    public void toCalculatePrice() {
        ActivityCompat.startActivity(mActivity, new Intent(mActivity, CalculateActivity.class), null);
    }

    public void toWebSite(String site) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
        ActivityCompat.startActivity(mActivity, browserIntent, null);
    }
}
