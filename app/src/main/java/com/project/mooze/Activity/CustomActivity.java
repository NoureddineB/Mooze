package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.project.mooze.Adapter.PageAdapter;
import com.project.mooze.R;
import com.project.mooze.Utils.CustomViewPager;
import com.project.mooze.Utils.Utils;

public class CustomActivity extends AppCompatActivity {

    public static final int FRAGMENT_SIZE = 0;
    public static final int FRAGMENT_DRINKS = 1;
    public static final int FRAGMENT_SAUCE = 2;

    private TextView text_next;
    private TextView text_previous;

    ViewPager pager;
    ViewPager.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        Utils.hideStatusBar(this);
        pager = findViewById(R.id.view_pager);
        layoutParams = new ViewPager.LayoutParams();
        text_next = findViewById(R.id.text_next);
        text_previous = findViewById(R.id.text_previous);
        configureViewPagerAndTabs();
        showFragment(FRAGMENT_SIZE);



    }
    private void configureViewPagerAndTabs() {
        TabLayout tabs = findViewById(R.id.activity_main_tabs);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2){
                    text_next.setText("VALIDER");
                }else {
                    text_next.setText("SUIVANT");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void nextView(View v){
        if (pager.getCurrentItem() < 3){
            pager.setCurrentItem(pager.getCurrentItem()+1);
        }
        checkNextItem();


    }
    public void previousView(View v){
        if (pager.getCurrentItem() >= 0){
            pager.setCurrentItem(pager.getCurrentItem()-1);
        }
        checkNextItem();

    }

    private void checkNextItem(){
        if (pager.getCurrentItem() == 2){
            text_next.setText("VALIDER");
        }else {
            text_next.setText("SUIVANT");
        }
    }




    private void showFragment(int fragmentIdentifier) {
        switch (fragmentIdentifier) {
            case FRAGMENT_SIZE:
                pager.setCurrentItem(FRAGMENT_SIZE);
                checkNextItem();
                break;
            case FRAGMENT_DRINKS:
                pager.setCurrentItem(FRAGMENT_DRINKS);
                checkNextItem();
                break;
            case FRAGMENT_SAUCE:
                layoutParams.gravity = Gravity.CENTER;
                checkNextItem();
                break;
        }
    }
}
