package com.checkapp.test.data.entities;

import com.checkapp.test.R;
import com.checkapp.test.app.App;
import com.checkapp.test.data.repository.people.local.models.CharacterLocal;
import com.checkapp.test.data.repository.people.remote.models.CharacterRest;
import com.checkapp.test.utils.StringUtils;

import java.util.List;

public class Character {

    private String name;
    private String birthYear;
    private String homeworld;
    private List<String> films;
    private List<String> vehicles;
    private boolean favorite;

    public Character(String name, String birthYear, String homeworld,
                  List<String> films, List<String> vehicles, boolean favorite) {
        this.name = name;
        this.birthYear = birthYear;
        this.homeworld = homeworld;
        this.films = films;
        this.vehicles = vehicles;
        this.favorite = favorite;
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

    public List<String> getVehicles() {
        return vehicles;
    }

    public String getVehicleText() {
        return getVehicles().isEmpty() ? App.getGlobalContext().getString(R.string.character_empty_vehicles) :
                StringUtils.join(getVehicles(), "\n", "");
    }

    public String getFilmsText() {
        return getFilms().isEmpty() ? App.getGlobalContext().getString(R.string.character_empty_film) :
                StringUtils.join(getFilms(), "\n", "");
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

    public static Character fromCharacterLocal(CharacterLocal CharacterLocal) {
        return new Character(CharacterLocal.getName(), CharacterLocal.getBirthYear(),
                CharacterLocal.getHomeworld(), CharacterLocal.getFilms(),
                CharacterLocal.getVehicles(), CharacterLocal.isFavorite());
    }

    public static Character fromCharacterRest(CharacterRest CharacterRest) {
        return new Character(CharacterRest.getName(), CharacterRest.getBirthYear(),
                CharacterRest.getHomeworld(), CharacterRest.getFilms(), CharacterRest.getVehicles(),
                false);
    }

    public Double getBirthYearNumbers() {
        String birth = "BBY";
        if (!getBirthYear().contains(birth)) {
            return 0.0;
        } else {
            return Double.parseDouble(getBirthYear()
                    .substring(0, getBirthYear().indexOf(birth)));
        }
    }

    public static String getIdFromPath(String path) {
        String var = path.substring(0, path.length() - 1);
        return var.substring(var.lastIndexOf("/") + 1);
    }
}
