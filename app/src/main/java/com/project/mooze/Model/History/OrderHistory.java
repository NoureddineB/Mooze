package com.project.mooze.Model.History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistory {

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
    @SerializedName("orderStarters")
    @Expose
    private List<OrderStarter> orderStarters = null;
    @SerializedName("orderDrinks")
    @Expose
    private List<OrderDrink> orderDrinks = null;
    @SerializedName("orderMains")
    @Expose
    private List<OrderMain> orderMains = null;
    @SerializedName("orderDesserts")
    @Expose
    private List<OrderDessert> orderDesserts = null;
    @SerializedName("orderSuggestions")
    @Expose
    private List<OrderSuggestion> orderSuggestions = null;
    @SerializedName("orderMenus")
    @Expose
    private List<OrderMenu> orderMenus = null;

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

    public List<OrderStarter> getOrderStarters() {
        return orderStarters;
    }

    public void setOrderStarters(List<OrderStarter> orderStarters) {
        this.orderStarters = orderStarters;
    }

    public List<OrderDrink> getOrderDrinks() {
        return orderDrinks;
    }

    public void setOrderDrinks(List<OrderDrink> orderDrinks) {
        this.orderDrinks = orderDrinks;
    }

    public List<OrderMain> getOrderMains() {
        return orderMains;
    }

    public void setOrderMains(List<OrderMain> orderMains) {
        this.orderMains = orderMains;
    }

    public List<OrderDessert> getOrderDesserts() {
        return orderDesserts;
    }

    public void setOrderDesserts(List<OrderDessert> orderDesserts) {
        this.orderDesserts = orderDesserts;
    }

    public List<OrderSuggestion> getOrderSuggestions() {
        return orderSuggestions;
    }

    public void setOrderSuggestions(List<OrderSuggestion> orderSuggestions) {
        this.orderSuggestions = orderSuggestions;
    }

    public List<OrderMenu> getOrderMenus() {
        return orderMenus;
    }

    public void setOrderMenus(List<OrderMenu> orderMenus) {
        this.orderMenus = orderMenus;
    }
}
