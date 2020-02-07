package com.project.mooze.Model.Favourites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Favourites {

    @SerializedName("favourites")
    @Expose
    private List<Favourite> favourites = null;

    public List<Favourite> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Favourite> favourites) {
        this.favourites = favourites;
    }

}
