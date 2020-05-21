package com.project.mooze.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.mooze.Fragment.MainFragment;
import com.project.mooze.Fragment.MapFragment;
import com.project.mooze.Fragment.QRfragment;
import com.project.mooze.Fragment.SettingsFragment;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;
import com.project.mooze.Utils.DetailsActivity;
import com.project.mooze.Utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements MapFragment.onLocationPass {

    final Fragment fragment_main = new MainFragment();
    final Fragment fragment_map = new MapFragment();
    Fragment fragment_QR = new QRfragment();
    final Fragment fragment_settings = new SettingsFragment();

    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private SharedPreferences preferences;

    BottomNavigationView navView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager =  findViewById(R.id.viewpager);
        preferences = getSharedPreferences("PREFS",0);
        Utils.hideStatusBar(HomeActivity.this);

        configureBottomNav();
        configureViewPager();
        viewPager.setOffscreenPageLimit(3);


    }

  @Override
    public void onBackPressed(){

    }

    public void startCartActivity(View v){
        Intent intent = new Intent(HomeActivity.this, ShoppingCartActivity.class);
        startActivity(intent);
    }




    public void startActivity(View view){
        if (Utils.isLogged(HomeActivity.this)) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.account_text:
                    intent = new Intent(HomeActivity.this, AccountActivity.class);
                    startActivity(intent);
                    break;
                    case R.id.help_text:
                    /*intent = new Intent(getActivity(),HelpActivity);*/
                    break;
                case R.id.policy_text:
                    openPrivacy();
                    break;
            }

        }else{
            Toast.makeText(HomeActivity.this,"Veuillez vous connectez",Toast.LENGTH_SHORT).show();
        }

    }

    private void  openPrivacy(){
     Intent intent = new Intent(this, DetailsActivity.class);
     startActivity(intent);

    }


    public void logout(View view){
        if (Utils.isLogged(HomeActivity.this)) {
        SharedPreferences.Editor editor = preferences.edit();
        ShoppingCart.getCart(HomeActivity.this).clear();
        editor.putInt("USERID", 0);
        editor.apply();
        finish();
        System.exit(0);}
    }

    public void login(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }



    //Configure Bottom Navigation
    private void configureBottomNav() {
        navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Configure click on bottom navigation's item
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_map:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_QRcode:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_settings:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };


    @Override
    public void onLocationPass(Location location) {

    }

    private void configureViewPager(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    navView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page",""+position);
                navView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = navView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),0);
        adapter.addFragment(fragment_main);
        adapter.addFragment(fragment_map);
        adapter.addFragment(fragment_QR);
        adapter.addFragment(fragment_settings);
        viewPager.setAdapter(adapter);
    }
}


 class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

      ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
         super(fm, behavior);
     }


     @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

     @Override
     public int getItemPosition(@NotNull Object object) {
         return POSITION_NONE;
     }

     public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

}
