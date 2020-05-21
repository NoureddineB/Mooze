package com.project.mooze.Model.Hours;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClosingHour {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("hour")
    @Expose
    private String hour;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("dayClosingHour")
    @Expose
    private DayClosingHour dayClosingHour;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
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

    public DayClosingHour getDayClosingHour() {
        return dayClosingHour;
    }

    public void setDayClosingHour(DayClosingHour dayClosingHour) {
        this.dayClosingHour = dayClosingHour;
    }
}
