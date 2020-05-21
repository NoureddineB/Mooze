package com.project.mooze.Model.History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.mooze.Model.Restaurent.Dessert;

public class OrderDessert {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("dessertId")
    @Expose
    private Integer dessertId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("dessert")
    @Expose
    private Dessert dessert;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDessertId() {
        return dessertId;
    }

    public void setDessertId(Integer dessertId) {
        this.dessertId = dessertId;
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

    public Dessert getDessert() {
        return dessert;
    }

    public void setDessert(Dessert dessert) {
        this.dessert = dessert;
}
}
