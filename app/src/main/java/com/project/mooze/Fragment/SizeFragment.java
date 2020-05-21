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
import android.widget.ListView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Adapter.RecyclerDrinkAdapter;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.Restaurent.Size;
import com.project.mooze.R;
import com.project.mooze.Utils.ItemClickSupport;
import com.project.mooze.Utils.MoozeStreams;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class SizeFragment extends Fragment {
    public int size;
    onSizePass sizePasser;


    public interface onSizePass{
        void onSizePass(int size);
    }

    public SizeFragment() {
        // Required empty public constructor
    }

    public static SizeFragment newInstance() {
        SizeFragment fragment = new SizeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ImageView menu_XL;
    private ImageView menu_big;
    private ImageView menu_normal;
    private ImageView image_check1;
    private ImageView image_check2;
    private ImageView image_check3;
    private Disposable disposable;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_size, container, false);
        menu_XL = view.findViewById(R.id.menu_XL);
        menu_big = view.findViewById(R.id.menu_big);
        menu_normal = view.findViewById(R.id.menu_normal);
        image_check1 = view.findViewById(R.id.image_check1);
        image_check2 = view.findViewById(R.id.image_check2);
        image_check3 = view.findViewById(R.id.image_check3);
        configureClick();

        return view;


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }


    private void getAllRestaurent() {
        this.disposable = MoozeStreams.getRestaurent(1).subscribeWith(create());

    }

    private DisposableObserver<Restaurent> create() {
        return new DisposableObserver<Restaurent>() {
            @Override
            public void onNext(Restaurent restaurents) {
                restaurents.getMenus().get(0).getSizes().get(0);




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




    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    private void configureClick(){
        menu_XL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = 3;
                passSize(size);
                image_check3.setVisibility(View.VISIBLE);
                image_check2.setVisibility(View.INVISIBLE);
                image_check1.setVisibility(View.INVISIBLE);
            }
        });
        menu_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = 2;
                passSize(size);
                image_check2.setVisibility(View.VISIBLE);
                image_check3.setVisibility(View.INVISIBLE);
                image_check1.setVisibility(View.INVISIBLE);
            }
        });
        menu_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size = 1;
                passSize(size);
                image_check1.setVisibility(View.VISIBLE);
                image_check2.setVisibility(View.INVISIBLE);
                image_check3.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void passSize(int size) {
        sizePasser.onSizePass(size);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        sizePasser= (onSizePass) context;
    }



}
