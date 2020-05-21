package com.project.mooze.Utils;



import com.project.mooze.Adapter.MoozeApi;
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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MoozeStreams {



    public static Observable<List<User>> getAllUser() {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getAllUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<User> getUser(int userid) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getUser(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<OrderHistory> getUserOrder(int userid) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getUserHistory(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<List<Restaurent>> getAllRestaurent() {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getAllRestaurent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Restaurent> getRestaurent(int restoid) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getRestaurent(restoid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Hours> getHours(int restoid) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getHours(restoid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<ResponseBody> postOrder(Order order, int userid, int restoid) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.postOrder(order,userid,restoid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public static Observable<User> registerUser(User user) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.postUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<User> loginUser(User user) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.loginUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<Menus> getMenus(long menuid) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getMenu(menuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<PaymentIntents> getPaymentIntent(int amount) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getPaymentIntent(amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<RestaurantSuggestion> getSuggestion(int restaurantid) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getSuggestion(restaurantid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
