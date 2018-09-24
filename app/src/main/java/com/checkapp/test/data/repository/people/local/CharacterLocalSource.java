/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.checkapp.test.data.repository.people.local;


import android.support.annotation.NonNull;

import com.checkapp.test.data.entities.Character;
import com.checkapp.test.data.repository.Character.local.CharacterDao;
import com.checkapp.test.data.repository.people.local.models.CharacterLocal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Using the Room database as a data source.
 */
public class CharacterLocalSource {

    private static CharacterLocalSource instance;
    private CharacterDao dao;

    private CharacterLocalSource() {
        this.dao = DataBaseCheckApp.getInstance().CharacterDao();
    }

    public static CharacterLocalSource getInstance() {
        if (instance == null) {
            instance = new CharacterLocalSource();
        }
        return instance;
    }

    public Single<List<Character>> getAllCharacter() {
        return dao.getAllCharacter()
                .flatMap(getMapper())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insertAllCharacter(final List<Character> characterList) {
        Completable
                .fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        dao.insertAll(transformCharactertoCharacterLocal(characterList));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public Completable updateCharacter(final Character character) {
        return Completable
                .fromAction(new Action() {
                    @Override
                    public void run() throws Exception {
                        dao.updateCharacter(character.getName(), character.isFavorite() ? 1 : 0);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private List<CharacterLocal> transformCharactertoCharacterLocal(List<Character> CharacterList) {
        List<CharacterLocal> characterLocalList = new ArrayList<>();
        for (Character characterLocal : CharacterList) {
            characterLocalList.add(CharacterLocal.fromCharacterToCharacterLocal(characterLocal));
        }
        return characterLocalList;
    }

    private List<Character> transformCharacterLocalToCharacter(List<CharacterLocal> CharacterLocalList) {
        List<Character> characterList = new ArrayList<>();
        for (CharacterLocal characterLocal : CharacterLocalList) {
            characterList.add(Character.fromCharacterLocal(characterLocal));
        }
        return characterList;
    }

    @NonNull
    private Function<List<CharacterLocal>, SingleSource<? extends List<Character>>> getMapper() {
        return new Function<List<CharacterLocal>, SingleSource<? extends List<Character>>>() {
            @Override
            public SingleSource<? extends List<Character>> apply(List<CharacterLocal> characterList) throws Exception {
                return Single.just(transformCharacterLocalToCharacter(characterList));
            }
        };
    }
}
