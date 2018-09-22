package com.checkapp.test.data.people_repository.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.checkapp.test.utils.StringTypeConverters;

import java.util.List;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 1/23/18.
 */


@Entity(tableName = "PeopleLocal", indices = {@Index(value = "name", unique = true)})
public class PeopleLocal {

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

    public PeopleLocal(@NonNull String name,
                       @NonNull String birthYear, @NonNull String homeworld, @NonNull List<String> films) {
        this.name = name;
        this.birthYear = birthYear;
        this.homeworld = homeworld;
        this.films = films;
    }

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

    public static PeopleLocal fromPeopletoPeopleLocal(People people) {
        return new PeopleLocal(people.getName(), people.getBirthYear(),
                people.getHomeworld(), people.getFilms());
    }
}
