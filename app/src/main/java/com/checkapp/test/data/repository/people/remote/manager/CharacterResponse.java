package com.checkapp.test.data.repository.people.remote.manager;

import com.checkapp.test.data.repository.people.remote.models.CharacterRest;
import com.squareup.moshi.Json;

import java.util.List;

public class CharacterResponse {

    @Json(name ="results")
    private List<CharacterRest> results;

    public List<CharacterRest> getResults() {
        return results;
    }

    public void setResults(List<CharacterRest> results) {
        this.results = results;
    }
}
