package com.checkapp.test.data.people_repository.local;

import com.checkapp.test.data.people_repository.remote.models.PeopleRest;

import java.util.List;

/**
 * @author Daniela Perez danielaperez@kogimobile.com on 1/23/18.
 */


public class People {

    private String name;
    private String birthYear;
    private String homeworld;
    private List<String> films;

    public People(String name, String birthYear, String homeworld, List<String> films) {
        this.name = name;
        this.birthYear = birthYear;
        this.homeworld = homeworld;
        this.films = films;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
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

    public static People fromPeopleLocal(PeopleLocal peopleLocal) {
        return new People(peopleLocal.getName(), peopleLocal.getBirthYear(),
                peopleLocal.getHomeworld(), peopleLocal.getFilms());
    }

    public static People fromPeopleRest(PeopleRest peopleRest) {
        return new People(peopleRest.getName(), peopleRest.getBirthYear(),
                peopleRest.getHomeworld(), peopleRest.getFilms());
    }

    public Double getBirthYearNumbers() {
        if (!getBirthYear().contains("BBY")) {
            return 0.0;
        } else {
            return Double.parseDouble(getBirthYear()
                    .substring(0, getBirthYear().indexOf("BBY")));
        }
    }

    public String getHomeworldId() {
        String var = getHomeworld().substring(0, getHomeworld().length() - 1);
        return var.substring(var.lastIndexOf("/") + 1);
    }

    public static String getFilmIdFromPath(String film) {
        String var = film.substring(0, film.length() - 1);
        return var.substring(var.lastIndexOf("/") + 1);
    }
}
