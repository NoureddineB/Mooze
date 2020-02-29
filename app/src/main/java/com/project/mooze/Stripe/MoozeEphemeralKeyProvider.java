package com.project.mooze.Stripe;

import androidx.annotation.NonNull;
import androidx.annotation.Size;

import com.project.mooze.Adapter.MoozeApi;
import com.stripe.android.EphemeralKeyUpdateListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MoozeEphemeralKeyProvider implements com.stripe.android.EphemeralKeyProvider {

    private final MoozeApi backendApi = MoozeApi.retrofit.create(MoozeApi.class);
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void createEphemeralKey(@NonNull @Size(min = 4) String apiVersion, @NonNull final EphemeralKeyUpdateListener keyUpdateListener) {
        final HashMap<String, String> apiParamMap = new HashMap<>();
        apiParamMap.put("api_version", apiVersion);

        // Using RxJava2 for handling asynchronous responses
        compositeDisposable.add(backendApi.createEphemeralKey(apiParamMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody response) throws Exception {
                                try {
                                    final String rawKey = response.string();
                                    keyUpdateListener.onKeyUpdate(rawKey);
                                } catch (IOException ignored) {
                                }
                            }
                        }));
    }
}
