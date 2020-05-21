package com.project.mooze.Fragment;

import android.content.Intent;
import android.location.Location;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.mooze.Activity.HistoryActivity;
import com.project.mooze.Activity.HomeActivity;
import com.project.mooze.Activity.OrderActivity;
import com.project.mooze.Activity.RestaurantActivity;
import com.project.mooze.Activity.ShoppingCartActivity;
import com.project.mooze.Adapter.RecyclerViewAdapter;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.Model.User.User;
import com.project.mooze.R;
import com.project.mooze.Utils.ItemClickSupport;
import com.project.mooze.Utils.MoozeStreams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.Disposable;

import io.reactivex.observers.DisposableObserver;


public class MainFragment extends Fragment implements MapFragment.onLocationPass {

    private RecyclerView recycler_offer;
    private RecyclerView recycler_near;
    private List<Restaurent> restaurents;
    private List<Restaurent> nearRestaurents;
    private Disposable disposable;
    private TextView cart_size;
    private Location userLocation = new Location("");
    private EditText search_edittext;
    private ImageView shopping_cart;

    public static final String restoID = "RESTOID";
    private float distance;


    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerViewAdapter recyclerNearAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        recycler_near = v.findViewById(R.id.recycler_near);
        recycler_offer = v.findViewById(R.id.recycler_offers);
        search_edittext = v.findViewById(R.id.search_bar);
        cart_size = v.findViewById(R.id.cart_size_fragment);
        shopping_cart = v.findViewById(R.id.fragment_shopping_cart);
        updateCartSize();
        search_edittext.setHint((Html.fromHtml("<font color = #D3D3D3>" + "Recherchez un restaurant" + "</font>")));
        getAllRestaurent();
        configureRecyclerView();
        search();
        this.configureOnClickRecyclerView();
        return v;


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }



    private void updateCartSize(){
        if (ShoppingCart.getShoppingCartSize(getActivity()) != 0){
            cart_size.setVisibility(View.VISIBLE);
            cart_size.setText(String.valueOf(ShoppingCart.getShoppingCartSize(getActivity())));

        }
        if (ShoppingCart.getShoppingCartSize(getActivity()) == 0){
            cart_size.setVisibility(View.INVISIBLE);

        }
    }


    public void startCartActivity(View v){
    }



    private void search(){
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

    }

    private void filter(String text) {
       ArrayList<Restaurent> filteredList = new ArrayList<>();
       for (Restaurent restaurent : restaurents){
           if (restaurent.getName().toLowerCase().contains(text.toLowerCase())){
               filteredList.add(restaurent);
           }
       }

       recyclerViewAdapter.filterList(filteredList);
    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManagerOffer = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManagerNear = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        this.restaurents = new ArrayList<>();
        this.nearRestaurents = new ArrayList<>();
        this.recyclerViewAdapter = new RecyclerViewAdapter(userLocation,this.restaurents, Glide.with(this));
        this.recyclerNearAdapter = new RecyclerViewAdapter(userLocation,this.nearRestaurents, Glide.with(this));
        this.recycler_offer.setAdapter(this.recyclerViewAdapter);
        this.recycler_near.setAdapter(this.recyclerNearAdapter);
        recycler_offer.setLayoutManager(layoutManagerOffer);
        recycler_near.setLayoutManager(layoutManagerNear);


    }

    private void getAllRestaurent() {
        this.disposable = MoozeStreams.getAllRestaurent().subscribeWith(create());

    }


    private DisposableObserver<List<Restaurent>> create() {
        return new DisposableObserver<List<Restaurent>>() {
            @Override
            public void onNext(List<Restaurent> restaurents) {
                updateOffersUI(restaurents);


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

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recycler_near, R.layout.recycler_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        int restaurentid = nearRestaurents.get(position).getId();
                        openOrderActivity(restaurentid);


                    }

                });
        ItemClickSupport.addTo(recycler_offer, R.layout.recycler_main_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        int restaurentid = recyclerViewAdapter.getRestaurents(position).getId();
                        openOrderActivity(restaurentid);


                    }

                });

    }


    private void openOrderActivity(int restaurantid) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra(restoID, restaurantid);
        startActivity(intent);
    }



    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    private void updateOffersUI(List<Restaurent> restaurent) {
        restaurents.addAll(restaurent);
        nearRestaurents.addAll(restaurent);
        Collections.shuffle(nearRestaurents);
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


    @Override
    public void onLocationPass(Location location) {
        userLocation = location;
    }
}
