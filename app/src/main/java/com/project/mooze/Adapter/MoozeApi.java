package com.project.mooze.Adapter;


import com.project.mooze.Model.History.OrderHistory;
import com.project.mooze.Model.Hours.Hours;
import com.project.mooze.Model.Order.Order;
import com.project.mooze.Model.Order.RestaurantSuggestion;
import com.project.mooze.Model.Order.Suggestion;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.Stripe.PaymentIntents;
import com.project.mooze.Model.User.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoozeApi {

    String url = "http://51.91.120.188:4466/";


    @GET("user/{id}")
    Observable<User> getUser(@Path("id") int id);

    @GET("user/{id}/orders")
    Observable<OrderHistory> getUserHistory(@Path("id") int id);

    @GET("user")
    Observable<List<User>> getAllUser();


    @GET("restaurant/{id}/suggestions")
    Observable<RestaurantSuggestion> getSuggestion(@Path("id") int id);

    @GET("restaurant/{id}/hours")
    Observable<Hours> getHours(@Path("id") int id);

    @POST("user")
    Observable<User> postUser(@Body User user);


    @POST("user/login")
    Observable<User> loginUser(@Body User user);

    @GET("restaurant")
    Observable<List<Restaurent>> getAllRestaurent();

    @GET("restaurant/{restoid}")
    Observable<Restaurent> getRestaurent(@Path("restoid") int id);

    @GET("menu/{menuid}")
    Observable<Menus> getMenu(@Path("menuid") long id);


    @POST("order")
    Observable<ResponseBody> postOrder(@Body Order order, @Query("userId") int userId,@Query("restaurantId") int restaurantId);

    @GET("stripe/paymentintent")
    Observable<PaymentIntents> getPaymentIntent(@Query("amount") int amount);



    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build();



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();



}
