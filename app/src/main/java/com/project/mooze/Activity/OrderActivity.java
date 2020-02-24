package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.mooze.Fragment.MainFragment;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;



public class OrderActivity extends AppCompatActivity {

    private Disposable disposable;
    private int restaurentid;
    private TextView restaurant_name;
    private TextView restaurant_adress;
    private TextView restaurant_current_state;
    public static final String restoID2 = "ID2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        restaurant_name = findViewById(R.id.text_name);
        restaurant_adress = findViewById(R.id.text_adress);
        restaurant_current_state = findViewById(R.id.text_current_state);
        restaurentid = getIntent().getIntExtra(MainFragment.restoID,0);
        Log.e("TAGERrestoid", String.valueOf(restaurentid));
        getRestaurent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void getRestaurent(){
        this.disposable = MoozeStreams.getRestaurent(restaurentid).subscribeWith(create());

    }


    private DisposableObserver<Restaurent> create(){
        return new DisposableObserver<Restaurent>() {
            @Override
            public void onNext(Restaurent restaurents) {
                updateUI(restaurents);


            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAGERRORYEA", "Error" + e);


            }



            @Override
            public void onComplete() {




            }
        };
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    public void openRestaurantActivity(View view){
        Intent intent = new Intent(OrderActivity.this,RestaurantActivity.class);
        intent.putExtra(restoID2,restaurentid);
        startActivity(intent);
    }

    private void updateUI(Restaurent restaurent){
        restaurant_name.setText(restaurent.getName());
        restaurant_adress.setText(restaurent.getAddress());
    }

}
