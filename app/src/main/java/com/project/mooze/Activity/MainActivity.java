package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;


import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.project.mooze.R;


import com.project.mooze.Utils.Utils;

import com.stripe.android.PaymentConfiguration;


public class MainActivity extends AppCompatActivity {

    private Button button_confirm;
    private EditText editText_phone;
    private SharedPreferences preferences;
    String phone_number;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_phone = findViewById(R.id.editText_num);
        button_confirm = findViewById(R.id.button_valider_phone);
        startHome();
        preferences = MainActivity.this.getSharedPreferences("PREFS", 0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        PaymentConfiguration.init(
                getApplicationContext(),
                Utils.stripe_key
        );

    }
    private boolean checkPhone(){
        String test = phone_number.substring(0,2);
        return test.equals("06") || test.equals("07");
    }

    public void startLogin(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    public void startHome() {
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone_number = editText_phone.getText().toString();
                if (phone_number.length() == 10 && checkPhone()) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Phone", editText_phone.getText().toString());
                    editor.apply();
                    if (preferences.getString("Phone",null) != null){
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Numéro de téléphone non valide",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
