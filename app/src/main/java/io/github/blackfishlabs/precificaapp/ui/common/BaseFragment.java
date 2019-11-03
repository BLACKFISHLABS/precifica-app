package io.github.blackfishlabs.precificaapp.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private BaseActivity hostActivity;

    private Unbinder mUnbinder;

    public BaseFragment() {
    }

    @LayoutRes
    protected abstract int provideContentViewResource();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            hostActivity = (BaseActivity) context;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Descendants of " + getClass().getName() +
                    " must be hosted by " + BaseActivity.class.getName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle inState) {
        final View fragmentView = inflater.inflate(provideContentViewResource(), container, false);
        mUnbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected Navigator navigate() {
        return hostActivity.navigate();
    }

    protected EventBus eventBus() {
        return hostActivity.eventBus();
    }

    protected void setTitle(String title) {
        hostActivity.setTitle(title);
    }

    protected BaseActivity getHostActivity() {
        return hostActivity;
    }

}
