package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.project.mooze.Model.User.User;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;
import com.project.mooze.Utils.Utils;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class AccountActivity extends AppCompatActivity {

    private TextView textView_firstname;
    private TextView textView_name;
    private TextView textView_mail;
    private TextView textView_phone;
    private TextView textView_password;
    private SharedPreferences preferences;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        preferences = AccountActivity.this.getSharedPreferences("PREFS",0);
        textView_firstname = findViewById(R.id.text_user_name);
        textView_name = findViewById(R.id.text_user_lastname);
        textView_mail = findViewById(R.id.text_user_mail);
        textView_phone = findViewById(R.id.text_user_phone);
        textView_password = findViewById(R.id.text_user_password);
        if (Utils.isLogged(AccountActivity.this)) {
            getUsers();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void getUsers(){
        this.disposable = MoozeStreams.getUser(preferences.getInt("USERID",0)).subscribeWith(create());

    }


    private DisposableObserver<User> create(){
        return new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                textView_firstname.setText(user.getName());
                textView_name.setText(user.getLastname());
                textView_mail.setText(user.getName());
                textView_phone.setText(user.getTelephone());
                textView_password.setText(user.getPassword());
            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAGUSER", "Error" + e);


            }

            @Override
            public void onComplete() {




            }
        };
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }



}
