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
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Adapter.RecyclerDrinkAdapter;
import com.project.mooze.Adapter.RecyclerSauceAdapter;
import com.project.mooze.Model.Restaurent.Drink;
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

public class DrinkFragment extends Fragment {

    private RecyclerView recycler_drink;
    private List<Drink> drinks;
    private RecyclerDrinkAdapter recyclerDrinkAdapter;
    private Disposable disposable;
    public ImageView image_selected_drink;
    private RequestManager glide;


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
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        recycler_drink = view.findViewById(R.id.recycler_drink);
        image_selected_drink = view.findViewById(R.id.selected_drink);
        glide = Glide.with(this);
        configureRecyclerView();
        getAllRestaurent();
        configureOnClickRecyclerView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void configureRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_drink.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.divider_drinks)));
        recycler_drink.addItemDecoration(dividerItemDecoration);
        this.drinks = new ArrayList<>();
        this.recyclerDrinkAdapter = new RecyclerDrinkAdapter(this.drinks, Glide.with(this));
        this.recycler_drink.setAdapter(this.recyclerDrinkAdapter);
        recycler_drink.setLayoutManager(layoutManager);


    }

    private void getAllRestaurent() {
        this.disposable = MoozeStreams.getAllRestaurent().subscribeWith(create());

    }


    private DisposableObserver<List<Restaurent>> create() {
        return new DisposableObserver<List<Restaurent>>() {
            @Override
            public void onNext(List<Restaurent> restaurents) {
                updateOffersUI(restaurents.get(0).getDrinks());
                Log.e("TAGF", String.valueOf(restaurents.get(0).getMenus().get(0).getMains().get(0).getSauces()));
                Log.e("TAGF", String.valueOf(restaurents.get(0).getMenus().get(0).getMains().get(0).getSauces().get(0).getName()));

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
        ItemClickSupport.addTo(recycler_drink, R.layout.recycler_drink_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Drink drink = recyclerDrinkAdapter.getDrinks(position);
                        Log.e("TAGDRINKSSS",drink.getName());
                        if (drink.getName() == "Coca Cola") {
                            glide.load(R.drawable.coca).apply(RequestOptions.centerCropTransform()).into(image_selected_drink);
                        } else {
                            glide.load(drink.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_selected_drink);
                        }

                    }

                });

    }


    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    private void updateOffersUI(List<Drink> drink) {
        drinks.addAll(drink);
        recyclerDrinkAdapter.notifyDataSetChanged();

    }


}
