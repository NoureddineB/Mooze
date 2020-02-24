package com.project.mooze.Utils;



import com.project.mooze.Adapter.MoozeApi;
import com.project.mooze.Model.Order.Order;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.User.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MoozeStreams {



    public static Observable<List<User>> getAllUser() {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.getAllUser()
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

    public static Observable<Order> postOrder(Order order,int userid, int restoid) {
        MoozeApi moozeApi = MoozeApi.retrofit.create(MoozeApi.class);
        return moozeApi.postOrder(order,userid,restoid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
