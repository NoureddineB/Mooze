package com.project.mooze.Stripe;

import androidx.annotation.NonNull;

import com.stripe.android.PaymentSessionConfig;

import com.stripe.android.model.PaymentMethod;


import static java.util.Arrays.asList;

public class MoozePaymentSession {
    @NonNull
    public static PaymentSessionConfig createPaymentSessionConfig() {
        return new PaymentSessionConfig.Builder()
                // collect shipping information
                .setShippingInfoRequired(false)
                // collect shipping method
                .setShippingMethodsRequired(false)
                // specify the payment method types that the customer can use;
                // defaults to PaymentMethod.Type.Card
                .setPaymentMethodTypes(
                        asList(PaymentMethod.Type.Card)
                )
                // specify a layout to display under the payment collection form
                //.setAddPaymentMethodFooter(R.layout.add_payment_method_footer)
                .setShouldShowGooglePay(true)
                .build();


    }


}
