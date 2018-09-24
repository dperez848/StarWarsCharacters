package com.checkapp.test.data.repository.people.remote.models;

import com.squareup.moshi.Json;

import java.util.List;

public class CharacterRest {

    @Json(name = "name")
    private String name;
    @Json(name = "birth_year")
    private String birthYear;
    @Json(name = "films")
    private List<String> films;
    @Json(name = "vehicles")
    private List<String> vehicles;
    @Json(name = "homeworld")
    private String homeworld;

    public CharacterRest(String name) {
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

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }
}
