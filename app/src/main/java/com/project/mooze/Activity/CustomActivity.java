package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.project.mooze.Adapter.PageAdapter;
import com.project.mooze.R;
import com.project.mooze.Utils.Utils;

public class CustomActivity extends AppCompatActivity {

    public static final int FRAGMENT_SIZE = 0;
    public static final int FRAGMENT_DRINKS = 1;
    public static final int FRAGMENT_SAUCE = 2;

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        Utils.hideStatusBar(this);
        pager = findViewById(R.id.view_pager);
        configureViewPagerAndTabs();
        showFragment(FRAGMENT_SIZE);
    }
    private void configureViewPagerAndTabs() {
        TabLayout tabs = findViewById(R.id.activity_main_tabs);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);

    }


    private void showFragment(int fragmentIdentifier) {
        switch (fragmentIdentifier) {
            case FRAGMENT_SIZE:
                pager.setCurrentItem(FRAGMENT_SIZE);
                break;
            case FRAGMENT_DRINKS:
                pager.setCurrentItem(FRAGMENT_DRINKS);
                break;
            case FRAGMENT_SAUCE:
                pager.setCurrentItem(FRAGMENT_SAUCE);
                break;
        }
    }
}
