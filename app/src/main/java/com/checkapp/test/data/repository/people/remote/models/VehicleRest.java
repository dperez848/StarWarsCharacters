package com.checkapp.test.data.repository.people.remote.models;

import com.squareup.moshi.Json;

public class VehicleRest {

    @Json(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
