package com.project.mooze.Model.Order;

import android.view.Menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.Restaurent;

import java.util.List;

public class Order {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("countDown")
    @Expose
    private Integer countDown;
    @SerializedName("checkedOut")
    @Expose
    private Boolean checkedOut;
    @SerializedName("takeAway")
    @Expose
    private Boolean takeAway;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("archived")
    @Expose
    private Boolean archived;
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
    @SerializedName("starters")
    @Expose
    private List<Starter> starters = null;
    @SerializedName("drinks")
    @Expose
    private List<Drink> drinks = null;
    @SerializedName("mains")
    @Expose
    private List<Main> mains = null;
    @SerializedName("desserts")
    @Expose
    private List<Dessert> desserts = null;
    @SerializedName("suggestions")
    @Expose
    private List<Suggestion> suggestions = null;
    @SerializedName("menus")
    @Expose
    private List<Menus> menus = null;

    @SerializedName("orderComment")
    @Expose
    private OrderComment orderComment;

    public Order(Integer userId,Integer countDown, Boolean takeAway, List<Starter> starters, List<Drink> drinks, List<Main> mains, List<Dessert> desserts, List<Suggestion> suggestions, List<Menus> menus,double price,OrderComment orderComment) {
        this.countDown = countDown;
        this.takeAway = takeAway;
        this.starters = starters;
        this.drinks = drinks;
        this.mains = mains;
        this.desserts = desserts;
        this.suggestions = suggestions;
        this.menus = menus;
        this.price = price;
        this.userId = userId;
        this.orderComment = orderComment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
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

    public List<Starter> getStarters() {
        return starters;
    }

    public void setStarters(List<Starter> starters) {
        this.starters = starters;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public List<Main> getMains() {
        return mains;
    }

    public void setMains(List<Main> mains) {
        this.mains = mains;
    }

    public List<Dessert> getDesserts() {
        return desserts;
    }

    public void setDesserts(List<Dessert> desserts) {
        this.desserts = desserts;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public List<Menus> getMenus() {
        return menus;
    }

    public void setMenus(List<Menus> menus) {
        this.menus = menus;
    }

    public OrderComment getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(OrderComment orderComment) {
        this.orderComment = orderComment;
    }

}