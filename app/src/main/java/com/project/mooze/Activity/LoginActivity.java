package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mooze.Model.User.User;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Disposable disposable;
    private EditText edit_password;
    private EditText edit_mail;
    private Button loginButton;
    private Button registerButton;
    private User loginUser;
    private TextView change_password;
    private SharedPreferences preferences;
    String user_mail;
    String user_password;
    public int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = LoginActivity.this.getSharedPreferences("PREFS", 0);
        edit_mail = findViewById(R.id.editText_mail);
        edit_password = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.button_login);
        registerButton = findViewById(R.id.button_new_account);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void login() {
        user_mail = String.valueOf(edit_mail.getText());
        user_password = String.valueOf(edit_password.getText());
        loginUser = new User(user_mail,user_password);
        this.disposable = MoozeStreams.loginUser(loginUser).subscribeWith(create());
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private DisposableObserver<User> create() {
        return new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                userid = user.getId();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("USERID", userid);
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                Log.e("TAGLOGINSUCCES", String.valueOf(user.getId()));


            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(LoginActivity.this,"Connexion impossible",Toast.LENGTH_SHORT).show();
                Log.e("TAGLOGINFAIL", "Error" + e);


            }

            @Override
            public void onComplete() {


            }
        };
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button_login:
                login();
                break;
            case R.id.button_new_account:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.text_new_password:
                break;
        }

    }


}
