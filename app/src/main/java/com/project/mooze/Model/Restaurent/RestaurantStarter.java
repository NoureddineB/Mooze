package com.project.mooze.Model.Restaurent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantStarter {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("menuId")
    @Expose
    private Object menuId;
    @SerializedName("orderId")
    @Expose
    private Object orderId;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;
    @SerializedName("toppings")
    @Expose
    private List<Topping> toppings = null;
    @SerializedName("sauces")
    @Expose
    private List<Sauce> sauces = null;
    @SerializedName("starterMenus")
    @Expose
    private List<Object> starterMenus = null;
    @SerializedName("starterRestaurants")
    @Expose
    private List<StarterRestaurant> starterRestaurants = null;




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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Object getMenuId() {
        return menuId;
    }

    public void setMenuId(Object menuId) {
        this.menuId = menuId;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public List<Sauce> getSauces() {
        return sauces;
    }

    public void setSauces(List<Sauce> sauces) {
        this.sauces = sauces;
    }

    public List<Object> getStarterMenus() {
        return starterMenus;
    }

    public void setStarterMenus(List<Object> starterMenus) {
        this.starterMenus = starterMenus;
    }

    public List<StarterRestaurant> getStarterRestaurants() {
        return starterRestaurants;
    }

    public void setStarterRestaurants(List<StarterRestaurant> starterRestaurants) {
        this.starterRestaurants = starterRestaurants;
    }
}
