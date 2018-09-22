package com.checkapp.test.data.people_repository.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleRest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("birth_year")
    @Expose
    private String birthYear;

    @SerializedName("films")
    @Expose
    private List<String> films;
    @SerializedName("homeworld")
    @Expose
    private String homeworld;

    public PeopleRest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }
}
