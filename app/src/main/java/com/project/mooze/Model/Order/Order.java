package com.project.mooze.Model.Order;

import android.view.Menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Restaurent;

import java.util.List;

public class Order {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("countDown")
    @Expose
    private Integer countDown;
    @SerializedName("checkedOut")
    @Expose
    private Boolean checkedOut;
    @SerializedName("takeAway")
    @Expose
    private Boolean takeAway;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("orderOwnerId")
    @Expose
    private Integer orderOwnerId;
    @SerializedName("restaurantId")
    @Expose
    private Integer restaurantId;
    @SerializedName("orderOwner")
    @Expose
    private OrderOwner orderOwner;
    @SerializedName("restaurant")
    @Expose
    private Restaurent restaurant;
    @SerializedName("starters")
    @Expose
    private List<Starter> starters = null;
    @SerializedName("mains")
    @Expose
    private List<Main> mains = null;
    @SerializedName("drinks")
    @Expose
    private List<Drink> drinks = null;
    @SerializedName("desserts")
    @Expose
    private List<Dessert> desserts = null;
    @SerializedName("menus")
    @Expose
    private List<Menu> menus = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCountDown() {
        return countDown;
    }

    public void setCountDown(Integer countDown) {
        this.countDown = countDown;
    }

    public Boolean getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(Boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public Boolean getTakeAway() {
        return takeAway;
    }

    public void setTakeAway(Boolean takeAway) {
        this.takeAway = takeAway;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderOwnerId() {
        return orderOwnerId;
    }

    public void setOrderOwnerId(Integer orderOwnerId) {
        this.orderOwnerId = orderOwnerId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public OrderOwner getOrderOwner() {
        return orderOwner;
    }

    public void setOrderOwner(OrderOwner orderOwner) {
        this.orderOwner = orderOwner;
    }

    public Restaurent getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurent restaurant) {
        this.restaurant = restaurant;
    }

    public List<Starter> getStarters() {
        return starters;
    }

    public void setStarters(List<Starter> starters) {
        this.starters = starters;
    }

    public List<Main> getMains() {
        return mains;
    }

    public void setMains(List<Main> mains) {
        this.mains = mains;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public List<Dessert> getDesserts() {
        return desserts;
    }

    public void setDesserts(List<Dessert> desserts) {
        this.desserts = desserts;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

}