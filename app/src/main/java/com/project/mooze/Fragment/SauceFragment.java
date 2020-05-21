package com.project.mooze.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.mooze.Adapter.RecyclerSauceAdapter;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.Restaurent.Sauce;
import com.project.mooze.R;
import com.project.mooze.Utils.ItemClickSupport;
import com.project.mooze.Utils.MoozeStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class SauceFragment extends Fragment {

    private RecyclerView recycler_sauce;
    private List<Sauce> sauce;
    private RecyclerSauceAdapter recyclerSauceAdapter;
    private Disposable disposable;
    onSaucePass saucePasser;



    public interface onSaucePass{
        void onSaucePass(List<Sauce> sauces);
    }



    public SauceFragment() {
        // Required empty public constructor
    }

    public static SauceFragment newInstance() {
        SauceFragment fragment = new SauceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sauce,container,false);
        recycler_sauce = view.findViewById(R.id.recycler_sauce);
        getAllRestaurent();
        configureRecyclerView();
        passSauces(recyclerSauceAdapter.getSelectedSauces());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void configureRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_sauce.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.divider)));
        recycler_sauce.addItemDecoration(dividerItemDecoration);
        this.sauce = new ArrayList<>();
        this.recyclerSauceAdapter = new RecyclerSauceAdapter(this.sauce, Glide.with(this));
        this.recycler_sauce.setAdapter(this.recyclerSauceAdapter);
        recycler_sauce.setLayoutManager(layoutManager);


    }
    private void getAllRestaurent(){
        this.disposable = MoozeStreams.getMenus(1).subscribeWith(create());
    }


    private DisposableObserver<Menus> create(){
        return new DisposableObserver<Menus>() {
            @Override
            public void onNext(Menus menus) {
                updateOffersUI(menus.getMains().get(0).getSauces());


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





    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    private void updateOffersUI(List<Sauce> sauces){

        sauce.addAll(sauces);
        recyclerSauceAdapter.notifyDataSetChanged();

    }


    public void passSauces(List<Sauce> sauces) {
        saucePasser.onSaucePass(sauces);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        saucePasser= (onSaucePass) context;
    }



}
