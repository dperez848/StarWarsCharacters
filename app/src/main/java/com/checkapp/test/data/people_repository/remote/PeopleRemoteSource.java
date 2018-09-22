package com.checkapp.test.data.people_repository.remote;

import com.checkapp.test.R;
import com.checkapp.test.app.App;
import com.checkapp.test.data.people_repository.local.People;
import com.checkapp.test.data.people_repository.remote.manager.APIServiceInterface;
import com.checkapp.test.data.people_repository.remote.manager.HttpClientGenerator;
import com.checkapp.test.data.people_repository.remote.manager.PeopleResponse;
import com.checkapp.test.data.people_repository.remote.models.FilmRest;
import com.checkapp.test.data.people_repository.remote.models.PeopleRest;
import com.checkapp.test.data.people_repository.remote.models.PlanetRest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PeopleRemoteSource {

    private static PeopleRemoteSource instance;
    private APIServiceInterface client;

    private PeopleRemoteSource() {
        this.client = HttpClientGenerator.createClient(APIServiceInterface.class, App.getGlobalContext().getString(R.string.base_url));
    }

    public static PeopleRemoteSource getInstance() {
        if (instance == null) {
            instance = new PeopleRemoteSource();
        }
        return instance;
    }

    public Single<List<People>> getAllPeople() {
        return client.getAllPeople()
                .flatMap(new Function<PeopleResponse, Single<List<People>>>() {
                    @Override
                    public Single<List<People>> apply(PeopleResponse peopleResponse) throws Exception {
                        return Single.just(transformPeopleRestToPeople(peopleResponse.getResults()));
                    }
                })
                .flatMapObservable(new Function<List<People>, ObservableSource<People>>() {
                    @Override
                    public ObservableSource<People> apply(List<People> peopleList) throws Exception {
                        return Observable.fromIterable(peopleList);
                    }
                })
                .flatMapSingle(new Function<People, Single<People>>() {
                    @Override
                    public Single<People> apply(People people) throws Exception {
                        return client.getPlanet(people.getHomeworldId())
                                .zipWith(Single.just(people), new BiFunction<PlanetRest, People, People>() {
                                    @Override
                                    public People apply(PlanetRest planetRest, People people) throws Exception {
                                        people.setHomeworld(planetRest.getName());
                                        return people;
                                    }
                                }).zipWith(getFilmsFromPeople(people), new BiFunction<People, List<String>, People>() {
                                    @Override
                                    public People apply(People people, List<String> filmRests) throws Exception {
                                        people.setFilms(filmRests);
                                        return people;
                                    }
                                });
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Single<List<String>> getFilmsFromPeople(People people) {
        return Single.just(people)
                .flatMapObservable(new Function<People, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(People people) throws Exception {
                        return Observable.fromIterable(people.getFilms());
                    }
                })
                .flatMapSingle(new Function<String, Single<FilmRest>>() {
                    @Override
                    public Single<FilmRest> apply(String film) throws Exception {
                        return client.getFilm(People.getFilmIdFromPath(film));
                    }
                })
                .map(new Function<FilmRest, String>() {
                    @Override
                    public String apply(FilmRest filmRest) throws Exception {
                        return filmRest.getTitle();
                    }
                })
                .toList();
    }

    private List<People> transformPeopleRestToPeople(List<PeopleRest> peopleRests) {
        List<People> peopleList = new ArrayList<>();
        for (PeopleRest peopleRest : peopleRests) {
            peopleList.add(People.fromPeopleRest(peopleRest));
        }
        return peopleList;
    }
}
