package com.project.mooze.Model.Restaurent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StarterRestaurantItem {
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("starterId")
    @Expose
    private Integer starterId;
    @SerializedName("restaurantId")
    @Expose
    private Integer restaurantId;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStarterId() {
        return starterId;
    }

    public void setStarterId(Integer starterId) {
        this.starterId = starterId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
