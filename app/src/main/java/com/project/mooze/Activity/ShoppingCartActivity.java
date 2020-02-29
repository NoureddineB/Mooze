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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.project.mooze.Adapter.ShoppingCartAdapter;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;
import com.project.mooze.Stripe.MoozeEphemeralKeyProvider;
import com.project.mooze.Stripe.MoozePaymentSession;

import com.project.mooze.Stripe.MoozePaymentSessionListener;
import com.project.mooze.Utils.Utils;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.CustomerSession;
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

import java.lang.ref.WeakReference;
import java.util.Objects;

public class ShoppingCartActivity extends AppCompatActivity  {

    private RecyclerView recycler_cartItem;

    private PaymentSession paymentSession;
    private Button payButton;
    private ShoppingCartAdapter shoppingCartAdapter;
    private Stripe stripe;
    private CardInputWidget cardInputWidget;
    private static final String RETURN_URL = "...";

    private String paymentIntentClientSecret;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        recycler_cartItem = findViewById(R.id.shopping_cart_recyclerView);
        payButton = findViewById(R.id.pay_button);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        stripe = new Stripe(getApplicationContext(), Objects.requireNonNull(Utils.stripe_key));
        configureRecyclerView();
        paymentSession = new PaymentSession(this, MoozePaymentSession.createPaymentSessionConfig());

    }



    private void startCheckout() {
        // ...

        // Hook up the pay button to the card widget and stripe instance
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                    final Context context = ShoppingCartActivity.this.getApplicationContext();
                    stripe = new Stripe(
                            context,
                            PaymentConfiguration.getInstance(context).getPublishableKey()
                    );
                    stripe.confirmPayment(ShoppingCartActivity.this, confirmParams);
                }
            }
        });
    }



    private void configureRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShoppingCartActivity.this);
        this.shoppingCartAdapter = new ShoppingCartAdapter(ShoppingCart.getCart(ShoppingCartActivity.this));
        this.recycler_cartItem.setAdapter(this.shoppingCartAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_cartItem.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(ShoppingCartActivity.this, R.drawable.divider_cart_item)));
        recycler_cartItem.addItemDecoration(dividerItemDecoration);
        recycler_cartItem.setLayoutManager(layoutManager);

    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
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

    // ...

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<ShoppingCartActivity> activityRef;

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
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent),
                        true
                );
            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).getMessage(),
                        false
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






    public void clearCart(View v){
    ShoppingCart.clearCart(ShoppingCart.getCart(this),this);
    }

}
