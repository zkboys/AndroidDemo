package com.zkboys.androiddemo.view.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zkboys.androiddemo.R;
import com.zkboys.androiddemo.presenter.MainPresenter;
import com.zkboys.androiddemo.utils.PreferenceUtil;
import com.zkboys.androiddemo.view.adapter.TabFragmentPagerAdapter;
import com.zkboys.androiddemo.view.fragment.TableListFragment;
import com.zkboys.sdk.model.TablesInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, TableListFragment.OnFragmentInteractionListener {
    private MainPresenter presenter;

    private int mBackKeyPressedTimes = 0;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    @Bind(R.id.tl_main_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.vp_main_pager)
    ViewPager mPager;


    /**
     * 启动当前Activity
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this);

        initNavigation();

        presenter.getTables();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mBackKeyPressedTimes == 0) {
            showShortToast("再按一次退出程序");
            mBackKeyPressedTimes = 1;
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        mBackKeyPressedTimes = 0;
                    }
                }
            }.start();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Demo2Activity.actionStart(this);
        } else if (id == R.id.nav_gallery) {
            DemoActivity.actionStart(this);
        } else if (id == R.id.nav_slideshow) {
            LoginActivity.actionStart(this);
        } else if (id == R.id.nav_setting) {
            SettingsActivity.actionStart(this);
        } else if (id == R.id.nav_switch_account) {
            SwitchAccountActivity.actionStart(this);
        } else if (id == R.id.nav_logout) {
            presenter.logout();
            this.finish();
            LoginActivity.actionStart(this);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false; // false: 清除选中状态，true: 点击过后，一直是选中状态
    }

    private void initNavigation() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);

        View headerView = mNavigationView.getHeaderView(0);

        TextView mUserNameView = (TextView) headerView.findViewById(R.id.tv_nav_header_main_user_name);
        TextView mLoginNameView = (TextView) headerView.findViewById(R.id.tv_nav_header_main_login_name);
        PreferenceUtil preUtil = PreferenceUtil.getInstance(this);
        String userName = preUtil.getUserName();
        String loginName = preUtil.getLoginName();

        mUserNameView.setText(userName);
        mLoginNameView.setText(loginName);
    }

    /**
     * 采用viewpager中切换fragment
     */
    public void initFragmentPages(List<TablesInfo> tablesInfoList) {
        List<String> mTitles = new ArrayList<>(); // tab名称列表
        List<Fragment> mFragments = new ArrayList<>();
        TabFragmentPagerAdapter mFragmentAdapter; // 定义以fragment为切换的adapter

        for (int i = 0; i < tablesInfoList.size(); i++) {
            TablesInfo tablesInfo = tablesInfoList.get(i);
            mTitles.add(tablesInfo.getTabRegionName());
            mFragments.add(TableListFragment.newInstance(tablesInfo));
        }


        mFragmentAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mPager.setAdapter(mFragmentAdapter);

        //将tabLayout与viewpager连起来
        mTabLayout.setupWithViewPager(mPager);

        //设置TabLayout的模式,这里主要是用来显示tab展示的情况的
        //TabLayout.MODE_FIXED          各tab平分整个工具栏,如果不设置，则默认就是这个值
        //TabLayout.MODE_SCROLLABLE     适用于多tab的，也就是有滚动条的，一行显示不下这些tab可以用这个，较少时，都会居左显示
        //                              当然了，你要是想做点特别的，像知乎里就使用的这种效果

        if (tablesInfoList.size() > 5) {
            // TODO: 这里最好能计算一下屏幕是否展示得开。
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
