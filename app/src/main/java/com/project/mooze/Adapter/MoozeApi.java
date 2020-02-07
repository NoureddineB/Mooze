package com.project.mooze.Adapter;


import com.project.mooze.Model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoozeApi {

    String url = "http://51.91.120.188:4455/";

    @GET("user/{id}")
    Observable<User> getUser(@Path("id") int id);

    @GET("user")
    Observable<List<User>> getAllUser();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

}
