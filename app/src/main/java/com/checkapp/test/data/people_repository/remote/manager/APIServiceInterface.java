package com.checkapp.test.data.people_repository.remote.manager;


import com.checkapp.test.data.people_repository.remote.models.FilmRest;
import com.checkapp.test.data.people_repository.remote.models.PlanetRest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIServiceInterface {

    @GET("api/people/")
    Single<PeopleResponse> getAllPeople();

    @GET("api/planets/{planet}/")
    Single<PlanetRest> getPlanet(@Path("planet") String planet);

    @GET("api/films/{film}/")
    Single<FilmRest> getFilm(@Path("film") String film);
}
