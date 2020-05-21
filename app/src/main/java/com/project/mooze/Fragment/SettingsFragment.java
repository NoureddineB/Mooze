package com.project.mooze.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mooze.Activity.AccountActivity;
import com.project.mooze.Activity.HistoryActivity;
import com.project.mooze.Model.User.User;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;
import com.project.mooze.Utils.Utils;

import org.w3c.dom.Text;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView text_greetings;
    private TextView text_logout;
    private TextView text_login;
    private String user_name;
    private SharedPreferences preferences;
    private Disposable disposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);
        preferences = getActivity().getSharedPreferences("PREFS",0);
        text_greetings = view.findViewById(R.id.text_greetings);
        text_logout = view.findViewById(R.id.logout_text);
        text_login = view.findViewById(R.id.login_text);
        if (Utils.isLogged(getActivity())){
            getUsers();
            text_logout.setVisibility(View.VISIBLE);
            text_login.setVisibility(View.GONE);
        }else {
            text_logout.setVisibility(View.GONE);
            text_login.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void getUsers(){
        this.disposable = MoozeStreams.getUser(preferences.getInt("USERID",0)).subscribeWith(create());

    }


    public void startActivity(View view){

    }

    public void logout(View view){

    }
    public void login(View view){

    }

    private DisposableObserver<User> create(){
        return new DisposableObserver<User>() {
            @Override
            public void onNext(User user) {
                if (Utils.isLogged(getActivity())){
                    text_greetings.setText("Bonjour, " + user.getName());
                }else {
                    text_greetings.setText("Connectez-vous");

                }
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
