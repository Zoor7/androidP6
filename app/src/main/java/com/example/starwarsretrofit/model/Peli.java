package com.example.starwarsretrofit.model;

import com.google.gson.annotations.SerializedName;

public class Peli {

    private String title;
    @SerializedName("opening_crawl")
    private String resum;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResum(String resum) {
        this.resum = resum;
    }

    public String getTitle() {
        return title;
    }

    public String getResum() {
        return resum;
    }
}
