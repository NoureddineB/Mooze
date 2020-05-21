package com.project.mooze.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.mooze.Adapter.ShoppingCartAdapter;
import com.project.mooze.Fragment.FragmentDialogRegister;
import com.project.mooze.Model.CartItem;
import com.project.mooze.Model.Order.Order;
import com.project.mooze.Model.Order.OrderComment;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Order.Suggestion;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.Model.Stripe.PaymentIntents;
import com.project.mooze.R;

import com.project.mooze.Utils.MoozeStreams;
import com.project.mooze.Utils.Utils;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentAuthConfig;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.model.ShippingInformation;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.PaymentMethodsActivity;


import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ShoppingCartActivity extends AppCompatActivity implements ShoppingCartAdapter.onPriceChange,View.OnClickListener {

    private RecyclerView recycler_cartItem;

    private Button payButton;
    private ShoppingCartAdapter shoppingCartAdapter;
    private Stripe stripe;
    private TextView total_price;
    private Disposable disposable;
    private Disposable order_disposable;
    private CardInputWidget cardInputWidget;
    private Button button_order_message;
    private double totalPrice;
    private SharedPreferences preferences;
    private int finalPrice;
    private int restaurantid;
    private int userid;
    private boolean takeaway;
    private String orderComments = "";
    private int countdown;
    private double price;
    private List<CartItem> shoppingCart;
    private List<Menus> menus = new ArrayList<>();
    private List<Starter> starters = new ArrayList<>();
    private List<Suggestion> suggestions = new ArrayList<>();
    private List<Main> mains = new ArrayList<>();
    private List<Dessert> desserts = new ArrayList<>();
    private List<Drink> drinks = new ArrayList<>();

    private Order order;



    private String paymentIntentClientSecret;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        recycler_cartItem = findViewById(R.id.shopping_cart_recyclerView);
        total_price = findViewById(R.id.text2);
        payButton = findViewById(R.id.pay_button);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        button_order_message = findViewById(R.id.button_order_message);
        stripe = new Stripe(getApplicationContext(), Objects.requireNonNull(Utils.stripe_key));
        total_price.setText(String.format("%.2f",getTotalPrice()) + " €");
        preferences = getSharedPreferences("PREFS",0);
        userid = preferences.getInt("USERID",0);
        restaurantid = preferences.getInt("RESTOID",0);
        countdown = preferences.getInt("COUNTDOWN",0);
        takeaway = preferences.getBoolean("TAKEAWAY",true);
        shoppingCart = ShoppingCart.getCart(this);
        button_order_message.setOnClickListener(this);
        makeOrder();
        configureRecyclerView();
        startCheckout();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
        this.disposeOrderWhenDestroy();

    }

    private void makeOrder(){
        OrderComment orderComment = new OrderComment(orderComments);
        for (CartItem cartItem : shoppingCart){
            if (cartItem.menus != null) {
                menus.add(cartItem.menus);
            }
            if (cartItem.starter != null) {
                starters.add(cartItem.starter);
            }
            if (cartItem.main != null) {
                mains.add(cartItem.main);
            }
            if (cartItem.dessert != null) {
                desserts.add(cartItem.dessert);
            }
            if (cartItem.drink != null) {
                drinks.add(cartItem.drink);
            }
            if (cartItem.suggestion != null) {
                suggestions.add(cartItem.suggestion);
            }
            price = price + cartItem.price;
        }

        order = new Order(userid,countdown,takeaway,starters,drinks,mains,desserts,suggestions,menus,price,orderComment);

    }

    @Override
    public void onPriceChange(double price,Order order) {
        total_price.setText(String.format("%.2f",price) + " €");
        this.order = order;

    }

    private void postOrder(){
        this.order_disposable = MoozeStreams.postOrder(order,userid,restaurantid).subscribeWith(createOrder());
    }

    private DisposableObserver<ResponseBody> createOrder(){
        return new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {



            }
            @Override
            public void onError(Throwable e) {



            }

            @Override
            public void onComplete() {
                Log.e("TAGMAINFRAGMENTERROR", "Error");



            }
        };
    }



    private void getPaymentIntents(){
        String str ="";
        String s = String.valueOf(totalPrice);
        str = s.replace(".", "");
        if (str.length() < 4){
           str = str + "0";
           finalPrice = Integer.parseInt(str);
        }
        if (str.length() > 4){
           str = str.substring(0,4);
           finalPrice = Integer.parseInt(str);
        }
        finalPrice = Integer.parseInt(str);
        this.disposable = MoozeStreams.getPaymentIntent(finalPrice).subscribeWith(create());

    }

    private DisposableObserver<PaymentIntents> create(){
        return new DisposableObserver<PaymentIntents>() {
            @Override
            public void onNext(PaymentIntents paymentIntents) {
                paymentIntentClientSecret = paymentIntents.getClientSecret();
                Log.e("TAGPAYMENTIENTENT2", String.valueOf(paymentIntents.getAmount()));


            }
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e("TAGMAINFRAGMENTERROR", "Error");



            }
        };
    }




    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
    private void disposeOrderWhenDestroy(){
        if (this.order_disposable != null && !this.order_disposable.isDisposed()) this.order_disposable.dispose();
    }
