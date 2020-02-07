package com.project.mooze.Utils;



import com.project.mooze.Adapter.MoozeApi;
import com.project.mooze.Model.User;

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
}
