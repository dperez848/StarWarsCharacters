package com.checkapp.test.app.ui.detail.mvp;

import com.checkapp.test.base.BasePresenter;
import com.checkapp.test.data.entities.Character;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;


public class PresenterFrgDetail<V extends DetailViewListener> extends BasePresenter<V>
        implements DetailPresenterListener<V> {

    private JsonAdapter<Character> CharacterAdapter =
            new Moshi.Builder().build().adapter(Character.class);
    private Character Character;

    public void setArguments(String CharacterInfo) {
        try {
            this.Character = CharacterAdapter.fromJson(CharacterInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Character getCharacter() {
        return Character;
    }

    public void doSetCharacterInfo() {
        getMvpView().onCharacterInfo(Character);
    }
}