private void securePhoneNumber(){
    final PaymentAuthConfig.Stripe3ds2UiCustomization uiCustomization =
            new PaymentAuthConfig.Stripe3ds2UiCustomization.Builder()
                    .setLabelCustomization(
                            new PaymentAuthConfig.Stripe3ds2LabelCustomization.Builder()
                                    .setTextFontSize(12)
                                    .build())
                    .build();
PaymentAuthConfig.init(new PaymentAuthConfig.Builder()
        .set3ds2Config(new PaymentAuthConfig.Stripe3ds2Config.Builder()
                .setTimeout(5)
                .setUiCustomization(uiCustomization)
                .build())
            .build());}


    private void startCheckout() {
        // Create a PaymentIntents by calling the sample server's /create-payment-intent endpoint.
        getPaymentIntents();
        // Hook up the pay button to the card widget and stripe instance
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utils.isLogged(ShoppingCartActivity.this)){
                    showRegisterDialog();
                }else {
                    if (price < 5.00 ){
                        buildAlertMessage();
                    }else {
                        cardInputWidget = ShoppingCartActivity.this.findViewById(R.id.cardInputWidget);
                        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                        if (params != null) {
                            ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                                    .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                            securePhoneNumber();
                            stripe.confirmPayment(ShoppingCartActivity.this, confirmParams);
                        }
                    }
            }
        }});

    }


    private void configureRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShoppingCartActivity.this);
        this.shoppingCartAdapter = new ShoppingCartAdapter(ShoppingCart.getCart(ShoppingCartActivity.this),ShoppingCartActivity.this);
        this.recycler_cartItem.setAdapter(this.shoppingCartAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_cartItem.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(ShoppingCartActivity.this, R.drawable.divider_cart_item)));
        recycler_cartItem.addItemDecoration(dividerItemDecoration);
        recycler_cartItem.setLayoutManager(layoutManager);

    }

    private void displayAlert(@NonNull String title, @Nullable String message, boolean restartDemo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Restart demo",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int index) {
                            CardInputWidget cardInputWidget = ShoppingCartActivity.this.findViewById(R.id.cardInputWidget);
                            cardInputWidget.clear();
                            makeOrder();
                            ShoppingCartActivity.this.startCheckout();

                        }
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }




    private static final class PaymentResultCallback implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<ShoppingCartActivity> activityRef;
        PaymentResultCallback(@NonNull ShoppingCartActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final ShoppingCartActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                activity.postOrder();
                activity.clearCart();
                activity.shoppingCartAdapter.notifyDataSetChanged();
                Intent intent = new Intent(activity,ConfirmOrderActivity.class);
                activity.startActivity(intent);

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed
                activity.displayAlert("Payment failed", Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(), false
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final ShoppingCartActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString(), false);
        }

    }
    private double getTotalPrice(){
        List<CartItem> cartItemList =  ShoppingCart.getCart(ShoppingCartActivity.this);
        for (int i = 0; i < cartItemList.size(); i++) {
            totalPrice += cartItemList.get(i).price;
        }
        return totalPrice;

    }



    public void clearCart() {
        ShoppingCart.clearCart(ShoppingCart.getCart(this), this);
        ShoppingCart.getCart(ShoppingCartActivity.this).clear();
        this.shoppingCartAdapter.notifyDataSetChanged();
        getTotalPrice();
        makeOrder();
        total_price.setText(0.00 + " €");

    }


    private void showRegisterDialog(){
        FragmentDialogRegister fragmentDialogRegister = FragmentDialogRegister.newInstance();
        fragmentDialogRegister.show(getSupportFragmentManager(),"REGISTER");

    }
    private void buildAlertMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
        builder.setMessage("Commande minimum de 5€")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onClick(View view) {
        new AlertDialog.Builder(view.getContext()).setView(R.layout.dialog_order_comment).setPositiveButton("C'est bon ! ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText CommentInput = ((AlertDialog) dialog).findViewById(R.id.EditText1);
                        orderComments = CommentInput.getText().toString();
                        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Comment", CommentInput.getText().toString());
                        editor.apply();


                    }
                })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();


        }
    }

