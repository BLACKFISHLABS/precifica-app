package io.github.blackfishlabs.precificaapp.ui.common;

import android.content.Intent;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.blackfishlabs.precificaapp.R;
import io.github.blackfishlabs.precificaapp.ui.AboutFragment;
import io.github.blackfishlabs.precificaapp.ui.AdsFragment;
import io.github.blackfishlabs.precificaapp.ui.CalcHourFragment;
import io.github.blackfishlabs.precificaapp.ui.CalcProjectFragment;
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

        FloatingActionButton fab = mActivity.findViewById(R.id.fab_all_main_action);
        fab.show();

        mActivity.setTitle(R.string.title_dashboard);
    }

    public void toCalcHour() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        CalcHourFragment.newInstance(), CalcHourFragment.TAG)
                .commit();

        FloatingActionButton fab = mActivity.findViewById(R.id.fab_all_main_action);
        fab.hide();

        mActivity.setTitle(R.string.title_calc_hour);
    }

    public void toCalcProject() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        CalcProjectFragment.newInstance(), CalcProjectFragment.TAG)
                .commit();

        FloatingActionButton fab = mActivity.findViewById(R.id.fab_all_main_action);
        fab.hide();

        mActivity.setTitle(R.string.title_calc_project);
    }

    public void toAbout() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();

        FloatingActionButton fab = mActivity.findViewById(R.id.fab_all_main_action);
        fab.hide();

        mActivity.setTitle(R.string.title_about_developer);
    }

    public void toCalculatePrice() {
        ActivityCompat.startActivity(mActivity, new Intent(mActivity, CalculateActivity.class), null);
    }

    public void toAds() {
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_all_fragment_container,
                        AdsFragment.newInstance(), AdsFragment.TAG)
                .commit();

        FloatingActionButton fab = mActivity.findViewById(R.id.fab_all_main_action);
        fab.hide();

        mActivity.setTitle(R.string.title_ads);
    }

    public void toWebSite(String site) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(site));
        ActivityCompat.startActivity(mActivity, browserIntent, null);
    }
}
