package com.project.mooze.Model.Stripe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferData {

    @SerializedName("destination")
    @Expose
    private String destination;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
