package com.checkapp.test.data.repository.people.remote;

import com.checkapp.test.R;
import com.checkapp.test.app.App;
import com.checkapp.test.data.entities.Character;
import com.checkapp.test.data.repository.people.remote.manager.APIServiceInterface;
import com.checkapp.test.data.repository.people.remote.manager.CharacterResponse;
import com.checkapp.test.data.repository.people.remote.manager.HttpClientGenerator;
import com.checkapp.test.data.repository.people.remote.models.CharacterRest;
import com.checkapp.test.data.repository.people.remote.models.FilmRest;
import com.checkapp.test.data.repository.people.remote.models.PlanetRest;
import com.checkapp.test.data.repository.people.remote.models.VehicleRest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CharacterRemoteSource {

    private static CharacterRemoteSource instance;
    private APIServiceInterface client;

    private CharacterRemoteSource() {
        this.client = HttpClientGenerator.createClient(APIServiceInterface.class, App.getGlobalContext().getString(R.string.base_url));
    }

    public static CharacterRemoteSource getInstance() {
        if (instance == null) {
            instance = new CharacterRemoteSource();
        }
        return instance;
    }

    public Single<List<Character>> getAllCharacter() {
        return client.getAllCharacter()
                .flatMap(new Function<CharacterResponse, Single<List<Character>>>() {
                    @Override
                    public Single<List<Character>> apply(CharacterResponse characterResponse) throws Exception {
                        return Single.just(transformCharacterRestToCharacter(characterResponse.getResults()));
                    }
                })
                .flatMapObservable(new Function<List<Character>, ObservableSource<Character>>() {
                    @Override
                    public ObservableSource<Character> apply(List<Character> characterList) throws Exception {
                        return Observable.fromIterable(characterList);
                    }
                })
                .flatMapSingle(new Function<Character, Single<Character>>() {
                    @Override
                    public Single<Character> apply(Character character) throws Exception {
                        return client.getPlanet(Character.getIdFromPath(character.getHomeworld()))
                                .zipWith(Single.just(character), new BiFunction<PlanetRest, Character, Character>() {
                                    @Override
                                    public Character apply(PlanetRest planetRest, Character character) throws Exception {
                                        character.setHomeworld(planetRest.getName());
                                        return character;
                                    }
                                }).zipWith(getFilmsFromCharacter(character), new BiFunction<Character, List<String>, Character>() {
                                    @Override
                                    public Character apply(Character character, List<String> films) throws Exception {
                                        character.setFilms(films);
                                        return character;
                                    }
                                }).zipWith(getVehiclesFromCharacter(character), new BiFunction<Character, List<String>, Character>() {
                                    @Override
                                    public Character apply(Character character, List<String> vehicles) throws Exception {
                                        character.setVehicles(vehicles);
                                        return character;
                                    }
                                });
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Single<List<String>> getFilmsFromCharacter(Character character) {
        return Single.just(character)
                .flatMapObservable(new Function<Character, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Character character) throws Exception {
                        return Observable.fromIterable(character.getFilms());
                    }
                })
                .flatMapSingle(new Function<String, Single<FilmRest>>() {
                    @Override
                    public Single<FilmRest> apply(String film) throws Exception {
                        return client.getFilm(Character.getIdFromPath(film));
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

    private Single<List<String>> getVehiclesFromCharacter(Character character) {
        return Single.just(character)
                .flatMapObservable(new Function<Character, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Character character) throws Exception {
                        return Observable.fromIterable(character.getVehicles());
                    }
                })
                .flatMapSingle(new Function<String, Single<VehicleRest>>() {
                    @Override
                    public Single<VehicleRest> apply(String vehicle) throws Exception {
                        return client.getVehicle(Character.getIdFromPath(vehicle));
                    }
                })
                .map(new Function<VehicleRest, String>() {
                    @Override
                    public String apply(VehicleRest vehicleRest) throws Exception {
                        return vehicleRest.getName();
                    }
                })
                .toList();
    }

    private List<Character> transformCharacterRestToCharacter(List<CharacterRest> characterRests) {
        List<Character> characterList = new ArrayList<>();
        for (CharacterRest characterRest : characterRests) {
            characterList.add(Character.fromCharacterRest(characterRest));
        }
        return characterList;
    }
}
