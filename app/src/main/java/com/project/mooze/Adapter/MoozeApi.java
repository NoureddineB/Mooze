package com.project.mooze.Adapter;


import com.project.mooze.Model.Order.Order;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.User.User;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoozeApi {

    String url = "http://51.91.120.188:4455/";

    @GET("user/{id}")
    Observable<User> getUser(@Path("id") int id);

    @GET("user")
    Observable<List<User>> getAllUser();

    @GET("restaurant")
    Observable<List<Restaurent>> getAllRestaurent();

    @GET("restaurant/{restoid}")
    Observable<Restaurent> getRestaurent(@Path("restoid") int id);

    @POST("order")
    Observable<Order> postOrder(@Body Order order, @Query("userId") int userId,@Query("restaurantId") int restaurantId);

    @FormUrlEncoded
    @POST("ephemeral_keys")
    Observable<ResponseBody> createEphemeralKey(@FieldMap HashMap<String, String> piVersionMap);



    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

}
