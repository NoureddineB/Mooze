package com.project.mooze.Model.Hours;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayOpeningHour {
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("openingDayId")
    @Expose
    private Integer openingDayId;
    @SerializedName("openingHourId")
    @Expose
    private Integer openingHourId;

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

    public Integer getOpeningDayId() {
        return openingDayId;
    }

    public void setOpeningDayId(Integer openingDayId) {
        this.openingDayId = openingDayId;
    }

    public Integer getOpeningHourId() {
        return openingHourId;
    }

    public void setOpeningHourId(Integer openingHourId) {
        this.openingHourId = openingHourId;
    }

}
