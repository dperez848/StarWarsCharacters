package com.checkapp.test.data.repository.people.remote.models;

import com.squareup.moshi.Json;

public class FilmRest {

    @Json(name = "title")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
