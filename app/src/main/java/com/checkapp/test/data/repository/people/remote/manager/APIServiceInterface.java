package com.checkapp.test.data.repository.people.remote.manager;



import com.checkapp.test.data.repository.people.remote.models.FilmRest;
import com.checkapp.test.data.repository.people.remote.models.PlanetRest;
import com.checkapp.test.data.repository.people.remote.models.VehicleRest;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIServiceInterface {

    @GET("api/people/")
    Single<CharacterResponse> getAllCharacter();

    @GET("api/planets/{planet}/")
    Single<PlanetRest> getPlanet(@Path("planet") String planet);

    @GET("api/films/{film}/")
    Single<FilmRest> getFilm(@Path("film") String film);

    @GET("api/vehicles/{vehicle}/")
    Single<VehicleRest> getVehicle(@Path("vehicle") String vehicle);
}
