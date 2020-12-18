package com.example.starwarsretrofit.model;

import com.google.gson.annotations.SerializedName;

public class Personaje {
    private String name;
    private String height;
    @SerializedName("hair_color")
    private String hairColor;
    private String[] films;

    public String[] getFilms() {
        return films;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }
}
