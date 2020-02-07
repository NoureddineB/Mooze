package com.project.mooze.Model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurent {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("discountId")
    @Expose
    private Integer discountId;
    @SerializedName("restaurantTables")
    @Expose
    private List<Object> restaurantTables = null;
    @SerializedName("restaurantStarters")
    @Expose
    private List<Object> restaurantStarters = null;
    @SerializedName("drinks")
    @Expose
    private List<Object> drinks = null;
    @SerializedName("desserts")
    @Expose
    private List<Object> desserts = null;
    @SerializedName("mains")
    @Expose
    private List<Object> mains = null;
    @SerializedName("menus")
    @Expose
    private List<Object> menus = null;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }



    public List<Object> getRestaurantTables() {
        return restaurantTables;
    }

    public void setRestaurantTables(List<Object> restaurantTables) {
        this.restaurantTables = restaurantTables;
    }

    public List<Object> getRestaurantStarters() {
        return restaurantStarters;
    }

    public void setRestaurantStarters(List<Object> restaurantStarters) {
        this.restaurantStarters = restaurantStarters;
    }

    public List<Object> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Object> drinks) {
        this.drinks = drinks;
    }

    public List<Object> getDesserts() {
        return desserts;
    }

    public void setDesserts(List<Object> desserts) {
        this.desserts = desserts;
    }

    public List<Object> getMains() {
        return mains;
    }

    public void setMains(List<Object> mains) {
        this.mains = mains;
    }

    public List<Object> getMenus() {
        return menus;
    }

    public void setMenus(List<Object> menus) {
        this.menus = menus;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}