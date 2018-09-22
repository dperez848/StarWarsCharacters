package com.checkapp.test.data.people_repository.remote.manager;

import com.checkapp.test.data.people_repository.remote.models.PeopleRest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleResponse {

    @SerializedName("results")
    @Expose
    private List<PeopleRest> results;

    public List<PeopleRest> getResults() {
        return results;
    }

    public void setResults(List<PeopleRest> results) {
        this.results = results;
    }
}
