
package com.jobportal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllJob {

    @SerializedName("job_categories")
    @Expose
    private ArrayList<String> jobCategories = null;
    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<String> getJobCategories() {
        return jobCategories;
    }

    public void setJobCategories(ArrayList<String> jobCategories) {
        this.jobCategories = jobCategories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
