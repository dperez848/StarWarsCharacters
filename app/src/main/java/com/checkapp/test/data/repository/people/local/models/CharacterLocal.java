package com.checkapp.test.data.repository.people.local.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.checkapp.test.data.entities.Character;
import com.checkapp.test.utils.StringTypeConverters;

import java.util.List;

@Entity(tableName = "CharacterLocal", indices = {@Index(value = "name", unique = true)})
public class CharacterLocal {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "birth_year")
    private String birthYear;

    @ColumnInfo(name = "homeworld")
    private String homeworld;

    @ColumnInfo(name = "films")
    @TypeConverters(StringTypeConverters.class)
    private List<String> films;

    @ColumnInfo(name = "vehicles")
    @TypeConverters(StringTypeConverters.class)
    private List<String> vehicles;

    @ColumnInfo(name = "favorite")
    private boolean favorite;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(@NonNull String birthYear) {
        this.birthYear = birthYear;
    }

    public String getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(String homeworld) {
        this.homeworld = homeworld;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }

    public List<String> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<String> vehicles) {
        this.vehicles = vehicles;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public static CharacterLocal fromCharacterToCharacterLocal(Character Character) {
        CharacterLocal CharacterLocal = new CharacterLocal();
        CharacterLocal.setName(Character.getName());
        CharacterLocal.setBirthYear(Character.getBirthYear());
        CharacterLocal.setFavorite(Character.isFavorite());
        CharacterLocal.setVehicles(Character.getVehicles());
        CharacterLocal.setFilms(Character.getFilms());
        CharacterLocal.setHomeworld(Character.getHomeworld());
        return CharacterLocal;
    }
}
