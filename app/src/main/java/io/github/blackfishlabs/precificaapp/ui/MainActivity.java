package io.github.blackfishlabs.precificaapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.List;

import butterknife.OnClick;
import info.androidhive.fontawesome.FontDrawable;
import io.github.blackfishlabs.precificaapp.BuildConfig;
import io.github.blackfishlabs.precificaapp.R;
import io.github.blackfishlabs.precificaapp.helper.AndroidUtils;
import io.github.blackfishlabs.precificaapp.ui.common.BaseActivity;

import static java.util.Objects.requireNonNull;

public class MainActivity extends BaseActivity implements Drawer.OnDrawerItemClickListener {

    private static final int DRAWER_ITEM_DASHBOARD = 1;
    private static final int DRAWER_ITEM_ABOUT = 10;

    private AccountHeader accountHeader;

    private PrimaryDrawerItem dashboardDrawerItem;
    private PrimaryDrawerItem aboutDrawerItem;

    private SecondaryDrawerItem infoDrawerItem;

    @Override
    protected int provideContentViewResource() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.fab_all_main_action)
    void onFabClicked() {
        navigate().toCalculatePrice();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setAsHomeActivity();
        initDrawerHeader(savedInstanceState);
        initDrawer(savedInstanceState);

        FloatingActionButton fab = findViewById(R.id.fab_all_main_action);
        FontDrawable drawable = new FontDrawable(this, R.string.fa_arrow_right_solid, true, false);
        drawable.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        fab.setImageDrawable(drawable);
    }

    private void initDrawerHeader(final Bundle inState) {
        String nameToShow = "Precifica App!";
        String emailToShow = "balckfishlabs.github.io";

        List<IProfile> profiles = Lists.newArrayList();
        TextDrawable.IShapeBuilder builder = createShapeBuilder();
        final String nameWithTwoLetters = generateNameWithTwoLetters(nameToShow);

        ProfileDrawerItem profile = new ProfileDrawerItem()
                .withName(nameToShow.toUpperCase())
                .withEmail(emailToShow)
                .withIcon(builder.buildRound(nameWithTwoLetters, Color.DKGRAY));
        profiles.add(profile);

        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withSavedInstance(inState)
                .withOnlyMainProfileImageVisible(true)
                .withCurrentProfileHiddenInList(true)
                .withSelectionListEnabledForSingleProfile(false)
                .withProfiles(profiles)
                .withCompactStyle(true)
                .build();
    }

    private TextDrawable.IShapeBuilder createShapeBuilder() {
        return TextDrawable.builder()
                .beginConfig()
                .fontSize(AndroidUtils.dpToPx(this, 26))
                .toUpperCase()
                .endConfig();
    }

    private String generateNameWithTwoLetters(String name) {
        String[] parts = name.trim().split("\\s+");

        if (parts.length == 1) {
            return parts[0].substring(0, 2);
        } else {
            return parts[0].substring(0, 1).concat(parts[1].substring(0, 1));
        }
    }

    private void initDrawer(final Bundle inState) {
        List<IDrawerItem> menuPrimaryToShow = Lists.newArrayList(createDashboardDrawerItem(),
                createAboutDrawerItem(),
                new DividerDrawerItem(),
                createInfoDrawerItem());

        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(requireNonNull(mToolbar))
                .withAccountHeader(accountHeader)
                .withDrawerItems(menuPrimaryToShow)
                .withSavedInstance(inState)
                .withShowDrawerOnFirstLaunch(true)
                .withOnDrawerItemClickListener(this)
                .withSelectedItem(DRAWER_ITEM_DASHBOARD)
                .withFireOnInitialOnClick(true)
                .withActionBarDrawerToggle(false)
                .build();
    }

    private PrimaryDrawerItem createDashboardDrawerItem() {
        if (dashboardDrawerItem == null) {
            dashboardDrawerItem = new PrimaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_DASHBOARD)
                    .withName(R.string.title_dashboard)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.ic_dashboard, getTheme()));
        }
        return dashboardDrawerItem;
    }

    private PrimaryDrawerItem createAboutDrawerItem() {
        if (aboutDrawerItem == null) {
            aboutDrawerItem = new PrimaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_ABOUT)
                    .withName(R.string.title_about_developer)
                    .withIcon(VectorDrawableCompat.create(getResources(), R.drawable.ic_developer_mode, getTheme()));
        }

        return aboutDrawerItem;
    }

    private SecondaryDrawerItem createInfoDrawerItem() {
        if (infoDrawerItem == null) {
            infoDrawerItem = new SecondaryDrawerItem()
                    .withIdentifier(DRAWER_ITEM_ABOUT)
                    .withIcon(VectorDrawableCompat
                            .create(getResources(), R.drawable.ic_bookmark_border, getTheme()))
                    .withName("Versão do Aplicativo")
                    .withDescription(BuildConfig.VERSION_NAME)
                    .withSelectable(false);
        }
        return infoDrawerItem;
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        try {
            Preconditions.checkNotNull(drawerItem);
        } catch (NullPointerException e) {
            return false;
        }

        switch ((int) drawerItem.getIdentifier()) {
            case DRAWER_ITEM_DASHBOARD: {
                if (!isViewingDashboard()) {
                    navigate().toDashboard();
                }
                break;
            }

            case DRAWER_ITEM_ABOUT: {
                if (!isViewingAbout()) {
                    navigate().toAbout();
                }
                break;
            }
        }

        return false;
    }

    private boolean isViewingDashboard() {
        return isViewingFragmentByTag(DashboardFragment.TAG);
    }

    private boolean isViewingAbout() {
        return isViewingFragmentByTag(AboutFragment.TAG);
    }

    private boolean isViewingFragmentByTag(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        return fragment != null && fragment.isVisible();
    }

}
