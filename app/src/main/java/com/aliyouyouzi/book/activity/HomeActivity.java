package com.aliyouyouzi.book.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aliyouyouzi.book.R;
import com.aliyouyouzi.book.activity.Base.BaseActivity;
import com.aliyouyouzi.book.controller.FragmentController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    @BindView(R.id.home_dl_drawer_layout)
    public DrawerLayout mDrLayout;
    @BindView(R.id.nav_view)
    public NavigationView mNaView;

    private int mLastItemId;
    long firstTime;
    private FragmentController mFragmentController;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrLayout, mToolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        mDrLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void initDatas() {
        //默认按下去了每日推荐
        mFragmentController = new FragmentController(this);
        mNaView.getMenu().getItem(0).setChecked(true);
        mLastItemId = mNaView.getMenu().getItem(0).getItemId();
        changeFragments(mLastItemId);
    }

    private void changeFragments(int lastItemId) {
        mFragmentController.changeFragments(lastItemId);
    }

    @Override
    protected void bindEvent() {
        mNaView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrLayout.isDrawerOpen(GravityCompat.START)) {
            mDrLayout.closeDrawer(GravityCompat.START);
        } else {
            long secondTime = System.currentTimeMillis();

            if (secondTime - firstTime > 2000) {
                Snackbar sb = Snackbar.make(mNaView, "再按一次退出", Snackbar.LENGTH_LONG);
                sb.getView().setBackgroundColor(getResources().getColor(R.color.common_color));
                sb.show();
                firstTime = secondTime;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_recommend) {
            mFragmentController.changeFragments(id);
        } else if (id == R.id.nav_article) {
            mFragmentController.changeFragments(id);
        } else if (id == R.id.nav_hot) {
            mFragmentController.changeFragments(id);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_setting) {

        }
        mDrLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}