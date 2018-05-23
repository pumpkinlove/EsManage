package com.miaxis.esmanage;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.miaxis.esmanage.adapter.MainFragmentAdapter;
import com.miaxis.esmanage.view.activity.BaseActivity;
import com.miaxis.esmanage.view.fragment.CarListFragment;
import com.miaxis.esmanage.view.fragment.EscortListFragment;
import com.miaxis.esmanage.view.fragment.SystemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindArray(R.array.title)
    String[] titles;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.bnv_main)
    BottomNavigationView bnvMain;

    private MainFragmentAdapter adapter;

    private MenuItem menuItem;

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        EscortListFragment upTaskFragment = new EscortListFragment();
        fragmentList.add(upTaskFragment);
        CarListFragment myTaskFragment = new CarListFragment();
        fragmentList.add(myTaskFragment);
        SystemFragment systemFragment = new SystemFragment();
        fragmentList.add(systemFragment);
        adapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
    }

    @Override
    protected void initView() {
        vpMain.setAdapter(adapter);
        vpMain.addOnPageChangeListener(this);
        vpMain.setOffscreenPageLimit(20);
        vpMain.setCurrentItem(0);
        toolbar.setTitle(titles[0]);
        bnvMain.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        toolbar.setTitle(titles[position]);
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bnvMain.getMenu().getItem(0).setChecked(false);
        }
        menuItem = bnvMain.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload_task:
                vpMain.setCurrentItem(0);
                break;
            case R.id.action_my_task:
                vpMain.setCurrentItem(1);
                break;
            case R.id.action_setting:
                vpMain.setCurrentItem(2);
                break;
        }
        return false;
    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onBackPressed() {
    }
}
