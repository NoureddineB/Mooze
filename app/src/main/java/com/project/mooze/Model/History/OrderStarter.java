package com.project.mooze.Model.History;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.mooze.Model.Order.Starter;

public class OrderStarter {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("starterId")
    @Expose
    private Integer starterId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("starter")
    @Expose
    private Starter starter;

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

    public Integer getStarterId() {
        return starterId;
    }

    public void setStarterId(Integer starterId) {
        this.starterId = starterId;
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

    public Starter getStarter() {
        return starter;
    }

    public void setStarter(Starter starter) {
        this.starter = starter;
    }
}
