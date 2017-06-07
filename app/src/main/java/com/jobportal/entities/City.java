package com.jobportal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pita on 6/7/2017.
 */
public class City {

    @SerializedName("all_cities")
    @Expose
    private List<String> allCities = null;

    public List<String> getAllCities() {
        return allCities;
    }

    public void setAllCities(List<String> allCities) {
        this.allCities = allCities;
    }


}
