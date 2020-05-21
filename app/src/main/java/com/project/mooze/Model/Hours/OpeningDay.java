package com.project.mooze.Model.Hours;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpeningDay {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("restaurantOpeningDay")
    @Expose
    private RestaurantOpeningDay restaurantOpeningDay;
    @SerializedName("openingHours")
    @Expose
    private List<OpeningHour> openingHours = null;
    @SerializedName("closingHours")
    @Expose
    private List<ClosingHour> closingHours = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDay() {
        return day.substring(0,1).toUpperCase() + day.substring(1).toLowerCase();
    }

    public void setDay(String day) {
        this.day = day;
    }

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

    public RestaurantOpeningDay getRestaurantOpeningDay() {
        return restaurantOpeningDay;
    }

    public void setRestaurantOpeningDay(RestaurantOpeningDay restaurantOpeningDay) {
        this.restaurantOpeningDay = restaurantOpeningDay;
    }

    public List<OpeningHour> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHour> openingHours) {
        this.openingHours = openingHours;
    }

    public List<ClosingHour> getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(List<ClosingHour> closingHours) {
        this.closingHours = closingHours;
    }
}
