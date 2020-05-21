package com.project.mooze.Model.Hours;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantOpeningDay {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("restaurantId")
    @Expose
    private Integer restaurantId;
    @SerializedName("openingDayId")
    @Expose
    private Integer openingDayId;

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

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getOpeningDayId() {
        return openingDayId;
    }

    public void setOpeningDayId(Integer openingDayId) {
        this.openingDayId = openingDayId;
    }
}
