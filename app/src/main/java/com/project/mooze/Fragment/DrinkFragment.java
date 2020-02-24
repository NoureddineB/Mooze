package com.project.mooze.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mooze.R;

public class DrinkFragment extends Fragment {




    public DrinkFragment() {
        // Required empty public constructor
    }

    public static DrinkFragment newInstance() {
        DrinkFragment fragment = new DrinkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink,container,false);
        return view;
    }





}
