package com.jobportal.entities;

/**
 * Created by pita on 6/6/2017.
 */
public class MyAccount {
    private String add_posted;
    private String add_responded;
    private String active_since;

    public String getAdd_posted() {
        return add_posted;
    }

    public void setAdd_posted(String add_posted) {
        this.add_posted = add_posted;
    }

    public String getAdd_responded() {
        return add_responded;
    }

    public void setAdd_responded(String add_responded) {
        this.add_responded = add_responded;
    }

    public String getActive_since() {
        return active_since;
    }

    public void setActive_since(String active_since) {
        this.active_since = active_since;
    }
}
