package com.project.mooze.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.mooze.Activity.CustomActivity;

import com.project.mooze.Fragment.DrinkFragment;

import com.project.mooze.Fragment.SauceFragment;
import com.project.mooze.Fragment.SizeFragment;

public class PageAdapter extends FragmentPagerAdapter {

    //Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    }


    @Override
    public int getCount() {
        return(3);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case CustomActivity.FRAGMENT_SIZE: //Page number 1
                return SizeFragment.newInstance();
            case CustomActivity.FRAGMENT_DRINKS: //Page number 2
                return DrinkFragment.newInstance();
            case CustomActivity.FRAGMENT_SAUCE: //Page number 3
                return SauceFragment.newInstance();
            default:
                return null;
        }

    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return "Taille";
            case 1: //Page number 2
                return "Boissons";
            case 2: //Page number 3
                return "Sauce";
            default:
                return null;

        }

    }

}
