package com.project.mooze.Model.Stripe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMethodOptions {

    @SerializedName("card")
    @Expose
    private CardB card;

    public CardB getCard() {
        return card;
    }

    public void setCard(CardB card) {
        this.card = card;
    }

}
