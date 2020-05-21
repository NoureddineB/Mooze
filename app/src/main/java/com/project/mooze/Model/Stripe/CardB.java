package com.project.mooze.Model.Stripe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardB {

    @SerializedName("installments")
    @Expose
    private Object installments;
    @SerializedName("request_three_d_secure")
    @Expose
    private String requestThreeDSecure;

    public Object getInstallments() {
        return installments;
    }

    public void setInstallments(Object installments) {
        this.installments = installments;
    }

    public String getRequestThreeDSecure() {
        return requestThreeDSecure;
    }

    public void setRequestThreeDSecure(String requestThreeDSecure) {
        this.requestThreeDSecure = requestThreeDSecure;
}
}
