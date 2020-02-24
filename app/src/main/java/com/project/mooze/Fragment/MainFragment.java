package com.project.mooze.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.project.mooze.Activity.HomeActivity;
import com.project.mooze.Activity.OrderActivity;
import com.project.mooze.Adapter.RecyclerViewAdapter;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.User.User;
import com.project.mooze.R;
import com.project.mooze.Utils.ItemClickSupport;
import com.project.mooze.Utils.MoozeStreams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

import io.reactivex.observers.DisposableObserver;


public class MainFragment extends Fragment {

    private RecyclerView recycler_offer;
    private RecyclerView recycler_near;
    private List<Restaurent> restaurents;
    private Disposable disposable;
    public static final String restoID = "ID";


    private RecyclerViewAdapter recyclerViewAdapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        recycler_near = v.findViewById(R.id.recycler_near);
        recycler_offer = v.findViewById(R.id.recycler_offers);
        getAllRestaurent();
        configureRecyclerView();
        this.configureOnClickRecyclerView();

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
        this.restaurents = new ArrayList<>();
        this.recyclerViewAdapter = new RecyclerViewAdapter(this.restaurents, Glide.with(this));
        this.recycler_offer.setAdapter(this.recyclerViewAdapter);
        this.recycler_near.setAdapter(this.recyclerViewAdapter);
        recycler_offer.setLayoutManager(layoutManagerOffer);
        recycler_near.setLayoutManager(layoutManagerNear);

    }

    private void getAllRestaurent(){
        this.disposable = MoozeStreams.getAllRestaurent().subscribeWith(create());

    }


    private DisposableObserver<List<Restaurent>> create(){
        return new DisposableObserver<List<Restaurent>>() {
            @Override
            public void onNext(List<Restaurent> restaurents) {
            updateOffersUI(restaurents);
                Log.e("TAGF", String.valueOf(restaurents));

            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAGMAINFRAGMENTERROR", "Error" + e);


            }

            @Override
            public void onComplete() {




            }
        };
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recycler_near, R.layout.recycler_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        int restaurentid = recyclerViewAdapter.getRestaurents(position).getId();
                        openOrderActivity(restaurentid);


                    }

                });

    }

    private void openOrderActivity(int restaurantid){
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra(restoID,restaurantid);
        startActivity(intent);
    }


    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    private void updateOffersUI(List<Restaurent> restaurent){
        restaurents.addAll(restaurent);
        recyclerViewAdapter.notifyDataSetChanged();

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
