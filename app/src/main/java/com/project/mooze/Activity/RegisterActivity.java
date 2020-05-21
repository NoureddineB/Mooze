package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mooze.Model.User.User;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Disposable disposable;
    private EditText edit_firstname;
    private EditText edit_name;
    private EditText edit_phone;
    private EditText edit_mail;
    private EditText edit_password;
    private EditText edit_confirm_password;
    private User registerUser;

    private Button registerButton;
    private CheckBox checkBox;
    private SharedPreferences preferences;

    String user_mail;
    String user_password;
    String user_confirm_password;
    String user_name;
    String user_firstname;
    String user_phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preferences = RegisterActivity.this.getSharedPreferences("PREFS",0);
        edit_mail = findViewById(R.id.edit_add_mail);
        edit_password = findViewById(R.id.edit_add_password);
        edit_name = findViewById(R.id.edit_add_name);
        edit_firstname = findViewById(R.id.editText_add_firstname);
        edit_phone = findViewById(R.id.edit_add_phone);
        edit_confirm_password = findViewById(R.id.edit_confirm_password);
        registerButton = findViewById(R.id.button_register);
        checkBox = findViewById(R.id.chkAndroid);
        registerButton.setOnClickListener(this);
        if (preferences.getString("Phone",null) != null){
            edit_phone.setText(preferences.getString("Phone",null));
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void register() {
        user_mail = String.valueOf(edit_mail.getText());
        user_password = String.valueOf(edit_password.getText());
        user_name = String.valueOf(edit_name.getText());
        user_firstname = String.valueOf(edit_firstname.getText());
        user_phone = String.valueOf(edit_phone.getText());
        user_confirm_password = String.valueOf(edit_confirm_password.getText());
        if (checkPassword()) {
            registerUser = new User(user_name, user_firstname, user_mail, user_password, true, user_phone);
            this.disposable = MoozeStreams.registerUser(registerUser).subscribeWith(create());
        }else {
            Toast.makeText(RegisterActivity.this,"Les mots de passe sont differents",Toast.LENGTH_SHORT).show();
        }
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private DisposableObserver<User> create() {
        return new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                Log.e("TAGLOGINSUCCES", String.valueOf(user.getId()));


            }

            @Override
            public void onError(Throwable e) {
                HttpException error = (HttpException)e;
                String errorBody = null;
                try {
                    errorBody = error.response().errorBody().string();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Toast.makeText(RegisterActivity.this,"Connexion impossible",Toast.LENGTH_SHORT).show();
                Log.e("TAGLOGINFAIL", "Error" + e);
                Log.e("TAGLOGINFAIL2",errorBody );


            }

            @Override
            public void onComplete() {


            }
        };
    }
    private boolean checkPassword(){
        return user_password.equals(user_confirm_password);
    }

    @Override
    public void onClick(View v){
        if (checkBox.isChecked()) {
            register();
        }else{
            Toast.makeText(RegisterActivity.this,"Veuillez accepter les conditions d'utilisations",Toast.LENGTH_SHORT).show();
        }
    }

    public void startLog(View view){
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
    }


}
