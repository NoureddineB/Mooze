package com.project.mooze.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.project.mooze.Activity.HomeActivity;
import com.project.mooze.Adapter.RecyclerNearAdapter;
import com.project.mooze.Adapter.RecyclerOffersAdapter;
import com.project.mooze.Model.User;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;
import com.project.mooze.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

import io.reactivex.observers.DisposableObserver;


public class MainFragment extends Fragment {

    private RecyclerView recycler_offer;
    private RecyclerView recycler_near;
    private List<User> users;
    private Disposable disposable;

    private RecyclerOffersAdapter recyclerOffersAdapter;
    private RecyclerNearAdapter recyclerNearAdapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        recycler_near = v.findViewById(R.id.recycler_near);
        recycler_offer = v.findViewById(R.id.recycler_offers);
        getAllUser();
        configureRecyclerView();
        return v;


    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void configureRecyclerView(){
        LinearLayoutManager layoutManagerOffer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerNear = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.users = new ArrayList<>();
        this.recyclerOffersAdapter = new RecyclerOffersAdapter(this.users);
        this.recyclerNearAdapter = new RecyclerNearAdapter(this.users);
        this.recycler_offer.setAdapter(this.recyclerOffersAdapter);
        this.recycler_near.setAdapter(this.recyclerNearAdapter);
        recycler_offer.setLayoutManager(layoutManagerOffer);
        recycler_near.setLayoutManager(layoutManagerNear);

    }

    private void getAllUser(){
        this.disposable = MoozeStreams.getAllUser().subscribeWith(create());


    }


    private DisposableObserver<List<User>> create(){
        return new DisposableObserver<List<User>>() {
            @Override
            public void onNext(List<User> user) {
            updateOffersUI(user);
                Log.e("TAGF", String.valueOf(user));

            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "Error" + e);


            }

            @Override
            public void onComplete() {




            }
        };
    }


    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    private void updateOffersUI(List<User> moozeUsers){
        users.addAll(moozeUsers);
        recyclerOffersAdapter.notifyDataSetChanged();
        recyclerNearAdapter.notifyDataSetChanged();

    }

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


}
